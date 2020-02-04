package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Nir", group="teleop")

public class happyNirTeleOp extends OpMode {

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;

    double left_stick_y;
    double left_stick_x;
    double right_stick_x;
    double LF;
    double RF;
    double LB;
    double RB;



    @Override
    public void init() {

        fr = hardwareMap.dcMotor.get("RF");
        fl = hardwareMap.dcMotor.get("LF");
        bl = hardwareMap.dcMotor.get("LB");
        br = hardwareMap.dcMotor.get("RB");

        fl.setDirection(DcMotor.Direction.FORWARD);
        fr.setDirection(DcMotor.Direction.FORWARD);
        bl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop() {

        /*left_stick_x = -gamepad1.left_stick_x;
        left_stick_y = -gamepad1.left_stick_y;
        right_stick_x = -gamepad1.right_stick_x;


        if(Math.abs(left_stick_x) > .05 ||
        Math.abs(left_stick_y) > .05 ||
        Math.abs(right_stick_x) > .05)
        {
            fl.setPower(right_stick_x - left_stick_x - left_stick_y);
            fr.setPower(right_stick_x + left_stick_x + left_stick_y);
            bl.setPower(right_stick_x + left_stick_x - left_stick_y);
            br.setPower(right_stick_x - left_stick_x + left_stick_y);
        }
        else
        {
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }*/

        left_stick_x = -gamepad1.left_stick_x;
        left_stick_y = -gamepad1.left_stick_y;
        right_stick_x = -gamepad1.right_stick_x;


        if(Math.abs(left_stick_x) > .05 ||
                Math.abs(left_stick_y) > .05 ||
                Math.abs(right_stick_x) > .05)
        {
            LF = (right_stick_x - left_stick_x - left_stick_y);
            RF = (right_stick_x + left_stick_x + left_stick_y);
            LB = (right_stick_x + left_stick_x - left_stick_y);
            RB = (right_stick_x - left_stick_x + left_stick_y);

            double[] motorPower = scalePower(LF, RF, LB, RB);

            fl.setPower(motorPower[0]);
            fr.setPower(motorPower[1]);
            bl.setPower(motorPower[2]);
            br.setPower(motorPower[3]);

        }
        else
        {
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);
        }



    }

    public double[] scalePower(double LF, double RF, double LB, double RB){ //important for if we try to turn while strafing
        double[] power = {LF, RF, LB, RB};
        double max = power[0];
        int index = 0;
        while(index < power.length){ //find the max power to scale all the powers down by it
            if(power[index] > max){
                max = power[index];
            }
            index += 1;
        }
        if(max > 1.0){
            power[0] /= max;
            power[1] /= max;
            power[2] /= max;
            power[3] /= max;
        }
        return power;
    }
}
