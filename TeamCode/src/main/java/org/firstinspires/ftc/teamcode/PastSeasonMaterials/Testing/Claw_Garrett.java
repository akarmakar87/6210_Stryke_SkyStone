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

        init(hardwareMap, true);

        waitForStart();
        manip.setPosition(0);
        wait(3000);
        manip.setPosition(1);

    }
}
