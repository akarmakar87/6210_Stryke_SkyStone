package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.vuforia.CameraDevice;
import com.qualcomm.hardware.rev.RevColorSensorV3;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


public class SkystoneLinearOpMode extends LinearOpMode{

    // DECLARE VARIABLES TO BE USED
    ElapsedTime runtime;

    //MOTORS, SERVOS, AND SENSORS
    public DcMotor LF;
    public DcMotor RF;
    public DcMotor LB;
    public DcMotor RB;
    public DcMotor intake;
    public BNO055IMU imu;
    public DcMotor lift;
    public Servo claw;
    public RevColorSensorV3 sensorColor;

    /**COLOR SENSOR VARIABLES
    float hsvValues[] = {0f, 0f, 0f};
    float values[] = hsvValues;
    **/

    //ClAW VARIABLES
    private double clawStartPosition = 0.0;
    private double clawEndPosition = 1.0;


    //GYRO VARIABLES
    Orientation angles;

    //ticks per inch = (Motor revolutions * gear up ratio) / (wheel diameter * pie)
    //Motor revolutions = COUNTS_PER_MOTOR_REV
    //gear up ratio = 2:1   (ratio beyond motor)
    //wheel diameter = WHEEL_DIAMETER_INCHES
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // REV Motor Encoder (1120 for 40:1) (560 for 20:1)
    static final double     DRIVE_GEAR_REDUCTION    = 0.5 ;   // This is < 1.0 if geared UP   (ratio is 2:1)
    static final double     WHEEL_DIAMETER_INCHES   = 2 * (3 + (15 / 16)) ;     // For figuring circumference

