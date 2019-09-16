package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name = "driveForwardV", group = "auto")
//@Disabled

public class driveForwardV extends SkystoneLinearOpMode { // DONT USE

    @Override
    public void runOpMode() throws InterruptedException {}

    /**
     *
     * @param kP (Depending on the situation, decide at what speed you want to start the turn,
     *           then divide by the angle) = For example, kP = 0.6/90
     * @param kI (Should be a very small number, big effect)
     * @param kD (Increase as needed to minimize oscillations)
     * @param timeOut
     */

    public void drivePID(double inches, double kP, double kI, double kD, double timeOut){ //tAngle is 0

        double power, prevError, error, dT, prevTime, currTime, P, I, D; //DECLARE ALL VARIABLES

        prevError = error = - getYaw(); //INITIALIZE THESE VARIABLES

        power = dT = prevTime = currTime = P = I = D = 0;

        ElapsedTime time = new ElapsedTime(); //CREATE NEW TIME OBJECT
        resetTime();
        while (Math.abs(error) > 0.5 && currTime < timeOut){
            prevError = error;
            error = - getYaw(); //GET ANGLE REMAINING TO TURN (tANGLE MEANS TARGET ANGLE, AS IN THE ANGLE YOU WANNA GO TO)
            prevTime = currTime;
            currTime = time.milliseconds();
            dT = currTime - prevTime; //GET DIFFERENCE IN CURRENT TIME FROM PREVIOUS TIME
            P = error;
            I = error * dT;
            D = (error - prevError)/dT;
            power = P * kP + I * kI + D * kD;

            if (power < 0) {
                setMotorPowers(power - .1, -power + .1);
            } else {
                setMotorPowers(power + .1, -power - .1);
            }


            telemetry.addData("P:", P)
                    .addData("I:", I)
                    .addData("D:", D)
                    .addData("power", power)
                    .addData("error: ", error)
                    .addData("currTime: ", currTime);
        }
    }
}

