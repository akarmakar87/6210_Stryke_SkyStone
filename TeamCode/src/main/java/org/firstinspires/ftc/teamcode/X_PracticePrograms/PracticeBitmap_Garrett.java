package org.firstinspires.ftc.teamcode.X_PracticePrograms;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.ArrayList;
import java.util.Collections;

import static android.graphics.Color.red;


@Autonomous(name="Yellow Cube Detector", group = "auto")
@Disabled
public class PracticeBitmap_Garrett extends SkystoneLinearOpMode {

    public int detectSkystoneOnePix(Bitmap bm, boolean red){

        //set threshold for yellow or not yellow?
        int stonepos = 0;
        int leftRed, midRed, rightRed;

        if (bm != null) {

            //figure out proper threshold

            //actually blue
            if(red){
                //USE ALL THREE OR JUST TWO?
                leftRed = red(bm.getPixel(1150, 250)); //originally 250 310
                midRed = red(bm.getPixel(860,250));
                rightRed = red(bm.getPixel(560,250));
            }else{ //GET PIXELS FOR BLUE LATER
                //actually red
                leftRed = red(bm.getPixel(850,250));
                midRed = red(bm.getPixel(470,250));//orig 490
                rightRed = red(bm.getPixel(300,250)); //originally x was 200 but no detecting correctly
            }

            ArrayList<Integer> pixels = new ArrayList<>();
            pixels.add(leftRed);
            pixels.add(midRed);
            pixels.add(rightRed);

            //Finds max instead of min because the highest red value would be the yellow cube
            if(Collections.max(pixels) == leftRed){
                stonepos = -1;
            }else if(Collections.max(pixels) == midRed){
                stonepos = 0;
            }else if (Collections.max(pixels) == rightRed){
                stonepos = 1;
            }

            telemetry.addData("Reds Detected: ", pixels);
            telemetry.addData("stonepos: ", stonepos);
            telemetry.update();
            sleep(1000);
        }else{
            //change it to whatever is closest
            telemetry.addData("Bitmap null:", "Default center");
            telemetry.update();
        }

        return stonepos;
    }
    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        initBitmapVuforia();

        int pos = 0;

        waitForStart();

        pos = detectSkystoneOnePix(getBitmap(), false);

        telemetry.addData("Position of Yellow Cube: ", "pos");
        telemetry.update();

    }
}
