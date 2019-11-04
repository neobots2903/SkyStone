package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class Arm9330 {
    Hardware9330 hwMap;
    double encoderDriveSpeed = 0.4;
    double Diameter = 6;
    double Circumference = 3.1415 * Diameter;
    int ppr = 560;
    int oneInchOfMovement = (int)Math.round(ppr / Circumference);
    private Thread thread;

    public void init (){
        thread.start();
    }

    public Arm9330(Hardware9330 hwMap){
        this.hwMap = hwMap;
        hwMap.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hwMap.arm.setTargetPosition(hwMap.arm.getCurrentPosition());
    }

    public void move (double power){
        hwMap.arm.setPower(-power/4);
    }

    public void moveToPos (double power, int position){


            hwMap.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hwMap.arm.setPower(power);
            hwMap.arm.setTargetPosition(position);


    }

    public void setPos (int position){
        hwMap.arm.setTargetPosition(position);
    }

    public int getEncoderTarget () {
        return hwMap.arm.getTargetPosition();

    }

    public void stop (){
        hwMap.arm.setPower(0);
    }

//    @Override
//    public void run() {
//       moveToPos(1);
//    }
}