package org.firstinspires.ftc.teamcode.X_PracticePrograms;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name = "TeleOp Tutorial", group = "teleop")
@Disabled
public class TeleOpTemplate extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //DECLARE VARIABLES
        double leftPower = 0;
        double rightPower = 0;

        init(hardwareMap, false);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            //WRITE IF STATEMENTS W/ GAMEPAD ELEMENTS

            //GIVE TELEMETRY FOR MOTOR POWERS (to the 2nd decimal)
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }

    }
}