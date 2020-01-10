package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="RedDoubleSkystone", group = "auto") // RED SIDE

//@Disabled
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

        longAdjust = forLongAdjust(pos,false) + 100;

        strafeAdjust(0.4,2,0,true);

        turnPID(-90, 0.6/360,0.001,2,3000);

        driveAdjust(270,0.4,59, 7); //GO TO STONES

        grabStone(pos,false); //GRAB SKYSTONE

        driveAdjust(270,-0.8,11, 7); //MOVE BACKWARD

        //strafeAdjust(0.6,55,-90,true);

        if (pos == 1){
            turnPID(-175, 0.6/360,0.001,2,4000);

            driveAdjust(182, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        } else {
            turnPID(175, 0.6/360,0.001,2,4000);

            driveAdjust(178, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        }

        foundationD(true); // drop stone

        //strafeAdjust(0.6,20,-90,false);

        turnPID(180, 0.6/360,0.001,2,3000); // autocorrect angle to account for stone friction

        driveAdjust(180, -0.8, 30, 2); //park

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
