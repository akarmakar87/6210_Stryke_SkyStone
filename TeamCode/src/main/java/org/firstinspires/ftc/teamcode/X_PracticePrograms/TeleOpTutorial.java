package org.firstinspires.ftc.teamcode.X_PracticePrograms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name = "TeleOp Tutorial", group = "teleop")
@Disabled
public class TeleOpTutorial extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        while (opModeIsActive()) {

            setMotorPowers(gamepad1.left_stick_y, gamepad1.right_stick_y);

            if (gamepad2.left_bumper) {
                //lift.setPower(1.0);
            } else {
                //lift.setPower(0);
            }
        }
    }
}