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
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;
import org.firstinspires.ftc.teamcode.Hardware9330;
import org.firstinspires.ftc.teamcode.Subsystems.Arm9330;
import org.firstinspires.ftc.teamcode.Subsystems.ColorSensor9330;
import org.firstinspires.ftc.teamcode.Subsystems.Drive9330;
import org.firstinspires.ftc.teamcode.Subsystems.Intake9330;
import org.firstinspires.ftc.teamcode.Subsystems.PlatformGrabber9330;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(name = "AutoOld9330", group = "Opmode")
public class AutoOld9330 extends LinearOpMode implements Runnable {

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
    PlatformGrabber9330 pGrabber;
    Arm9330 arm;
    ColorSensor9330 color;
    private Thread thread;

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;


    private static final String VUFORIA_KEY = "AQ5rX2z/////AAABmftRpcYx3ksil75FHfjVmeJ38uEwEOUSx8N+Bj6bGGS3vUnd+bFcHJWfS4SH9kPQN8igGtAr4uC8lwuR5elGNtWaDyyxU34KjTJjXoU7rGbuDl/SZk5k1QACTtyh+/ej78I331C0YkOM/GXIwO3IrcKuFPrvE7WfBWfo4EKgpmAlBIMJZw8D/38uP4Rf32fo8ylp3SdNt0/Zdh2yS6ilnkAZnZ4trQUHchlq1jMMtJgYy+RwqdfUo3rdFUKwkDv/JWIK43HP1rZw4w8ltVBNe+Te65JHaMqwIqK3Rdnzi819rFEFof6o2Sy0awrF/7UifgLlUyIL087tfdpl0RMvYUGSOglNrzZJLxcIbHwCCUjE";

    //define constants and conversions
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;

    VuforiaTrackables targetsSkyStone;
    List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();

    int armStartPos = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("State", "init");
        telemetry.update();
        initVuforia();
        initGeneral();
        thread = new Thread(this);
        thread.start();
        telemetry.addData("State", "Ready to Run");
        telemetry.update();
        waitForStart();

        arm.moveToPos(1, 500+armStartPos);
        Thread.sleep(1000);
        targetsSkyStone.activate();
        telemetry.addData("State", "skystone target activated");
        telemetry.update();
        String startPos = "";

        //find start position
        while (true) {
            telemetry.addData("State", "Driving backwards");
            telemetry.update();
            if (System.currentTimeMillis() < 3000)
                drive.driveForward(0.7);

            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    if(trackable.getName() == "Red Perimeter 2"){
                        startPos = "red1";
                    } else if(trackable.getName() == "Red Perimeter 1"){
                        startPos = "red2";
                    } else if(trackable.getName() == "Blue Perimeter 1"){
                        startPos = "blue1";
                    } else if(trackable.getName() == "Blue Perimeter 2"){
                        startPos = "blue2";
                    }


                    telemetry.addData("Visible Target", trackable.getName());
                    telemetry.update();

                    targetVisible = true;
                    break;
                }
            }

            if (startPos != ""){
                telemetry.addData("State", "Breaking out of while loop");
                telemetry.addData("Startpos", startPos);
                telemetry.update();
                drive.stop();
                break;
            }

