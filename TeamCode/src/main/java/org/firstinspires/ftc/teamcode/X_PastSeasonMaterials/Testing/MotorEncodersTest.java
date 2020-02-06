package org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name = "Motor Test", group = "Sensor")
//@Disabled
public class MotorEncodersTest extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        double startTime = 0, elapsedTime = 0;
        boolean moving = false;
        ElapsedTime time = new ElapsedTime();
        resetTime();

        waitForStart();

        if(gamepad1.dpad_up){
            moving = true;
            startTime = time.milliseconds();
            LF.setPower(1);
            RF.setPower(1);
            LB.setPower(1);
            RB.setPower(1);
        }else if (gamepad1.dpad_down){
            moving = true;
            startTime = time.milliseconds();
            LF.setPower(-1);
            RF.setPower(-1);
            LB.setPower(-1);
            RB.setPower(-1);
        }else if (gamepad1.dpad_left){
            moving = true;
            startTime = time.milliseconds();
            setStrafePowers(1, false );
        }else if(gamepad1.dpad_right){
            moving = true;
            startTime = time.milliseconds();
            setStrafePowers(1, true);
        }else if(moving && startTime > time.milliseconds() - 10000){
            elapsedTime = time.milliseconds() - startTime;
            LF.setPower(0);
            RF.setPower(0);
            LB.setPower(0);
            RB.setPower(0);
            moving = false;
        }

        telemetry.addData("Elapsed time: ", elapsedTime);
        telemetry.addData("Left front motor encoders: ", LF.getCurrentPosition());
        telemetry.addData("Right front motor encoders: ", RF.getCurrentPosition());
        telemetry.addData("Left back motor encoders: ", LB.getCurrentPosition());
        telemetry.addData("Right back motor encoders: ", RB.getCurrentPosition());
    }
}
