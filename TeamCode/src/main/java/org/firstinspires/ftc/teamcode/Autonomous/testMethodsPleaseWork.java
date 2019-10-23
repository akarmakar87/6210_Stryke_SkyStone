package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.CameraDevice;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="pray to all the gods", group = "auto")

//@Disabled
public class testMethodsPleaseWork extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        turnPID(90, .01, 0, 0, 3000);
        telemetry.addData("turn", "done");
        telemetry.update();

        sleep(2000);

        telemetry.addData("end yaw: ", getYaw());
        telemetry.update();

        sleep(5000);

        strafeDistance(1,true, 10);

        //strafeDistance(1, true, 15);

        //driveDistance(0.5, 10);

        //telemetry.addData("drive forward", "done");
        //telemetry.update();

        //sleep(1000);

        //strafeDistance(0.5, 10, false);

        //telemetry.addData("strafe right", "done");
        //telemetry.update();



        //driveForward(-32, 50, -0.5, -90); // test drive backwards

        //telemetry.addData("drive forward", "done");
        //telemetry.update();
    }
}