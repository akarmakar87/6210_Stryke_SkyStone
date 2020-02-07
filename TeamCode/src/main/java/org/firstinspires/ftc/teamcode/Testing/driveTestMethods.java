package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Drive Test Methods", group = "auto")

//@Disabled
public class driveTestMethods extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);
        initBitmapVuforia();

        waitForStart();

        foundationD(true);

        sleep(1000);

        turnArc(90, 0.8/90,0.0001,2, true, 4000);

        telemetry.addData("First Turn ", "done");
        telemetry.update();

        sleep(3000);

        turnArc(0, 0.8/90,0.0001,2, false, 4000);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }

}
