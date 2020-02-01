package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="BlueSingleSkystone", group = "auto") // BLUE SIDE

//@Disabled
public class BlueSingleSkystone extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);
        initBitmapVuforia();

        int pos = 0;
        double adjust = 0.0;
        double longAdjust = 0.0;

        waitForStart();

        pos = detectSkystoneOnePix(getBitmap(),true); //DETECT SKYSTONE

        adjustForSkystone(pos, true); //MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONE

        longAdjust = forLongAdjust(pos,true) + 100;

        strafeAdjust(0.6,2,0,true);

        turnPID(-90, 0.6/90,0.0001,2,2000);

        driveAdjust(270,0.4,61, 7); //GO TO STONES

        grabStoneBlue(pos,false); //GRAB SKYSTONE

        if (pos == -1)
            driveAdjust(270, -0.8, 6, 7);
        else
            driveAdjust(270,-0.8,8, 7); //MOVE BACKWARD

        turnPID(0, 0.6/90,0.0001,2,2000);

        driveAdjust(0, 0.5, longAdjust, 4); // MOVE OTHER SIDE

        foundationD(true); // drop stone

        driveAdjust(0, -0.8, 20, 2); //park

        sleep(100);

        strafeAdjust(.4,8,0,true);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
