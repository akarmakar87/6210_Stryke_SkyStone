package org.firstinspires.ftc.teamcode.Autonomous.Regionals;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="Red Dual", group = "auto") // RED SIDE DOUBLE

@Disabled
public class dualRed extends SkystoneLinearOpMode {

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

        longAdjust = forLongAdjust(pos,false) + 87;

        strafeAdjust(0.6,2,0,true);

        turnPID(-90, 0.6/180,0.0001,0.5,5000);

        driveAdjust(270,0.4,59, 7); //GO TO STONES

        grabStone(pos,false); //GRAB SKYSTONE

        if (pos == 1)
            driveAdjust(270, -0.6, 7, 7);
        else
            driveAdjust(270,-0.6,8, 7); //MOVE BACKWARD

        turnPID(180, 0.4/180,0.0001,0.5,2000);

        driveAdjust(180, 0.5, longAdjust, 4); // MOVE OTHER SIDE

        hook(false, false); // drop stone

        //turnPID(180, 0.6/180,0.0001,0.5,5000); // realign

        // +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ second stone

        driveAdjust(180, -0.6, 190, 3); // MOVE BACK TO STONE SIDE

        switch (pos) {
            case -1:
                driveAdjust(180, 0.6, 5, 2);
                break;
            case 0:
                driveAdjust(180, 0.6, 16.5, 2);
                break;
            case 1:
                driveAdjust(180,0.6,12, 2);
                break;
        }

        //strafeAdjust(0.4,2,180,true);

        turnPID(-90, 0.6/180,0.0001,0.5,5000);

        if (pos == -1) strafeAdjust(0.4,2,270,false); // strafe

        driveAdjust(270, 0.4, 24, 5); //GO TO STONES

        grabStone(pos, false);

        //MOVE BACKWARD
        if (pos == 1)
            driveAdjust(270,-0.4,11.5, 7);
        else
            driveAdjust(270,-0.4,14.5, 7);

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
                driveAdjust(180, .5, longAdjust + 43, 3000); // MOVE OTHER SIDE
                break;
        }

        // FOUNDATION MOVEMENTS

        driveAdjust(180, .7, 70, 3000); // go to foundation

        hook(false, false); // drop stone

        turnPID(90, 0.6/180,0.0001,0.5,5000);   //turn toward foundation

        driveAdjust(90, -0.4, 16, 2); //Carefully approach foundation

        foundationD(true);  //Grab foundation

        sleep(500);    //wait for grab

        turnPIDF(80, .8/90,.0001, 2, 3000);

        driveAdjust(90, 2, 85, 4);

        turnPIDF(90, .8/90,.0001, 2, 3000);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
