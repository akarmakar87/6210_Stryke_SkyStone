package org.firstinspires.ftc.teamcode.Autonomous.Regionals.Foundation;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue Foundation( flat )", group = "auto")

@Disabled
public class foundBlueFlat extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 20);    //align with foundation

        turnPID(90, 0.6/180,0.0001,0.5,5000);   //turn toward foundation

        driveAdjust(90,-0.5, 48, 4);    //Drive to foundation

        driveAdjust(90, -.2, 5, 2); //Carefully approach foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        turnArc(0, -0.8/90,-0.0001,-2, true, 3000);

        driveAdjust(0, -0.5, 15, 2);

        foundationD(false);

        sleep(250);

        turnPID(90, 0.8/90,0.0001,2, 4000);

        driveAdjust(90, 0.7, 25, 4);

        strafeAdjust(1, 25, 90, true);

        driveAdjust(90, -0.2, 15, 3);

        foundationD(true);     //Grab foundation

        sleep(250);

        driveAdjust(90, 0.8, 60,3);

        foundationD(false); //Release foundation

        strafeAdjust(0.8, 25, 0, false);

        turnPID(180, 0.8/90,0.0001,2, 4000);

        driveAdjust(90, 0.8, 100,5);

        strafeAdjust(0.8, 5, 0, true);
    }
}