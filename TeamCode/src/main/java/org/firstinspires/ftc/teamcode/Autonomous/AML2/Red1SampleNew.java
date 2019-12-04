package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red1SampleNew", group = "auto")

//@Disabled
public class Red1SampleNew extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);
        initBitmapVuforia();

        int pos = 0;
        double adjust = 0.0;

        waitForStart();

        pos = detectSkystoneOnePix(getBitmap(),true); //DETECT SKYSTONE

        adjust = adjustForSkystone(pos, true); //MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONE

        sleep(500);

        turnPID(273, 0.6/273, 0.004, 1, 5000); //TURN 90 TO FACE STONES

        //driveDistance(0.5,15); //MOVE BACK TO LINE UP AGAINST WALL
        sleep(500);

        driveDistance(-0.4,48); //GO TO STONES

        grabStone(pos); //GRAB SKYSTONE

        driveDistance(0.5,20); //MOVE BACKWARD

        sleep(500);

        turnPID(180, 0.7/180, 0.004, 1, 5000); //TURN 90 TO FACE PARK

        sleep(500);

        //turnPIDtest(180,0.5/90,0,1,5000);

        driveDistance(-0.5, 11+adjust); //CROSS PARK LINE (adjust distance)

        foundationD(true); //RELEASE BLOCK

        driveDistance(0.5, 53+adjust); //second stone (adjust distance) (35 = 11+24)

        sleep(500);

        turnPID(270, 0.6/270, 0.004, 1, 5000); //TURN 90 TO FACE STONES
        //turnPIDtest(270,0.5/90,0,1,5000);

        driveDistance(-0.5,28); //MOVE forward

        grabStone(pos); //GRAB SKYSTONE

        driveDistance(0.5,20); //MOVE back

        turnPID(180, 0.7/90, 0.004, 1, 5000); //TURN 90 TO FACE PARK

        sleep(500);

        driveDistance(-0.5, 53+adjust); //second stone (adjust distance) (35 = 11+24)

        foundationD(true); //RELEASE BLOCK

        }

}
