package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Hardware9330;
import org.firstinspires.ftc.teamcode.Subsystems.Drive9330;
import org.firstinspires.ftc.teamcode.Subsystems.Intake9330;

@Autonomous(name = "AutoRightIn9330", group = "Opmode")
public class AutoRightIn9330 extends LinearOpMode {

    /**
     *
     * 1.back up until see image
     * 2.Check which quadrant the robot is located in
     *
     * AUTONOMOUS RED 1 STEPS:
     * 1. Turn 180 degrees
     * 2. scan for Skyblocks
     * 3. when see skyblock strafe until y is -8 and our heading is 0
     * 4. once we're there turn left and strafe right for a couple seconds
     * 5. take in the skystone
     *
     *
     * **/

    Hardware9330 robot9330 = new Hardware9330();
    Drive9330 drive;
    Intake9330 intake;




    @Override
    public void runOpMode() throws InterruptedException {

        initGeneral();
        waitForStart();
        //drive.driveForwardTime(-1, 0.35);
        drive.driveRightTime(1,0.75);




    }

    public void initGeneral() {
        robot9330.init(hardwareMap);
        drive = new Drive9330(robot9330);
        intake = new Intake9330(robot9330);

    }



}
