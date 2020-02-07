package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="encoder/turn test", group = "auto")

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

        /*strafeAdjust(0.4, 50,  0,true);

        sleep(2000);

        strafeAdjust(0.4, 50,  0,false);

        sleep(1000);*/

        //rotate(87,3000);
        //sleep(1000);
        //strafeAdjust(0.5,40,0,true);

        //turnPID(-90, 0.6/360,0.001,2,3000);

        //driveAdjust(0,-0.5,40, 7); //MOVE BACKWARD

        //strafeAdjust(0.5,70,0,true);

        /*turnPID(180, 0.6/180,0.0001,0.5,5000);
        sleep(1000);
        turnPID(-90, 0.6/180,0.0001,0.5,5000);*/

        driveAdjust(0,-0.5,40, 7);

        //driveDistance(-0.5,24);
    }

}
