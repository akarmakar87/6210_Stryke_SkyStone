package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Blue2", group = "auto")

//@Disabled
public class foundationBlue2 extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(-0.4, 20);    //align with foundation

        turnPID(274, 0.7/270, 0.004, 1, 5000);   //turn toward foundation

        driveDistance(-0.4, 39);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        setMotorPowers(0.2, 0.6);
        sleep(500);
        stopMotors();



        setMotorPowers(0.6, 0.2);
        sleep(500);
        stopMotors();

        //driveDistance(1, 32);     //Pull foundation into building site

        //strafeAdjust(0.4, 30, false);

        turnPID(360, 0.4/120, 0.004, 1, 5000);

        foundationD(true);     //Release foundation

        sleep(300);

        driveDistance(-0.8,23); //push foundation forward

        setMotorPowers(-0.2, 0.6);
        sleep(500);
        stopMotors();

        driveDistance(0.8,10);

        setMotorPowers(0.6, -0.2);
        sleep(500);
        stopMotors();

        driveDistance(0.8,30);

        // driveDistance(0.8,10); //move back a little

        //turnPID(90, 0.7/90, 0.004, 1, 5000); //turn toward the wall

        //driveDistance(-0.4, 22); //drive toward the wall

        //turnPID(180, 0.7/90, 0.004, 1, 5000); //turn to the park

        //turnPID(270, 0.7/90, 0.004, 1, 5000); //turn to the park

        //driveDistance(-0.4, 48);     //Drive to the parking spot

    }
}
