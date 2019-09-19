package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Position 1", group = "auto")
//@Disabled
public class Position1 extends SkystoneLinearOpMode {

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

        // scan for skystones
        // driveForward to skystone farthest from the wall ----- are we strafing or are we turning and driving straight to the skystone
        // pick up skystone
        // turn and drive toward a spot just past the middle of the field
        // drop skystone
        // drive back towards other skystone and pick it up
        // turn and drive toward a spot just past the middle of the field



    }
}
