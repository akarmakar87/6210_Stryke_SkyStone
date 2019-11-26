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

        strafeAdjust(0.6, 50, true);//Strafe in front of foundation

        driveDistance(-0.6, 50);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1500);

        turnPID(-15,0.6, 0, 2, 2000);   //angle robot toward wall

        driveDistance(0.6, 20);     //Drag foundation to wall at an angle

        turnPID(0, 0.6, 0, 2, 2000);   //set robot to face directly away from the wall

        driveDistance(0.6, 50);     //Pull foundation into building site

        foundationD(false);     //Release foundation

        sleep(1000);

        strafeDistance(0.25, 70, false);      //strafe away from foundation

        turnPID(90, 0.6, 0, 2, 2000);   //Align with parking spot

        driveDistance(0.6, 12);     //Drive to the parking spot (forward)
    }
}
