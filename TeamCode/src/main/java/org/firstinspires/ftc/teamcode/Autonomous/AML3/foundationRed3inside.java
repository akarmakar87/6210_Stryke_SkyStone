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

        driveDistance(0.4, 40);    //align with foundation

        strafeAdjust(0.4,2,0,false); // move away from wall

        turnPID(90, 0.6/90,0.001,2,5000);

        driveAdjust(90,0.5,50, 7); //GO TO foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        driveAdjust(90, -0.2, 5, 2);

        driveAdjust(90,-0.7, 75,4); // pull foundation into building site

        //driveDistance(-1, 75);     //Pull foundation into building site

        foundationD(true);

        strafeAdjust(0.7, 35, 90, false);

        driveAdjust(90, .5, 45, 5);

        turnPID(0, 0.7/90,0.001,2,4000);

        driveAdjust(0, -0.6, 50, 7);

        /*turnPID(0, 0.9/90, 0.004, 1, 7000);

        //setMotorPowers(0.8, -0.8);
        //sleep(1500);
        //stopMotors();

        //foundationD(true);     //Release foundation

        sleep(1000);

        driveDistance(0.8,20);

        //driveDistance(-0.5,5);

        turnPID(-90, 0.7/90, 0.004, 1, 8000);

        driveDistance(0.5, 25);

        turnPID(0, 0.7/90, .004, 1, 3000);

        driveDistance(-0.4, 75);     //Drive to the parking spot (backward)
        */
    }
}