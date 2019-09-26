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

        //Lift increments

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

            //HALFSPEED (toggle)
            if (gamepad1.x == true) {
                if (halfSpeed == false) {
                    halfSpeed = true;
                }
                if (halfSpeed == true){
                    halfSpeed = false;
                }
            }

            //LIFT CONTROLS
            if (gamepad2.right_trigger > 0.05) {
                lift.setPower(gamepad2.right_trigger); //LIFT DOWN
            }else if(gamepad2.left_trigger > 0.05){
                lift.setPower(-gamepad2.left_trigger); //LIFT UP
            }else{
                lift.setPower(0);
            }

            //LIFT CONTROLS (incremental)
            if (gamepad2.dpad_up) {
                double currPos = lift.getCurrentPosition();//get current position 960/pi
                int tarPos = ((int)currPos/10) + 1; //find tens place and add 1 to it -----------------------------Fix values so it adjusts to the right increments (levels of skyscraper) in inches (encoders to inches)
                lift.setTargetPosition(tarPos * 10); //Make lift go to position
            }
            if (gamepad2.dpad_down) {
                double currPos = lift.getCurrentPosition();//get current position
                int tarPos = ((int)currPos/10) - 1; //find tens place and add 1 to it -----------------------------Fix values so it adjusts to the right increments (levels of skyscraper) in inches (encoders to inches)
                lift.setTargetPosition(tarPos * 10); //Make lift go to position
            }

            //if (gamepad1.dpad_up) -------------------To Do
            //    lift.set

            //CLAW MOVEMENT
            if (gamepad2.b){
                setClawPosition(true);
            }
            if (gamepad2.a){
                setClawPosition(false);
            }

            //STRAFE CONTROLS
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

            //MOTOR POWERS (if not strafing)
            if (sidePower == true) {
                if (halfSpeed == true){
                    setMotorPowers(leftPower/2, rightPower/2);
                }
                else{
                    setMotorPowers(leftPower, rightPower);
                }
            }

            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower)
                    .addData("Half Speed", halfSpeed);
                   // .addData("Lift pos", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}
