package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Hardware9330;

public class Gyro9330 implements Runnable {
    private Hardware9330 hwMap = null;
    Orientation angles;
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    float absoluteAngle;
    float lastAngle = 0;
    private Thread thread;

    public Gyro9330(Hardware9330 robotMap) {
        hwMap = robotMap;
    }

    public void resetGyro() {
        hwMap.gyro.initialize(parameters);
        absoluteAngle = 0;
    }

    public void init() {
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";

        resetGyro();

        thread = new Thread(this);
        thread.start();

    }

    public void calculateYaw() {
        float currentAngle;
        angles = hwMap.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        if (angles.firstAngle >= 0) currentAngle = angles.firstAngle;
        else currentAngle = 360 + angles.firstAngle;

        if (lastAngle < currentAngle) { //if the last angle is less than the current (increase)
            if (currentAngle - lastAngle > 180) { //if the change is more than 180 (overflow from 0 to 360?)
                absoluteAngle -= lastAngle + (360 - currentAngle); //get the actual change and subtract it
            } else { //if the change is a normal increase
                absoluteAngle += currentAngle - lastAngle; //add the difference
            }
        } else if (lastAngle > currentAngle) { //if the last angle is more than the current (decrease)
            if (lastAngle - currentAngle > 180) { //if the change is more than 180 (overflow from 360 to 0?))
                absoluteAngle += (360 - lastAngle) + currentAngle; //get the actual change and add it
            } else { //if the change is a normal decrease
                absoluteAngle -= lastAngle - currentAngle;//subtract the difference
            }
        }
        lastAngle = currentAngle;
    }

    public double getYaw() {
        return -absoluteAngle;
    }

    public double getPitch() {
        angles = hwMap.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.secondAngle;
    }

    public double getRoll() {
        angles = hwMap.gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.thirdAngle;
    }

    public boolean isCalibrated() {
        return hwMap.gyro.isGyroCalibrated();
    }

    @Override
    public void run() {
        while (true) {
            try {
                calculateYaw();
                thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}