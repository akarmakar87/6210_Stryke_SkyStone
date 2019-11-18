package org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;
import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name = "ClawTest", group = "Sensor")
@Disabled
public class Claw_Garrett extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();
        //claw.setPosition(0);
        Thread.sleep(3000);
        //claw.setPosition(1);
        Thread.sleep(3000);
    }
}
