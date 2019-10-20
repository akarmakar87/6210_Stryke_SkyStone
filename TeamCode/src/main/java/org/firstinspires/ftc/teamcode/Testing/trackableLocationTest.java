package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Le Croissant:", group = "auto")  // PARKS AWAY FORM WALL

//@Disabled
public class trackableLocationTest extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        // SET UP DETECTOR

        initVuforia();

        telemetry.addData("Mode", "setting up detector...");
        telemetry.update();

        telemetry.addData("detector", "enabled");
        telemetry.update();

        waitForStart();

        telemetry.addData("Pos:", getSkystonePos());
        telemetry.update();
    }


}
