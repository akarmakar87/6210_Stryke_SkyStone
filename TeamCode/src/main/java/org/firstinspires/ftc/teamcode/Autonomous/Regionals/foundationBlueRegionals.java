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

        driveDistance(0.4, 11);    //align with foundation

        turnPID(90, 0.6/90,0.0001,2,3000);   //turn toward foundation

        driveAdjust(90,-0.5, 43, 4);    //Drive to foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        turnPIDF(70, .8/90,.0001, 2, 3000);

        driveAdjust(80, 2, 90, 4);  //pull foundation back

        turnPIDF(90, .8/90,.0001, 2, 3000);

        foundationD(false);     //Release foundation

        sleep(5000);    //wait for alliance partner to finish

        strafeAdjust(0.5,23,90,false);   //Align with parking spot

        driveAdjust(90, 0.5, 10,2);
    }
}