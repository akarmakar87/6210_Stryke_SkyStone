package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;
import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@TeleOp(name="MainTeleOp", group="teleop")
//@Disabled
public class MainTeleOp extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        double leftPower = 0, rightPower = 0;

        boolean halfSpeed = false;

        boolean reset = true;

        double angle = 0.0;

        boolean sidePower = false;

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            //Resetting sidePower
            sidePower = false;

            //RIGHT (ACTUALLY LEFT) MOTOR
            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                rightPower = gamepad1.left_stick_y;
                sidePower = true;
            }else{
                rightPower = 0;
                sidePower = false;
            }
            //LEFT (ACTUALLY RIGHT) MOTOR
            if(Math.abs(gamepad1.right_stick_y) > 0.05){
                leftPower = gamepad1.right_stick_y;
                sidePower = true;
            }else {
                leftPower = 0;
                sidePower = false;
            }
            //HALFSPEED
            if (gamepad1.right_bumper == true) {
                halfSpeed = true;
                leftPower = leftPower / 2;
                rightPower = rightPower / 2;
                sidePower = true;
            }else{
                halfSpeed = false;
                sidePower = false;
            }

            //LIFT CONTROLS
            if (gamepad2.right_trigger > 0.05) {
                lift.setPower(gamepad2.right_trigger); //LIFT DOWN
            }else if(gamepad2.left_trigger > 0.05){
                lift.setPower(-gamepad2.left_trigger); //LIFT UP
            }else{
                lift.setPower(0);
            }

            //if (gamepad1.dpad_up) -------------------To Do
            //    lift.set

            //Claw Movement
            if (gamepad2.b){
                setClawPosition(true);
            }
            if (gamepad2.a){
                setClawPosition(false);
            }

            //Strafe Controls
            if (gamepad1.left_bumper && reset == true){
                angle = getYaw();
                reset = false;
            }
            if (gamepad1.left_bumper = false){
                reset = true;
            }
            while (gamepad1.left_bumper){ //Strafe left
                StrafePowers(1, false, angle);
            }
            while (gamepad1.right_bumper){ //Strafe right
                StrafePowers(-1,true, angle);
            }

            if (sidePower == false) {
                setMotorPowers(leftPower, rightPower);
            }

            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower)
                    .addData("Half Speed", halfSpeed);
                   // .addData("Lift pos", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}
