package org.firstinspires.ftc.teamcode.Autonomous.AML1;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="arm testing", group = "auto")

//@Disabled
public class armTesting extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, false);

        boolean red = true;
        int heading = 0, adjustment = 0;

        //should we keep this button pressing system? is it legal?
        while (!isStarted() && opModeIsActive()) {

            if (gamepad1.b) red = false;

            telemetry.addData("red? ", red);
            telemetry.update();
        }

        // scan for skystone
        // variables based on:
        //      - distance needed to move to be in front of skystone
        //      - distance to get to other side of field
        // no need to set a variable for distance traveled to skystone 2 because skystones are paired up
        // and thus a set distance apart << measured
        // will be different for red and blue side so set inside if else loop

        // +-+-+-+-+-+-+-+-+-+- NOTE - find diff in inches between each pair of skystones

        waitForStart();


        setArm(-560 ,-1.0); //PUT ARM DOWN

        sleep(1000);


        //setClawPosition(true);//OPEN CLA
    }

}
