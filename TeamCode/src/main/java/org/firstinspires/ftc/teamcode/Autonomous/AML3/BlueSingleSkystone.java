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

        strafeAdjust(0.6,2,0,true);

        turnPID(-90, 0.6/90,0.0001,2,2000);

        driveAdjust(270,0.4,61, 7); //GO TO STONES

        grabStoneBlue(pos,false); //GRAB SKYSTONE

        driveAdjust(270,-0.8,7, 7); //MOVE BACKWARD

        //turnPID(0, 0.6/90,0.0001,2,2000);

        //driveAdjust(0, 0.6, longAdjust, 4); // MOVE OTHER SIDE

        if (pos == -1){
            turnPID(-2, 0.6/90,0.0001,2,2000);

            driveAdjust(358, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        } else {
            turnPID(2, 0.6/90,0.0001,2,2000);

            driveAdjust(2, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        }

        foundationD(true); // drop stone

        turnPID(0, 0.6/90,0.0001,2,1500); // autocorrect angle to account for stone friction

        driveAdjust(0, -0.6, 25, 2); //park

        strafeAdjust(.4,14,0,true);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
