package org.firstinspires.ftc.teamcode.TeleOp;

import android.util.Range;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="PID Test", group = "auto")

@Disabled
public class PIDconstantTest extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        double kP = 0,kU = 3.254, tU = 202.995;
        init(hardwareMap, true);

        while (!isStarted()) {

            if(gamepad1.a){
                kP -= 0.1;
                sleep(250);
            }
            if(gamepad1.b){
                kP += 0.1;
                sleep(250);
            }
            if(gamepad1.x){
                kP -= 0.01;
                sleep(250);
            }
            if(gamepad1.y){
                kP += 0.01;
                sleep(250);
            }

            telemetry.addData("kp", kP);
            telemetry.update();

        }

        waitForStart();

//1/15)*kU/tU (2/5)*kU/tU
        turnPID(180,0.6/180,0.02,1,10000);
    }
}
