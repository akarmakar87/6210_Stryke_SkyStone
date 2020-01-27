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

        driveDistance(0.4, 20);    //align with foundation

        turnPID(90, 0.6/360, 0.001, 2, 3000);   //turn toward foundation

        driveAdjust(90,0.6, 55, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        //driveDistance(-1, 77);     //Pull foundation into building site

        driveAdjust(90, -0.2, 10, 4);

        driveAdjust(90, -0.7, 65, 5);

        turnPID(0, 0.6/360, 0.001, 2, 4000);

        /*setMotorPowers(0.8, -0.8);
        sleep(1500);
        stopMotors();*/

        foundationD(true);     //Release foundation

        sleep(1000);

        driveDistance(.8,35);

        driveAdjust(0, -0.5, 5, 2);  //back away from foundation

        //driveDistance(-0.5,5);

        strafeAdjust(0.6,12,0,false);

        driveDistance(-0.4, 85);     //Drive to the parking spot (backward)
    }
}