package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="DrivetrainTeleOp", group="teleop")
//@Disabled
public class DrivetrainTeleOp extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        //For tank drive
        double leftPower = 0, rightPower = 0, strafePower = 0;

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            //LEFT SIDE MOTOR POWERS
            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                leftPower = gamepad1.left_stick_y;
            }else{
                leftPower = 0;
            }
            //RIGHT SIDE MOTOR POWERS
            if(Math.abs(gamepad1.right_stick_y) > 0.05){
                rightPower = gamepad1.right_stick_y;
            }else {
                rightPower = 0;
            }

            //HALFSPEED (toggle)
            if (gamepad1.x == true) {
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
