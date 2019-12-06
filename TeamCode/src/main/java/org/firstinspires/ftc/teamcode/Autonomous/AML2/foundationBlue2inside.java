package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Blue2(inside park)", group = "auto")

@Disabled
public class foundationBlue2inside extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        strafeAdjust(0.6, 30, true);//Strafe in front of foundation

        driveDistance(-0.6, 50);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1500);

        driveDistance(1, 72);     //Drag foundation to build site

        turnPID(90, 0.6, 0, 2, 2000);   //Rotate robot (w/ foundation) toward the wall

        driveDistance(-0.6, 6);     //Push foundation against the wall

        foundationD(false);     //Release foundation

        driveDistance(0.6, 6);      //Move from foundation

        turnPID(0, 0.6, 0, 2, 2000);   //Align to zero

        driveDistance(-0.6, 8);     //Align with inside parking spot (backward)     --- Only code that is changed for different parking spot

        turnPID(-90,0.6,0,2,2000);      //Turn toward alliance bridge

        driveDistance(0.6, 45);     //Drive under alliance bridge
    }
}
