package org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;
import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name = "ClawTest", group = "Sensor")
//@Disabled
public class Claw_Garrett extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();
        while (opModeIsActive() && !isStopRequested()){
            claw.setPosition(0);
            wait(3000);
            claw.setPosition(1);
        }

    }
}
