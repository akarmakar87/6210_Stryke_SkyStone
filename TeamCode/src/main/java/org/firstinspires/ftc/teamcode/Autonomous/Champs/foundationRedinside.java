package org.firstinspires.ftc.teamcode.Autonomous.Champs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Red inside sample", group = "auto")

//Disabled
public class foundationRedinside extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 40);    //align with foundation

        strafeAdjust(0.4,2,0,false); // move away from wall

        turnPID(90, 0.6/90,0.001,2,5000);

        driveAdjust(90,0.5,57 + 2, 7); //GO TO foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        driveAdjust(90, -0.2, 5, 2);

        driveAdjust(90,-0.7, 75,4); // pull foundation into building site

        //driveDistance(-1, 75);     //Pull foundation into building site

        turnPID(0, 0.9/360,0.001,2,5000);

        foundationD(true);

        driveAdjust(0, 0.5, 10, 2);

        strafeAdjust(0.7, 30, 90, false);

        turnPID(180, 0.9/360,0.001,2,5000);

        driveAdjust(180, 0.7, 120, 4);

        turnPID(90, 0.9/360,0.001,2,5000);

        driveAdjust(90, 0.5, 10, 2);

        foundationL.setPosition(0);

        sleep(500);

        driveAdjust(90, -0.5, 10, 2);

        turnPID(0, 0.9/360,0.001,2,5000);

        driveAdjust(0, 0.5, 30, 2);

        foundationL.setPosition(1);

        sleep(500);

        driveAdjust(0, -0.5, 15, 2);

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