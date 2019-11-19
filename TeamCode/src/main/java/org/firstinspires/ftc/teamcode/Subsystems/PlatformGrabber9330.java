package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class PlatformGrabber9330 {
    private Hardware9330 hwMap = null;
    public boolean isUp = false;
    public PlatformGrabber9330(Hardware9330 ahwMap){
        hwMap = ahwMap;

    }

    public void init(){
        open();
    }

    public void open (){
        hwMap.pGrabber1.setPosition(0);
        hwMap.pGrabber2.setPosition(1);
        isUp = true;
    }

    public void close (){
        hwMap.pGrabber1.setPosition(1);
        hwMap.pGrabber2.setPosition(0);
        isUp = false;
    }

    public void toggle(){


        if (isUp == true){

            close();

        }
        else if(isUp == false){

            open();


    }
    }
}
