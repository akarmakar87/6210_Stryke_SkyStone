package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Red3(inside park)", group = "auto")

//Disabled
public class foundationRed3inside extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 30);    //align with foundation

        turnPID(90, 0.7/90, 0.004, 1, 8000);   //turn toward foundation

        driveDistance(0.6, 60);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        driveDistance(-1, 65);     //Pull foundation into building site

        turnPID(0, 0.7/90, 0.004, 1, 7000);

        /*setMotorPowers(0.8, -0.8);
        sleep(1500);
        stopMotors();*/

        foundationD(true);     //Release foundation

        sleep(1000);

        driveDistance(0.8,10);

        driveDistance(-0.8,10);

        turnPID(90, 0.9/90, 0.004, 1, 8000);

        driveDistance(0.5, 9);

        turnPID(180, 0.7/90, .004, 1, 3000);

        driveDistance(0.4, 58);     //Drive to the parking spot (forward)
    }
}