package org.firstinspires.ftc.teamcode.Autonomous.AML3;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Foundation Blue3", group = "auto")

//@Disabled
public class foundationBlue3 extends SkystoneLinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu
        init(hardwareMap, true);

        waitForStart();

        driveDistance(0.4, 20);    //align with foundation

        turnPID(-90, 0.6/360, 0.001, 2, 3000);   //turn toward foundation

        driveAdjust(270,0.6, 54, 4);    //Drive to foundation

        foundationD(false);  //Grab foundation

        sleep(1000);    //wait for grab

        driveAdjust(270, -0.2, 10, 4); //pull hooks into position

        driveAdjust(270, -0.6, 65, 5);  //pull foundation back

        turnPID(0, 0.6/360, 0.001, 2, 4000);    //turn robot & foundation into wall

        foundationD(true);     //Release foundation

        sleep(1000);    //wait for release

        driveDistance(.5,35);   //Push foundation into wall (maybe switch to driveAdjust incase it gets stuck)

        strafeAdjust(0.6,20,0,false);   //Align with parking spot

        driveDistance(-0.4, 95);     //Drive to the parking spot (backward)
    }
}