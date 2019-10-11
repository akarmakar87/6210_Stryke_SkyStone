package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="bread and two apples", group = "auto")

//@Disabled
public class Backup2a extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        boolean red = true;

        if (gamepad1.b) red = false;

        telemetry.addData("red? ", red);

        waitForStart();

        if (red){
            strafeDistance(0.8, 15, true); //Strafe to middle of foundation
            driveDistance(0.8, 30); //Drive forward to foundation
            setClawPosition(true); //Pick up foundation
            driveDistance(0.8, -25); //Drive back to building zone
            setClawPosition(false); //Drop off foundation
            driveDistance(0.5, 5); //Drive back from foundation
            strafeDistance(0.8, 50, false); //Strafe to park
        }
        else {
            strafeDistance(0.8, 15, false); //Strafe to middle of foundation
            driveDistance(0.8, 30); //Drive forward to foundation
            setClawPosition(true); //Pick up foundation
            driveDistance(0.8, -25); //Drive back to building zone
            setClawPosition(false); //Drop off foundation
            driveDistance(0.5, 5); //Drive back from foundation
            strafeDistance(0.8, 50, true); //Strafe to park
        }

    }

}
