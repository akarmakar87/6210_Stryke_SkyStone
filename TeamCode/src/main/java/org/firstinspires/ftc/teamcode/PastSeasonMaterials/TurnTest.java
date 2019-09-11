package org.firstinspires.ftc.teamcode.PastSeasonMaterials;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="TurnTest", group = "auto")

@Disabled

public class TurnTest extends Original_TurnPIDMethod_Asha {

    @Override
    public void runOpMode() throws InterruptedException {

        init(hardwareMap, true);

        waitForStart();

        turnP(90, 0.6/90,5);
        telemetry.addData("Turn P","Complete");
        turnPI(90, 0.6/90, 0.5, 5);
        telemetry.addData("Turn PI","Complete");
        turnPD(90, 0.6/90, 0.1, 5);
        telemetry.addData("Turn PD","Complete");
        turnPID(90, 0.6/90, 0.5, 0.1, 5);
        telemetry.addData("Turn PID","Complete");


    }
}