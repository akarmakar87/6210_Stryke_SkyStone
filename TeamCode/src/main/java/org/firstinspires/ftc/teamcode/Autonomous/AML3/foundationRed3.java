package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Red3", group = "auto")

//@Disabled
public class foundationRed3 extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 20);    //align with foundation

        turnPID(90, 0.6/360, 0.001, 2, 3000);   //turn toward foundation

        driveAdjust(90,0.6, 55, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        //driveDistance(-1, 77);     //Pull foundation into building site

        driveAdjust(90, -0.2, 10, 4);

        driveAdjust(90, -0.6, 65, 5);

        turnPID(0, 0.6/360, 0.001, 2, 4000);


        foundationD(true);     //Release foundation

        sleep(1000);

        //driveAdjust(0,1, 35, 3);  //Push foundation into build site
        driveDistance(.8,35);

        driveAdjust(0, -0.5, 5, 2);  //back away from foundation

        strafeAdjust(0.6,20,0,true);

        //turnPID(90, 0.6/260, 0.001, 2, 4000);    //Turn parallel to bridge

        //driveDistance(0.5, 25); //Align with parking spot

        //turnPID(0, 0.6/360, .001, 2, 3000);  //Turn toward parking spot

        driveDistance(-0.4, 75);     //Drive to the parking spot (backward)
    }
}