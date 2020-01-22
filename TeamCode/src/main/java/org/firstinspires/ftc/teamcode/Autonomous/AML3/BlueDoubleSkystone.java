package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="BlueDoubleSkystone (TBD)", group = "auto") // BLUE SIDE

@Disabled
public class BlueDoubleSkystone extends SkystoneLinearOpMode{

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

        // +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ second stone
        /*
        // MOVE BACK TO STONE SIDE
        switch (pos) {
            case -1:
                driveAdjust(0, -0.8, longAdjust + 38, 3);
                break;
            case 0:
                driveAdjust(0, -0.8, longAdjust + 40, 3); //originally 39
                break;
            case 1:
                driveAdjust(0, -0.8, longAdjust + 49, 3);
                break;
        }

        turnPID(-90, 0.6/360,0.001,2,3000);

        if (pos == 1) strafeAdjust(0.6,3,90,true); // strafe

        driveAdjust(270, 0.6, 25, 5); //GO TO STONES

        grabStoneBlue(pos, false);

        driveAdjust(270,-0.6,11, 7); //MOVE BACKWARD

        // MOVE TO OTHER SIDE
        switch (pos) {
            case -1:
                turnPID(175, 0.6/360,0.001,2,3000);
                driveAdjust(173, 1, longAdjust + 53, 3000);
                break;
            case 0:
                turnPID(175, 0.6/360,0.001,2,3000);
                driveAdjust(175, 1, longAdjust + 50, 3000);
                break;
            case 1:
                turnPID(-176, 0.6/360,0.001,2,3000);
                driveAdjust(182, 1, longAdjust + 40, 3000);
                break;
        }

        foundationD(true);

        driveAdjust(180, -0.8, 30, 2); //park

        strafeAdjust(.6,13,180,false);
        */

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
