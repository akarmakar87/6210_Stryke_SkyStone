package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


@Autonomous(name="Give name to show on DS", group = "auto")
@Disabled
public class LucasJackson_AutoPractice extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 10);
        rotate(-90, 2);
        driveDistance(0.4, 15);
        rotate(135, 2);
        driveDistance(0.4, 20);
        rotate(-75, 2);
        driveDistance(0.4, 8);
        rotate(-60, 2);
        driveDistance(0.4, 5);

        telemetry.addData("LABEL:", "VALUE HERE");
        telemetry.update();

    }
}
