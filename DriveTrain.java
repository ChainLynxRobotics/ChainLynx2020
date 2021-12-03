package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LambdaJoystick.ThrottlePosition;
// import com.analog.adis16448.frc.ADIS16448_IMU;

public class DriveTrain {
    private final VictorSPX leftMotor1;
    private final VictorSPX rightMotor1;
    private final VictorSPX leftMotor2;
    private final VictorSPX rightMotor2;
    // public ADIS16448_IMU gyro;
   
   
    public double throttleInput;
    public double VelocityCheck;
    public double speedbrake;

    private boolean throttleMode = true;
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
    public boolean auto=false;
   

    public int throttleDirectionConstant = 1;
    

    public DriveTrain(final int leftPort1, final int leftPort2, final int rightPort1, final int rightPort2,
            final int gyroPortNumber) {
        leftMotor1 = new VictorSPX(leftPort1);
        leftMotor2 = new VictorSPX(leftPort2);
        rightMotor1 = new VictorSPX(rightPort1);
        rightMotor2 = new VictorSPX(rightPort2);

           
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

        double throttle1 = scaledZ * -1.00; 
        // double throttle1 = 1.00;
        double throttle2 = throttleMode ? ((throttle1 + 1.00) / 2.00) : 0.30; // Throttle as a value between 1
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
      
        SmartDashboard.putNumber("status/throttlePrime", throttle3);
        SmartDashboard.putNumber("status/thrust", thrust1);
        SmartDashboard.putNumber("raw data/Xraw", throttlePosition.x);
        SmartDashboard.putNumber("raw data/Yraw", throttlePosition.y);
        SmartDashboard.putNumber("raw data/Zraw", throttlePosition.z);
       

        scaledX = (scaledX * 0.5 * (stopDriveMotors==false ? (throttle2) : 0.00));
        scaledY = scaledY * throttleDirectionConstant * (auto ==false ? (throttle2) : 0.10);


        final double right = ((-scaledX - scaledY) * -1);// +throttlePosition.z; //why plus throttle z?//dunno, just leave it for now
        final double left = (scaledY - scaledX) * -1;

        
        leftMotor1.set(ControlMode.PercentOutput, left);
        rightMotor1.set(ControlMode.PercentOutput, right);
        leftMotor2.follow(leftMotor2);
        rightMotor2.follow(rightMotor1);
    }

    public void autoUpdateSpeed(double left, double right) {
        leftMotor1.set(ControlMode.PercentOutput, left);
        rightMotor1.set(ControlMode.PercentOutput, right);
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);
    }
       
    public void stopAuto() {
        auto=false;
    }

    public void startAuto() {
        auto = true;
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

	// public ADIS16448_IMU getGyro() {
	// 	return gyro;
	// }

   


}