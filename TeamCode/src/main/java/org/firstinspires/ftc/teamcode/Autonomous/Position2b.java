package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Position 2b", group = "auto") // PARKS AWAY FROM WALL
//@Disabled
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

        waitForStart();

        driveToPoint(0.8,50, 50); // drives up to foundation
        // grabs foundation
        driveToPoint(0.8, 50, 50); // drives backwards? pulling foundation to depot
        // lets go of depot
        StrafetoPosition(1, 50, 50, getRobotHeading()); // strafes out from foundation
        driveToPoint(0.8, 50, 50); // drives forward to align with away park position
        StrafetoPosition(1, 50, 50, getRobotHeading()); // strafes to park AWAY FORM WALL

        telemetry.addData("crossaint", "mille-feuille");
        telemetry.addData("asha gets", "crushed");
        telemetry.addData("in the", "layers");
        telemetry.update();
    }
}
