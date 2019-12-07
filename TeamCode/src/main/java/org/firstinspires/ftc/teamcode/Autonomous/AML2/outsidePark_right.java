package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="inside park right", group = "auto")

//@Disabled
public class outsidePark_right extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(-0.4, 25);    //align with parking spot

        turnPID(90, 0.7/270, 0.004, 1, 5000);   //turn toward parking spot

        driveDistance(-0.4, 10);    //park
    }
}
