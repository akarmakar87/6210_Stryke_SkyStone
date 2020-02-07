package org.firstinspires.ftc.teamcode.Autonomous.Regionals;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red Foundation Regionals", group = "auto")

//@Disabled
public class foundationRedRegionals extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 11+5);    //align with foundation

        turnPID(-90, 0.6/180,0.0001,0.5,5000);   //turn toward foundation

        driveAdjust(270,-0.5, 27, 4);    //Drive to foundation

        driveAdjust(270, -.2, 5, 2); //Carefully approach foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        //turnPIDF(-70, .8/90,.0001, 2, 3000);

        driveAdjust(270, 2, 85, 4);  //pull foundation back

        turnPIDF(-90, .8/90,.0001, 2, 3000);

        foundationD(false);     //Release foundation

        sleep(14000);    //wait for alliance partner to finish

        strafeAdjust(0.5,18,-90,true);   //strafe out of building site

        turnPID(-180, 0.6/180,0.0001,0.5,5000);   //turn toward parking spot

        strafeAdjust(0.5,5,-90,false);   //Align with parking spot

        driveAdjust(-180, 0.5, 30,2);  //drive into parking spot
    }
}