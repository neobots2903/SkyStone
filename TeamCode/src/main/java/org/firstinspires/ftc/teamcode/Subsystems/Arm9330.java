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



    public Arm9330(Hardware9330 hwMap){
        this.hwMap = hwMap;
        hwMap.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void moveToPos (double power){

        //while(hwMap.arm.getCurrentPosition() < position-5 || hwMap.arm.getCurrentPosition() > position+5) {
            hwMap.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hwMap.arm.setPower(power);
            hwMap.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //}
    }

    public void setPos (int position){
        hwMap.arm.setTargetPosition(position);
    }

//    public int getEncoderValue () {
//        int value = hwMap.arm.getCurrentPosition();
//        return value;
//    }

    public void stop (){
        hwMap.arm.setPower(0);
    }
}