package org.firstinspires.ftc.teamcode.Autonomous.AML1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Blue", group = "auto")

//@Disabled
public class foundationBlue extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);

        boolean red = false;
        int heading = 0, adjustment = 0;

        waitForStart();

            strafeAdjust(0.6, 30, false);//Strafe in front of foundation

            driveDistance(-0.6, 48);    //Drive to foundation

            foundationD(true);  //Grab foundation

            sleep(1500);

            driveDistance(1, 70);     //Drag foundation to build site

            foundationD(false);     //Release foundation

            turnPID(0, 0.6, 0, 2, 2000);   //Turn toward alliance bridge

            //strafeDistance(0.6, 53,false);     // Drive to alliance bridge
            strafeDistance(0.6, 30,false);     // Drive to alliance bridge
            driveDistance(-0.6, 30);     //Drag foundation to build site
            strafeDistance(0.6, 15,true);     // Drive to alliance bridge
            driveDistance(0.6, 35);     // Drive to alliance bridge
            strafeDistance(0.6, 27,false);     // Drive to alliance bridge


    }
}
