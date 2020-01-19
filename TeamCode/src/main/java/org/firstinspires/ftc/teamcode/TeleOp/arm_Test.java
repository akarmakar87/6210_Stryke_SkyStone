package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="arm_Test", group="auto")
//@Disabled
public class arm_Test extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap, true);

        telemetry.addData("Mode: ", "Waiting for start");

        waitForStart();

        intakeL.setPower(1);
        intakeR.setPower(-1);

        arm.setTargetPosition(-540);

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        arm.setPower(.8);

        //rotate.setPosition(0.5);
        //setArmPosition(100);
        sleep(8000);
        /*setArmPosition(false);
        sleep(3000);
        setArmPosition(false);
        sleep(2000);*/
    }
}