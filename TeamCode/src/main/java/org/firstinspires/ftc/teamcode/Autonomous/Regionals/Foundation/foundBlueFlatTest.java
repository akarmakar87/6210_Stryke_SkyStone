package org.firstinspires.ftc.teamcode.Autonomous.Regionals.Foundation;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue Foundation( flat )*test* ", group = "auto")

@Disabled
public class foundBlueFlatTest extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 11);    //align with foundation

        turnPID(90, 0.6/180,0.0001,0.5,5000);   //turn toward foundation

        driveAdjust(90,-0.5, 48, 4);    //Drive to foundation

        driveAdjust(90, -.2, 5, 2); //Carefully approach foundation

        foundationD(true);  //Grab foundation

        sleep(1000);    //wait for grab

        turnPIDF(70, .8/90,.0001, 2, 3000);

        driveAdjust(80, 2, 90, 4);  //pull foundation back

        turnArc(0, -0.8/90,-0.0001,-2, true, 3000);

        driveAdjust(0, -0.5, 10, 2);

        foundationD(false);

        sleep(250);

        strafeAdjust(0.7, 20, 90, true);

        driveAdjust(90, 0.8, 90,5);

        strafeAdjust(0.8, 5, 0, true);
    }
}