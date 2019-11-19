package org.firstinspires.ftc.teamcode.Subsystems;

import org.firstinspires.ftc.teamcode.Hardware9330;

public class Drive9330 {

    Hardware9330 hwMap;
    Gyro9330 gyro;
    double turnError = 1;


    public Drive9330(Hardware9330 hwMap) {
        this.hwMap = hwMap;
        gyro = new Gyro9330(hwMap);
        gyro.init();

    }


    public void turnCounterClockwise(double power) {
        hwMap.rightFront.setPower(-power);
        hwMap.leftFront.setPower(-power);
        hwMap.rightBack.setPower(-power);
        hwMap.leftBack.setPower(-power);
    }

    public void turnClockwise(double power) {
        hwMap.rightFront.setPower(power);
        hwMap.leftFront.setPower(power);
        hwMap.rightBack.setPower(power);
        hwMap.leftBack.setPower(power);
    }


    public void gyroTurn(double targetAngle){

        double minAngle = targetAngle - turnError +  gyro.getYaw();
        double maxAngle = targetAngle + turnError +  gyro.getYaw();
        while (gyro.getYaw() < minAngle || gyro.getYaw() > maxAngle){
            double calcPower = Math.abs(maxAngle - gyro.getYaw()) / Math.abs(targetAngle);
            if (calcPower < 0.3) calcPower = 0.1;
            else calcPower = 1;
            if (gyro.getYaw() < minAngle) {
                turnClockwise(-calcPower);
            } else if (gyro.getYaw() > maxAngle) {
                turnCounterClockwise(-calcPower);
            }

        }
        stop();
    }


    public void driveForward(double power){
        hwMap.rightFront.setPower(-power);
        hwMap.leftFront.setPower(power);
        hwMap.rightBack.setPower(-power);
        hwMap.leftBack.setPower(power);
    }

//    public void driveBackward(float power){
//        hwMap.rightFront.setPower(-power);
//        hwMap.leftFront.setPower(power);
//        hwMap.rightBack.setPower(-power);
//        hwMap.leftBack.setPower(power);
//    }

    public void driveRight(double power){
        hwMap.rightFront.setPower(-power);
        hwMap.leftFront.setPower(-power);
        hwMap.rightBack.setPower(power);
        hwMap.leftBack.setPower(power);
    }

    public void driveRightTime(double power, double seconds){

        long targetTime = System.currentTimeMillis() + (long)(seconds * 1000);
        while (targetTime > System.currentTimeMillis()) {
            driveRight(power);
        }
        stop();
    }

    public void driveForwardTime(double power, double seconds){

        long targetTime = System.currentTimeMillis() + (long)(seconds * 1000);
        while (targetTime > System.currentTimeMillis()) {
            driveForward(power);
        }
        stop();
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

    public void driveTopRight(double power){
        hwMap.leftFront.setPower(-power);
        hwMap.rightBack.setPower(power);
    }

    public void driveTopLeft(double power){
        hwMap.rightFront.setPower(-power);
        hwMap.leftBack.setPower(power);
    }

    public void testRightFront(double power){
        hwMap.rightFront.setPower(power);
    }

    public void testRightBack(double power){
        hwMap.rightBack.setPower(power);
    }

    public void testLeftBack(double power){
        hwMap.leftBack.setPower(power);
    }

    public void testLeftFront(double power){
        hwMap.leftFront.setPower(power);
    }

    public void stop(){
        hwMap.rightFront.setPower(0);
        hwMap.leftFront.setPower(0);
        hwMap.rightBack.setPower(0);
        hwMap.leftBack.setPower(0);
    }

    public double getYaw(){
        return gyro.getYaw();
    }




}
