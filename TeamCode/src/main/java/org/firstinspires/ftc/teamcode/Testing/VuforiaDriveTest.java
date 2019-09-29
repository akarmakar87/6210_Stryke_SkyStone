package org.firstinspires.ftc.teamcode.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;
import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name="VuforiaDriveTest", group = "auto")
//@Disabled
public class VuforiaDriveTest extends SkystoneLinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);
        initVuforia();

        waitForStart();

    }

    public void driveToPoint(double power, double xTarget, double yTarget){
        updateRobotPosition();
        double curX = getRobotX();
        double curY = getRobotY();

        double trgtX = xTarget;
        double trgtY = yTarget;

        double diffX = trgtX-curX;
        double diffY = trgtY-curY;

        double curHeading = getRobotHeading(); //Assuming 0-360
        double diffHeading = Math.abs(Math.toDegrees(Math.atan(diffX/diffY))); //CALCULATE ANGLE FROM CURRENT COORD TO TARGET COORD
        double trgtHeading;

        //ACCOUNT FOR ANGLES IN ALL FOUR QUADRANTS
        if(diffX > 0 && diffY > 0)
            trgtHeading = diffHeading;
        else if(diffX < 0 && diffY > 0)
            trgtHeading = 360-diffHeading;
        else if(diffX < 0 && diffY < 0)
            trgtHeading = 180 + diffHeading;
        else if(diffX > 0 && diffY < 0)
            trgtHeading = 180 - diffHeading;
        else
            trgtHeading = 0;

        turnPIDV(trgtHeading, 0.4, 0, 0, (trgtHeading-curHeading > 180));
        driveForward(trgtX, trgtY, power, trgtHeading);
    }

   /* public void turnPIDV(double tAngle, double kP, double kI, double kD, boolean flip){

        double power, prevError, error, dT, prevTime, currTime, P, I, D; //DECLARE ALL VARIABLES

        prevError = error = tAngle - getRobotHeading(); //INITIALIZE THESE VARIABLES

        power = dT = prevTime = currTime = P = I = D = 0;

        ElapsedTime time = new ElapsedTime(); //CREATE NEW TIME OBJECT
        resetTime();
        while (Math.abs(error) > 0.5){ //put timeout in later
            updateRobotPosition();
            prevError = error;
            error = tAngle - getRobotHeading(); //GET ANGLE REMAINING TO TURN (tANGLE MEANS TARGET ANGLE, AS IN THE ANGLE YOU WANNA GO TO)
            prevTime = currTime;
            currTime = time.milliseconds();
            dT = currTime - prevTime; //GET DIFFERENCE IN CURRENT TIME FROM PREVIOUS TIME
            P = error;
            I = error * dT;
            D = (error - prevError)/dT;
            power = P * kP + I * kI + D * kD;

            if (flip)
                power *= -1;

            //Basically, tell the robot to take the shortest way to the target angle.
            //For example, if robot is at 359 and target angle is 5 degrees, move 6 deg clockwise instead
            //of 354 deg counter clockwise

            setMotorPowers(Range.clip(power, 0.2, 1), -Range.clip(power, 0.2, 1));

            telemetry.addData("tAngle: ", tAngle)
                    .addData("P:", P)
                    .addData("I:", I)
                    .addData("D:", D)
                    .addData("power", power)
                    .addData("error: ", error)
                    .addData("currTime: ", currTime);
            telemetry.update();
        }
    }

    public void driveForward(double x, double y, double power, double trgtHead){
        // Angle adjustment while driving to a specific point
        // TO DO: calculate proportional error to decrease power more if robot angle is larger

        double error = 0;

        if(trgtHead > getRobotHeading()){
            error = trgtHead - getRobotHeading();
        }else{
            error = getRobotHeading() - getRobotHeading();
        }

        while ((Math.abs(x - getRobotX()) > 5) || (Math.abs(y - getRobotY()) > 5))  {
            if (trgtHead - getRobotHeading() > 1)
                setMotorPowers(power, power * Math.abs((trgtHead - getRobotHeading())/error)); // default error is 0.8
            else if (trgtHead - getRobotHeading() < -1)
                setMotorPowers(power * 0.8, power);
            else
                setMotorPowers(power, power);

            //if (Math.abs(trgtHead - getRobotHeading()) > 30) // stops robot if robot is turned too off course
            //break;
        }

        telemetry.addData("Target: ", x + " , " + y);
        telemetry.update();
    }*/
}