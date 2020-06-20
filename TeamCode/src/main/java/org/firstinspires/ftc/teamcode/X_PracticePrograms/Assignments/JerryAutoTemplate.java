package org.firstinspires.ftc.teamcode.X_PracticePrograms.Assignments;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

import java.util.ArrayList;
import java.util.Collections;

import static android.graphics.Color.blue;
import static android.graphics.Color.red;


@Autonomous(name="Give name to show on DS", group = "auto")
@Disabled
public class JerryAutoTemplate extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);
        initBitmapVuforia();
        int pos;

        waitForStart();

        //WRITE ACTIONS HERE
        pos = newDetectSkystone(getBitmap());

        telemetry.addData("LABEL:", "VALUE HERE");
        telemetry.update();

    }


    public int newDetectSkystone(Bitmap bm) throws InterruptedException {

        //set threshold for yellow or not yellow?
        int stonepos = 0;
        int leftRed, midRed, rightRed;

        if (bm != null) {

            //figure out proper threshold

            //actually blue

            leftRed = blue(bm.getPixel(1150, 250)); //originally 250 310
            midRed = blue(bm.getPixel(860,250));
            rightRed = blue(bm.getPixel(560,250));

            ArrayList<Integer> pixels = new ArrayList<>();
            pixels.add(leftRed);
            pixels.add(midRed);
            pixels.add(rightRed);

            if(Collections.min(pixels) == leftRed){
                stonepos = -1;
            }else if(Collections.min(pixels) == midRed){
                stonepos = 0;
            }else if (Collections.min(pixels) == rightRed){
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
}
