package org.firstinspires.ftc.teamcode.Autonomous.Champs;

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

        driveDistance(0.4, 40);    //align with foundation

        strafeAdjust(0.4,2,0,false); // move away from wall

        sleep(250);

        turnPID(90, 0.6/360,0.001,2,4000);

        driveAdjust(90,0.5,50, 5); //GO TO foundation

        foundationD(false);  //Grab foundation

        sleep(1000);

        driveAdjust(90, -0.2, 5, 2);

        driveAdjust(90,-0.7, 78,4); // pull foundation into build site

        turnPIDF(0, 0.8/90, 0.0001, 2, 4000);

        foundationD(true);

        driveAdjust(0, 0.5, 10, 2);

        strafeAdjust(0.7, 20, 90, true);

        turnPID(180, 0.9/360,0.001,2,5000);

        driveAdjust(180, 0.7, 75, 4);
    }
}