package org.firstinspires.ftc.teamcode.PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PastSeasonMaterials.MecanumLinearOpMode;


@Autonomous(name="GyroTurn", group = "Sensor")

@Disabled

public class GyroTest extends MecanumLinearOpMode {

    double power, correction;

    @Override
    public void runOpMode() {

        init(hardwareMap, true);

        waitForStart();

        rotate(0.2, 90);
        rotate(0.2, -90);

        telemetry.addData("Status", "done");
        telemetry.update();

    }

}