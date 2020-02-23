package org.firstinspires.ftc.teamcode.Autonomous.Regionals;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red Skystone", group = "auto") // BLUE SIDE

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

        pos = detectSkystoneOnePix(getBitmap(),false); //DETECT SKYSTONE

        adjustForSkystone(pos, false); //MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONe

        longAdjust = forLongAdjust(pos,false) + 90;

        strafeAdjust(0.6,2,0,true);

        turnPID(-90, 0.6/180,0.0001,0.5,5000);

        driveAdjust(270,0.4,59, 7); //GO TO STONES

        grabStone(pos,false); //GRAB SKYSTONE

        if (pos == 1)
            driveAdjust(270, -0.6, 6, 7);
        else if (pos == 0)
            driveAdjust(270,-0.6,8, 7); //MOVE BACKWARD
        else
            driveAdjust(270,-0.6,10, 7);

        turnPID(180, 0.4/180,0.0001,0.5,2000);

        driveAdjust(180, 0.5, longAdjust, 4); // MOVE OTHER SIDE

        hook(false, false); // drop stone

        //turnPID(180, 0.6/180,0.0001,0.5,5000); // realign

        // +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ second stone

        driveAdjust(180, -0.6, 190, 3); // MOVE BACK TO STONE SIDE

        switch (pos) {
            case -1:
                driveAdjust(180, 0.6, 4, 2);//orig 5
                break;
            case 0:
                driveAdjust(180, 0.6, 18, 2);//orig 16.5
                break;
            case 1:
                driveAdjust(180,0.6,12, 2);
                break;
        }

        //strafeAdjust(0.4,2,180,true);

        turnPID(-90, 0.6/180,0.0001,0.5,5000);

        if (pos == -1) strafeAdjust(0.3,1,270,false); // strafe

        driveAdjust(270, 0.4, 21, 5); //GO TO STONES

        grabStone(pos, false);

        //MOVE BACKWARD
        if (pos == 1)
            driveAdjust(270,-0.4,11.5, 7);
        else if (pos == 0)
            driveAdjust(270,-0.4,14, 7);
        else
            driveAdjust(270,-0.4,17.5, 7);

        if (pos == -1) strafeAdjust(0.3,1,270,true); // avoid hit wall when turn

        // MOVE TO OTHER SIDE
        turnPID(180, 0.4/180,0.0001,0.5,5000);
        switch (pos) {
            case -1:
                driveAdjust(180, .5, longAdjust + 50, 3000); // MOVE OTHER SIDE
                break;
            case 0:
                driveAdjust(180, .5, longAdjust + 47, 3000); // MOVE OTHER SIDE
                break;
            case 1:
                driveAdjust(180, .5, longAdjust + 45, 3000); // MOVE OTHER SIDE
                break;
        }

        hook(false, false); // drop stone

        driveAdjust(180, -0.8, 24, 2); //park

        sleep(100);

        strafeAdjust(.4,8,0,false);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
