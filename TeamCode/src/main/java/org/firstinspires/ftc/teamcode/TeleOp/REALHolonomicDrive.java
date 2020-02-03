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
        int lpos = 0, apos = 0;
        double halfspeedValue = 0;
        double leftX, leftY, rightX = 0;
        double[] motorPower = {0.0, 0.0, 0.0, 0.0};
        long htime = 0;
        boolean lControl = true, aControl = true, foundation = false;
        double halfspeedTime = 0;

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

            if(Math.abs(gamepad1.left_stick_x) > 0.05){
                leftX = gamepad1.left_stick_x;
            }else{
                leftX = 0.0;
            }

            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                leftY = gamepad1.left_stick_y;
            }else{
                leftY = 0.0;
            }

            if(Math.abs(gamepad1.right_stick_x) > 0.05){
                rightX = gamepad1.right_stick_x;
            }else{
                rightX = 0.0;
            }

            motorPower = holonomicPower(leftX * halfspeedValue, leftY * halfspeedValue, rightX * halfspeedValue);

            LF.setPower(motorPower[0]);
            RF.setPower(motorPower[1]);
            LB.setPower(motorPower[2]);
            RB.setPower(motorPower[3]);

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

            telemetry.addData("Left Front Power: ", LF);
            telemetry.addData("Right Front Power: ", RF);
            telemetry.addData("Left Back Power: ", LB);
            telemetry.addData("Right Back Power: ", RB);
            telemetry.update();

        }
    }
}
