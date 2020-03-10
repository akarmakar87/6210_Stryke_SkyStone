package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Color sensor Test", group="auto")
//@Disabled
public class colorTest extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap, true);

        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            telemetry.addData("red", colorSensor.red());
            telemetry.addData("green", colorSensor.green());
            telemetry.addData("blue", colorSensor.blue());
            telemetry.update();
        }

    }
}