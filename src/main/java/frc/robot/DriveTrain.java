package frc.robot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LambdaJoystick.ThrottlePosition;

public class DriveTrain {
    private final VictorSP leftMotor1;
    private final VictorSP rightMotor1;
    private final VictorSP leftMotor2;
    private final VictorSP rightMotor2;

    public double throttleInput;
    public double VelocityCheck;
    public double speedbrake;

    private boolean throttleMode = true;//formally slowSpeed, side note we're calling the default spped baby mode, outreach mode, or rookie mode
    private boolean stopDriveMotors;

    public boolean Brakes;
    public boolean braketoggler=true;
    public boolean drivingOffSpeed;
    public boolean masteralarm = false;
    public boolean revrSpeedWarn = false;
    public boolean rushing = false;
    public boolean RvsThrottleWarn;
    public boolean throttleForward = true;
    public boolean velocityNeverToExcede = false;
    public boolean velocityToTurn;
   
    
    private int counter = 0;//the hell does this do?

    public int throttleDirectionConstant = 1;
    

    public DriveTrain(final int leftPort1, final int leftPort2, final int rightPort1, final int rightPort2,
            final int gyroPortNumber) {
        leftMotor1 = new VictorSP(leftPort1);
        leftMotor2 = new VictorSP(leftPort2);
        rightMotor1 = new VictorSP(rightPort1);
        rightMotor2 = new VictorSP(rightPort2);

                //cannot resolve break
        // leftMotor1.setIdleMode(IdleMode.kBrake);
        // leftMotor2.setIdleMode(IdleMode.kBrake);
        // rightMotor1.setIdleMode(IdleMode.kBrake);
        // rightMotor2.setIdleMode(IdleMode.kBrake);
            //Cannot slave PWMs
        // leftMotor2.follow(leftMotor1);
        // rightMotor2.follow(rightMotor1);
    }

    public void updateSpeed(final ThrottlePosition throttlePosition) {
        double scaledX = throttlePosition.x;
        double scaledY = throttlePosition.y;
        double scaledZ = throttlePosition.z;
        final double scaleFactorA = 0.3;
        final double scaleFactorB = 0.7;
        // Top is X scale bottem is Y
        final double scaleFactorC = 0.3;
        final double scaleFactorD = 0.7;
        scaledY = (scaleFactorC * Math.abs(throttlePosition.y))
                + (scaleFactorD * throttlePosition.y * throttlePosition.y);
        scaledX = (scaleFactorA * Math.abs(throttlePosition.x))
                + (scaleFactorB * throttlePosition.x * throttlePosition.x);
        if (throttlePosition.x < 0)
            scaledX = -scaledX;
        if (throttlePosition.y < 0)
            scaledY = -scaledY;

        leftMotor1.set(scaledY + scaledX);
        rightMotor1.set(scaledY + scaledX);
        leftMotor2.set(scaledY + scaledX);
        rightMotor2.set(scaledY + scaledX);

        // WACK CODE STARTS HER
    
        double throttle1 = scaledZ * -1.00; // isaac helped fix the broken code (ishan messed up the sig figs)
        // double throttle1 = 1.00;
        double throttle2 = throttleMode ? ((throttle1 + 1.00) / 2.00) : 0.70; // Throttle as a value between 1
                                                                                        // and 2
        double throttle3 = throttle2 * 100.00;
        double thrust1 = (java.lang.Math.abs((throttlePosition.y * 1.00) * throttle3)); // Thrust as a value between 1
                                                                                        // and 100

        /*
         * in theory creates a value double trust which gives a value between 0 and 1
         * for the y input and should Give proportion thrust out put when throtle is
         * enabled)
         */

        velocityNeverToExcede = (thrust1 >= 85.00) ? true : false;
        velocityToTurn = (thrust1 > 20.00) ? true : false;
        masteralarm = (throttle3 <= 20.00)
                || ((velocityNeverToExcede == true) || ((revrSpeedWarn == true) && (RvsThrottleWarn == true)));
        RvsThrottleWarn = ((throttleForward == false) && (throttleMode == true)) ? /* (thrust1 >= 60.00)? */ (true)
                : (false);
        revrSpeedWarn = ((throttle3 >= 55.00) && (throttleForward == false) ? (revrSpeedWarn = true)
                : (revrSpeedWarn = false));
        SmartDashboard.putBoolean("Alarms/RvsOverSpeed", revrSpeedWarn);
        SmartDashboard.putBoolean("Alarms/masteralarm", masteralarm);
        SmartDashboard.putNumber("status/throttlePrime", throttle3);
        SmartDashboard.putBoolean("Alarms/RvsThrottleWarn",RvsThrottleWarn);
        SmartDashboard.putNumber("status/thrust", thrust1);
        SmartDashboard.putNumber("raw data/Xraw", throttlePosition.x);
        SmartDashboard.putNumber("raw data/Yraw", throttlePosition.y);
        SmartDashboard.putNumber("raw data/Zraw", throttlePosition.z);
        SmartDashboard.putBoolean("Alarms/VNE", velocityNeverToExcede);
        SmartDashboard.putBoolean("Alarms/V1", velocityToTurn);
        //SmartDashboard.putBoolean("status/RobotArmed", masterSafteyOff);
        // SmartDashboard.putBoolean("BrakesIndicator",Brakes);
        // SmartDashboard.putNumber
        // VelocityCheck = (Brakes == true)?(speedbrake):throttle2;

        scaledX = (scaledX * 0.5 * (stopDriveMotors==false ? (throttle2) : 0.00));
        scaledY = scaledY * throttleDirectionConstant * (stopDriveMotors ==false ? (throttle2) : 0.00);

        // if (throttleMode == false) {
        // scaledX = scaledX * (drivingOffSpeed ? 0.27 : (throttle1+1.00));//note to
        // self: default is .5 , .75 I assumed the they were proportinal so sclaed it by
        // a factor of 40/7
        // scaledY = scaledY * (drivingOffSpeed ? 0.40 : (throttle1+1.00));
        // }

        final double right = ((-scaledX - scaledY) * -1);// +throttlePosition.z; //why plus throttle z?
        final double left = (scaledY - scaledX) * -1;
    }
       

    public void togglethrottleMode() {
        throttleMode = !throttleMode;
        SmartDashboard.putBoolean("status/LowSpeed", throttleMode);
    }



    public void setThrottleDirectionConstant() {
        throttleDirectionConstant *= -1;
        throttleForward = !throttleForward;
        SmartDashboard.putBoolean("status/foward", throttleForward);
    }

    //Resolve
    // public void stopDriveMotors() {
    //   stopDriveMotors=true;
    // }
    // public void restartDriveMotors(){
    //   stopDriveMotors=false;
    // }

    public void setDrivingOffSpeed() {
        drivingOffSpeed = !drivingOffSpeed;
        SmartDashboard.putBoolean("DB/String 7", drivingOffSpeed);
    } 

}