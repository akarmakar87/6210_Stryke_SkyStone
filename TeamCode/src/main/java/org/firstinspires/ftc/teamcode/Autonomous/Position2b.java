package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Position 2b", group = "auto") // PARKS AWAY FROM WALL
@Disabled
public class Position2b extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        // SET UP DETECTOR

        initVuforia();

        telemetry.addData("Mode", "setting up detector...");
        telemetry.update();

        telemetry.addData("detector", "enabled");
        telemetry.update();

        boolean red = isRed(1000); // outputs whether we are on red or blue side
        position();

        waitForStart();

        if (red) {
            driveToPoint(0.8, RobotCoordinates.one_2.get(0), -RobotCoordinates.one_2.get(1)); // drive forward to catch target
            driveToPoint(0.8, RobotCoordinates.two_2.get(0), -RobotCoordinates.two_2.get(1)); // drive to foundation
            setClawPosition(true);
            driveForward(RobotCoordinates.three_2.get(0), -RobotCoordinates.three_2.get(1), -0.8, -RobotCoordinates.three_2.get(2)); // back up dragging foundation
            setClawPosition(false);
            driveForward(RobotCoordinates.four_2.get(0), -RobotCoordinates.four_2.get(1), -0.5, -RobotCoordinates.four_2.get(2)); // back up from foundation
            driveToPoint(0.5, RobotCoordinates.five_2b.get(0), -RobotCoordinates.five_2b.get(1)); // drive forward
            StrafetoPosition(0.5, RobotCoordinates.six_2b.get(0), -RobotCoordinates.six_2b.get(1), -RobotCoordinates.six_2b.get(2)); // strafe to park Y pos
            driveForward(RobotCoordinates.seven_2b.get(0), -RobotCoordinates.seven_2b.get(1), 0.5, -RobotCoordinates.seven_2b.get(2)); // park

            /*
            driveToPoint(0.8, 50, 50); // drives up to foundation
            setClawPosition(false);// grabs foundation (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            driveToPoint(0.8, 50, 50); // drives backwards? pulling foundation to depot
            setClawPosition(true);// lets go of foundation (Don't know how Pranav has robot designed so I will adjust once I know how robot will work)
            StrafetoPosition(1, 50, 50, getRobotHeading()); // strafes to park NEAR WALL
            */

        } else {
            // blue code
            driveToPoint(0.8, RobotCoordinates.one_2.get(0), RobotCoordinates.one_2.get(1)); // drive forward to catch target
            driveToPoint(0.8, RobotCoordinates.two_2.get(0), RobotCoordinates.two_2.get(1)); // drive to foundation
            setClawPosition(true);
            driveForward(RobotCoordinates.three_2.get(0), RobotCoordinates.three_2.get(1), -0.8, RobotCoordinates.three_2.get(2)); // back up dragging foundation
            setClawPosition(false);
            driveForward(RobotCoordinates.four_2.get(0), RobotCoordinates.four_2.get(1), -0.5, RobotCoordinates.four_2.get(2)); // back up from foundation
            driveToPoint(0.5, RobotCoordinates.five_2b.get(0), RobotCoordinates.five_2b.get(1)); // drive forward
            StrafetoPosition(0.5, RobotCoordinates.six_2b.get(0), RobotCoordinates.six_2b.get(1), RobotCoordinates.six_2b.get(2)); // strafe to park Y pos
            driveForward(RobotCoordinates.seven_2b.get(0), RobotCoordinates.seven_2b.get(1), 0.5, RobotCoordinates.seven_2b.get(2)); // park
        }

        telemetry.addData("jerry", "yes");
        telemetry.update();
    }
}
