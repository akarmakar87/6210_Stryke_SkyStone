package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="BlueDoubleSkystone (TBD)", group = "auto") // BLUE SIDE

//@Disabled
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

        //turnPID(180, 0.6/360,0.001,2,3000); // autocorrect angle to account for stone friction

        // +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ second stone
        /*
        driveAdjust(180, -0.8, longAdjust + 31, 3); // MOVE BACK TO STONE SIDE

        turnPID(90, 0.5/360,0.001,2,4000);

        driveAdjust(90, 0.5, 20, 5); //GO TO STONES

        grabStone(pos, false);

        driveAdjust(90,-0.5,10, 7); //MOVE BACKWARD

        turnPID(-178, 0.6/360,0.001,2,5000);

        driveAdjust(182, 1, longAdjust + 60, 3000); // MOVE OTHER SIDE

        foundationD(true);

        driveAdjust(180, -0.8, 30, 2); //park
        */

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
