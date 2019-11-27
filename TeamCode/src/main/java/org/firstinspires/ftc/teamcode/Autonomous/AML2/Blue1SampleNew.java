package org.firstinspires.ftc.teamcode.Autonomous.AML2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

@Autonomous(name="Blue1SampleNew", group = "auto")

//@Disabled
public class Blue1SampleNew extends SkystoneLinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        //sets up imu and vuforia
        init(hardwareMap, true);

        int heading = 0, adjustment = 0;

        waitForStart();

        //adjustment = adjustForSkystone(detectSkystoneOnePix(getBitmap(),red)); //MOVE ROBOT FORWARD OR BACKWARD ALONG WALL

        turnPID(-90, 0.7, 0, 2, 3000); //TURN 90 TO FACE STONES

        driveDistance(0.5,15);

        driveDistance(-0.5,50);

        foundationR.setPosition(1); //down

        sleep(1500);

        driveDistance(0.5,15);

        turnPID(-180, 0.7, 0, 2, 4000); //TURN 90 TO FACE STONES


        /**
         driveDistance(0.5, 25);

         setArm(-600,-0.3); //PUT ARM DOWN

         arm.setPower(0);

         sleep(2000);

         driveDistance(0.4, 5);

         setClawPosition(false); //CLOSE CLAW

         sleep(250); //WAIT FOR CLAW TO CLOSE

         setArm(-550,0.3); //LIFT ARM

         driveDistance(-0.3, 14); //MOVE BACKWARDS A BIT

         turnPID(180, 0.6, 0, 2, 3500); //WHATS HEADING HERE?

         driveDistance(0.5, 40 + adjustment); //DRIVE TO LINE

         setClawPosition(true); //OPEN CLAW TO DROP BLOCK

         sleep(250);

         driveDistance(-0.5, 60 + adjustment); //DRIVE TO SECOND SET OF STONES

         turnPID(0, 0.6, 0, 0, 3500); //TURN TO FACE STONES

         setArmPosition(-550); //MOVE ARM BACK TO POSITION ABOVE BLOCK?

         driveDistance(0.3, 9); //MOVE TO THE STONES

         setArmPosition(-600);//PROBABLY WILL ALSO HAVE TO LOWER ARM BEFORE

         setClawPosition(false); //CLOSE CLAW

         sleep(250);

         setArmPosition(-550); //LIFT UP ARM

         driveDistance(-0.3, 9); //MOVE BACKWARDS

         turnPID(heading, 0.6, 0, 2, 3500); //TURN TO FACE LINE

         driveDistance(0.5, 33 + adjustment); //DRIVE TO LINE AND PARK

         setClawPosition(true); //OPEN CLAW AND DROP BLOCK
     **/
        }

}
