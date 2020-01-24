package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Blue3(inside park)", group = "auto")

//@Disabled
public class foundationBlue3inside extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 20);    //align with foundation

        turnPID(-90, 0.6/360, 0.001, 2, 3000);   //turn toward foundation

        driveAdjust(270,0.6, 53, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        //driveDistance(-1, 77);     //Pull foundation into building site

        driveAdjust(270, -0.2, 10, 4);

        driveAdjust(270, -0.6, 65, 5);

        turnPID(0, 0.6/360, 0.001, 2, 4000);


        foundationD(true);     //Release foundation

        sleep(1000);

        //driveAdjust(0,1, 35, 3);  //Push foundation into build site
        driveDistance(.5,35);

        strafeAdjust(0.6,10,0,true);

        //turnPID(90, 0.6/260, 0.001, 2, 4000);    //Turn parallel to bridge

        //driveDistance(0.5, 25); //Align with parking spot

        //turnPID(0, 0.6/360, .001, 2, 3000);  //Turn toward parking spot

        driveDistance(-0.4, 90);     //Drive to the parking spot (backward)
    }
}