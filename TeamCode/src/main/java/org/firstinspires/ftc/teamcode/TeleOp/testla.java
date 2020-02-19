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
        double currHeading = 0, tHeading = 0, correction = 0, zeroAng = 0;
        double strafePower, xAxis, yAxis, zAxis;
        boolean robotOriented = true;
        boolean htime = false, dtime = false;
        double[] motorP;
        motorP = new double[4];
        boolean getTarget = false;

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

            currHeading = get180Yaw();

            if(getTarget){
                tHeading = get180Yaw();
            }
            if(!gamepad1.x){
                htime = true;
            }

            if(!gamepad1.y){
                dtime = true;
            }

            //RESET GYRO
            if (gamepad1.a) {
                zeroAng = getYaw();
                zeroAng += 180;
                if(zeroAng > 360){
                    zeroAng -= 360;
                }
            }

            //HALFSPEED TOGGLE
            if (gamepad1.x && htime) {
                htime = false;
                halfSpeed = !halfSpeed;
            }

            //DRIVE MODE TOGGLE
            if (gamepad1.y && dtime) {
                dtime = false;
                robotOriented = !robotOriented;
            }

            //CONTROLLER INPUTS
            yAxis = gamepad1.left_stick_y;
            xAxis = -gamepad1.left_stick_x;
            zAxis = -gamepad1.right_stick_x * 0.75;

            if(Math.abs(gamepad1.left_stick_x) > .05 ||
                    Math.abs(gamepad1.left_stick_y) > .05 ||
                    Math.abs(gamepad1.right_stick_x) > .05)
            {

                //ROBOT ORIENTED HOLONOMIC
                if(robotOriented){
                    motorP = holonomicDrive(xAxis, yAxis, zAxis, 0);
                }
                //FIELD ORIENTED HOLONOMIC
                else{
                    motorP = fieldOriented(xAxis, yAxis, zAxis, 0, zeroAng);
                }

                //RIGHT AUTO STRAFE
            }else if (gamepad1.right_trigger > 0.05) {
                getTarget = false;
                correction = getCorrection(currHeading, tHeading);
                strafePower = gamepad1.right_trigger * 0.75;
                strafeCorrection(strafePower, correction, true);

                //LEFT AUTO STRAFE
            }else if (gamepad1.left_trigger > 0.05) {
                getTarget = false;
                correction = getCorrection(currHeading, tHeading);
                strafePower = gamepad1.left_trigger * 0.75;
                strafeCorrection(strafePower, correction, false);

            }else {
                getTarget = true;
                motorP[0] = 0;
                motorP[1] = 0;
                motorP[2] = 0;
                motorP[3] = 0;
            }

            setEachPower(motorP[0], motorP[1], motorP[2], motorP[3], halfSpeed);

            //TELEMETRY
            telemetry.addData("Drive Mode (Y): ", robotOriented ? "Robot Oriented" : "Field Oriented");
            telemetry.addData("Half Speed (X): ", halfSpeed);
            telemetry.update();
        }
    }
}
