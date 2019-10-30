package org.firstinspires.ftc.teamcode.Testing.AML1Testing;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;


@TeleOp (name="pray to all the vuforia gods", group = "teleop")
//@Disabled
public class bitmapMethodsPleaseWork extends LinearOpMode{

    public static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    public static final boolean PHONE_IS_PORTRAIT = false;
    public float phoneXRotate    = 0;
    public float phoneYRotate    = 0;
    public float phoneZRotate    = 0;
    public VuforiaLocalizer vuforiaWC = null;

    WebcamName LogitechC310 = null;

    String VUFORIA_KEY = "AQt2xVL/////AAABmXIVKUnTcEJbqvVBjp/Sw/9SqarohYyKotzRjT/Xl1/S8KDwsFHv/zYw6rXqXTjKrnjk92GfBA4hbZaQP17d1N6BiBuXO2W/hFNoMGxiF+fWlnvtDmUM1H/MF9faMOjZcPNjnQ7X8DVwdDDha3A3aqaoegefkKxb4A5EjP8Xcb0EPJ1JA4RwhUOutLbCDJNKUq6nCi+cvPqShvlYTvXoROcOGWSIrPxMEiOHemCyuny7tJHUyEg2FTd2upiQygKAeD+LN3P3cT02aK6AJbQ0DlQccxAtoo1+b//H6/eGro2s0fjxA2dH3AaoHB7qkb2K0Vl7ReFEwX7wmqJleamNUG+OZu7K3Zm68mPudzNuhAWQ";

    VuforiaLocalizer.CloseableFrame frame; //takes the frame at the head of the queue
    Image rgb = null;

    @Override
    public void runOpMode() throws InterruptedException {

        initVuforia();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()){
            detectSkystone(getBitmap());
        }
    }

    public void initVuforia(){

        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        LogitechC310 = hardwareMap.get(WebcamName.class, "Logitech C310");

        //localizer for webcam
        VuforiaLocalizer.Parameters paramWC = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        paramWC.vuforiaLicenseKey = VUFORIA_KEY;
        paramWC.cameraName = LogitechC310;
        vuforiaWC = ClassFactory.getInstance().createVuforia(paramWC);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB565 format for the image
        vuforiaWC.setFrameQueueCapacity(1); //tells VuforiaLocalizer to only store one frame at a time

        telemetry.addData("Vuforia:", "initialized");
        telemetry.update();
    }

    public Bitmap getBitmap() throws InterruptedException {

        frame = vuforiaWC.getFrameQueue().take();
        long num = frame.getNumImages();

        for(int i = 0; i < num; i++){
            if(frame.getImage(i).getFormat() == PIXEL_FORMAT.RGB565){
                rgb = frame.getImage(i);
            }
        }

        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());

        /*telemetry.addData("Bitmap:", "Got it");
        telemetry.update();*/

        frame.close();

        return bm;
    }

    public int detectSkystone(Bitmap bm){
        //if(vuforiaWC.getFrameQueue() == null){ //Just guessing here because vuforiaWC.rgb is not a thing

            //set threshold for yellow or not yellow?
            int stonepos = 0;

            //figure out proper thresholds
            int redLim = 250;
            int greenLim = 220;
            int blueLim = 2;

            ArrayList<Integer> colorPix = new ArrayList<Integer>();

            for (int c = 0; c < bm.getWidth(); c++){
                for(int r = 0; r < bm.getHeight(); r++){
                    if(red(bm.getPixel(c, r)) >= redLim && green(bm.getPixel(c, r)) >= greenLim && blue(bm.getPixel(c, r)) >= blueLim){
                        colorPix.add(c);
                    }
                }
            }

            int sum = 0;
            for(Integer x : colorPix)
                sum += x;

            int avgX = sum/colorPix.size();

            int maxX = Collections.max(colorPix);
            int minX = Collections.min(colorPix);

            if(avgX < 0){
                stonepos = -1;
            }else if(avgX < 0){
                stonepos = 0;
            }else{
                stonepos = 1;
            }

            telemetry.addData("max x: ", maxX);
            telemetry.addData("min x: ", minX);
            telemetry.addData("x avg: ", avgX);
            telemetry.addData("stonepos: ", stonepos);
            telemetry.update();

            return stonepos;
    }
}


