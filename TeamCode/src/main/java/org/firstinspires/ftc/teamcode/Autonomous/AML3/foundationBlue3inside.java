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

        driveDistance(0.4, 33);    //align with foundation

        turnPID(-90, 0.7/90, 0.004, 1, 8000);   //turn toward foundation

        driveDistance(0.6, 52);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        //driveDistance(-1, 77);     //Pull foundation into building site

        driveAdjust(-90, -1, 77, 6);

        turnPID(0, 0.7/90, 0.004, 1, 7000);

        /*setMotorPowers(0.8, -0.8);
        sleep(1500);
        stopMotors();*/

        foundationD(true);     //Release foundation

        sleep(1000);

        driveDistance(0.8,20);

        //driveDistance(-0.5,5);

        turnPID(-90, 0.7/90, 0.004, 1, 8000);

        driveDistance(0.5, 30);

        turnPID(180, 0.7/90, .004, 1, 3000);

        driveDistance(0.4, 75);     //Drive to the parking spot (backward)
    }
}