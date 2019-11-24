package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware9330 {
    HardwareMap hwMap = null;

    public static DcMotor rightFront;
    public static DcMotor leftFront;
    public static DcMotor rightBack;
    public static DcMotor leftBack;
    public static DcMotor intakeLeft;
    public static DcMotor intakeRight;
    public static DcMotor teleskop;
    public static DcMotor arm;

    public static Servo grabber;
    public static Servo pGrabber1;
    public static Servo pGrabber2;

    public static BNO055IMU gyro;
    public static ColorSensor colorSensor;


    public Hardware9330(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap){
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        grabber = hwMap.servo.get("Grabber");

        rightFront = hwMap.dcMotor.get("rightFront");
        leftFront = hwMap.dcMotor.get("leftFront");

        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightBack = hwMap.dcMotor.get("rightBack");
        leftBack = hwMap.dcMotor.get("leftBack");

        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        arm = hwMap.dcMotor.get("arm");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        gyro = hwMap.get(BNO055IMU.class, "gyro");
        colorSensor = hwMap.get(ColorSensor.class, "sensor_color");

        pGrabber1 = hwMap.servo.get("pGrabber1");
        pGrabber2 = hwMap.servo.get("pGrabber2");

        intakeLeft = hwMap.dcMotor.get("intakeLeft");
        intakeRight = hwMap.dcMotor.get("intakeRight");
//
//        teleskop = hwMap.dcMotor.get("teleskop");


    }

}

