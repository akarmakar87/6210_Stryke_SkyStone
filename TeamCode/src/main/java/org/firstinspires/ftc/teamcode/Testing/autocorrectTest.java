package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="teleop")
//@Disabled
public class autocorrectTest extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);
        int lpos = 0, apos = 0, liftHeight = 0, lowestArm = 0;
        double xAxis = 0, yAxis = 0, zAxis = 0, currHeading = 0, tHeading= 0, hError = 0;
        double lfPower = 0, rfPower = 0, lbPower = 0, rbPower = 0, strafePower = 0, armPower = 0, liftPower = 0, strafeTog = 0, oldTarget = 0;
        boolean lControl = true, aControl = true, foundation = false, strafeD = true, rHook = false, lHook = false, stopTurn = false, start = false;
        double lTime = 0, rTime = 0, fTime = 0, htime = 0, toggleTime = 0, deployTime = 0, min = 0, max = 0, pow = 0, turnTime = 0, prevTime = 0, prevError = 0;

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
            //GAMEPAD 1
            //Holonomic drive inputs

            currHeading = getYaw(); //Get current heading
            oldTarget = tHeading;
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
                tHeading = getYaw();
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
                tHeading = getYaw();
                if(Math.abs(gamepad1.left_stick_x) > 0.05 && Math.abs(gamepad1.left_stick_y) > 0.05){ //not trying to move
                    stopTurn = false;
                }
            }

            if(tHeading > 180){ //make it so that if it's over 180 degrees just make it the negatives so 210 = -150
                tHeading -= 360;
            }
            if(currHeading > 180){
                currHeading -= 360;
            }


            if((oldTarget - tHeading) == 0){ //when stop changing target heading
                start = true;
            }
            if(start){ //initial time and error for PID
                prevTime = time.milliseconds();
                prevError = tHeading - currHeading;
                start = false;
            }

            double correction[] = getCorrectionPID(currHeading, tHeading, 0.004, 0, 0, time.milliseconds(), prevTime, prevError);

            prevError = correction[1]; //feed PID loop past error
            prevTime = correction[2]; //feed PID loop past time

            //Calculating power for each motor
            // Real holonomic
            lfPower = xAxis - yAxis - zAxis;
            rfPower = xAxis - yAxis + zAxis;
            lbPower = -xAxis - yAxis - zAxis;
            rbPower = -xAxis - yAxis + zAxis;

            double[] motorP = scalePower(lfPower, rfPower, lbPower, rbPower, correction[0]);

            //Checking if halfspeed is toggled
            if (gamepad1.x && htime < time.milliseconds() - 250) {
                halfSpeed = !halfSpeed;
                htime = time.milliseconds();
            }

            //Halfspeed controls
            if (halfSpeed && !strafing) {
                motorP[0] /= 2;
                motorP[1] /= 2;
                motorP[2] /= 2;
                motorP[3] /= 2;
                LF.setPower(Range.clip(motorP[0], -.5, .5));
                RF.setPower(Range.clip(motorP[1], -.5, .5));
                LB.setPower(Range.clip(motorP[2], -.5, .5));
                RB.setPower(Range.clip(motorP[3], -.5, .5));
            }

            //Normal controls
            else if (!strafing){
                LF.setPower(Range.clip(motorP[0], -1, 1));
                RF.setPower(Range.clip(motorP[1], -1, 1));
                LB.setPower(Range.clip(motorP[2], -1, 1));
                RB.setPower(Range.clip(motorP[3], -1, 1));
            }

            //Auto-strafe controls
            //strafe right
            if(gamepad1.y && strafeTog + 250 < time.milliseconds())
            {
                strafeD = !strafeD;
                strafeTog = time.milliseconds();
            }
            if (gamepad1.right_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.right_trigger * 0.75;
                if(halfSpeed)
                {
                    strafePower /= 2;
                }
                setStrafePowers(strafePower, strafeD);
            }

            //strafe left
            else if (gamepad1.left_trigger > 0.05) {
                strafing = true;
                strafePower = gamepad1.left_trigger * 0.75;
                if(halfSpeed)
                {
                    strafePower /= 2;
                }
                setStrafePowers(strafePower, !strafeD);
            }

            //stop strafing

            else {
                strafing = false;
            }


            telemetry.update();
        }
    }
}
