package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Red2", group = "auto")

//@Disabled
public class foundationRed2 extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(-0.4, 20);    //align with foundation

        turnPID(90, 0.7/90, 0.004, 1, 5000);   //turn toward foundation

        driveDistance(-0.4, 39);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        setMotorPowers(0.2, 0.5);
        sleep(500);
        stopMotors();

        //turnPID(265,0.7/195, 0.004, 1, 2000);   //angle robot toward wall

        //driveDistance(0.4, 20);     //Drag foundation to wall at an angle

        //turnPID(180, 0.7/180, 0.004, 1, 2000);   //set robot to face directly away from the wall

        setMotorPowers(0.6, 0.2);
        sleep(500);
        stopMotors();

        driveDistance(1, 32);     //Pull foundation into building site

        //strafeAdjust(0.4, 30, false);

        turnPID(180, 0.7/90, 0.004, 1, 5000);

        driveDistance(-0.8,15);

        foundationD(true);     //Release foundation

        sleep(1000);

        driveDistance(0.4, 75);     //Drive to the parking spot (forward)
    }
}