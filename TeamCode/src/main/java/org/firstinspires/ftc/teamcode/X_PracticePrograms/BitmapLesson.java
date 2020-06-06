package org.firstinspires.ftc.teamcode.X_PracticePrograms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;


@Autonomous(name="Give name to show on DS", group = "auto")
@Disabled
public class BitmapLesson extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        //WRITE ACTIONS HERE

        telemetry.addData("LABEL:", "VALUE HERE");
        telemetry.update();

    }
}
