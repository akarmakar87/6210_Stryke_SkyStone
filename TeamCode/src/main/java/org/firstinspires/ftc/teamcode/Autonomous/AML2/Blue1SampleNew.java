package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue1SampleNew", group = "auto") // RED SIDE

//@Disabled
public class Blue1SampleNew extends SkystoneLinearOpMode{

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

        adjust = adjustForSkystone(pos, false); //MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONE

        longAdjust = forLongAdjust(pos,false) + 100;

        turnPID(273, 0.7/273, 0.004, 1, 5000); //TURN 90 TO FACE STONES
        //turnPIDtest(270,0.5/90,0,1,4000);

        //driveDistance(0.2,5); //MOVE BACK TO LINE UP AGAINST WALL

        driveAdjust(270,-0.5,46, 7); //GO TO STONES

        grabStone(pos); //GRAB SKYSTONE

        driveAdjust(270,0.9,13, 7); //MOVE BACKWARD

        turnPID(180, 0.8/180, 0.004, 1, 5000); //TURN 90 TO FACE PARK

        //turnPIDtest(180,0.5/90,0,1,5000);

        driveAdjust(180,-0.4, 68+adjust, 7); //CROSS PARK LINE (adjust distance)

        foundationD(true); //RELEASE BLOCK

        //turnPID(180, 0.7/180, 0.004, 1, 5000);

        //driveDistance(0.2, 105+adjust); //second stone (adjust distance) (35 = 11+24)

        //driveDistance(0.6, 108+adjust);
        driveAdjust(180,0.6, longAdjust,7);

        turnPID(270, 0.7/270, 0.004, 1, 5000); //TURN 90 TO FACE STONES
        //turnPIDtest(270,0.5/90,0,1,5000);

        if (pos == -1)
            strafeDistance(0.5, 5, true);

        driveAdjust(270,-0.6,19, 7); //MOVE forward

        grabStone(pos); //GRAB SKYSTONE

        driveAdjust(270,0.9,16, 7); //MOVE back

        turnPID(185, 0.7/90, 0.004, 1, 5000); //TURN 90 TO FACE PARK

        driveAdjust(180,-0.6, longAdjust - 5, 7); //second stone (adjust distance) (35 = 11+24)

        foundationD(true); //RELEASE BLOCK

        driveAdjust(180,0.9,15, 7); //MOVE back

        }

}
