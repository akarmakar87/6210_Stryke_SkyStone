package org.firstinspires.ftc.teamcode.Autonomous.Regionals;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue Foundation(regional)", group = "auto")

//@Disabled
public class foundationBlueRegionals extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 15);    //align with foundation

        turnPID(-90, 0.6/90,0.0001,2,3000);   //turn toward foundation

        driveAdjust(270,0.6, 63, 4);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        driveAdjust(270, -0.6, 60, 5);  //pull foundation back

        foundationD(false);     //Release foundation

        sleep(250);    //wait for release

        strafeAdjust(0.6,15,0,true);   //Align with parking spot

        sleep(5000);    //wait for alliance partner to finish

        driveDistance(-0.4, 95);     //Drive to the parking spot (backward)
    }
}