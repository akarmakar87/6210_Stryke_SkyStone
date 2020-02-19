package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@TeleOp(name="wheel test", group="teleop")
@Disabled
public class arm_Test extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        init(hardwareMap, false);



        telemetry.addData("Mode: ", "Waiting for start");

        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {


            if(gamepad1.y)
            {
                setMotorPowers(0.1, 0.1);
            }else if(gamepad1.a)
            {
                setMotorPowers(-1, -1);
            }
            else if(gamepad1.x)
            {
                setStrafePowers(1, false);
            }
            else if(gamepad1.b)
            {
                setStrafePowers(1, true);
            }
            else    setMotorPowers(0, 0);

            telemetry.addData("RF encoder:" , RF.getCurrentPosition());
            telemetry.addData("RB encoder:" , RB.getCurrentPosition());
            telemetry.addData("LF encoder:" , LF.getCurrentPosition());
            telemetry.addData("LB encoder:" , LB.getCurrentPosition());
            telemetry.update();
        }
    }
}