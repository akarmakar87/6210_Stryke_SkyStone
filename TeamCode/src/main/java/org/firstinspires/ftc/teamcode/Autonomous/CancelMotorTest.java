package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="cancel test", group = "auto")

//@Disabled
public class CancelMotorTest extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();


        //setEachMotorPowers(-0.5,0,0.5,0,false);

        //sleep(2000);

        //setEachMotorPowers(0,-0.5,0,0.5,false);

        setEachMotorPowers(0.5,0,-0.5,0,false);

        sleep(2000);

        setEachMotorPowers(0,0.5,0,-0.5,false);

        sleep(2000);

        telemetry.addData("LF Power", LF.getPower() + " " + LF.getCurrentPosition());
        telemetry.addData("RF Power", RF.getPower() + " " + RF.getCurrentPosition());
        telemetry.addData("LB Power", LB.getPower() + " " + LB.getCurrentPosition());
        telemetry.addData("RB Power", RB.getPower() + " " + RB.getCurrentPosition());
        telemetry.update();

    }

}
