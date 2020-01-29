package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="right side bridge park", group = "auto")

//@Disabled
public class outsidePark_right extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        sleep(25000);

        driveDistance(0.5, 60);    //align with parking spot

        turnPID(90, 0.7/90, 0.004, 1, 5000);   //turn toward parking spot

        driveDistance(0.5, 25);    //park
    }
}
