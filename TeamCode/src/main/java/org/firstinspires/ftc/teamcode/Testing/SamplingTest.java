package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="VuforiaTFtest(sampling)", group = "auto")
//@Disabled
public class SamplingTest extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //init(hardwareMap, true);
        initVuforia();
        //initTensorFlow();

        // ^ SET UP DETECTOR

        telemetry.addData("All Initialization", "Complete");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()){
            //telemetry.addData("Skystone Position (1 to 3)", detectSkystone(3000));
            telemetry.update();
        }
    }
}
