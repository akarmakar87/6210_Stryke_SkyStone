package org.firstinspires.ftc.teamcode.Autonomous.Regionals.Foundation;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red Foundation Turn", group = "auto")

//@Disabled
public class foundRedTurn extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 11+5);    //align with foundation

        turnPID(-90, 0.6/180,0.0001,0.5,5000);   //turn toward foundation

        driveAdjust(270,-0.5, 47, 4);    //Drive to foundation

        driveAdjust(270, -.2, 5, 2); //Carefully approach foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        turnPIDF(-100, .8/90,.0001, 2, 3000);

        driveAdjust(280, 2, 85, 4);  //pull foundation back

        turnPIDF(180, 1.5/90,.0001, 2, 6000);

        driveAdjust(180, -.8, 5, 2);  //push foundation against wall

        foundationD(false);     //Release foundation

        //sleep(14000);    //wait for alliance partner to finish

        strafeAdjust(0.5,10,270,false);   //strafe to wall

        turnPID(-180, 0.6/180,0.0001,0.5,5000);   //turn toward parking spot

        sleep(10000);

        driveAdjust(-180, 0.5, 100,2);  //drive into parking spot
    }
}