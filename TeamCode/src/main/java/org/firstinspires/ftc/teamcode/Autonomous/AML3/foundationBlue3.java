package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue Foundation", group = "auto")

//@Disabled
public class foundationBlue3 extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 15);    //align with foundation

        turnPID(-90, 0.6/90,0.0001,2,3000);   //turn toward foundation

        driveAdjust(270,0.6, 57, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);    //wait for grab

        driveAdjust(270, -0.2, 10, 4); //pull hooks into position

        driveAdjust(270, -0.6, 60, 5);  //pull foundation back

        turnPIDF(0, 0.8/90,0.0001,2, 4000);    //turn robot & foundation into wall

        foundationD(true);     //Release foundation

        sleep(1000);    //wait for release

        driveDistance(.8,35);   //Push foundation into wall (maybe switch to driveAdjust incase it gets stuck)

        driveAdjust(0, -0.5, 7, 2);  //back away from foundation

        strafeAdjust(0.6,15,0,false);   //Align with parking spot

        turnPID(0, 0.6/90,0.0001,2,2000);

        driveDistance(-0.4, 95);     //Drive to the parking spot (backward)
    }
}