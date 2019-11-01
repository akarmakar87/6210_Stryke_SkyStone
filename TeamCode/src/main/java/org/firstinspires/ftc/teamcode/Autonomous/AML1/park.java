package org.firstinspires.ftc.teamcode.Autonomous.AML1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="simple park", group = "auto")

//@Disabled
public class park extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.5, 40);
    }
}