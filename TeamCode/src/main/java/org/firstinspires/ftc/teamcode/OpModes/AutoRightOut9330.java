package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Hardware9330;
import org.firstinspires.ftc.teamcode.Subsystems.Drive9330;
import org.firstinspires.ftc.teamcode.Subsystems.Intake9330;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(name = "AutoRightOut9330", group = "Opmode")
public class AutoRightOut9330 extends LinearOpMode {

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
        drive.driveForwardTime(-1, 0.5);
        drive.driveRightTime(1,0.75);




    }

    public void initGeneral() {
        robot9330.init(hardwareMap);
        drive = new Drive9330(robot9330);
        intake = new Intake9330(robot9330);
    }



}
