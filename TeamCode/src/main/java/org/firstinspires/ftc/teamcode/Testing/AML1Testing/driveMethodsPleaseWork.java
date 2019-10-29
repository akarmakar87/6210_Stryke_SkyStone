package org.firstinspires.ftc.teamcode.Testing.AML1Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="pray to all the drive/strafe gods", group = "auto")

//@Disabled
public class driveMethodsPleaseWork extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        telemetry.addData("start yaw: ", getYaw());
        telemetry.update();

        sleep(2000);

        sleep(2000);

        driveAdjust(0.5, 10);

        sleep(500);

        strafeAdjust(0.5, 10, true);
    }
}