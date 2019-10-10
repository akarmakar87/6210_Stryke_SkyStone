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
            driveDistance(0.8, 15); //Drive forward to foundation
            setClawPosition(true);
            driveDistance(0.8, -15); //Drive back to building zone
            setClawPosition(false);
            driveDistance(0.5, 5); //Drive back from foundation
            //turnPIDV(90, 0.4, 0, 0, 5);
            driveDistance(0.8, 25); //Drive to park
        }
        else {
            driveDistance(0.8, 15); //Drive forward to foundation
            setClawPosition(true);
            driveDistance(0.8, -15); //Drive back to building zone
            setClawPosition(false);
            driveDistance(0.5, 5); //Drive back from foundation
            //turnPIDV(-90, 0.4, 0, 0, 5);
            driveDistance(0.8, 25); //Drive to park
        }

    }

}
