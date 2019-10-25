package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class PlatformGrabber9330 {
    private Hardware9330 hwMap = null;

    public PlatformGrabber9330(Hardware9330 ahwMap){
        hwMap = ahwMap;
    }

    public void init(){
        hwMap.pGrabber1.setPosition(1);
        hwMap.pGrabber2.setPosition(1);
    }

    public void open (){
        hwMap.pGrabber1.setPosition(1);
        hwMap.pGrabber2.setPosition(1);
    }

    public void close (){
        hwMap.pGrabber1.setPosition(0);
        hwMap.pGrabber2.setPosition(0);
    }

    public void toggle(){


        if (hwMap.pGrabber1.getPosition() > 0.5 && hwMap.pGrabber2.getPosition() > 0.5){

            close();

        }
        else if(hwMap.pGrabber1.getPosition() <= 0.5 && hwMap.pGrabber2.getPosition() <= 0.5){

            open();

        }

    }
}
