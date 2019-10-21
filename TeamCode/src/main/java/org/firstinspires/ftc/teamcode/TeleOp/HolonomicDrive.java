package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="HolonomicDrive", group="teleop")
//@Disabled
public class HolonomicDrive extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        double xAxis = -gamepad1.left_stick_x, yAxis = -gamepad1.left_stick_y, zAxis = -gamepad1.right_stick_x;

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            double lfPower = yAxis+xAxis-zAxis, rfPower = yAxis-xAxis+zAxis, lbPower = yAxis-xAxis-zAxis, rbPower = yAxis+xAxis+zAxis;

            if (Math.abs(gamepad1.left_stick_y) > 0.05) {
                LF.setPower(Range.clip(lfPower, -1, 1));
                RF.setPower(Range.clip(rfPower, -1, 1));
                LB.setPower(Range.clip(lbPower, -1, 1));
                RB.setPower(Range.clip(rbPower, -1, 1));
            }
        }
    }
}
