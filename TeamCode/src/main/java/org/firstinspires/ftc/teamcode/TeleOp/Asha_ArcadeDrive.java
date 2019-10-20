package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="ArcadeTeleOp", group="teleop")
//@Disabled
public class Asha_ArcadeDrive extends SkystoneLinearOpMode {

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

            //set drive power
            if(Math.abs(gamepad1.left_stick_y) > 0.05){
                drivePower = gamepad1.left_stick_y;

                if(Math.abs(gamepad1.right_stick_x) > 0.05) {

                }else{

                }

            }else{
                drivePower = 0;
            }

            //if movement on right stick
            if(Math.abs(gamepad1.right_stick_x) > 0.05){

                /**
                //if drivepower is 0
                if(Math.abs(drivePower) < 0.05){
                    leftPower = gamepad1.right_stick_x;
                    rightPower = -gamepad1.right_stick_x;
                }else {**/
                    if (gamepad1.right_stick_x < 0) {
                        leftPower = Range.clip(drivePower * (1 - Math.abs(gamepad1.right_stick_x)), -1, 1);
                    } else {
                        rightPower = Range.clip(drivePower * (1 - Math.abs(gamepad1.right_stick_x)), -1, 1);
                    }
                    setMotorPowers(leftPower, rightPower);
                //}
            }

           /**     //HALFSPEED (toggle)
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
            rightPower = halfSpeed ? rightPower/2 : rightPower; **/

            setMotorPowers(leftPower,rightPower );

            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower)
                    .addData("Strafe Speed", strafePower)
                    .addData("Half Speed", halfSpeed);
            telemetry.update();
        }
    }
}
