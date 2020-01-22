package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Blue3", group = "auto")

//@Disabled
public class foundationBlue3 extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 33);    //align with foundation

        turnPID(-90, 0.7/90, 0.004, 1, 5000);   //turn toward foundation

        driveAdjust(90,0.6, 57, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        //driveDistance(-1, 77);     //Pull foundation into building site

        driveAdjust(-90, -0.2, 5, 2);

        driveAdjust(-90, -0.7, 77, 4);

        turnPID(0, 0.6/360, 0.001, 2, 4000);


        foundationD(true);     //Release foundation

        sleep(1000);

        driveAdjust(0,0.7, 30, 2);  //Push foundation into build site

        //driveDistance(-0.5,5);

        turnPID(90, 0.7/90, 0.004, 1, 4000);    //Turn parallel to bridge

        driveDistance(0.5, 25); //Align with parking spot

        turnPID(0, 0.7/90, .004, 1, 3000);  //Turn toward parking spot

        driveDistance(-0.4, 75);     //Drive to the parking spot (backward)
    }
}