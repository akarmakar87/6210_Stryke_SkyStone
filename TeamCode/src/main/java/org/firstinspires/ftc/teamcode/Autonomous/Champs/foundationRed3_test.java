package org.firstinspires.ftc.teamcode.Autonomous.Champs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Red3(test)", group = "auto")

//@Disabled
public class foundationRed3_test extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 30);    //align with foundation

        strafeAdjust(0.4,2,0,false); // move away from wall

        sleep(250);

        turnPID(90, 0.6/360,0.001,2,4000);

        driveAdjust(90,0.5,50, 5); //GO TO foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        driveAdjust(90, -0.2, 5, 2);

        driveAdjust(90,-0.7, 78,4); // pull foundation into building site

        //driveDistance(-1, 75);     //Pull foundation into building site

        turnPID(0, 0.8/360,0.001,2,5000);

        foundationD(true);

        driveAdjust(0, 1, 40, 4);

        strafeAdjust(0.4,10,0,true); // align with parking spot

        turnPID(0, 0.6/360,0.001,2,3000);   //straighten out

        driveAdjust(0, -0.7/360, 75, 4000);   //drive into parking spot

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