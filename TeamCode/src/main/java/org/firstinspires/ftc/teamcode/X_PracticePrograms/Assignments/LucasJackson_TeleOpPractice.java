package org.firstinspires.ftc.teamcode.X_PracticePrograms.Assignments;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name = "TeleOp Tutorial", group = "teleop")
@Disabled
public class LucasJackson_TeleOpPractice extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, false);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            // Bumpers are used for moving back and forth
            if (gamepad2.right_bumper) {
                intakeL.setPower(1);
                intakeR.setPower(1);
            } else if (gamepad2.left_bumper) {
                intakeL.setPower(-1);
                intakeL.setPower(-1);
            } else {
                intakeL.setPower(0);
                intakeR.setPower(0);
            }

            // Buttons x and y could be used for turning in tank drive
            if (gamepad2.x) {
                intakeR.setPower(2);
            }

            if (gamepad1.b) {
                intakeL.setPower(2);
            }

            if (gamepad1.y)
                claw.setPosition(1);
        }
        if (gamepad1.a) {
            claw.setPosition(0);
        }

        telemetry.addData("Motors", "left (%.2f), right (%.2f)", intakeL.getPower(), intakeR.getPower());
        telemetry.update();
    }
}
