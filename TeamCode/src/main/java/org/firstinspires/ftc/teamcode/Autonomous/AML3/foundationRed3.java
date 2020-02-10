package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red Foundation", group = "auto")

@Disabled
public class foundationRed3 extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 30);    //align with foundation

        strafeAdjust(0.6,2,0,false);

        turnPID(90, 0.6/90,0.0001,2, 3000);   //turn toward foundation

        driveAdjust(90,0.5, 53, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);    //wait to grab foundation

        driveAdjust(90, -0.2, 10, 4);   //pull hooks into place

        driveAdjust(90, -0.7, 60, 5);   //pull foundation into build site

        foundationD(false);

        //originally 0 
        turnPIDF(-20, 0.8/90,0.0001,2, 4000);  //Turn the foundation and the robot toward the site

        foundationD(true);     //Release foundation

        sleep(1000);    //wait to release foundation

        driveDistance(.8,35);   //push foundation into build site

        driveAdjust(0, -0.5, 5, 2);  //back away from foundation

        strafeAdjust(0.6,20,0,true);    //align with parking spot

        sleep(4000);    //wait to release foundation

        driveDistance(-0.4, 70);     //Drive to the parking spot (backward)
    }
}