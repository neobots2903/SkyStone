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
    private boolean isBHeld = false;


    @Override
    public void init() {
        robot9330.init(hardwareMap);
        grabber = new Grabber9330(robot9330);
        grabber.init();
        pGrabber = new PlatformGrabber9330(robot9330);
//        pGrabber.init();
        drive = new Drive9330(robot9330);
        arm = new Arm9330(robot9330);
        intake = new Intake9330(robot9330);
        teleskop = new TeleskopArm9330(robot9330);
    }

    @Override
    public void loop() {

        telemetry.addData("Encoder Value: " , robot9330.arm.getCurrentPosition());

        if(gamepad2.a && !isAHeld){

            telemetry.addData("Program: ", "A is tapped");
            grabber.toggle();
            isAHeld = true;

        } else if (!gamepad2.a){

            isAHeld = false;
            telemetry.addData("Program: ", "A isn't tapped");
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


        if (gamepad2.left_stick_y > 0 || gamepad2.left_stick_y < 0) {
            //arm.move(gamepad2.left_stick_y,300);
        } else if(gamepad2.left_stick_y == 0){
            arm.stop();
        }

        arm.setPos(gamepad2.dpad_up  ? 300 : 0);

        arm.moveToPos(1);




        //drive.spinEverythingWow(gamepad2.left_stick_y);

        float yPower = -gamepad1.left_stick_y;
        float xPower = gamepad1.left_stick_x;
        float averagePower = (Math.abs(gamepad1.left_stick_y) + Math.abs(gamepad1.left_stick_x))/2;

        if (yPower != 0 && xPower == 0) {
            drive.driveForward(-yPower);
        } else if (xPower != 0 && yPower == 0){
            drive.driveRight(xPower);
        } else if (yPower > 0 && xPower > 0) {
            telemetry.addData("Program: ", "top right");
            drive.driveTopRight(averagePower);
        } else if (yPower < 0 && xPower < 0){
            telemetry.addData("Program: ", "bottom left");
            drive.driveTopRight(-averagePower);
        } else if (yPower < 0 && xPower > 0) {
            telemetry.addData("Program: ", "top left");
            drive.driveTopLeft(averagePower);
        } else if (yPower > 0 && xPower < 0){
            telemetry.addData("Program: ", "bottom right");
            drive.driveTopLeft(-averagePower);
        }

        drive.turnClockwise(gamepad1.dpad_left ? 1 : 0);
        drive.turnCounterClockwise(gamepad1.dpad_right ? 1 : 0);


//        intake.takeIn(gamepad2.left_stick_y);
//
//        teleskop.teleskopArm(gamepad2.right_stick_y);

    }
}
