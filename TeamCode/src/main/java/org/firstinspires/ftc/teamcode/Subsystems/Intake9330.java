package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class Intake9330 {

    Hardware9330 hwMap;

    public Intake9330(Hardware9330 hwMap){
        this.hwMap = hwMap;
    }

    public void takeIn(float power){
        hwMap.intakeLeft.setPower(-power);
        hwMap.intakeRight.setPower(power);

    }


}
