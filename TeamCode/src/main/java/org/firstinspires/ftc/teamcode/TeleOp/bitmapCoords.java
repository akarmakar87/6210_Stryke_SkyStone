package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="bitmapTeleOpTest", group="auto")
//@Disabled
public class bitmapCoords extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap, false);
        initBitmapVuforia();

        waitForStart();

        int rx =0,ry=0,lx=0,ly=0;

        while(opModeIsActive() && !isStopRequested()) {
            if(gamepad1.a){
                ry -= 10;
                sleep(250);
            }
            if(gamepad1.y){
                ry += 10;
                sleep(250);
            }
            if(gamepad1.x){
                rx -= 10;
                sleep(250);
            }
            if(gamepad1.b){
                rx += 10;
                sleep(250);
            }

            if(gamepad1.dpad_down){
                ly -= 10;
                sleep(250);
            }
            if(gamepad1.dpad_up){
                ly += 10;
                sleep(250);
            }
            if(gamepad1.dpad_left){
                lx -= 10;
                sleep(250);
            }
            if(gamepad1.dpad_right){
                lx += 10;
                sleep(250);
            }

            detectSkystoneOnePix(getBitmap(), true, rx, ry, lx, ly);
        }
    }
}