package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class Grabber9330 {

    final double RETRACTED_POS = 0.12;
    final double EXTENDED_POS = 0.79;

    private Hardware9330 hwMap = null;

    public Grabber9330(Hardware9330 ahwMap){
        hwMap = ahwMap;
    }

    public void extend(){

        hwMap.grabber.setPosition(EXTENDED_POS);

    }

    public void retract(){

        hwMap.grabber.setPosition(RETRACTED_POS);

    }

    public void toggle(){

        hwMap.grabber.getPosition();
        if (hwMap.grabber.getPosition() == EXTENDED_POS){

            retract();

        }
        else if(hwMap.grabber.getPosition() == RETRACTED_POS){

            extend();

        }

    }

    public void init(){
        hwMap.grabber.setPosition(0.2);
    }
}
