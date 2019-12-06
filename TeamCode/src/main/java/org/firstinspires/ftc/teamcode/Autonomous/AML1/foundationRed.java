package org.firstinspires.ftc.teamcode.Autonomous.AML1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Red", group = "auto")

@Disabled
public class foundationRed extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        //strafeAdjust(0.6, 30, true); <--ORIGINAL
        strafeDistance(0.8, 35, false);//Strafe in front of foundation

        //driveDistance(-0.6, 48);    <--ORIGINAL
        driveDistance(-0.5, 45);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1500);

        //driveDistance(1, 70); <--ORIGINAL
        driveDistance(1, 72);     //Drag foundation to build site

        foundationD(false);     //Release foundation

        turnPID(0, 0.6, 0, 2, 2000);   //align to zero

        //strafeDistance(0.6, 53,false);     // Drive to alliance bridge (REMOVE)

        strafeDistance(0.6, 35,true);     // Drive a little towards alliance bridge

        driveDistance(-0.6, 30);     //move away from wall (forward)

        strafeDistance(0.6, 15,false);     // move left to push foundation against wall

        driveDistance(0.6, 35);     // move back against wall

        strafeDistance(0.6, 27,true);     // strafe to park
    }
}
