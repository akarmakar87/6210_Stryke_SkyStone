package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;
import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@TeleOp(name="MainTeleOp", group="teleop")
@Disabled
public class MainTeleOp extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        //For tank drive
        double leftPower = 0, rightPower = 0;

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;

        //So we get the target angle when strafing only once
        boolean reset = true;

        //Used for strafing
        double angle = 0.0;

        //Whether we are using tank drive or strafing
        boolean sidePower = false;

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            //Resetting sidePower
            sidePower = false;

            //LEFT SIDE MOTOR POWERS
            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                leftPower = gamepad1.left_stick_y;
                sidePower = true;
            }else{
                leftPower = 0;
                sidePower = false;
            }
            //RIGHT SIDE MOTOR POWERS
            if(Math.abs(gamepad1.right_stick_y) > 0.05){
                rightPower = gamepad1.right_stick_y;
                sidePower = true;
            }else {
                rightPower = 0;
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

            /**

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
                double currPos = lift.getCurrentPosition();//get current position
                int tarPos = ((int)currPos/10) + 1; //find tens place and add 1 to it -----------------------------Fix values so it adjusts to the right increments (levels of skyscraper) in inches (encoders to inches)
                lift.setTargetPosition(tarPos * 10); //Make lift go to position
            }
            if (gamepad2.dpad_down) {
                double currPos = lift.getCurrentPosition();//get current position
                int tarPos = ((int)currPos/10) - 1; //find tens place and add 1 to it -----------------------------Fix values so it adjusts to the right increments (levels of skyscraper) in inches (encoders to inches)
                lift.setTargetPosition(tarPos * 10); //Make lift go to position
            }

            //CLAW MOVEMENT
            if (gamepad2.b){
                setClawPosition(true); //OPEN CLAW
            }
            if (gamepad2.a){
                setClawPosition(false); //CLOSE CLAW
            }
**/
            //STRAFE CONTROLS
            if (gamepad1.left_bumper && reset == true){ //So we only get the facing angle once while strafing (to avoid the target angle from changing if the robot goes off course)
                angle = getYaw();
                reset = false;
            }
            if (gamepad1.left_bumper = false){
                reset = true;
            }
            while (gamepad1.left_bumper){ //Strafe left
                if (halfSpeed == true) {
                    StrafePowers(1, false, angle);
                }
                else {
                    StrafePowers(1/2, false, angle);
                }
            }
            while (gamepad1.right_bumper){ //Strafe right
                if (halfSpeed == true) {
                    StrafePowers(1, true, angle);
                }
                else {
                    StrafePowers(1/2, true, angle);
                }
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

            setMotorPowers(leftPower, rightPower);

            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower)
                    .addData("Half Speed", halfSpeed);
                   // .addData("Lift pos", lift.getCurrentPosition());
            telemetry.update();
        }
    }
}
