package org.firstinspires.ftc.teamcode.Autonomous.Regionals;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Park Right Side Bridge", group = "auto")

//@Disabled
public class parkOutRight extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        sleep(25000);

        driveDistance(0.5, 60);    //align with parking spot

        turnPID(90, 0.6/90, 0.0001, 2, 5000);   //turn toward parking spot

        driveDistance(0.5, 25);    //park
    }
}
