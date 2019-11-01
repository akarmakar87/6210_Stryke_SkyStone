package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="arm_Test", group="auto")

public class arm_Test extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap, true);
        resetArm(); //350 = pick up stone           300 = pointing straight forward

        waitForStart();

        rotate.setPosition(0.5);
        //setArmPosition(100);
        //sleep(3000);
        /*setArmPosition(false);
        sleep(3000);
        setArmPosition(false);
        sleep(2000);*/
    }
}