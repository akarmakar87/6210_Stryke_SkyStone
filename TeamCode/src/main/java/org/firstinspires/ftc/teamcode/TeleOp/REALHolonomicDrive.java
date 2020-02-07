package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.concurrent.TimeUnit;

@TeleOp(name="REALHolonomicDrive", group="teleop")
//@Disabled
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
                leftX = gamepad1.left_stick_x;
            }else{
                leftX = 0.0;
            }

            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                leftY = -gamepad1.left_stick_y;
            }else{
                leftY = 0.0;
            }

            if(Math.abs(gamepad1.right_stick_x) > 0.05){
                rightX = -gamepad1.right_stick_x;
            }else{
                rightX = 0.0;
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




            //TELEMETRY
            telemetry.addData("LF: ", motorPower[0]);
            telemetry.addData("RF: ", motorPower[1]);
            telemetry.addData("LB: ", motorPower[2]);
            telemetry.addData("RB: ", motorPower[3]);

            telemetry.update();

        }
    }
}
