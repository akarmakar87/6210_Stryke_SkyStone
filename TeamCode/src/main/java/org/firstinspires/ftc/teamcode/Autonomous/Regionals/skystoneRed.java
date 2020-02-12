package org.firstinspires.ftc.teamcode.Autonomous.Regionals;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="New Skystone Red", group = "auto") // BLUE SIDE

public class skystoneRed extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);
        initBitmapVuforia();

        int pos = 0;
        double adjust = 0.0;
        double longAdjust = 0.0;

        waitForStart();
//
        pos = detectSkystoneOnePix(getBitmap(),false); //DETECT SKYSTONE
//
        adjustForSkystone(pos, false); //MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONe
//
        longAdjust = forLongAdjust(pos,false) + 100;
//
        strafeAdjust(0.6,2,0,true);
//
        turnPID(-90, 0.6/180,0.0001,0.5,5000);
//
        driveAdjust(270,0.4,59, 7); //GO TO STONES
//
        grabStone(pos,false); //GRAB SKYSTONE
//
       if (pos == 0)
            driveAdjust(270, -0.6, 7, 7);
        else
            driveAdjust(270,-0.6,8, 7); //MOVE BACKWARD

        //
        turnPID(180, 0.6/180,0.0001,0.5,5000);

        //
        driveAdjust(180, 0.6, longAdjust-7, 4); // MOVE OTHER SIDE

        hook(false, false); // drop stone

        //
        turnPID(180, 0.6/180,0.0001,0.5,5000);

        // +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ second stone

        driveAdjust(180, -0.6, 190, 3); // MOVE BACK TO STONE SIDE

        //
        switch (pos) {
            case 1:
                driveAdjust(0, 0.6, 10, 2);
                break;
            case 0:
                driveAdjust(0, 0.6, 28, 2);
                break;
            case -1:
                driveAdjust(0,0.6,5, 1);
                break;
        }

        strafeAdjust(0.4,2,180,true);

        turnPID(-90, 0.6/180,0.0001,0.5,5000);

        if (pos == -1) strafeAdjust(0.4,2,270,false); // strafe

        /**

        driveAdjust(270, 0.4, 23, 5); //GO TO STONES

        grabStoneBlue(pos, false);

        //MOVE BACKWARD
        if (pos == 1)
            driveAdjust(270,-0.4,7.5, 7);
        else
            driveAdjust(270,-0.4,9, 7);

        // MOVE TO OTHER SIDE
        turnPID(0, 0.6/180,0.0001,0.5,2000);
        switch (pos) {
            case -1:
                driveAdjust(0, .8, longAdjust + 57, 3000); // MOVE OTHER SIDE
                break;
            case 0:
                driveAdjust(0, .8, longAdjust + 50, 3000); // MOVE OTHER SIDE
                break;
            case 1:
                driveAdjust(0, .8, longAdjust + 43, 3000); // MOVE OTHER SIDE
                break;
        }

        hook(false, false); // drop stone

        driveAdjust(0, -0.8, 22, 2); //park

        sleep(100);

        strafeAdjust(.4,8,0,true);

        ***/
        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
