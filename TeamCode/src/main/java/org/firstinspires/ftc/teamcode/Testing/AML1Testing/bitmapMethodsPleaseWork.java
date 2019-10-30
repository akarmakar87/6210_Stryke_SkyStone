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
import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

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
public class bitmapMethodsPleaseWork extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        initBitmapVuforia();

        waitForStart();

        int pos;
        while (opModeIsActive() && !isStopRequested()){
            detectSkystone(getBitmap(500));
        }
    }
}


