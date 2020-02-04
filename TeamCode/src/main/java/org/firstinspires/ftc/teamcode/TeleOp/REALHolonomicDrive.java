package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.concurrent.TimeUnit;

@TeleOp(name="REALHolonomicDrive", group="teleop")
@Disabled
public class REALHolonomicDrive extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);
        int lpos = 0, apos = 0, lowestArm = 0;
        double halfspeedValue, strafePower, min, max, pow;
        double leftX, leftY, rightX; //Controller inputs
        double[] motorPower; //Motor powers
        double armPower = 0.0, liftPower = 0.0; //Manipulator powers
        boolean foundation = false, strafing = false, strafeD = false, changeMode = false, goArm = false, actuallyGo = false, goLift = false;
        double halfspeedTime = 0.0, strafeTog = 0.0, fTime = 0.0, deployTime = 0.0; //Toggle times

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;
        resetEncoders();
        resetArm();

        //Set up a timer for half speed
        ElapsedTime time = new ElapsedTime();
        resetTime();

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {


            //
            //#GAMEPAD1
            //


            //HALFSPEED (X BUTTON)
            if (gamepad1.x && halfspeedTime < time.milliseconds() - 250) {
                halfSpeed = !halfSpeed;
                halfspeedTime = time.milliseconds();
            }

            if(halfSpeed){
                halfspeedValue = 0.5;
            }
            else {
                halfspeedValue = 1;
            }

            //HOLONOMIC DRIVE (LEFT JOYSTICK LATERAL, RIGHT JOYSTICK TURN)
            if(Math.abs(gamepad1.left_stick_x) > 0.05){
                rightX = gamepad1.left_stick_x;
            }else{
                rightX = 0.0;
            }

            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                leftY = -gamepad1.left_stick_y;
            }else{
                leftY = 0.0;
            }

            if(Math.abs(gamepad1.right_stick_x) > 0.05){
                leftX = -gamepad1.right_stick_x;
            }else{
                leftX = 0.0;
            }

            motorPower = holonomicPower(leftX * halfspeedValue, leftY * halfspeedValue, rightX * halfspeedValue);

            if(!strafing) {
                LF.setPower(motorPower[0]);
                RF.setPower(motorPower[1]);
                LB.setPower(motorPower[2]);
                RB.setPower(motorPower[3]);
            }
            //STRAFE BUTTONS (BUMPERS)
            //SWITCH STRAFE BUTTONS
            if(gamepad1.y && strafeTog + 250 < time.milliseconds())
            {
                strafeD = !strafeD;
                strafeTog = time.milliseconds();
            }

            //STRAFE RIGHT
            if (gamepad1.right_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.right_trigger * halfspeedValue * 0.75;
                setStrafePowers(strafePower, !strafeD);
            }

            //STRAFE LEFT
            else if (gamepad1.left_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.left_trigger * halfspeedValue * 0.75;
                setStrafePowers(strafePower, strafeD);
            }

            //STOP STRAFING
            else{
                strafing = false;
            }

            //FOUNDATION HOOKS
            if (gamepad1.right_bumper && fTime < time.milliseconds() - 250) {
                foundation = !foundation;
                fTime = time.milliseconds();
                foundationD(foundation);
            }


            //
            //GAMEPAD 2
            //


            //INTAKE CONTROLS

            //intake
            if (gamepad2.right_bumper) {
                intakeL.setPower(-1);
                intakeR.setPower(-1);
            }
            //expel
            else if (gamepad2.left_bumper) {
                intakeL.setPower(1);
                intakeR.setPower(1);
            }
            //unstick block
            else if (gamepad2.a) {
                intakeL.setPower(1);
                intakeR.setPower(-1);
            }
            //idle
            else if (changeMode){
                intakeL.setPower(0);
                intakeR.setPower(0);
            }

            //Arm Module Controls
            //Claw Controls
            if (gamepad2.x) {
                claw.setPosition(0);
            }
            if (gamepad2.y) {
                claw.setPosition(1);
            }

            //Arm Controls
            //Manuel
            if (Math.abs(gamepad2.right_stick_y) > 0.05) {
                arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                min = -1;
                max = 1;
                pow = -gamepad2.right_stick_y;
                if(arm.getCurrentPosition() > lowestArm){//technically higher because its weird
                    lowestArm = arm.getCurrentPosition();
                }
                if(gamepad2.right_stick_y > -0.6 && gamepad2.right_stick_y < 0.6)
                {
                    pow /= 2;
                }
                if (gamepad2.right_stick_y > 0.05 && arm.getCurrentPosition() > 100)
                {
                    max = 0.2;
                }
                if (gamepad2.right_stick_y < -0.05 && arm.getCurrentPosition() < 120)
                {
                    min = -0.2;
                }
                armPower = Range.clip(pow, min, max);
                arm.setPower(armPower);
            } else if(changeMode){
                arm.setPower(0);
            }
            //Automatic
            //Arm (automatic to manuel) Toggle
            if (Math.abs(gamepad2.right_stick_y) > 0.05){ //go back to manuel

                changeMode = true;

            }
            if (gamepad2.b) //go to up position or back inside the robot
            {
                changeMode = false;

                deployTime = time.milliseconds(); //for compliant unstick later

                if(arm.getCurrentPosition() > -100 ){ //if arm inside the robot
                    goArm = true;
                } else { //else it's outside
                    goArm = false;
                }
            }

            //DEPLOY ARM
            if (goArm && !changeMode) //if inside (goArm) and manuel movement is not being used (changeMode)
            {
                if(deployTime > time.milliseconds() - 1000){ //1 second to clamp
                    claw.setPosition(0); //clamp down
                    actuallyGo = false;
                }
                else if(deployTime > time.milliseconds() - 2000) //expel block for 1 second
                {
                    actuallyGo = true;
                    intakeL.setPower(1);
                    intakeR.setPower(-1);
                }
                else{
                    intakeL.setPower(0);
                    intakeR.setPower(0);
                }

                if(actuallyGo){
                    arm.setTargetPosition(lowestArm - 540); //-540 for up, -830 for horizontal
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(1);
                }

            }

            if(!goArm && !changeMode) //if outside of robot (goArm)
            {
                //move lift down
                lift.setTargetPosition(0);
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lift.setPower(1);

                if(lift.getCurrentPosition() > -100)
                {
                    arm.setTargetPosition(lowestArm);
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(1);
                }
            }


            //Lift Controls
            //Manuel
            if(!goLift && changeMode) {
                if (gamepad2.right_trigger > 0.05) {
                    lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    liftPower = Range.clip(gamepad2.right_trigger, 0, 0.7);  //LIFT DOWN
                } else if (gamepad2.left_trigger > 0.05) {
                    liftPower = -Range.clip(gamepad2.left_trigger, 0, 0.7);  //LIFT UP
                } else {
                    liftPower = 0;
                }

                if (lift.getCurrentPosition() < -4600 && gamepad2.left_trigger > 0.05){
                    liftPower = 0;
                } else if (lift.getCurrentPosition() > 0 && gamepad2.right_trigger > 0.05){
                    liftPower = 0;
                }

                lift.setPower(liftPower);
            }
            //Automatic...
            //double y = blockHieght * x + first hieght (if not equal to blockHieght)
            /*if (gamepad2.dpad_up) {
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                double currPos = lift.getCurrentPosition();//get current position
                int tarPos = ((int)currPos/10) + 1; //find tens place and add 1 to it -----------------------------Fix values so it adjusts to the right increments (levels of skyscraper) in inches (encoders to inches)
                lift.setPower(0.5);
                lift.setTargetPosition(tarPos * 10); //Make lift go to position
            }
            if (gamepad2.dpad_down) {
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                double currPos = lift.getCurrentPosition();//get current position
                int tarPos = ((int)currPos/10) - 1; //find tens place and add 1 to it -----------------------------Fix values so it adjusts to the right increments (levels of skyscraper) in inches (encoders to inches)
                lift.setPower(0.5);
                lift.setTargetPosition(tarPos * 10); //Make lift go to position
            }

            //SAVE LIFT HEIGHT
            if (gamepad2.dpad_left)
            {
                liftHeight = lift.getCurrentPosition(); //saves the current height of lift
            }

            if (gamepad2.dpad_right) //Lift to certain position
            {
                goLift = true; //start lift
            }

            if(goLift)
            {
                lift.setTargetPosition(liftHeight - 1000); //Last recorded block height + one block up
                lift.setMode(DcMotor.RunMode.RUN_TO_POSITION); //set mode for target position
                lift.setPower(0.8);
                if(!lift.isBusy() || Math.abs(liftHeight) - Math.abs(lift.getCurrentPosition()) < 5 ){ //if not busy or lift height is close to target
                    liftHeight = lift.getCurrentPosition(); //save position for next time
                    lift.setPower(0);
                    goLift = false;
                }
            }*/

            if (gamepad2.right_trigger > 0.05 || gamepad2.left_trigger > 0.05) //stop auto lift
            {
                goLift = false;
                changeMode = true;
            }


            //TELEMETRY
            telemetry.addData("Motor powers: ", motorPower);
            telemetry.update();

        }
    }
}
