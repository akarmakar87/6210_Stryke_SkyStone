package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="vtest", group="teleop")
//@Disabled
public class testVuforia extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap, true);
        initVuforia();  //350 = pick up stone           300 = pointing straight forward

        waitForStart();



        updateRobotPosition();
    }
}