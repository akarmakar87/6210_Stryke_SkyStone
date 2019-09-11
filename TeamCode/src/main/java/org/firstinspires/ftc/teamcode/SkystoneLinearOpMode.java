package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


public class SkystoneLinearOpMode extends LinearOpMode{

    // DECLARE VARIABLES TO BE USED
    ElapsedTime runtime;

    //MOTORS, SERVOS, AND SENSORS
    public DcMotor LF;
    public DcMotor RF;
    public DcMotor LB;
    public DcMotor RB;
    public BNO055IMU imu;

    //GYRO VARIABLES
    Orientation angles;

    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // REV Motor Encoder (1120 for 20:1)
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference

    public double encoderToInches = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)/(WHEEL_DIAMETER_INCHES * Math.PI); //Multiply desired distance (inches)

    //CAMERA
    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    public static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    public static final String VUFORIA_KEY = "AQt2xVL/////AAABmXIVKUnTcEJbqvVBjp/Sw/9SqarohYyKotzRjT/Xl1/S8KDwsFHv/zYw6rXqXTjKrnjk92GfBA4hbZaQP17d1N6BiBuXO2W/hFNoMGxiF+fWlnvtDmUM1H/MF9faMOjZcPNjnQ7X8DVwdDDha3A3aqaoegefkKxb4A5EjP8Xcb0EPJ1JA4RwhUOutLbCDJNKUq6nCi+cvPqShvlYTvXoROcOGWSIrPxMEiOHemCyuny7tJHUyEg2FTd2upiQygKAeD+LN3P3cT02aK6AJbQ0DlQccxAtoo1+b//H6/eGro2s0fjxA2dH3AaoHB7qkb2K0Vl7ReFEwX7wmqJleamNUG+OZu7K3Zm68mPudzNuhAWQ";
    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;

    // INITIALIZE
    public void init(HardwareMap map, boolean auto){

        runtime     = new ElapsedTime();
        LF  = map.dcMotor.get("LF");
        RF  = map.dcMotor.get("RF");
        LB  = map.dcMotor.get("LB");
        RB  = map.dcMotor.get("RB");
        imu = map.get(BNO055IMU.class, "imu"); // Check which IMU is being used

        LF.setDirection(DcMotorSimple.Direction.FORWARD);
        RF.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setDirection(DcMotorSimple.Direction.REVERSE);
        LB.setDirection(DcMotorSimple.Direction.FORWARD);


        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        resetEncoders();

        //SET UP GYRO
        angles = new Orientation();

        if (auto) {
            BNO055IMU.Parameters bparameters = new BNO055IMU.Parameters();
            bparameters.mode = BNO055IMU.SensorMode.IMU;
            bparameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
            bparameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            bparameters.loggingEnabled = false;

            imu.initialize(bparameters);

            angles = imu.getAngularOrientation(); //GET ORIENTATION

            telemetry.addData("Mode", "calibrating...");
            telemetry.update();

            while (!isStopRequested() && !imu.isGyroCalibrated()) {
                sleep(50);
                idle();
            }

            telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
            telemetry.update();
        }
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    //SET POWER TO DRIVE MOTORS
    public void setMotorPowers(double leftPower, double rightPower) {
        LF.setPower(Range.clip(leftPower, -1, 1));
        RF.setPower(Range.clip(rightPower, -1, 1));
        LB.setPower(Range.clip(leftPower, -1, 1));
        RB.setPower(Range.clip(rightPower, -1, 1));
    }

    public void setStrafePowers(double power, boolean right){
        if (right){
            LF.setPower(-Range.clip(power, -1, 1));
            RF.setPower(Range.clip(power, -1, 1));
            LB.setPower(Range.clip(power, -1, 1));
            RB.setPower(-Range.clip(power, -1, 1));
        }else{
            LF.setPower(Range.clip(power, -1, 1));
            RF.setPower(-Range.clip(power, -1, 1));
            LB.setPower(-Range.clip(power, -1, 1));
            RB.setPower(Range.clip(power, -1, 1));
        }

    }

    /**
    public void strafeAdjust(double power, double distance, boolean right, int timeout){

        double deltaHeading = 0;

        resetEncoders();
        while (getEncoderAvg() < distance * 55 && !isStopRequested()) {
           deltaHeading = getYaw();
           power = Range.clip(deltaHeading/0.5, 0.25, 1);

           if (Math.abs(getYaw()-90) > 5){
               driveTime(1,0.5);
           }else{
               setStrafePowers(power,right);
           }

        }
    }
     **/

    //TIME BASED MOVEMENT
    public void driveTime(double power, double seconds){
        setMotorPowers(power, power);
        long time = (long)(seconds) * 1000;
        sleep(time);
    }

    //TIME BASED TURNING
    public void turnTime(double power, boolean right, long seconds){
        if (right)
            setMotorPowers(power, -power);
        else
            setMotorPowers(-power, power);

        sleep(seconds);
    }

    //SET RUNMODE TO DRIVE MOTORS
    public void setMode(DcMotor.RunMode runMode) throws InterruptedException {
        LF.setMode(runMode);
        idle();
        RF.setMode(runMode);
        idle();
        LB.setMode(runMode);
        idle();
        RB.setMode(runMode);
        idle();
    }

    //STOP DRIVE MOTORS
    public void stopMotors(){
        setMotorPowers(0,0);
    }

    public int getEncoderAvg(){
        //divided by three for now because RB encoder returns 0
        int avg = (Math.abs(LF.getCurrentPosition()) + Math.abs(RF.getCurrentPosition()) + Math.abs(LB.getCurrentPosition()) + Math.abs(RB.getCurrentPosition()))/3;
        //MAKE SURE ALL ENCODERS WORK BEFORE DIVIDING BY 4 OR PUT IN A CONDITION TO DETERMINE IF AN ENCODER VALUE IS WEIRD, AND DIVIDE ACCORDINGLY
        return avg;
    }

    public void resetEncoders(){
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        idle();
        RB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        idle();
        LF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        idle();
        LB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        idle();
    }

    /**
    public void resetLift(){
        //lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        //lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        idle();
    }
     **/

    // ENCODER BASED MOVEMENT
    public void driveDistance(double power, double distance) throws InterruptedException{
        resetEncoders();

        while (!isStopRequested() && getEncoderAvg() < distance * encoderToInches){
            setMotorPowers(power, power);
        }

        stopMotors();
    }

    public void strafeDistance(double power, double distance, boolean right) throws InterruptedException{
        resetEncoders();
        while (getEncoderAvg() < distance * 55 && !isStopRequested()){
            if (right){
                LF.setPower(power);
                RF.setPower(-power);
                LB.setPower(-power);
                RB.setPower(power);
            }else {
                LF.setPower(-power);
                RF.setPower(power);
                LB.setPower(power);
                RB.setPower(-power);
            }
        }
        stopMotors();
    }

    //GET ANGLE
    public double getYaw() {
        angles = imu.getAngularOrientation();
        return angles.firstAngle;
    }

    /**
    public void turnPID(double tAngle, double kP, double kI, double kD, double timeOut){
        double power, prevError, error, dT, prevTime, currTime, P, I, D; //DECLARE ALL VARIABLES
        prevError = error = tAngle - getYaw(); //INITIALIZE THESE VARIABLES
        power = dT = prevTime = currTime = P = I = D = 0;
        ElapsedTime time = new ElapsedTime(); //CREATE NEW TIME OBJECT
        resetTime();
        while (Math.abs(error) > 0.5 && currTime < timeOut){
            prevError = error;
            error = tAngle - getYaw(); //GET ANGLE REMAINING TO TURN (tANGLE MEANS TARGET ANGLE, AS IN THE ANGLE YOU WANNA GO TO)
            prevTime = currTime;
            currTime = time.milliseconds();
            dT = currTime - prevTime; //GET DIFFERENCE IN CURRENT TIME FROM PREVIOUS TIME
            P = error;
            I = error * dT;
            D = (error - prevError)/dT;
            power = P * kP + I * kI + D * kD;
            setMotorPowers(Range.clip(power, 0.2, 1), -Range.clip(power, 0.2, 1));

            telemetry.addData("tAngle: ", tAngle)
                    .addData("P:", P)
                    .addData("I:", I)
                    .addData("D:", D)
                    .addData("power", power)
                    .addData("error: ", error)
                    .addData("currTime: ", currTime);
        }
    }
    **/

    //ROTATE USING GYRO
    public void rotate(double targetAngleChange, int timeout) {

        runtime.reset();

        double power = 0;
        double origDiff = getYaw() - targetAngleChange;
        double deltaHeading = 0;

        while ((Math.abs(getYaw()-targetAngleChange) > 1) && opModeIsActive() && (runtime.seconds() < timeout)) {

            telemetry.addData("Turning:", "From " + getYaw() + " to " + targetAngleChange);
            telemetry.update();

            deltaHeading = getYaw() - targetAngleChange; //GET ANGLE LEFT UNTIL TARGET ANGLE
            power = Range.clip(0.4 * deltaHeading/origDiff, 0.2, 1); //PROPORTIONAL SPEED
            /** Why is dHeading/oDiff multiplied by 0.4? -Garrett **/
            if (deltaHeading < -180 || (deltaHeading > 0 && deltaHeading < 180) ) { //LEFT IS + , RIGHT IS -
                LF.setPower(power);
                LB.setPower(power);
                RF.setPower(-power);
                RB.setPower(-power);
            } else {
                LF.setPower(-power);
                LB.setPower(-power);
                RF.setPower(power);
                RB.setPower(power);
            }
        }
        stopMotors();
    }

    public void resetTime(){
        runtime.reset();
    }

    public double getTime(){
        return runtime.seconds();
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}