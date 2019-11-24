package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class ColorSensor9330 {

    Hardware9330 hwMap;

    public ColorSensor9330(Hardware9330 hwMap){
        this.hwMap = hwMap;
    }

    public void init(){
        ledOn();
    }

    public int getR(){
        return hwMap.colorSensor.red();
    }

    public int getG(){
        return hwMap.colorSensor.green();
    }

    public int getB(){
        return hwMap.colorSensor.blue();
    }

    public void ledOn(){
        hwMap.colorSensor.enableLed(true);
    }

    public void ledOff(){
        hwMap.colorSensor.enableLed(false);
    }


}
