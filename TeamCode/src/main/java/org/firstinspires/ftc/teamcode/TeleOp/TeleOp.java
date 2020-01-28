package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.concurrent.TimeUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="teleop")
//@Disabled
public class TeleOp extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);
        int lpos = 0, apos = 0, liftHeight = 0;
        double xAxis = 0, yAxis = 0, zAxis = 0;
        double lfPower = 0, rfPower = 0, lbPower = 0, rbPower = 0, strafePower = 0, armPower = 0, liftPower = 0;
        boolean lControl = true, aControl = true, foundation = false;
        double lTime = 0, aTime = 0, fTime = 0, htime = 0, toggleTime = 0, deployTime = 0, min = 0, max = 0, pow = 0;

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;
        boolean goArm = false;
        boolean changeMode = true;
        boolean goLift = false;
        boolean strafing = false;
        boolean leave = false;
        resetEncoders();
        resetArm();

        //Set up a timer for half speed
        ElapsedTime time = new ElapsedTime();
        resetTime();

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            //GAMEPAD 1
            //Holonomic drive inputs
            if (Math.abs(gamepad1.left_stick_y) > 0.05) {
                yAxis = gamepad1.left_stick_y;
            } else {
                yAxis = 0;
            }
            if (Math.abs(gamepad1.left_stick_x) > 0.05) {
                xAxis = -gamepad1.left_stick_x;
            } else {
                xAxis = 0;
            }
            if (Math.abs(gamepad1.right_stick_x) > 0.05) {
                zAxis = -gamepad1.right_stick_x;
            } else {
                zAxis = 0;
            }

            //Calculating power for each motor
            // Real holonomic
            lfPower = xAxis - yAxis - zAxis;
            rfPower = xAxis - yAxis + zAxis;
            lbPower = -xAxis - yAxis - zAxis;
            rbPower = -xAxis - yAxis + zAxis;

            /*lfPower = xAxis - yAxis - zAxis;
            rfPower = -xAxis - yAxis + zAxis;
            lbPower = -xAxis - yAxis - zAxis;
            rbPower = xAxis - yAxis + zAxis;*/

            //Checking if halfspeed is toggled
            if (gamepad1.x && htime < time.milliseconds() - 250) {
                halfSpeed = !halfSpeed;
                htime = time.milliseconds();
            }

            //Halfspeed controls
            if (halfSpeed && !strafing) {
                lfPower /= 2;
                rfPower /= 2;
                lbPower /= 2;
                rbPower /= 2;
                LF.setPower(Range.clip(lfPower, -.5, .5));
                RF.setPower(Range.clip(rfPower, -.5, .5));
                LB.setPower(Range.clip(lbPower, -.5, .5));
                RB.setPower(Range.clip(rbPower, -.5, .5));
            }

            //Normal controls
            else if (!strafing){
                LF.setPower(Range.clip(lfPower, -1, 1));
                RF.setPower(Range.clip(rfPower, -1, 1));
                LB.setPower(Range.clip(lbPower, -1, 1));
                RB.setPower(Range.clip(rbPower, -1, 1));
            }

            //Auto-strafe controls
            //strafe right
            if (gamepad1.right_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.right_trigger * 0.75;
                setStrafePowers(strafePower, true);
            }

            //strafe left
            else if (gamepad1.left_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.left_trigger * 0.75;
                setStrafePowers(strafePower, false);
            }

            //stop strafing

            else {
                strafing = false;
            }

            //Hook Controls
            if (gamepad1.right_bumper && fTime < time.milliseconds() - 250) {
                foundation = !foundation;
                fTime = time.milliseconds();
                foundationD(foundation);
            }

            //GAMEPAD 2
            //Intake controls
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
                min = -1;
                max = 1;
                pow = -gamepad2.right_stick_y;
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
            if (Math.abs(gamepad2.right_stick_y) > 0.05){
                if(!changeMode){
                    arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    arm.setPower(armPower);
                }
                changeMode = true;

            }
            if (gamepad2.b && toggleTime < time.milliseconds() - 250)
            {
                changeMode = false;

                toggleTime = time.milliseconds();

                deployTime = time.milliseconds();

                goArm = true;


            }

            //DEPLOY ARM
            if (goArm && !changeMode) //if b is pressed and manuel movement is not being used (changeMode)
            {
                if(arm.getCurrentPosition() < -100){
                    leave = true;
                }else{
                    leave = false;
                }

                if (!leave)
                { //if not inside robot
                    arm.setTargetPosition(0); //-830 for horizontal
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    arm.setPower(0.8);
                    if(lift.getCurrentPosition() < 0){ //if lift is not in robot
                        lift.setTargetPosition(0);
                        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        lift.setPower(0.8);
                    }
                }

                else if(leave){ //if inside robot

                    arm.setTargetPosition(-540); //-830 for horizontal
                    arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    if(deployTime > time.milliseconds() - 1000) //expel block for 1 second
                    {
                        intakeL.setPower(1);
                        intakeR.setPower(-1);
                    }
                    else{
                        intakeL.setPower(0);
                        intakeR.setPower(0);
                    }

                    arm.setPower(0.8);

                }
            }


            //Lift Controls
            //Manuel
            if(!goLift && changeMode) {
                if (gamepad2.right_trigger > 0.05) {
                    lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    liftPower = Range.clip(gamepad2.right_trigger, 0, 0.7);  //LIFT UP
                } else if (gamepad2.left_trigger > 0.05) {
                    liftPower = -Range.clip(gamepad2.left_trigger, 0, 0.7);  //LIFT DOWN
                } else {
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
            }*/

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
            }

            if (gamepad2.right_trigger > 0.05) //stop auto lift
            {
                goLift = false;
                changeMode = true;
            }


            telemetry.addData("Lift Position", lift.getCurrentPosition());
            telemetry.addData("Arm Position", arm.getCurrentPosition());
            telemetry.addData("Halfspeed", halfSpeed);
            telemetry.addData("Arm Speed", armPower);
            telemetry.addData("Mode change", changeMode);
            telemetry.addData("Lift Height", liftHeight);
            telemetry.addData("strafing: ", strafing);
            telemetry.update();



        }
    }
}
