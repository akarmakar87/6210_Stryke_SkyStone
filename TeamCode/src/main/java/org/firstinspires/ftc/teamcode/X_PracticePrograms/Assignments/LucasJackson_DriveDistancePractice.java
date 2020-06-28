package org.firstinspires.ftc.teamcode.X_PracticePrograms.Assignments;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.SkystoneLinearOpMode;

public class LucasJackson_DriveDistancePractice extends SkystoneLinearOpMode {

    private void driveDistance(double power, double distance, int timeout) {

        double encodedDistance = distance * encoderToInches;
        ElapsedTime et = new ElapsedTime();
        et.reset();
        resetEncoders();
        setMotorPowers(power, power);

        while (opModeIsActive() && et.seconds() < timeout && getEncoderAvg() <= encodedDistance) {
        }
        setMotorPowers(0, 0);

    }
}
