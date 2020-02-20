package org.firstinspires.ftc.teamcode.Autonomous.Regionals.Foundation;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue Foundation Turn", group = "auto")

//@Disabled
public class foundationBlueTurn extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 15);    //align with foundation

        turnPID(90, 0.6/180,0.0001,0.5,5000);   //turn toward foundation

        driveAdjust(90,-0.5, 48, 4);    //Drive to foundation

        driveAdjust(90, -.2, 5, 2); //Carefully approach foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        turnPIDF(80, .8/90,.0001, 2, 3000);

        driveAdjust(90, 2, 70, 4);  //pull foundation back

        turnPIDF(180, 0.8/90,0.0001,2, 4000);    //turn robot & foundation toward wall

        //driveAdjust(0, -0.7, 15, 2); //push foundation into wall

        foundationD(false);     //Release foundation

        sleep(250);    //wait for release

        driveAdjust(0, 0.5, 10, 2);  //back away from foundation

        strafeAdjust(0.6,10,0,true);   //Align with parking spot

        driveAdjust(0, 0.7, 95, 2);  //park
    }
}