package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class Drive9330 {

    Hardware9330 hwMap;
    Gyro9330 gyro;
    float turnError = 1;


    public Drive9330(Hardware9330 hwMap) {
        this.hwMap = hwMap;
        gyro = new Gyro9330(hwMap);
        gyro.init();
    }


    public void turnCounterClockwise(float power) {
        hwMap.rightFront.setPower(power);
        hwMap.leftFront.setPower(power);
        hwMap.rightBack.setPower(power);
        hwMap.leftBack.setPower(power);
    }

    public void turnClockwise(float power) {
        hwMap.rightFront.setPower(-power);
        hwMap.leftFront.setPower(-power);
        hwMap.rightBack.setPower(-power);
        hwMap.leftBack.setPower(-power);
    }


    public void gyroTurn(float targetAngle, float power){
        float minAngle = targetAngle - turnError + (float) gyro.getYaw();
        float maxAngle = targetAngle + turnError + (float) gyro.getYaw();
        while (gyro.getYaw() < minAngle || gyro.getYaw() > maxAngle){
            if(gyro.getYaw() < minAngle){
                turnClockwise(power);
            } else if (gyro.getYaw() > maxAngle){
                turnCounterClockwise(power);
            }
        }

    }


    public void driveForward(float power){
        hwMap.rightFront.setPower(power);
        hwMap.leftFront.setPower(-power);
        hwMap.rightBack.setPower(power);
        hwMap.leftBack.setPower(-power);
    }

//    public void driveBackward(float power){
//        hwMap.rightFront.setPower(-power);
//        hwMap.leftFront.setPower(power);
//        hwMap.rightBack.setPower(-power);
//        hwMap.leftBack.setPower(power);
//    }

    public void driveRight(float power){
        hwMap.rightFront.setPower(power);
        hwMap.leftFront.setPower(power);
        hwMap.rightBack.setPower(-power);
        hwMap.leftBack.setPower(-power);
    }

    public void driveRightTime(float power, float time){
        //driveRight();
    }

//    public void driveLeft(float power){
//        hwMap.rightFront.setPower(power);
//        hwMap.leftFront.setPower(power);
//        hwMap.rightBack.setPower(-power);
//        hwMap.leftBack.setPower(-power);
//    }

//    public void driveBottomLeft(float power){
//        hwMap.leftFront.setPower(-power);
//        hwMap.rightBack.setPower(-power);
//    }
//
//    public void driveBottomRight(float power){
//        hwMap.rightFront.setPower(-power);
//        hwMap.leftBack.setPower(-power);
//    }

    public void driveTopRight(float power){
        hwMap.leftFront.setPower(power);
        hwMap.rightBack.setPower(-power);
    }

    public void driveTopLeft(float power){
        hwMap.rightFront.setPower(power);
        hwMap.leftBack.setPower(-power);
    }

    public void testRightFront(float power){
        hwMap.rightFront.setPower(power);
    }

    public void testRightBack(float power){
        hwMap.rightBack.setPower(power);
    }

    public void testLeftBack(float power){
        hwMap.leftBack.setPower(power);
    }

    public void testLeftFront(float power){
        hwMap.leftFront.setPower(power);
    }



}
