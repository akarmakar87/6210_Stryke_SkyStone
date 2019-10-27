package org.firstinspires.ftc.teamcode.Testing;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


@TeleOp(name="AshaBitmap", group = "auto")
//@Disabled
public class AshaBitmap extends LinearOpMode {

    public static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    public static final boolean PHONE_IS_PORTRAIT = false  ;

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
    public OpenGLMatrix lastLocPC = null;
    public OpenGLMatrix lastLocWC = null;
    public VuforiaLocalizer vuforia = null;

    public boolean targetVisible = false;
    public float phoneXRotate    = 0;
    public float phoneYRotate    = 0;
    public float phoneZRotate    = 0;

    WebcamName LogitechC310 = null;

    String VUFORIA_KEY = "AQt2xVL/////AAABmXIVKUnTcEJbqvVBjp/Sw/9SqarohYyKotzRjT/Xl1/S8KDwsFHv/zYw6rXqXTjKrnjk92GfBA4hbZaQP17d1N6BiBuXO2W/hFNoMGxiF+fWlnvtDmUM1H/MF9faMOjZcPNjnQ7X8DVwdDDha3A3aqaoegefkKxb4A5EjP8Xcb0EPJ1JA4RwhUOutLbCDJNKUq6nCi+cvPqShvlYTvXoROcOGWSIrPxMEiOHemCyuny7tJHUyEg2FTd2upiQygKAeD+LN3P3cT02aK6AJbQ0DlQccxAtoo1+b//H6/eGro2s0fjxA2dH3AaoHB7qkb2K0Vl7ReFEwX7wmqJleamNUG+OZu7K3Zm68mPudzNuhAWQ";
    VuforiaLocalizer vuforiaPC = null;
    VuforiaLocalizer vuforiaWC = null;
    VuforiaTrackables targetsPC = null;
    VuforiaTrackables targetsWC = null;
    List<VuforiaTrackable> allTrackables = null;

    VectorF translation;
    Orientation rotation;

    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        LogitechC310 = hardwareMap.get(WebcamName.class, "LogitechC310");

        //localizer for phone camera
        VuforiaLocalizer.Parameters paramPC = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        paramPC.vuforiaLicenseKey = VUFORIA_KEY;
        paramPC.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforiaPC = ClassFactory.getInstance().createVuforia(paramPC);
        targetsPC = this.vuforiaPC.loadTrackablesFromAsset("Skystone");

        //localizer for webcam
        VuforiaLocalizer.Parameters paramWC = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        paramWC.vuforiaLicenseKey = VUFORIA_KEY;
        paramWC.cameraName = LogitechC310;
        vuforiaWC = ClassFactory.getInstance().createVuforia(paramWC);
        targetsWC = this.vuforiaWC.loadTrackablesFromAsset("Skystone");

        //LIST OF ALL  for PC
        // know that indexes 1-12
        VuforiaTrackable blueRearBridge = targetsPC.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsPC.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsPC.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsPC.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsPC.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsPC.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsPC.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsPC.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsPC.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsPC.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsPC.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsPC.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */

        //MAY HAVE TO DO TWO SETS OF TRACKABLES FOR BOTH CAMERAS

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

        ////////////
        // indexes 13-24 wc
        VuforiaTrackable blueRearBridge_2 = targetsWC.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge_2 = targetsWC.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge_2 = targetsWC.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge_2 = targetsWC.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1_2 = targetsWC.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2_2 = targetsWC.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1_2 = targetsWC.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2_2 = targetsWC.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1_2 = targetsWC.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2_2 = targetsWC.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1_2 = targetsWC.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2_2= targetsWC.get(12);
        rear2.setName("Rear Perimeter 2");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */

        //MAY HAVE TO DO TWO SETS OF TRACKABLES FOR BOTH CAMERAS

        allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsPC);
        allTrackables.addAll(targetsWC);


        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge_2.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge_2.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge_2.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge_2.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1_2.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2_2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1_2.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2_2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1_2.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2_2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1_2.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2_2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));


        ///////////

        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float PC_FORWARD_DISPLACEMENT  = 0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float PC_VERTICAL_DISPLACEMENT = 0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float PC_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        final float WC_FORWARD_DISPLACEMENT  = 0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot-center
        final float WC_VERTICAL_DISPLACEMENT = 0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float WC_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromPC = OpenGLMatrix
                .translation(PC_FORWARD_DISPLACEMENT, PC_LEFT_DISPLACEMENT, PC_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        OpenGLMatrix robotFromWC = OpenGLMatrix
                .translation(WC_FORWARD_DISPLACEMENT, WC_LEFT_DISPLACEMENT, WC_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (int i = 0; i < allTrackables.size(); i++) {

            if (((VuforiaTrackableDefaultListener) allTrackables.get(i).getListener()).isVisible()) {

                if(i < 13){
                    ((VuforiaTrackableDefaultListener) allTrackables.get(i).getListener()).setPhoneInformation(robotFromPC, paramPC.cameraDirection);
                }else{
                    ((VuforiaTrackableDefaultListener) allTrackables.get(i).getListener()).setPhoneInformation(robotFromWC, paramWC.cameraDirection);
                }
            }
        }

        waitForStart();

        while (opModeIsActive()){
            //content here
        }

    }



}


