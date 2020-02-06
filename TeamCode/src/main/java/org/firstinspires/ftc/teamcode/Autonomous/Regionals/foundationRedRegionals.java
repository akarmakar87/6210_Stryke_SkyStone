package org.firstinspires.ftc.teamcode.Autonomous.Regionals;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red Foundation Regionals", group = "auto")

//@Disabled
public class foundationRedRegionals extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 30);    //align with foundation

        turnPID(90, 0.6/90,0.0001,2, 3000);   //turn toward foundation

        driveAdjust(90,0.5, 55, 4);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait to grab foundation

        driveAdjust(90, -0.2, 10, 4);   //pull hooks into place

        driveAdjust(90, -0.4, 60, 5);   //pull foundation into build site

        foundationD(false);     //Release foundation

        sleep(250);    //wait to release foundation

        strafeAdjust(0.6,20,0,false);    //align with parking spot

        sleep(5000);    //wait for alliance partner to finish

        driveDistance(0.4, 70);     //Drive to the parking spot
    }
}