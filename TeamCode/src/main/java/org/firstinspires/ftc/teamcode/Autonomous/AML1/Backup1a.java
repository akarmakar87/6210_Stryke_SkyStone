package org.firstinspires.ftc.teamcode.Autonomous.AML1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="bread and one apple", group = "auto")

//@Disabled
public class Backup1a extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);

        boolean red = true;

        //should we keep this button pressing system? is it legal?
        while (!isStarted()) {

            if (gamepad1.b) red = false;

            telemetry.addData("red? ", red);
            telemetry.update();
        }

        resetArm();

        waitForStart();

        // scan for skystone
        // variables based on:
        //      - distance needed to move to be in front of skystone
        //      - distance to get to other side of field
        // no need to set a variable for distance traveled to skystone 2 because skystones are paired up
        // and thus a set distance apart << measured
        // will be different for red and blue side so set inside if else loop
        int stonePos = 0;
        //int whereStone = detectSkystone(getBitmap(1000));
        int seeyouontheotherside = 0;
        int heading = 0; // << we only turn one way ever but the angle depends on alliance side

        // +-+-+-+-+-+-+-+-+-+- NOTE - find diff in inches between each pair of skystones


        if (red){
            heading = 90;

            // if stone is on left
            stonePos = 0;
            seeyouontheotherside = 0;
        }
        else {
            heading = -90;

            // if stone is on left
            stonePos = 0;
            seeyouontheotherside = 0;
        }

        // +-+-+- first skystone

        setArmPosition(true);

        driveDistance(0.3, 38);
        setClawPosition(false);

        sleep(250);

        driveDistance(-0.3, 14);
        setArmPosition(false);

        sleep(250);

        turnPID(-90, 0.75, 0, 2, 3500);

        sleep(250);

        driveDistance(0.5, 40);
        setClawPosition(true);

        sleep(250);

        turnPID(-90, 0.6, 0, 2, 3500);

        sleep(250);

        driveDistance(-0.5, 33);
        setArmPosition(true);

        sleep(250);

        turnPID(0, 0.6, 0, 2, 3500);

        sleep(250);

        driveDistance(0.3, 9);
        setClawPosition(false);

        sleep(250);

        setArmPosition(false);
        driveDistance(-0.3, 9);

        sleep(250);

        turnPID(-90, 0.6, 0, 2, 3500);

        sleep(250);

        driveDistance(0.5, 33);
        setClawPosition(true);






        /*
        strafeDistance(0.5, 6, true);       // strafe to in front of skystone
        driveDistance(0.8, 15);                 // drive to skystone
        // intake
        driveDistance(-0.5, 5);                 // back up
        turnPIDV(heading, 0, 0, 0, false);
        driveDistance(0.5, seeyouontheotherside);       // drive to other side
        // drop stone

        // +-+-+- other skystone

        seeyouontheotherside += 10;

        driveDistance(-0.5, seeyouontheotherside);      // back up to other skystone
        turnPIDV(0, 0, 0, 0, false);    // turn to skystone
        // intake
        driveDistance(-0.5, 5);                 // back up
        turnPIDV(heading, 0, 0, 0, false);
        driveDistance(0.5, seeyouontheotherside);       // drive to other side
        // drop stone

        // park
        driveDistance(-0.5, 10);

        */
    }

}
