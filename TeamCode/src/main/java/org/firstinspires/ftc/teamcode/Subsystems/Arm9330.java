package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class Arm9330 {
    Hardware9330 hwMap;


    public Arm9330(Hardware9330 hwMap){
        this.hwMap = hwMap;
        hwMap.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void move (double power){
        hwMap.arm.setPower(-power/2);
    }

    public void stop (){
        hwMap.arm.setPower(0);
    }
}