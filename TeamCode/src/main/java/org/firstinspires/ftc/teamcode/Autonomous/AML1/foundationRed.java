package org.firstinspires.ftc.teamcode.Autonomous.AML1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Red", group = "auto")

//@Disabled
public class foundationRed extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);

        boolean red = true;
        int heading = 0, adjustment = 0;

        //should we keep this button pressing system? is it legal?

        waitForStart();

        strafeAdjust(0.5, 15, true);//Strafe in front of foundation

        driveDistance(-0.6, 48);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1500);

        driveDistance(0.6, 50);     //Drag foundation to build site

        foundationD(false);     //Release foundation

        turnPID(0, 0.6, 0, 2, 3000);   //Turn toward alliance bridge

        strafeDistance(0.6, 53,false);     // Drive to alliance bridge
    }
}
