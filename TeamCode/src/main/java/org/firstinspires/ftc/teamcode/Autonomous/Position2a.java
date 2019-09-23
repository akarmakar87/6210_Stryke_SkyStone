package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Position 2a", group = "auto") // PARKS NEXT TO WALL
//@Disabled
public class Position2a extends SkystoneLinearOpMode {

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

        driveToPoint(0.8,50, 50); // drives up to foundation
        // grabs foundation
        driveToPoint(0.8, 50, 50); // drives backwards? pulling foundation to depot
        // lets go of depot
        setStrafePowers(0.8, false, 0); // strafes to park NEAR WALL

        telemetry.addData("donut", "fried");
        telemetry.addData("there's a hole", "in your code");
        telemetry.addData("O", "<< look hole");
        telemetry.update();
    }
}
