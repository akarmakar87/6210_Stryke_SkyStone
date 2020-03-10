package org.firstinspires.ftc.teamcode.Autonomous.Worlds;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Red Skystone Foundation", group = "auto") // BLUE SIDE

public class twoSkystoneFoundationRed extends SkystoneLinearOpMode {

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
        pos = detectSkystoneOnePix(getBitmap(),true);

        // MOVE ROBOT FORWARD OR BACKWARD ALONG WALL TO LINE UP WITH SKYSTONE
        adjustForSkystone(pos, true);

        // LOWER RIGHT GRABBER

        // STRAFE UNTIL IN FRONT OF SKYSTONE

        // CLOSE GRABBER

        // STRAFE AWAY FROM STONE

        // MOVE BACKWARD UNTIL COLOR SENSOR DETECTS LINE

        // MOVE BACKWARD A SET DISTANCE TO THE FOUNDATION

        // STRAFE RIGHT AGAINST FOUNDATION

        // LOWER GRABBER SERVO

        // OPEN GRABBER

        // LIFT GRABBER SERVO

        // STRAFE LEFT AWAY FROM  FOUNDATION

        //ROTATE 180 IF SKYSTONE POSITION == -1 (reverse all movements if true)

        // MOVE FORWARD UNTIL DETECTS LINE

        // MOVE FORWARD UNTIL HITS WALL

        // MOVE BACKWARD TO REACH SECOND SKYSTONE

        // LOWER GRABBER SERVO (ALREADY OPEN)

        // STRAFE RIGHT UNTIL GETS TO BLOCK

        // CLOSE GRABBER

        // STRAFE LEFT AWAY FROM STONE

        // MOVE BACKWARD UNTIL DETECTS LINE

        // MOVE BACKWARD A SET DISTANCE TO THE FOUNDATION

        // STRAFE RIGHT AGAINST FOUNDATION

        // LOWER GRABBER SERVO

        // OPEN GRABBER

        // LIFT GRABBER SERVO

        // STRAFE LEFT AWAY FROM FOUNDATION

        // TURN 90 DEGREES TO THE LEFT

        //BACK UP INTO FOUNDATION

        //DEPLOY FOUNDATION GRABBERS

        //PULL FOUNDATION INTO BUILD SITE

        //TURN 90 DEGREES RIGHT (TOWARD THE BRIDGE)

        //MOVE FORWARD UNTIL RED LINE DETECTED AND PARK

        telemetry.addData("auto:", "complete");
        telemetry.update();
    }
}
