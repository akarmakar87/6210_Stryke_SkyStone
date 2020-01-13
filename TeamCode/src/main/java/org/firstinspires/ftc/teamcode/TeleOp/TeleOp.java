package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.concurrent.TimeUnit;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="teleop")
//@Disabled
public class TeleOp extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);
        int lpos = 0, apos = 0;
        double xAxis = 0, yAxis = 0, zAxis = 0;
        double lfPower = 0, rfPower = 0, lbPower = 0, rbPower = 0, strafePower = 0, armPower = 0, liftPower = 0;
        boolean lControl = true, aControl = true, foundation = false;
        double lTime = 0, aTime = 0, fTime = 0, htime = 0;

        //For more controlled movement when moving the foundation
        boolean halfSpeed = false;
        resetEncoders();
        resetArm();

        //Set up a timer for half speed
        ElapsedTime time = new ElapsedTime();
        resetTime();

        telemetry.addData("Mode: ", "Waiting for start");
        telemetry.update();

        waitForStart();

        //GAMEPAD 1
        //Holonomic drive inputs
        if (Math.abs(gamepad1.left_stick_y) > 0.05) {
            yAxis = gamepad1.left_stick_y;
        }
        else{
            yAxis = 0;
        }
        if (Math.abs(gamepad1.left_stick_x) > 0.05) {
            xAxis = -gamepad1.left_stick_x;
        }
        else{
            xAxis = 0;
        }
        if (Math.abs(gamepad1.right_stick_x) > 0.05) {
            zAxis = -gamepad1.right_stick_x;
        }
        else{
            zAxis = 0;
        }

        //Calculating power for each motor
        lfPower = xAxis - yAxis - zAxis;
        rfPower = xAxis - yAxis + zAxis;
        lbPower = xAxis - yAxis - zAxis;
        rbPower = xAxis - yAxis + zAxis;

        //Checking if halfspeed is toggled
        if(gamepad1.x && htime < time.milliseconds() - 250)
        {
            halfSpeed = !halfSpeed;
            htime = time.milliseconds();
        }

        //Halfspeed controls
        if(halfspeed)
        {
            lfPower /= 2;
            rfPower /= 2;
            lbPower /= 2;
            rbPower /= 2;
            LF.setPower(Range.clip(lfPower, -.5, .5));
            RF.setPower(Range.clip(rfPower, -.5, .5));
            LB.setPower(Range.clip(lbPower, -.5, .5));
            RB.setPower(Range.clip(rbPower, -.5, .5));
        }

        //Normal controls
        else
        {
            LF.setPower(Range.clip(lfPower, -1, 1));
            RF.setPower(Range.clip(rfPower, -1, 1));
            LB.setPower(Range.clip(lbPower, -1, 1));
            RB.setPower(Range.clip(rbPower, -1, 1));
        }

        //Auto-strafe controls
        //strafe right
        if (gamepad1.right_trigger > 0.05)
        {
            strafePower = gamepad1.right_trigger * 0.75;
            setStrafePowers(strafePower,true);
        }

        //strafe left
        else if (gamepad1.left_trigger > 0.05)
        {
            strafePower = gamepad1.left_trigger * 0.75;
            setStrafePowers(strafePower, false);
        }

        //Hook Controls
        if (gamepad1.right_bumper && fTime < time.milliseconds() - 250){
            foundation = !foundation;
            fTime = time.milliseconds();
            foundationD(foundation);
        }

        //GAMEPAD 2
        //Intake controls
        //intake
        if(gamepad2.right_bumper)
        {
            intakeL.setPower(-1);
            intakeR.setPower(-1);
        }
        //expel
        else if(gamepad2.left_bumper)
        {
            intakeL.setPower(1);
            intakeR.setPower(1);
        }
        //idle
        else
        {
            intakeL.setPower(0);
            intakeR.setPower(0);
        }
        //Unstick block if stuck
        if(gamepad2.a)
        {
            intakeL.setPower(1);
            intakeR.setPower(-1);
        }

        //Arm Module Controls
        //Claw Controls
        if(gamepad2.x)
        {
            claw.setPosition(0);
        }
        if(gamepad2.y)
        {
            claw.setPosition(1);
        }

        //Arm Controls
        //Toggle...
        //Manuel
        if (Math.abs(gamepad2.right_stick_y) > 0.05)
        {
            armPower = Range.clip(-gamepad2.right_stick_y, -0.8, 0.8);
            arm.setPower(armPower);
        }
        else
        {
            arm.setPower(0);
        }
        //Automatic...

        //Lift Controls
        //Toggle...
        //Manuel
        if (gamepad2.right_trigger > 0.05) {
            liftPower = Range.clip(gamepad2.right_trigger, 0, 0.5);  //LIFT UP
        } else if (gamepad2.left_trigger > 0.05) {
            liftPower = -Range.clip(gamepad2.left_trigger, 0, 0.5);  //LIFT DOWN
        } else {
            liftPower = 0;
        }
        //Automatic...
    }
}
