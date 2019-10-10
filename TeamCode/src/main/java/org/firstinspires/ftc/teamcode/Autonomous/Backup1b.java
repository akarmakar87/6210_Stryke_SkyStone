package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="bread and one banana", group = "auto")

//@Disabled
public class Backup1b extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        boolean red = true;

        if (gamepad1.b) red = false;

        telemetry.addData("red? ", red);

        waitForStart();

        if (red){

        }
        else {

        }

    }

}
