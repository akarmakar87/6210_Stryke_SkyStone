package org.firstinspires.ftc.teamcode.X_PracticePrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;


@Autonomous(name="Give name to show on DS", group = "auto")
@Disabled
public class PracticeAuto extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        //WRITE ACTIONS HERE

        for(int i = 0; i < 12; i++){
            driveDistance(0.5, 10);
            rotate(90, 5);
            telemetry.addData("Angle:", get180Yaw());
            telemetry.update();
        }

        telemetry.addData("Auto", "Complete");
        telemetry.update();

    }
}
