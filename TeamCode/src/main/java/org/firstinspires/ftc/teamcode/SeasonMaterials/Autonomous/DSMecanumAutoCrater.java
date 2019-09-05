package org.firstinspires.ftc.teamcode.SeasonMaterials.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.teamcode.SeasonMaterials.OldMecanumLinearOpMode;

@Autonomous(name="DSMecanumAutoCrater", group = "auto")
@Disabled
public class DSMecanumAutoCrater extends OldMecanumLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        double startAng = init(hardwareMap, true);

        double angleOff = 0;

        init(hardwareMap, true);
//
        // Set up detector
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        telemetry.addData("Mode", "setting up detector...");
        telemetry.update();

        telemetry.addData("detector", "enabled");
        telemetry.update();

        double dist = 0;
        waitForStart();

        lift.setPower(0.90);    //LIFT PULLS ROBOT UP (releases tension for easy unlock)
        lock.setPosition(1);    //UNLOCK LIFT
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT); //LET GRAVITY TAKE THE ROBOT DOWN
        sleep(1000);
        lock.setPosition(0);    //Stop lock movement
        sleep(1000);
        int liftTarget = lift.getCurrentPosition()-640; //FIND HOW FAR THE LIFT NEEDS TO RETRACT
        while (!isStopRequested() && lift.getCurrentPosition() > liftTarget){   //RETRACT LIFT
            lift.setPower(-1);
        }
        lift.setPower(0);
        driveDistance(0.3,0.5); //What is this for???
        double ang = getYaw();  //Why do we need this???

        strafeDistance(-0.3, 5, true); //MOVE A BIT TO TRIGGER CAMERA VIEWING

        //START DETECTION

        findGold(3); //GET GOLD POSITION
        int gold = retPos();
        sleep(1000);
        telemetry.addData("Gold is at", gold);
        telemetry.update();
        driveDistance(-0.3,4); //MOVE FORWARD OUT OF LANDER ZONE

        dist = pushGold(gold, true);

        angleOff = getYaw(); //UPDATE ANGLE
        disableDetector();
        rotate(0.2, 90 - angleOff, false, 5);   //ROTATE TOWARD WALL
        sleep(1000);


        driveDistance(0.5, dist); //MOVE TOWARD WALL
        sleep(1000);
        driveDistance(0.5, 5);  //ALIGN WITH WALL (by running into it)
        driveDistance(-0.5, 0.5);   //MOVE BACK FROM WALL (so we don't get caught on it)
        strafeDistance(0.8, 17,true);   //STRAFE TOWARD DEPOT
        driveDistance(0.5,5);   //REALIGN WITH WALL (to avoid hitting a mineral)
        strafeDistance(0.8, 17,true);   //CONTINUE INTO DEPOT
        marker.setPosition(0.41);   //DEPLOY MARKER
        sleep(1000);
        strafeDistance(0.8, 36.5,false);    //STRAFE TOWARD CRATER
        driveDistance(-0.5,2.5);    //REALIGN WITH WALL (so we don't hit our alliance partner's mineral)
        strafeDistance(0.8, dist+10,false); //STRAFE INTO CRATER
        marker.setPosition(0.2);    //RETRACT MARKER DEPLOYMENT*/

        telemetry.addData("Status ", " auto done");
    }
}