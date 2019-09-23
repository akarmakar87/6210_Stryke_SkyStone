package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Position 1b", group = "auto") // PARKS NEXT TO WALL
//@Disabled
public class Position1b extends SkystoneLinearOpMode {

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
        setStrafePowers(0.8, false, 0);     // strafe to align with skystone
        driveToPoint(0.8, 50, 50);      // drive toward skystone
        // pick up skystone
        driveToPoint(0.8, 50, 50);      // turns and drives toward other side of field
        // drop skystone
        driveToPoint(0.8, 50, 50);      // turns? back around and drives to other skystone
        // or drive backwards to other skystone
        turnPIDV(0, 0, 0, 0, false);   // turns to face other skystone
        // pick up skystone
        driveToPoint(0.8, 50, 50);      // turns and drives toward other side of field
        // drop skystone
        driveToPoint(0.5, 0, 0);        // parks on tape NEAR WALL

        telemetry.addData("cake", "baked");
        telemetry.addData("red velvet cake", "is a bad version of chocolate");
        telemetry.update();
    }
}
