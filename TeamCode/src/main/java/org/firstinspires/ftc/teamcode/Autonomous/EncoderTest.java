package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="encoder test", group = "auto")

//@Disabled
public class EncoderTest extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

       /* driveDistance(0.4,10);

        sleep(2000);

        driveDistance(-0.4,10);

        sleep(2000);*/

        //strafeDistance(0.3, 10,  true);

        //sleep(1000);

        /*strafeAdjust(0.5, 60,  0,true);

        sleep(1000);

        strafeAdjust(0.5, 60,  0,false);

        sleep(1000);*/

        strafeAdjust(0.4, 50,  0,true);

        sleep(2000);

        strafeAdjust(0.4, 50,  0,false);

        sleep(1000);



    }

}
