
package org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.Testing;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.vuforia.CameraDevice;

        import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
        import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;
        import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name = "FlashTest", group = "Phone")
//@Disabled
public class Flash_Garrett extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        initVuforia();
        init(hardwareMap, true);
        CameraDevice camera = CameraDevice.getInstance();

        telemetry.addData("Ready", "true");
        telemetry.update();

        boolean placeholder = isRed();

        telemetry.addData("Done", "true");
        telemetry.update();
        waitForStart();


    }
}