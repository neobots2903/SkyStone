package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Hardware9330;
import org.firstinspires.ftc.teamcode.Subsystems.Arm9330;
import org.firstinspires.ftc.teamcode.Subsystems.Drive9330;
import org.firstinspires.ftc.teamcode.Subsystems.Grabber9330;
import org.firstinspires.ftc.teamcode.Subsystems.Intake9330;
import org.firstinspires.ftc.teamcode.Subsystems.PlatformGrabber9330;
import org.firstinspires.ftc.teamcode.Subsystems.TeleskopArm9330;

@TeleOp(name = "TeleOp9330", group = "Opmode")

public class TeleOp9330 extends OpMode {

    Hardware9330 robot9330 = new Hardware9330();
    Grabber9330 grabber;
    Drive9330 drive;
    Arm9330 arm;
    Intake9330 intake;
    TeleskopArm9330 teleskop;
    PlatformGrabber9330 pGrabber;

    private boolean isAHeld = false;
    private boolean isAHeld1 = false;
    private boolean isBHeld = false;
    private int armPosSwitch = 1;


    @Override
    public void init() {
        robot9330.init(hardwareMap);
        grabber = new Grabber9330(robot9330);
        grabber.init();
        pGrabber = new PlatformGrabber9330(robot9330);
        pGrabber.init();
        drive = new Drive9330(robot9330);
        arm = new Arm9330(robot9330);
        intake = new Intake9330(robot9330);
        teleskop = new TeleskopArm9330(robot9330);
    }

    @Override
    public void loop() {

        telemetry.addData("Encoder Value: " , robot9330.arm.getCurrentPosition());
        telemetry.addData("pGrabber Pos:", pGrabber.isUp);
        if(gamepad2.a && !isAHeld){

            telemetry.addData("Program: ", "A2 is tapped");
            grabber.toggle();
            isAHeld = true;

        } else if (!gamepad2.a){

            isAHeld = false;
            telemetry.addData("Program: ", "A2 isn't tapped");
        }

        if(gamepad1.a && !isAHeld1){

            telemetry.addData("Program: ", "A1 is tapped");
            pGrabber.toggle();
            isAHeld1 = true;

        } else if (!gamepad1.a){

            isAHeld1 = false;
            telemetry.addData("Program: ", "A1 isn't tapped");
        }

//        if(gamepad2.a){
//            grabber.extend();
//        }
//        else if(gamepad2.b){
//            grabber.retract();
//        }


        if(gamepad2.b && !isBHeld){


            isBHeld = true;

        } else if (!gamepad2.b){

            isBHeld = false;

        }


//        if (gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0) {
////            arm.move(gamepad2.left_stick_y);
////        } else if(gamepad2.left_stick_y == 0){
////            arm.stop();
////        }


//        if (gamepad2.dpad_down){
//            arm.moveToPos(1, arm.getEncoderTarget()-10);
//        }
//        else if  (gamepad2.dpad_up){
//            arm.moveToPos(1, arm.getEncoderTarget()+10);
//        }
        if(Math.abs(gamepad2.left_stick_y) > 0.1) {
                arm.moveToPos(1, (int) (arm.getEncoderTarget() + gamepad2.left_stick_y * 10));
        }

        //drive.spinEverythingWow(gamepad2.left_stick_y);

        float yPower = -gamepad1.left_stick_y;
        float xPower = gamepad1.left_stick_x;
        float averagePower = (Math.abs(gamepad1.left_stick_y) + Math.abs(gamepad1.left_stick_x))/2;

        if (Math.abs(yPower) > 0.1) {
            drive.driveForward(-yPower);
        } else if (Math.abs(xPower) > 0.1){
            drive.driveRight(xPower);
        }
//        else if (yPower > 0.5 && xPower > 0.5) {
//            telemetry.addData("Program: ", "top right");
//            drive.driveTopRight(averagePower);
//        } else if (yPower < 0.5 && xPower < 0.5){
//            telemetry.addData("Program: ", "bottom left");
//            drive.driveTopRight(-averagePower);
//        } else if (yPower < 0.5 && xPower > 0.5) {
//            telemetry.addData("Program: ", "top left");
//            drive.driveTopLeft(averagePower);
//        } else if (yPower > 0.5 && xPower < 0.5){
//            telemetry.addData("Program: ", "bottom right");
//            drive.driveTopLeft(-averagePower);
//        }

        drive.turnClockwise(-gamepad1.right_stick_x );
//        drive.turnCounterClockwise(gamepad1.dpad_right ? 1 : 0);


        intake.takeIn(gamepad2.right_stick_y);
//
//        teleskop.teleskopArm(gamepad2.right_stick_y);

    }
}
