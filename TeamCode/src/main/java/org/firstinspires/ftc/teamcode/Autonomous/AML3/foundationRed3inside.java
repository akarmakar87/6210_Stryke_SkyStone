package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red FoundationBridge", group = "auto")

@Disabled
public class foundationRed3inside extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 30);    //align with foundation

        turnPID(90, 0.6/90,0.0001,2, 3000);   //turn toward foundation

        driveAdjust(90,0.6, 55, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);    //wait to grab foundation

        driveAdjust(90, -0.2, 10, 4);   //pull hooks into position

        driveAdjust(90, -0.7, 60, 5);   //pull foundation into build site

        turnPIDF(0, 0.7/90,0.0001,2, 4000);  //turn foundation and robot toward the wall

        foundationD(true);     //Release foundation

        sleep(1000);    //wait to release foundation

        driveDistance(.8,45);   //push foundation into build site

        driveAdjust(0, -0.5, 5, 2);  //back away from foundation

        strafeAdjust(0.6,12,0,false);   //align with parking spot

        turnPID(0, 0.6/90,0.0001,2, 3000); //straighten out

        driveDistance(-0.4, 85);     //Drive to the parking spot (backward)
    }
}