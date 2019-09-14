package org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@TeleOp(name="Gyro", group="sensor")
@Disabled
public class GyroTeleOp extends MecanumLinearOpMode {


    @Override
    public void runOpMode() {

        init(hardwareMap, true);

        while (opModeIsActive()) {

            //updateValues();
            telemetry.addData("Raw angle: ", getYaw());
            telemetry.addData("Converted 360 angle: ", getYaw());
            telemetry.update();
        }
    }
}
