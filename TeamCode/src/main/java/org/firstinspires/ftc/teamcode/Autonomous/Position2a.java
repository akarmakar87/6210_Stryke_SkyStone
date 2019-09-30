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

        boolean red = isRed(); // outputs whether we are on red or blue side
        //position();

        waitForStart();

        if (red) {
            driveToPoint(0.8, 50, 50); // drives up to foundation
            setClawPosition(false);// grabs foundation (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            driveToPoint(0.8, 50, 50); // drives backwards? pulling foundation to depot
            setClawPosition(true);// lets go of foundation (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            StrafetoPosition(1, 50, 50, getRobotHeading()); // strafes to park NEAR WALL
        } else {
            // blue code
        }

        telemetry.addData("garrett", "yes");
        telemetry.update();
    }
}
