package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="RedDoubleSkystone", group = "auto") // RED SIDE

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

        // +=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ second stone

        driveAdjust(180, -0.8, longAdjust + 31, 3); // MOVE BACK TO STONE SIDE

        sleep(250);
        turnPID(-90, 0.5/360,0.001,2,4000);

        driveAdjust(270, 0.5, 23, 5); //GO TO STONES

        grabStone(pos, false);

        driveAdjust(270,-0.5,10, 7); //MOVE BACKWARD

        if (pos == 1){
            turnPID(-178, 0.6/360,0.001,2,5000);

            driveAdjust(182, 1, longAdjust + 60, 3000); // MOVE OTHER SIDE
        } else {
            turnPID(178, 0.6/360,0.001,2,5000);

            driveAdjust(178, 1, longAdjust + 60, 3000); // MOVE OTHER SIDE
        }

        foundationD(true);

        driveAdjust(180, -0.8, 30, 2); //park

        //+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+ old stuffing
        /*
        foundationD(true); // drop stone

        turnPID(180, 0.6/360,0.001,2,3000); // autocorrect angle to account for stone friction

        strafeAdjust(0.6,20,-90,false);

        driveAdjust(180, -0.6, 20, 3000); // MOVE BACK TO STONE SIDE

        turnPID(180, 0.8/180, 0.004, 1, 5000); //TURN 90 TO FACE PARK

        //turnPIDtest(180,0.5/90,0,1,5000);

        driveAdjust(180,-0.4, 68+adjust, 7); //CROSS PARK LINE (adjust distance)

        foundationD(true); //RELEASE BLOCK

        //turnPID(180, 0.7/180, 0.004, 1, 5000);

        //driveDistance(0.2, 105+adjust); //second stone (adjust distance) (35 = 11+24)

        //driveDistance(0.6, 108+adjust);
        driveAdjust(180,0.6, longAdjust,7);

        if(pos == -1)
            turnPID(280, 0.7/275, 0.004, 1, 5000); //TURN 90 TO FACE STONES
        else
            turnPID(270,0.7/270, 0.004, 1,5000);
        //turnPIDtest(270,0.5/90,0,1,5000);

        driveAdjust(270,-0.6,23, 7); //MOVE forward

        grabStone(pos, true); //GRAB SKYSTONE

        driveAdjust(270,0.9,16, 7); //MOVE back

        turnPID(188, 0.7/90, 0.004, 1, 5000); //TURN 90 TO FACE PARK

        driveAdjust(180,-0.6, longAdjust - 1, 7); //second stone (adjust distance) (35 = 11+24)

        foundationD(true); //RELEASE BLOCK

        driveAdjust(180,0.9,20, 7); //MOVE back

         */

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
