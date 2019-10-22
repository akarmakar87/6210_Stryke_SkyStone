package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="HolonomicDrive", group="teleop")
//@Disabled
public class HolonomicDrive extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, false);

        double xAxis = 0, yAxis = 0, zAxis = 0;
        double lfPower = 0, rfPower = 0, lbPower = 0, rbPower = 0, strafePower = 0;

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            if (Math.abs(gamepad1.left_stick_y) > 0.05) {
               yAxis = gamepad1.left_stick_y;
            }
            else{
                yAxis = 0;
            }
            if (Math.abs(gamepad1.left_stick_x) > 0.05) {
                xAxis = -gamepad1.left_stick_x;
            }
            else{
                xAxis = 0;
            }
            if (Math.abs(gamepad1.right_stick_x) > 0.05) {
                zAxis = gamepad1.right_stick_x;
            }
            else{
                zAxis = 0;
            }


            if (gamepad1.left_trigger > 0.05){
                strafePower = gamepad1.left_trigger * 0.75;
                setStrafePowers(strafePower,false);
            }else if (gamepad1.right_trigger > 0.05){
                strafePower = gamepad1.right_trigger * 0.75;
                setStrafePowers(strafePower,true);
            }else{
                lfPower = yAxis+xAxis-zAxis;
                rfPower = yAxis-xAxis+zAxis;
                lbPower = yAxis-xAxis-zAxis;
                rbPower = yAxis+xAxis+zAxis;

                LF.setPower(Range.clip(lfPower, -1, 1));
                RF.setPower(Range.clip(rfPower, -1, 1));
                LB.setPower(Range.clip(lbPower, -1, 1));
                RB.setPower(Range.clip(rbPower, -1, 1));
            }

            telemetry.addData("Y Axis", yAxis);
            telemetry.addData("X Axis", xAxis);
            telemetry.addData("Z Axis", zAxis);
            telemetry.addData("LF Power", lfPower);
            telemetry.addData("RF Power", rfPower);
            telemetry.addData("LB Power", lbPower);
            telemetry.addData("RB Power", rbPower);
            telemetry.update();
        }
    }
}
