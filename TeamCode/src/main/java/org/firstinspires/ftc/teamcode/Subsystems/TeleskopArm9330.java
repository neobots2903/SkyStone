package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class TeleskopArm9330 {

    Hardware9330 hwMap;

    public TeleskopArm9330(Hardware9330 hwMap){
        this.hwMap = hwMap;
    }

    public void teleskopArm(float power){
        hwMap.teleskop.setPower(power);
    }
}
