package org.firstinspires.ftc.teamcode.Autonomous.AML1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation", group = "auto")

@Disabled
public class foundation extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);

        boolean red = true;
        int heading = 0, adjustment = 0;

        //should we keep this button pressing system? is it legal?
        while (!isStarted() && opModeIsActive()) {

            if (gamepad1.b) red = false;

            telemetry.addData("red? ", red);
            telemetry.update();
        }

        waitForStart();

        if(red){
            strafeDistance(0.5, 10, false); //Strafe in front of foundation

            driveDistance(-0.5,30 );    //Drive to foundation

            foundationD(true);  //Grab foundation

            driveDistance(0.5, 30);     //Drag foundation to build site

            foundationD(false);     //Release foundation

            turnPID(90, 0.6, 0, 2, 3000);   //Turn toward alliance bridge

            driveDistance(0.5, 30);     // Drive to alliance bridge
        }

        else{
            strafeAdjust(0.5, 10, false);//Strafe in front of foundation

            driveDistance(-0.5, 45);    //Drive to foundation

            foundationD(true);  //Grab foundation

            sleep(1500);

            driveDistance(0.5, 45);     //Drag foundation to build site

            foundationD(false);     //Release foundation

            turnPID(-90, 0.6, 0, 2, 3000);   //Turn toward alliance bridge

            driveDistance(0.5, 50);     // Drive to alliance bridge
        }
    }
}
