package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.concurrent.TimeUnit;

@TeleOp(name="testu", group="teleop")
//@Disabled
public class testla extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);
        int lpos = 0, apos = 0, liftHeight = 0, lowestArm = 0;
        double xAxis = 0, yAxis = 0, zAxis = 0, currHeading = 0, tHeading= 0, hError = 0, correction = 0, zeroAng = 0;
        double strafePower = 0, armPower = 0, liftPower = 0, strafeTog = 0;
        boolean lControl = true, aControl = true, foundation = false, strafeD = true, rHook = false, lHook = false, stopTurn = false, robotOriented = true;
        double lTime = 0, rTime = 0, fTime = 0, htime = 0, dtime = 0, deployTime = 0, min = 0, max = 0, pow = 0, turnTime = 0;
        double[] motorP;
        motorP = new double[4];

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;
        boolean goArm = false;
        boolean actuallyGo = false;
        boolean changeMode = true;
        boolean goLift = false;
        boolean strafing = false;
        resetEncoders();
        resetArm();

        //Set up a timer for half speed
        ElapsedTime time = new ElapsedTime();
        resetTime();

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            /**
             * GAMEPAD 1
             *
             * Controls:
             * Right Joystick - Robot Movement (Arcade Drive)
             * Left Joystick - Robot Orientation
             * Right Trigger - Right Strafe , Left Trigger - Left Strafe
             * Right Bumper - Right Hook , Left Bumper - Left Hook
             * X - Half Speed , B - Foundation Hooks
             * Y - Switch Drive Modes (Robot Oriented or Field Oriented)
             * A - Reset Gyro
             *
             */

            //GET CURRENT HEADING
            currHeading = get180Yaw();

            //RESET GYRO
            if (gamepad1.a) {
                zeroAng = getYaw();
                zeroAng += 180;
                if(zeroAng > 360){
                    zeroAng -= 360;
                }
            }

            //CONTROLLER INPUTS AND ROBOT HEADINGS
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
                zAxis = -gamepad1.right_stick_x*3/4;
                tHeading = get180Yaw();
                turnTime = time.milliseconds();
            }else if(turnTime > time.milliseconds() - 1000){ //Keep getting target angle for another second to account for drift
                stopTurn = true;
                zAxis = 0;
            }
            else {
                zAxis = 0;
                stopTurn = false;
            }
            if(stopTurn){ //1 second after stopping turning or start
                tHeading = get180Yaw();
                if(Math.abs(gamepad1.left_stick_x) > 0.05 && Math.abs(gamepad1.left_stick_y) > 0.05){ //not trying to move
                    stopTurn = false;
                }
            }

            //DRIVE MODE TOGGLE
            if (gamepad1.y && dtime < time.milliseconds() - 250) {
                robotOriented = !robotOriented;
                dtime = time.milliseconds();
            }

            //DRIFT CORRECTION
            if(Math.abs(gamepad1.left_stick_x) > 0.05 || Math.abs(gamepad1.left_stick_y) > 0.05){
                correction = getCorrection(currHeading, tHeading);
            }else{
                correction = 0;
            }

            //AUTO STRAFE
            //STRAFE RIGHT (FIELD ORIENTED)
            if (gamepad1.right_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.right_trigger * 0.75;
                motorP = strafeField(0, strafePower, correction, zeroAng);
            }

            //STRAFE LEFT (FIELD ORIENTED)
            else if (gamepad1.left_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.left_trigger * 0.75;
                motorP = strafeField(Math.PI, strafePower, correction, zeroAng);
            }

            //STOP AUTO STRAFE
            else {
                strafing = false;
            }

            //DRIVE MODES
            if(!strafing){
                //ROBOT ORIENTED HOLONOMIC
                if(robotOriented){
                    motorP = holonomicDrive(xAxis, yAxis, zAxis, Range.clip(correction, -0.5, 0.5));
                }
                //FIELD ORIENTED HOLONOMIC
                else{
                    motorP = fieldOriented(xAxis, yAxis, zAxis, correction, zeroAng);
                }
            }


            //HALFSPEED TOGGLE
            if (gamepad1.x && htime < time.milliseconds() - 250) {
                halfSpeed = !halfSpeed;
                htime = time.milliseconds();
            }

            //HALFSPEED
            if (halfSpeed) {
                motorP[0] /= 2;
                motorP[1] /= 2;
                motorP[2] /= 2;
                motorP[3] /= 2;
                LF.setPower(Range.clip(motorP[0], -.5, .5));
                RF.setPower(Range.clip(motorP[1], -.5, .5));
                LB.setPower(Range.clip(motorP[2], -.5, .5));
                RB.setPower(Range.clip(motorP[3], -.5, .5));
            }

            //NORMAL
            else{
                LF.setPower(Range.clip(motorP[0], -1, 1));
                RF.setPower(Range.clip(motorP[1], -1, 1));
                LB.setPower(Range.clip(motorP[2], -1, 1));
                RB.setPower(Range.clip(motorP[3], -1, 1));
            }

            //TELEMETRY
            telemetry.addData("Drive Mode (Y): ", robotOriented ? "Robot Oriented" : "Field Oriented");
            telemetry.update();
        }
    }
}
