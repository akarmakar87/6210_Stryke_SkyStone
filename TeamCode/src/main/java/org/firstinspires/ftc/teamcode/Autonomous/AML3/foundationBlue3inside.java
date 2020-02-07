package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue FoundationBridge", group = "auto")

@Disabled
public class foundationBlue3inside extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 15);    //align with foundation

        turnPID(-90, 0.6/90,0.0001,2, 3000);   //turn toward foundation

        driveAdjust(270,0.6, 57, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);    //wait for grab

        driveAdjust(270, -0.2, 10, 4);  //pull hooks into place

        driveAdjust(270, -0.6, 65, 5);  //pull foundation back

        turnPIDF(0, 0.8/90,0.0001,2, 3000);    //turn robot & foundation

        foundationD(true);     //Release foundation

        sleep(1000);    //wait for release

        driveDistance(.8,35);   //push foundation into wall

        driveAdjust(0, -0.5, 5, 2);  //back away from foundation

        strafeAdjust(0.4,12,0,true);    //align with parking spot (maybe switch to driveAdjust incase it gets stuck)

        driveDistance(-0.4, 85);     //Drive to the parking spot (backward)
    }
}