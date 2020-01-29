package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Drive Test Methods", group = "auto")

@Disabled
public class driveTestMethods extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);
        initBitmapVuforia();

        waitForStart();

        driveAdjust(0, 0.6, 70, 4);

        driveAdjust(0, -0.6, 70, 4);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }

}
