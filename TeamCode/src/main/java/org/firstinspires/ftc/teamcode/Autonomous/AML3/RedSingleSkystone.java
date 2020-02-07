package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="RedSingleSkystone", group = "auto") // RED SIDE

@Disabled
public class RedSingleSkystone extends SkystoneLinearOpMode{

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

        adjustForSkystone(pos, false); //MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONE

        longAdjust = forLongAdjust(pos,false) + 95;

        strafeAdjust(0.4,2,0,true);

        turnPID(-90, 0.6/90,0.0001,2,2000);

        driveAdjust(270,0.4,62, 7); //GO TO STONES

        grabStone(pos,false); //GRAB SKYSTONE

        driveAdjust(270,-0.8,9, 7); //MOVE BACKWARD

        if (pos == 1){
            turnPID(180, 0.6/90,0.0001,2,2000);

            driveAdjust(180, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        } else {
            turnPID(180, 0.6/90,0.0001,2,2000);

            driveAdjust(180, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        }

        hook(false, false); // drop stone

        driveAdjust(180, -0.8, 30, 2); //park

        strafeAdjust(.6,10,180,false);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
