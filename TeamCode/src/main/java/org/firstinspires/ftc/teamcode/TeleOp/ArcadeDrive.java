package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="ArcadeTeleOp", group="teleop")
//@Disabled
public class ArcadeDrive extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        //For tank drive
        double drivePower = 0, leftPower = 0, rightPower = 0, strafePower = 0, x_value = 0;

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            //LEFT SIDE MOTOR POWERS
            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                drivePower = gamepad1.left_stick_y;
            }else{
                drivePower = 0;
            }

            if(Math.abs(gamepad1.right_stick_x) > 0.05){
                x_value = gamepad1.right_stick_x;
            }
            else
                x_value = 0;

            if(drivePower == 0)
                setMotorPowers(x_value, -x_value);
            else{
                leftPower = drivePower; //Put this in once driver is ready to turn faster "* (1 + x_value)"
                rightPower = drivePower * (1 - x_value);
                Range.clip(leftPower, -1, 1);
                Range.clip(rightPower, -1, 1);
                setMotorPowers(leftPower, rightPower);
            }

                //HALFSPEED (toggle)
            if (gamepad1.x) {
                halfSpeed = !halfSpeed;
            }

            //STRAFE CONTROLS
            if (gamepad1.left_trigger > 0.05){
                strafePower = gamepad1.left_trigger;
                setStrafePowers(strafePower,false);
            }else if (gamepad1.right_trigger > 0.05){
                strafePower = gamepad1.right_trigger;
                setStrafePowers(strafePower,true);
            }else{
                setMotorPowers(leftPower,rightPower);
            }

            leftPower = halfSpeed ? leftPower/2 : leftPower;
            rightPower = halfSpeed ? rightPower/2 : rightPower;
            //setMotorPowers(leftPower,rightPower );

            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower)
                    .addData("Strafe Speed", strafePower)
                    .addData("Half Speed", halfSpeed);
            telemetry.update();
        }
    }
}
