package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="RedDoubleSkystone", group = "auto") // RED SIDE DOUBLE

//@Disabled
public class RedDoubleSkystone extends SkystoneLinearOpMode{

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

        strafeAdjust(0.6,2,0,true);

        turnPID(-90, 0.6/360,0.001,2,3000);

        driveAdjust(270,0.5,61, 7); //GO TO STONES

        grabStone(pos,false); //GRAB SKYSTONE

        driveAdjust(270,-0.8,11, 7); //MOVE BACKWARD

        //strafeAdjust(0.6,55,-90,true);

        if (pos == 1){
            turnPID(-178, 0.6/360,0.001,2,4000);

            driveAdjust(182, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        } else {
            turnPID(178, 0.6/360,0.001,2,4000);

            driveAdjust(178, 0.8, longAdjust, 4); // MOVE OTHER SIDE
        }

        foundationD(true); // drop stone

        //strafeAdjust(0.6,20,-90,false);

        turnPID(180, 0.6/360,0.001,2,1500); // autocorrect angle to account for stone friction

        // +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ second stone

        // MOVE BACK TO STONE SIDE
        switch (pos) {
            case -1:
                driveAdjust(180, -0.8, longAdjust + 38, 3);
                break;
            case 0:
                driveAdjust(180, -0.8, longAdjust + 40, 3); //originally 39
                break;
            case 1:
                driveAdjust(180, -0.8, longAdjust + 49, 3);
                break;
        }
        //driveAdjust(180, -0.8, longAdjust + 31, 3);

        sleep(250);
        //turnPID(-90, 0.5/360,0.001,2,4000);
        turnPID(-90, 0.6/360,0.001,2,3000);

        if (pos == -1) strafeAdjust(0.6,3,90,false); // strafe

        driveAdjust(270, 0.6, 25, 5); //GO TO STONES

        grabStone(pos, false);

        driveAdjust(270,-0.6,11, 7); //MOVE BACKWARD

        // MOVE TO OTHER SIDE
        switch (pos) {
            case -1:
                //turnPID(175, 0.6/360,0.001,2,3000);
                turnPID(175, 0.6/360,0.001,2,3000);
                driveAdjust(173, 1, longAdjust + 53, 3000); // MOVE OTHER SIDE
                break;
            case 0:
                turnPID(175, 0.6/360,0.001,2,3000);
                driveAdjust(175, 1, longAdjust + 50, 3000); // MOVE OTHER SIDE
                break;
            case 1:
                //turnPID(-178, 0.6/360,0.001,2,3000);
                turnPID(-176, 0.6/360,0.001,2,3000);
                driveAdjust(182, 1, longAdjust + 40, 3000); // MOVE OTHER SIDE
                break;
        }

        foundationD(true);

        driveAdjust(180, -0.8, 30, 2); //park

        strafeAdjust(.6,13,180,false);

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
