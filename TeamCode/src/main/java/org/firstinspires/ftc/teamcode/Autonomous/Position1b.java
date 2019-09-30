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

        boolean red = isRed(1000); // outputs whether we are on red or blue side
        //position();

        waitForStart();

        // scan for skystone
        int x = 0; // represents X pos of skystone
        if (red) {
            // red code
            driveToPoint(0.8, RobotCoordinates.one_1red.get(0), RobotCoordinates.one_1red.get(1)); // drive forward
            StrafetoPosition(0.5, x, RobotCoordinates.three_1red.get(1), RobotCoordinates.three_1red.get(2)); // strafe to stone X pos

            driveToPoint(0.8, x, -RobotCoordinates.four_1.get(1)); // drive forward to stone
            // pick up stone
            driveForward(x, -RobotCoordinates.five_1b.get(1), -0.5, -RobotCoordinates.five_1b.get(2)); // back up from stone
            driveToPoint(0.5, RobotCoordinates.six_1b.get(0), -RobotCoordinates.six_1b.get(1)); // drive to other side
            // drop off stone
            driveForward(x, -RobotCoordinates.seven_1b.get(1), -0.5, -RobotCoordinates.seven_1b.get(2)); // drive back to other stone
            turnPIDV(90, 0, 0, 0, false); // turn to stone
            driveToPoint(0.5, x, -RobotCoordinates.eight_1b.get(1)); // drive up to other stone
            driveForward(x, -RobotCoordinates.nine_1b.get(1), -0.5, -RobotCoordinates.nine_1b.get(2)); // back up from stone
            driveToPoint(0.5, RobotCoordinates.ten_1b.get(0), -RobotCoordinates.ten_1b.get(1)); // drive to other side
            // drop off stone
            driveForward(RobotCoordinates.elev_1a.get(0), -RobotCoordinates.elev_1b.get(1), -0.5, -RobotCoordinates.elev_1b.get(2)); // back up and park

        } else {
            // blue code
            driveToPoint(0.8, RobotCoordinates.one_1blue.get(0), RobotCoordinates.one_1blue.get(1)); // drive forward
            StrafetoPosition(0.5, RobotCoordinates.two_1blue.get(0), RobotCoordinates.two_1blue.get(1), RobotCoordinates.two_1blue.get(2)); // strafe to catch target
            StrafetoPosition(0.5, x, RobotCoordinates.three_1blue.get(1), RobotCoordinates.three_1blue.get(2)); // strafe to stone X pos

            driveToPoint(0.8, x, RobotCoordinates.four_1.get(1)); // drive forward to stone
            // pick up stone
            driveForward(x, RobotCoordinates.five_1a.get(1), -0.5, RobotCoordinates.five_1a.get(2)); // back up from stone
            driveToPoint(0.5, RobotCoordinates.six_1a.get(0), RobotCoordinates.six_1a.get(1)); // drive to other side
            // drop off stone
            driveForward(x, RobotCoordinates.seven_1a.get(1), -0.5, RobotCoordinates.seven_1a.get(2)); // drive back to other stone
            turnPIDV(90, 0, 0, 0, false); // turn to stone
            driveToPoint(0.5, x, RobotCoordinates.eight_1a.get(1)); // drive up to other stone
            driveForward(x, RobotCoordinates.nine_1a.get(1), -0.5, RobotCoordinates.nine_1a.get(2)); // back up from stone
            driveToPoint(0.5, RobotCoordinates.ten_1a.get(0), RobotCoordinates.ten_1a.get(1)); // drive to other side
            // drop off stone
            driveForward(RobotCoordinates.elev_1a.get(0), RobotCoordinates.elev_1a.get(1), -0.5, RobotCoordinates.elev_1a.get(2)); // back up and park
        }

        telemetry.addData("mindy", "yes");
        telemetry.update();
    }
}
