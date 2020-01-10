package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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

        strafeAdjust(0.4,2,0,true);

        turnPID(-90, 0.6/360,0.001,2,3000);

        driveAdjust(270,0.5,59, 7); //GO TO STONES

        grabStoneBlue(pos,false); //GRAB SKYSTONE

        driveAdjust(90,-0.6,11, 7); //MOVE BACKWARD

        sleep(250);

        if (pos == -1){
            turnPID(-5, 0.6/360,0.001,2,4000);

            driveAdjust(2, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        } else {
            turnPID(5, 0.6/360,0.001,2,4000);

            driveAdjust(358, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        }

        foundationD(true); // drop stone

        driveAdjust(90,-0.8,20, 7); //MOVE BACKWARD

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
