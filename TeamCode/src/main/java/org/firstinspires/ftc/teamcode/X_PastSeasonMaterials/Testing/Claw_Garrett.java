package org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name = "ColorTutorial", group = "Sensor")
//@Disabled
public class Claw_Garrett extends MecanumLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();
        manip.setPosition(0);
        wait(3000);
        manip.setPosition(1);

    }
}
