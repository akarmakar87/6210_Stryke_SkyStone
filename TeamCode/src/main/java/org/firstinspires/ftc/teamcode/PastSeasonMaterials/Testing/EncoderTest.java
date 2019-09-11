package org.firstinspires.ftc.teamcode.PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name="MoveTest", group = "auto")

@Disabled

public class EncoderTest extends MecanumLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        strafeDistance(0.5, 10, true);
    }
}