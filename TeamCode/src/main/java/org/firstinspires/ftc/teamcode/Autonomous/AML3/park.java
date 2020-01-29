package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.vuforia.CameraDevice;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="wall park", group = "auto")

//@Disabled
public class park extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        sleep(28000);

        driveDistance(0.5, 20);
    }
}