    public double encoderToInches = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)/(WHEEL_DIAMETER_INCHES * Math.PI); //Multiply desired distance (inches)

    //Vuforia variables
    // IMPORTANT: If you are using a USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    public static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    public static final boolean PHONE_IS_PORTRAIT = false  ;


    public static final String VUFORIA_KEY = "AQt2xVL/////AAABmXIVKUnTcEJbqvVBjp/Sw/9SqarohYyKotzRjT/Xl1/S8KDwsFHv/zYw6rXqXTjKrnjk92GfBA4hbZaQP17d1N6BiBuXO2W/hFNoMGxiF+fWlnvtDmUM1H/MF9faMOjZcPNjnQ7X8DVwdDDha3A3aqaoegefkKxb4A5EjP8Xcb0EPJ1JA4RwhUOutLbCDJNKUq6nCi+cvPqShvlYTvXoROcOGWSIrPxMEiOHemCyuny7tJHUyEg2FTd2upiQygKAeD+LN3P3cT02aK6AJbQ0DlQccxAtoo1+b//H6/eGro2s0fjxA2dH3AaoHB7qkb2K0Vl7ReFEwX7wmqJleamNUG+OZu7K3Zm68mPudzNuhAWQ";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    public static final float mmPerInch        = 25.4f;
    public static final float mmTargetHeight   = (6) * mmPerInch;  // the HEIGHT of the center of the target image above the floor

    // Constant (z-axis = height) for SKYSTONE BLOCK IMAGE Target
    public static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    public static final float bridgeZ = 6.42f * mmPerInch;
    public static final float bridgeY = 23 * mmPerInch;
    public static final float bridgeX = 5.18f * mmPerInch;
    public static final float bridgeRotY = 59;                                 // Units are degrees
    public static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    public static final float halfField = 72 * mmPerInch;
    public static final float quadField  = 36 * mmPerInch;

    // Class Members
    public OpenGLMatrix lastLocation = null;
    public VuforiaLocalizer vuforia = null;

    WebcamName LogitechC310 = null;

    public boolean targetVisible = false;
    public float phoneXRotate    = 0;
    public float phoneYRotate    = 0;
    public float phoneZRotate    = 0;

    //Vuforia stuff moved from init in order to make global variables

    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
    VectorF translation;
    Orientation rotation;
    VuforiaTrackables targetsSkyStone;

    //TensorFlow stuff

    public static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    public static final String LABEL_STONE = "Stone";
    public static final String LABEL_SKYSTONE = "Skystone";

    public TFObjectDetector tfod;
    //private BlockingQueue<VuforiaLocalizer.CloseableFrame> frame;


    // INITIALIZE
    public void init(HardwareMap map, boolean auto){

        runtime     = new ElapsedTime();
        LF  = map.dcMotor.get("LF");
        RF  = map.dcMotor.get("RF");
        LB  = map.dcMotor.get("LB");
        RB  = map.dcMotor.get("RB");
       // intake  = map.dcMotor.get("intake");
        imu = map.get(BNO055IMU.class, "imu"); // Check which IMU is being used
        //lift = map.dcMotor.get("lift");
        //claw = map.servo.get("claw");
        //sensorColor = map.get(RevColorSensorV3.class, "color");

        LF.setDirection(DcMotorSimple.Direction.FORWARD);
        RF.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setDirection(DcMotorSimple.Direction.REVERSE);
        LB.setDirection(DcMotorSimple.Direction.FORWARD);
        //intake.setDirection(DcMotorSimple.Direction.FORWARD);
        //lift.setDirection(DcMotorSimple.Direction.FORWARD);
        //setClawPosition(false);

        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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

    public void initVuforia(){
        //LogitechC310 = hardwareMap.get(WebcamName.class, "LogitechC310");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        /**
         * We also indicate which camera on the RC we wish to use.
         */
        //parameters.cameraName = LogitechC310;

        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        //LIST OF ALL VUMARKS
        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targetsSkyStone);

        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));


        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));


        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        targetsSkyStone.activate();

        telemetry.addData("Tracking: ", "Enabled");
        telemetry.update();

      /*  if (tfod != null) {
            tfod.activate();
        }*/

        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
    }

    public void initTensorFlow(){
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
    }

    //CREATION OF TFOD MONITOR VIEW IN ADDITION TO VUFORIA CAMERA MONITOR VIEW IS PROBABLY CAUSING TWO VIEWS
    // HOW TO FIX? SHOULD I GET RID OF ONE? OR DOES IT NOT MATTER IF THERE ARE TWO?
    public void initTfod() {
       int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
               "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
       TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
       tfodParameters.minimumConfidence = 0.8;
       tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
       tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_STONE, LABEL_SKYSTONE);
   }

    //IDK anymore
    /**public Bitmap convertToBitmap() throws InterruptedException{
        Image rgb = null;
        com.vuforiaPC.Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        Bitmap imageBitmap;
        vuforiaPC.setFrameQueueCapacity(1);
        vuforiaPC.enableConvertFrameToBitmap();
        VuforiaLocalizer.CloseableFrame picture;

        frame = vuforiaPC.getFrameQueue();
        picture = frame.take();

        long imgCount = picture.getNumImages(); //GET NUMBER OF FORMATS FOR FRAME

        for (int i = 0; i < imgCount; i++){
            if(picture.getImage(i).getFormat() == PIXEL_FORMAT.RGB565){
                rgb = picture.getImage(i);
                break;
            }
        }

        imageBitmap = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565 );
        imageBitmap.copyPixelsFromBuffer(rgb.getPixels());

        picture.close();
        return imageBitmap;
    }**/


    //SET POWER TO DRIVE MOTORS
    public void setMotorPowers(double leftPower, double rightPower) {
        LF.setPower(Range.clip(leftPower, -1, 1));
        RF.setPower(Range.clip(rightPower, -1, 1));
        LB.setPower(Range.clip(leftPower, -1, 1));
        RB.setPower(Range.clip(rightPower, -1, 1));
    }

    public void setStrafePowers(double power, boolean right){
        if (right){
            LF.setPower(-power);
            RF.setPower(power);
            LB.setPower(power);
            RB.setPower(-power);
        }else {
            LF.setPower(power);
            RF.setPower(-power);
            LB.setPower(-power);
            RB.setPower(power);
        }
    }

    public void StrafetoPosition(double power, double tarX, double tarY, double tarheading) {  // Garrett(9/13/19) edited
        //Declare variables
        double min = 0.3;
        double curX = getRobotX();
        double tarHead = tarheading;
        double curHead = getRobotHeading();
        double RFpower = power;
        double RBpower = power;
        turnPIDV(tarHead, 0.4, 0, 0, false);    //turn towards the correct heading
        while (opModeIsActive() && (Math.abs(tarX - getRobotX()) > 0) || (Math.abs(tarY - getRobotY()) > 0)) {  //Move until at target position
            if (curX < tarX) {  //If curX < tarX strafe right
                if (curHead >= tarHead + 0.5) { //If robot turning too far left
                    RBpower -= .05 * (curHead - tarHead);
                }
                else if (curHead <= tarHead - 0.5) { //If robot turning too right left
                    RFpower -= .05 * (tarHead - curHead);
                }
                LF.setPower(-Range.clip(power, min, 1));
                RF.setPower(Range.clip(-RFpower, min, 1));
                LB.setPower(Range.clip(-power, min, 1));
                RB.setPower(-Range.clip(RBpower, min, 1));
            } else {    //If curX > tarX strafe left
                if (curHead >= tarHead + 0.5) { //If robot turning too far left
                    RFpower -= .05 * (curHead - tarHead);
                }
                if (curHead <= tarHead - 0.5) { //If robot turning too far left
                    RBpower -= .05 * (tarHead - curHead);
                }
                LF.setPower(Range.clip(-power, min, 1));
                RF.setPower(-Range.clip(RFpower, min, 1));
                LB.setPower(-Range.clip(power, min, 1));
                RB.setPower(Range.clip(-RBpower, min, 1));
            }
        }
    }

    public void StrafePowers(double power, boolean right, double angle) {  // Garrett(9/13/19)
        //Declare variables
        double min = 0.3;
        double tarHead = angle;
        double curHead = getRobotHeading();
        double RFpower = power;
        double RBpower = power;
            if (right) {  //If strafing right
                if (curHead >= tarHead + 0.5) {
                    RBpower -= .05 * (curHead - tarHead);
                }
                if (curHead <= tarHead - 0.5) {
                    RFpower -= .05 * (tarHead - curHead);
                }
                LF.setPower(-Range.clip(power, min, 1));
                RF.setPower(Range.clip(-RFpower, min, 1));
                LB.setPower(Range.clip(-power, min, 1));
                RB.setPower(-Range.clip(RBpower, min, 1));
            } else {    //If strafing left
                if (curHead >= tarHead + 0.5) {
                    RFpower -= .05 * (curHead - tarHead);
                }
                if (curHead <= tarHead - 0.5) {
                    RBpower -= .05 * (tarHead - curHead);
                }
                LF.setPower(Range.clip(-power, min, 1));
                RF.setPower(-Range.clip(RFpower, min, 1));
                LB.setPower(-Range.clip(power, min, 1));
                RB.setPower(Range.clip(-RBpower, min, 1));
            }
        }

    /**public void strafeAdjust(double power, double distance, boolean right, int timeout){

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
    }**/

    /**COLOR SENSOR
    public void colorPark(boolean red){
        Color.RGBToHSV(sensorColor.red() * 8, sensorColor.green() * 8, sensorColor.blue() * 8, hsvValues);
        float hue = hsvValues[0];
        boolean redPark = hue < 60 || hue > 320; //red hues
        boolean bluePark = hue > 120 && hue < 260; /blue hues

        if(red){
            while(!redPark && !isStopRequested()){
            setMotorPowers(0.5, 0.5);
            }
        stopMotors();
        }
        else{
            while(!bluePark && !isStopRequested()){
            setMotorPowers(0.5, 0.5);
            }
        stopMotors();
        }
    **/

    //CLAW SERVO
    public void setClawPosition(boolean open){
        if (open){
            //claw.setPosition(clawStartPosition);
        }
        else{
            //claw.setPosition(clawEndPosition);
        }
    }

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
                setMotorPowers(power, -power);
            } else {
                setMotorPowers(-power, power);
            }
        }
        stopMotors();
    }

    public void turnP(double tAngle, double kP, double timeOut){
        double power, prevError, error, dT, prevTime, currTime, P; //DECLARE ALL VARIABLES
        prevError = error = tAngle - getYaw(); //INITIALIZE THESE VARIABLES
        power = dT = prevTime = currTime = P = 0;
        ElapsedTime time = new ElapsedTime(); //CREATE NEW TIME OBJECT
        resetTime();
        while (opModeIsActive() && Math.abs(error) > 0.5 && currTime < timeOut){
            prevError = error;
            error = tAngle - getYaw(); //GET ANGLE REMAINING TO TURN (tANGLE MEANS TARGET ANGLE, AS IN THE ANGLE YOU WANNA GO TO)
            prevTime = currTime;
            currTime = time.milliseconds();
            dT = currTime - prevTime; //GET DIFFERENCE IN CURRENT TIME FROM PREVIOUS TIME
            P = error;
            power = P * kP;
            setMotorPowers(Range.clip(power, 0.2, 1), -Range.clip(power, 0.2, 1));

            telemetry.addData("tAngle: ", tAngle)
                    .addData("P:", P)
                    .addData("power", power)
                    .addData("error: ", error)
                    .addData("currTime: ", currTime);
        }
    }

    public void resetTime(){
        runtime.reset();
    }

    public double getTime(){
        return runtime.seconds();
    }

    int pos = 0;

  /**  public void detectSkystone(double timeLimit){
        runtime.reset();
        activateTfod();
        while(runtime.seconds() < timeLimit && opModeIsActive() && tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());
                if (updatedRecognitions.size() > 0) { //IF DETECT BOTH OBJECTS

                    int skystoneX = -1, stone1X = -1, stone2X = -1;
                    double skystoneConf = 0;

                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_SKYSTONE)) { //IF OBJECT DETECTED IS GOLD
                            skystoneX = (int) recognition.getLeft();
                            skystoneConf = recognition.getConfidence();
                        } else if (recognition.getLabel().equals(LABEL_STONE) && stone1X != -1) {
                            stone1X = (int) recognition.getLeft();
                        } else {
                            stone2X = (int) recognition.getLeft();
                        }
                    }

                    //Adjust based on if it can see 3 stones or 2 stones
                    //Ask galligher how to get the y-value of the object, but could also use
                    //ratio of block height to frame height

                    //(*^*)\\ <-- A CHICK!

                    if (skystoneX != -1 && skystoneConf > 0.2) { //adjust confidence level

                        if (skystoneX < 600 || (skystoneX < stone1X && skystoneX < stone2X)) { //adjust threshold3
                            telemetry.addData("Skystone Position", "Left");
                            pos = 1;
                        } else{
                            telemetry.addData("Skystone Position", "Center");
                            pos = 2;
                        }
                    }else{
                        telemetry.addData("Skystone Position", "Right");
                        pos = 3;
                    }
                    telemetry.addData("Gold x pos ", skystoneX);
                    telemetry.addData("Gold conf ", skystoneConf);
                    telemetry.addData("Runtime", getTime());
                    telemetry.update();
                }
            }
        }
    }
   **/

    //USE VUFORIA TRACKABLE COORDINATE TO DETERMINE SKYSTONE POSITION
    //GO GET COORDINATES OF THE TARGET IN EACH POSITION
    public int detectSkystone(){
        OpenGLMatrix skystonePos = null;
        VectorF skystoneCoords = null;
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        int pos = 0;
        while(opModeIsActive()) {
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());

                    if (trackable.getName() == "Stone Target") {
                        skystonePos = trackable.getLocation();
                        skystoneCoords = skystonePos.getTranslation();

                        if (skystoneCoords.get(0) / mmPerInch < 0 && skystoneCoords.get(0) / mmPerInch > 0) {
                            pos = -1;
                        } else if (skystoneCoords.get(0) / mmPerInch < 0 && skystoneCoords.get(0) / mmPerInch > 0) {
                            pos = 0;
                        } else if (skystoneCoords.get(0) / mmPerInch < 0 && skystoneCoords.get(0) / mmPerInch > 0) {
                            pos = 1;
                        } else {
                            pos = 0;
                        }
                    }
                    break;
                }
            }
        }
        telemetry.addData("Skystone Pos:", pos);
        telemetry.update();
        return pos;
    }

    public int getSkystonePos(){
        return pos;
    }

    public boolean updateRobotPosition(){
        targetVisible = false;
        for (VuforiaTrackable trackable : allTrackables) {
            if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() && trackable.getName() != "Stone Target") {
                telemetry.addData("Visible Target", trackable.getName());
                targetVisible = true;

                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
                break;
            }
        }

        if (targetVisible) {
            // express position (translation) of robot in inches.
            translation = lastLocation.getTranslation();
            telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

            // express the rotation of the robot in degrees.
            rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            telemetry.update();
            return true;
        }
        else {
            telemetry.addData("Visible Target", "none");
            telemetry.update();
            return false;
        }
    }

    public double getRobotX() {
        return translation.get(0) / mmPerInch;
    }

    public double getRobotY() {
        return translation.get(1) / mmPerInch;
    }

    public double getRobotZ() {
        return translation.get(2) / mmPerInch;
    }

    public double getRobotHeading(){
        return rotation.thirdAngle;
    }

    public void disableTracking(){
        targetsSkyStone.deactivate();
        telemetry.addData("Tracking: ", "Disabled");
        telemetry.update();
    }

    /**public void activateTfod(){
        if (tfod != null)
            tfod.activate();
    }

    public void deactivateTfod(){
        tfod.deactivate(); //is both deactivate and shutdown necessary?
        tfod.shutdown();
    }**/

    // PID IMU GYRO BASED TURNING
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

    // VUFORIA BASED MOVEMENT
    public void driveToPoint(double power, double xTarget, double yTarget) {
        updateRobotPosition();
        double curX = getRobotX();
        double curY = getRobotY();

        double trgtX = xTarget;
        double trgtY = yTarget;

        double diffX = trgtX - curX;
        double diffY = trgtY - curY;

        double curHeading = getRobotHeading(); //Assuming 0-360
        double diffHeading = Math.abs(Math.toDegrees(Math.atan(diffX / diffY))); //CALCULATE ANGLE FROM CURRENT COORD TO TARGET COORD
        double trgtHeading;

        //ACCOUNT FOR ANGLES IN ALL FOUR QUADRANTS
        if (diffX > 0 && diffY > 0)
            trgtHeading = diffHeading;
        else if (diffX < 0 && diffY > 0)
            trgtHeading = 360 - diffHeading;
        else if (diffX < 0 && diffY < 0)
            trgtHeading = 180 + diffHeading;
        else if (diffX > 0 && diffY < 0)
            trgtHeading = 180 - diffHeading;
        else
            trgtHeading = 0;

        turnPIDV(trgtHeading, 0.4, 0, 0, (trgtHeading - curHeading > 180));
        driveForward(trgtX, trgtY, power, trgtHeading);
    }

    public void turnPIDV(double tAngle, double kP, double kI, double kD, boolean flip){

        double power, prevError, error, dT, prevTime, currTime, P, I, D; //DECLARE ALL VARIABLES

        prevError = error = tAngle - getRobotHeading(); //INITIALIZE THESE VARIABLES

        power = dT = prevTime = currTime = P = I = D = 0;

        ElapsedTime time = new ElapsedTime(); //CREATE NEW TIME OBJECT
        resetTime();
        while (opModeIsActive() && Math.abs(error) > 0.5){ //put timeout in later
            updateRobotPosition();
            prevError = error;
            error = tAngle - getRobotHeading(); //GET ANGLE REMAINING TO TURN (tANGLE MEANS TARGET ANGLE, AS IN THE ANGLE YOU WANNA GO TO)
            prevTime = currTime;
            currTime = time.milliseconds();
            dT = currTime - prevTime; //GET DIFFERENCE IN CURRENT TIME FROM PREVIOUS TIME
            P = error;
            I = error * dT;
            D = (error - prevError)/dT;
            power = P * kP + I * kI + D * kD;

            if (flip)
                power *= -1;

            //Basically, tell the robot to take the shortest way to the target angle.
            //For example, if robot is at 359 and target angle is 5 degrees, move 6 deg clockwise instead
            //of 354 deg counter clockwise

            if(power < 0.2){
                power = 0.2;
            }else if(power > -0.2){
                power = -0.2;
            }

            setMotorPowers(power, -power);

            telemetry.addData("tAngle: ", tAngle)
                    .addData("P:", P)
                    .addData("I:", I)
                    .addData("D:", D)
                    .addData("power", power)
                    .addData("error: ", error)
                    .addData("currTime: ", currTime);
            telemetry.update();
        }
    }

    public void driveForward(double x, double y, double power, double trgtHead){
        // Angle adjustment while driving to a specific point
        // TO DO: calculate proportional error to decrease power more if robot angle is larger

        while (opModeIsActive() && (Math.abs(x - getRobotX()) > 5) || (Math.abs(y - getRobotY()) > 5))  {
            updateRobotPosition();
            if (trgtHead - getRobotHeading() > 1)
                setMotorPowers(power, power * 0.8); // default error is 0.8
            else if (trgtHead - getRobotHeading() < -1)
                setMotorPowers(power * 0.8, power);
            else
                setMotorPowers(power, power);

            if (Math.abs(trgtHead - getRobotHeading()) > 30) // stops robot if robot is turned too off course
                break;
        }

        /*

        original foundational method - mindy

        while ((Math.abs(x - getRobotX()) > 0) || (Math.abs(y - getRobotY()) > 0))  {

            if (trgtHead - getRobotHeading() > 1)
                setMotorPowers(power, power * 0.8); // default error is 0.8
            else if (trgtHead - getRobotHeading() < -1)
                setMotorPowers(power * 0.8, power);
            else
                setMotorPowers(power, power);

            if (Math.abs(trgtHead - getRobotHeading()) > 30) // stops robot if robot is turned too off course
               break;
        }
         */

        telemetry.addData("Target: ", x + " , " + y);
        telemetry.update();
    }

    public boolean isRed(int timeout) { // in milliseconds
        runtime.reset();
        CameraDevice camera = CameraDevice.getInstance();
        camera.setFlashTorchMode(true);

        while (!isStopRequested() && runtime.milliseconds() < timeout) {
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                        translation = lastLocation.getTranslation();
                    }
                    camera.setFlashTorchMode(false);
                    switch (trackable.getName()) {
                        default:
                            telemetry.addData("No Trackable", "Detected");
                        case "Red Perimeter 2":
                            return true;
                        case "Red Perimeter 1":
                            return true;
                    }

                }
            }
        }
            return false;
    }

    public void position(){ //constantly gives X and Y position of robot
        while(!isStopRequested()){
            telemetry.addData("X -", getRobotX());
            telemetry.addData("Y -", getRobotY());
            telemetry.update();
        }
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}