package org.firstinspires.ftc.teamcode.Testing;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.ArrayList;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;
@Autonomous(name="Bitmap", group = "auto")
@Disabled
public class BitmapTest extends SkystoneLinearOpMode {

    private LinearOpMode opMode;

    // importing vuforiaPC class for taking an image
    private VuforiaLocalizer vuforia;

    // variable for the gold cube's position
    private String skystone;

    // RGB values for finding yellow pixels
    private final int RED_THRESHOLD = 140;
    private final int GREEN_THRESHOLD = 100;
    private final int BLUE_THRESHOLD = 60;

    // constructor turns on webcam imaging and sets capacity and format for frame
    public BitmapTest() throws InterruptedException {
        initVuforia();
        while (opModeIsActive() && !isStopRequested()){
            telemetry.addData("pos", sample());
            telemetry.update();
        }

        // set RGB format to 565
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

        // allowing the frame to only be 4 images at a time
        vuforia.setFrameQueueCapacity(4);
    }

    public Bitmap getBitmap() throws InterruptedException{
        // method to actually capture frame
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        Image rgb = frame.getImage(1);

        long numImages = frame.getNumImages();

        // go through all images taken in frame and find ones that match correct format
        for (int i = 0; i < numImages; i++) {
            int fmt = frame.getImage(i).getFormat();

            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;

            }
            else {
                opMode.telemetry.addLine("Didn't find correct rgb format");
                opMode.telemetry.update();

            }

        }

        // create bitmap
        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());

        frame.close();

        opMode.telemetry.addLine("Got Bitmap");
        opMode.telemetry.update();

        opMode.sleep(500);

        return bm;
    }

    public String sample() throws InterruptedException {
        Bitmap bitmap = getBitmap();
        ArrayList<Integer> xValues = new ArrayList<>();

        int avgX = 0;

        // scan all columns
        for (int colNum = 0; colNum < bitmap.getWidth(); colNum ++) {

            // GET MAX ROW NUM
            for (int rowNum = 0 ; rowNum < 240; rowNum ++) {
                int pixel = bitmap.getPixel(colNum, rowNum);

                // receive R, G, and B values for each pixel
                int redPixel = red(pixel);
                int greenPixel = green(pixel);
                int bluePixel = blue(pixel);

                // only add x-coordinates of yellow pixels to list
                if (redPixel >= RED_THRESHOLD && greenPixel >= GREEN_THRESHOLD && bluePixel <= BLUE_THRESHOLD) {

                    xValues.add(colNum);

                }

            }

        }
        // get sum of all yellow pixels' x coordinates
        for (int x : xValues) {
            avgX+= x;
        }


        // get average x-coordinate value of all yellow pixels
        try {
            avgX /= xValues.size();
        } catch (ArithmeticException E) {
            skystone = "right";

        }

        opMode.telemetry.addData("AVG X = ", avgX);

        // use constants (235, 500) to determine which third of the image cube is in
        if (avgX < 235) {
            skystone = "left";

        }
        else if (avgX < 500) {
            skystone = "center";

        }
        else {
            skystone = "right";

        }
        opMode.telemetry.addData("SkyStone Position", skystone);
        opMode.telemetry.update();

        return skystone;

    }
}