//            // check all the trackable targets to see which one (if any) is visible.
//            targetVisible = false;
//            for (VuforiaTrackable trackable : allTrackables) {
//                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
//                    telemetry.addData("Visible Target", trackable.getName());
//                    targetVisible = true;
//
//                    // getUpdatedRobotLocation() will return null if no new information is available since
//                    // the last time that call was made, or if the trackable is not currently visible.
//                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
//                    if (robotLocationTransform != null) {
//                        lastLocation = robotLocationTransform;
//                    }
//                    break;
//                }
//            }
//
//            // Provide feedback as to where the robot is located (if we know).
//            if (targetVisible) {
//                // express position (translation) of robot in inches.
//                VectorF translation = lastLocation.getTranslation();
//                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
//                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);
//
//                // express the rotation of the robot in degrees.
//                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
//                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
//            }
//            else {
//                telemetry.addData("Visible Target", "none");
//            }
//            telemetry.update();
                }

        switch(startPos) {
            case "red1":

//                telemetry.addData("Quadrant", "red1");
//                telemetry.update();
//
//                double lastYPos = 0;
//                double lastXPos = 0;
//                double lastHeadingPos = 0;
//                double targetYPos = -8;
//                double targetXPos = 24;
//                double targetHeadingPos = 0;


                drive.gyroTurn(93);
                drive.driveForwardTime(-1, 0.3);
                drive.driveRightTime(1 , 0.78);
                thread.sleep(2000);
                Boolean searching = true;
                double beforeWhile = System.currentTimeMillis();
                double afterWhile = 0;
                while(searching){
                    if( color.getG() < 2 && color.getR() < 2){
                     drive.driveForward(0);
                     searching = false;
                     afterWhile = System.currentTimeMillis();

                    }
                    else{
                        drive.driveForward(-0.3);
                    }
                }
                double searchingTime = (afterWhile - beforeWhile)/1000;
                telemetry.addData("searchingTime: ", searchingTime);
                telemetry.update();
                thread.sleep(2000);
                drive.driveForwardTime(1, 0.4);
                drive.driveRightTime(1, 0.7);
                drive.driveForwardTime(-1, 0.4);
//                telemetry.addData("get Yaw", drive.getYaw());
//                telemetry.update();
//                while (!isStopRequested()) {
//                    VuforiaTrackable targetTrackable = null;
//
//
//                    for (VuforiaTrackable trackable : allTrackables) {
//                        if (((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible()) {
//                            if (trackable.getName() == "Stone Target") {
//                               if(targetTrackable == null)
//                                   targetTrackable = trackable;
//                               else if (targetTrackable.getLocation().getTranslation().get(1) <= trackable.getLocation().getTranslation().get(1))
//                                   targetTrackable = trackable;
//                            }
//
//                            telemetry.addData("Visible Skystone", trackable.getName());
//                            telemetry.update();
//
//                            targetVisible = true;
//                            break;
//                        }
//                    }
//                    if(targetTrackable != null) {
//                        lastYPos = targetTrackable.getLocation().getTranslation().get(1);
//                        lastXPos = targetTrackable.getLocation().getTranslation().get(0);
//                        lastHeadingPos = Orientation.getOrientation(targetTrackable.getLocation(), EXTRINSIC, XYZ, DEGREES).thirdAngle;
//                        drive.gyroTurn(-lastHeadingPos);
//
//                        if(lastYPos > targetYPos){
//
//                            drive.driveRight(1);
//
//                        } else if (lastYPos < targetYPos ){
//
//                            drive.driveRight(-1);
//
//                        }
//
//                        if (lastXPos > targetXPos){
//                            drive.driveForward(1);
//                        }
//                        else if (lastXPos < targetXPos)
//                            drive.driveForward(-1);
//
//                        if ((Math.round(lastXPos) == Math.round(targetXPos))&&(Math.round(lastYPos) == Math.round(targetYPos))&&(Math.round(lastHeadingPos) == Math.round(targetHeadingPos)) ){
//                            drive.stop();
//                            break;
//                        }
//
//                    } else{
//                        drive.stop();
//
//                    }
//
//
//                }


//                drive.gyroTurn(-90);
////                intake.takeInTime(1, 3);
                drive.gyroTurn(180);
                drive.driveRightTime(1, 0.5);
                drive.driveForwardTime(-1,1.5+searchingTime);
                drive.driveForwardTime(1, 0.6);

                break;
            case "red2":

                drive.gyroTurn(-90);
                drive.driveForwardTime(-1, 0.9);
                drive.driveRightTime(-1, 1.1);
                pGrabber.close();
                thread.sleep(1000);
                drive.driveRightTime(1, 2);
                drive.turnClockwiseTime(-1, 0.8);
                thread.sleep(1000);

                pGrabber.open();
                thread.sleep(1000);
                drive.turnClockwiseTime(1, 0.35);
                arm.moveToPos(1, 200+armStartPos);

                drive.driveForwardTime(1,1.5);
                drive.driveRightTime(-1, 1.1);
                drive.driveForwardTime(1,0.7);
                break;
            case "blue2":
                telemetry.addData("State: ", "blue2 running");
                telemetry.update();

                drive.gyroTurn(-90);
                drive.driveForwardTime(1, 0.9);
                drive.driveRightTime(-1, 1.1);
                pGrabber.close();
                thread.sleep(1000);
                drive.driveRightTime(1, 2);
                drive.turnClockwiseTime(1, 0.8);
                thread.sleep(1000);

                pGrabber.open();
                thread.sleep(1000);
                drive.turnClockwiseTime(-1, 0.35);
                arm.moveToPos(1, 100+armStartPos);

                drive.driveForwardTime(-1,1.3);
                drive.driveRightTime(-1, 1.1);
                drive.driveForwardTime(-1,0.7);
                break;
        }

        // Disable Tracking when we are done;
        targetsSkyStone.deactivate();


    }

    public void initGeneral() {

        robot9330.init(hardwareMap);
        drive = new Drive9330(robot9330);
        intake = new Intake9330(robot9330);
        pGrabber = new PlatformGrabber9330(robot9330);
        pGrabber.init();
        arm = new Arm9330(robot9330);
        armStartPos = arm.getEncoderValue();
        color = new ColorSensor9330(robot9330);
        color.init();

    }

    private void initVuforia() {
        //configure camera
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);


        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        //trackable objects


        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");

        //trackable objects array list

        allTrackables.addAll(targetsSkyStone);

        //locate position
        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 0, bridgeRotY, 0)));

        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0 , -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //rotation
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        //camera location on the robot
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        //let everything know where the camera is
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }
    }

    @Override
    public void run(){

    }

}
