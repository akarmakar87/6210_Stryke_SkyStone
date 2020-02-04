package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;


@Autonomous(name="Red Foundation Strafe", group = "auto")

public class redFoundationStrafe extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 30);    //align with foundation

        strafeAdjust(0.6,2,0,false);

        turnPID(90, 0.6/90,0.0001,2, 3000);   //turn toward foundation

        driveAdjust(90,0.5, 53, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);    //wait to grab foundation

        driveAdjust(90, -0.2, 10, 4);   //pull hooks into place

        driveAdjust(90, -0.7, 60, 5);   //pull foundation into build site

        foundationD(true);

        turnPID(0, 0.6/90,0.0001,2, 3000);

        strafeAdjust(0.6,2,0,true);

        driveAdjust(0, -0.5, 60, 5);

    }
}
