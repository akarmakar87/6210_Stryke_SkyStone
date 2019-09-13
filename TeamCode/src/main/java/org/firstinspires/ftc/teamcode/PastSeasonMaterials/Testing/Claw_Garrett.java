package org.firstinspires.ftc.teamcode.PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name = "ColorTutorial", group = "Sensor")
//@Disabled
public class Claw_Garrett extends MecanumLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, false);

        waitForStart();
        manip.setPosition(0.25);
        wait(1000);
        manip.setPosition(0.75);

    }
}
