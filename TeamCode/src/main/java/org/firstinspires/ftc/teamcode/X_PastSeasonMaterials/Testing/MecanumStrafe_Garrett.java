package org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.X_PastSeasonMaterials.MecanumLinearOpMode;

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

