package org.firstinspires.ftc.teamcode.Autonomous.Worlds;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue Skystone", group = "auto") // BLUE SIDE

public class twoSkystoneParkBlue extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true); // <-- LIFT GRABBER SERVO AND OPEN GRABBER
        initBitmapVuforia();

        int pos = 0;
        double adjust = 0.0;
        double longAdjust = 0.0;

        waitForStart();

        // DETECT SKYSTONE
        pos = detectSkystoneOnePix(getBitmap(),false);

        // MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONE
        adjustForSkystone(pos, false);

        // LOWER RIGHT GRABBER

        // STRAFE UNTIL IN FRONT OF SKYSTONE

        // CLOSE GRABBER

        // STRAFE AWAY FROM STONE

        // MOVE FORWARD UNTIL COLOR SENSOR DETECTS LINE

        // MOVE FORWARD A SET DISTANCE TO THE FOUNDATION

        // STRAFE RIGHT AGAINST FOUNDATION

        // LOWER GRABBER SERVO

        // OPEN GRABBER

        // LIFT GRABBER SERVO

        // STRAFE LEFT AWAY FROM  FOUNDATION

        //ROTATE 180 IF SKYSTONE POSITION == -1 (reverse all movements if true)

        // MOVE BACK UNTIL DETECTS LINE

        // MOVE BACK UNTIL HITS WALL

        // MOVE FORWARD TO REACH SECOND SKYSTONE

        // LOWER GRABBER SERVO (ALREADY OPEN)

        // STRAFE RIGHT UNTIL GETS TO BLOCK

        // CLOSE GRABBER

        // STRAFE LEFT AWAY FROM STONE

        // MOVE FORWARD UNTIL DETECTS LINE

        // MOVE FORWARD A SET DISTANCE TO THE FOUNDATION

        // STRAFE RIGHT AGAINST FOUNDATION

        // LOWER GRABBER SERVO

        // OPEN GRABBER

        // LIFT GRABBER SERVO

        // STRAFE LEFT AWAY FROM FOUNDATION

        // MOVE BACK UNTIL DETECTS LINE AND PARK



        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
