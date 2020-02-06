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

        strafeAdjust(0.5, 15, 0, true); //align with foundation

        driveAdjust(270,-0.6, 63, 4);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        driveAdjust(270, 0.4, 60, 5);  //pull foundation back

        foundationD(false);     //Release foundation

        sleep(5000);    //wait for alliance partner to finish

        strafeAdjust(0.5,60,0,false);   //Align with parking spot
    }
}