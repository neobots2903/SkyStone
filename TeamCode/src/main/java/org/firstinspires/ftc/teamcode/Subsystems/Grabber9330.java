package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class Grabber9330 {

    final double RETRACTED_POS = 1;
    final double EXTENDED_POS = 0;

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


        if (hwMap.grabber.getPosition() < 0.5){

            retract();

        }
        else if(hwMap.grabber.getPosition() >= 0.5){

            extend();

        }

    }

    public void init(){hwMap.grabber.setPosition(0);}

}
