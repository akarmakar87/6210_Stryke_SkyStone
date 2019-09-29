package org.firstinspires.ftc.teamcode.X_PracticePrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;


@Autonomous(name="Give name to show on DS", group = "auto")
//@Disabled
public class Template extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.3, 4);

        telemetry.addData("Distance: ", getEncoderAvg());
        telemetry.update();

    }
}
