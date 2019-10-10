package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="bread and one apple", group = "auto")

//@Disabled
public class Backup1a extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        boolean red = true;

        while(!isStarted()) {
            if (gamepad1.b) red = false;
            telemetry.addData("red? ", red);
            telemetry.update();
        }

        waitForStart();

        // scan for skystone
        // variable for distance robot has to strafe to be in front of skystone
        // values will be different for blue and red side so change within if else statement
        int stonePos = 0;

        if (red){

            driveDistance(0.5, 5);                // move away from wall
            strafeDistance(0.5, stonePos, true);    // strafe in front of skystone
            driveDistance(0.5, 20);              // move toward skystone
            setClawPosition(true);
            

        }
        else {

        }

    }

}
