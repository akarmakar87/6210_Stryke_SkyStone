package org.firstinspires.ftc.teamcode.PastSeasonMaterials.Testing;

import android.graphics.drawable.GradientDrawable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.PastSeasonMaterials.MecanumLinearOpMode;

@Autonomous(name = "ColorTutorial", group = "Sensor")
//@Disabled

public class MecanumStrafe_Garrett extends MecanumLinearOpMode {


    Orientation             lastAngles = new Orientation();
    double power, correction;


    @Override
    public void runOpMode() throws InterruptedException {
        //Initialize
        init(hardwareMap, true);
        double angle = getYaw();
        //Wait for start
        waitForStart();

        while (opModeIsActive()) {
            setStrafePowers(1.00, true, angle);
        }
    }
    }

