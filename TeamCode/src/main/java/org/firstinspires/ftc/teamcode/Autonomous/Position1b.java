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

        boolean red = isRed(); // outputs whether we are on red or blue side

        waitForStart();

        if (red) {
            // scan for skystones
            StrafetoPosition(1, 50, 50, getRobotHeading());     // strafe to align with skystone
            driveToPoint(0.8, 50, 50);      // drive toward skystone
            setClawPosition(false);// pick up skystone (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            driveToPoint(0.8, 50, 50);      // turns and drives toward other side of field
            setClawPosition(true);// drop skystone (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            driveToPoint(0.8, 50, 50);      // turns? back around and drives to other skystone
            // or drive backwards to other skystone
            turnPIDV(0, 0, 0, 0, false);   // turns to face other skystone
            setClawPosition(false);// pick up skystone (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            driveToPoint(0.8, 50, 50);      // turns and drives toward other side of field
            setClawPosition(true);// drop skystone (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            driveToPoint(0.5, 0, 0);        // parks on tape NEAR WALL
        }  else {
            // blue code
        }

        telemetry.addData("mindy", "yes");
        telemetry.update();
    }
}
