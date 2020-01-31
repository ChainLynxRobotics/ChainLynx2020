package frc.robot;
//this is a test

// import com.analog.adis16448.frc.ADIS16448_IMU;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
// import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.LambdaJoystick.ThrottlePosition;
// import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
// import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
// import edu.wpi.first.wpilibj.SendableBase;
// import edu.wpi.first.wpilibj.shuffleboard.*;
// import edu.wpi.first.wpilibj.GyroBase;
// import edu.wpi.first.wpilibj.AnalogGyro;
// import java.util.Map;
// import java.util.Timer;

//import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets.kGyro;

public class DriveTrain {

    private final VictorSPX leftMotor1;
    private final VictorSPX rightMotor1;
    private final VictorSPX leftMotor2;
    private final VictorSPX rightMotor2;
    private final Encoder enco;
    // public ADIS16448_IMU gyro;

    private boolean throttleMode = true;// formally slowSpeed, side not we're calling the default spped baby mode,
                                        // outreach mode, or rookie mode
    // private int counter = 0;//the hell does this do?
    private boolean drivingOffSpeed;
    public int throttleDirectionConstant = 1;
    private boolean throttleForward = true;
    public boolean masteralarm = false;
    public boolean velocityNeverToExcede = false;
    public boolean revrSpeedWarn = false;
    public double throttleInput;
    public boolean RvsThrottleWarn;
    public boolean velocityToTurn;
    public boolean Brakes;
    public double VelocityCheck;
    public double speedbrake;
    public boolean braketoggler = true;
    boolean rushing = false;
    public boolean masterSafteyOff = true;

    public DriveTrain(final int leftPort1, final int leftPort2, final int rightPort1, final int rightPort2, final int gyroPortNumber) {
        leftMotor1 = new VictorSPX(leftPort1);
        leftMotor2 = new VictorSPX(leftPort2);
        rightMotor1 = new VictorSPX(rightPort1);
        rightMotor2 = new VictorSPX(rightPort2);
        leftMotor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        rightMotor1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
  
        enco = new Encoder(8, 9);
        enco.setDistancePerPulse(2.0943 / 4);

        if (braketoggler == true) {
            rightMotor1.setNeutralMode(NeutralMode.Brake);
            rightMotor2.setNeutralMode(NeutralMode.Brake);
            leftMotor1.setNeutralMode(NeutralMode.Brake);
            leftMotor2.setNeutralMode(NeutralMode.Brake);
        } else {
            rightMotor1.setNeutralMode(NeutralMode.Coast);
            rightMotor2.setNeutralMode(NeutralMode.Coast);
            leftMotor1.setNeutralMode(NeutralMode.Coast);
            leftMotor2.setNeutralMode(NeutralMode.Coast);
        }

        // gyro.reset();
        drivingOffSpeed = false;
        // SmartDashboard.putNumber("status/gyroprime", gyro);
        SmartDashboard.putBoolean("status/throttleMode", throttleMode);
        SmartDashboard.putBoolean("status/foward", throttleForward);
        SmartDashboard.putNumber("status/throttle", 0);
        // gyroPortNumber should be analong 0 or 1

    }

    // Code from here till next commment is setting up for lame drive imitiation,
    // but does nothin aon
    public double getDistance() {
        return enco.getRaw() * -0.0002661;
    }

    public void toggleBrakesMode() {
        braketoggler = !braketoggler;
    }

    public void armMasterSaftey() {
        masterSafteyOff = true;
    }

    public void disarmMasterSaftey() {
        masterSafteyOff = false;
    }

    public void reset() {
        enco.reset();

    }
    // Normal code resumes after this

    public void makeVictorsFollowers() {
        leftMotor2.set(ControlMode.Follower, leftMotor1.getDeviceID());
        leftMotor2.setInverted(InvertType.FollowMaster);
        rightMotor2.set(ControlMode.Follower, rightMotor2.getDeviceID());
        rightMotor2.setInverted(InvertType.FollowMaster);
    }

    public void updateSpeed(final ThrottlePosition throttlePosition) {
        double scaledX = throttlePosition.x;
        double scaledY = throttlePosition.y;
        double scaledZ = throttlePosition.z;
        double scaleFactorA = 0.3;
        double scaleFactorB = 0.7;
        // Top is X scale bottem is Y
        double scaleFactorC = 0.3;
        double scaleFactorD = 0.7;
        scaledY = (scaleFactorC * Math.abs(throttlePosition.y))
                + (scaleFactorD * throttlePosition.y * throttlePosition.y);
        scaledX = (scaleFactorA * Math.abs(throttlePosition.x))
                + (scaleFactorB * throttlePosition.x * throttlePosition.x);
        if (throttlePosition.x < 0) {
            scaledX = -scaledX;
        }

        if (throttlePosition.y < 0) {
            scaledY = -scaledY;
        }

        double throttle1 = scaledZ * -1.00; // isaac helped fix the broken code (ishan messed up the sig figs)
        // double throttle1 = 1.00;
        double throttle2 = (throttleMode == true) ? ((throttle1 + 1.00) / 2.00) : 0.40; // Throttle as a value between 1
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
        SmartDashboard.putNumber("status/throttlePrime", (throttle3));
        SmartDashboard.putBoolean("Alarms/RvsThrottleWarn", (RvsThrottleWarn));
        SmartDashboard.putNumber("status/thrust", ((thrust1)));
        SmartDashboard.putNumber("raw data/Xraw", throttlePosition.x);
        SmartDashboard.putNumber("raw data/Yraw", throttlePosition.y);
        SmartDashboard.putNumber("raw data/Zraw", throttlePosition.z);
        SmartDashboard.putBoolean("Alarms/VNE", velocityNeverToExcede);
        SmartDashboard.putBoolean("Alarms/V1", velocityToTurn);
        SmartDashboard.putBoolean("status/RobotArmed", masterSafteyOff);
        // SmartDashboard.putBoolean("BrakesIndicator",Brakes);
        // SmartDashboard.putNumber
        // VelocityCheck = (Brakes == true)?(speedbrake):throttle2;
        // (throttleMode ? (throttle2) : 0.40 );
        scaledX = (scaledX * 0.5 * (throttleMode ? (throttle2) : 0.70));
        scaledY = scaledY * throttleDirectionConstant * (throttleMode ? (throttle2) : 0.70);

        // if (throttleMode == false) {
        // scaledX = scaledX * (drivingOffSpeed ? 0.27 : (throttle1+1.00));//note to
        // self: default is .5 , .75 I assumed the they were proportinal so sclaed it by
        // a factor of 40/7
        // scaledY = scaledY * (drivingOffSpeed ? 0.40 : (throttle1+1.00));
        // }

        final double right = ((-scaledX - scaledY) * -1);// +throttlePosition.z; //why plus throttle z?
        final double left = (scaledY - scaledX) * -1;

        leftMotor1.set(ControlMode.PercentOutput, left);
        leftMotor2.follow(leftMotor1);
        rightMotor1.set(ControlMode.PercentOutput, right);
        rightMotor2.follow(rightMotor1);
    }

    // it is now safe to touch stuff

    public void autoUpdateSpeed(double left, double right) {
        leftMotor1.set(ControlMode.PercentOutput, left);
        rightMotor1.set(ControlMode.PercentOutput, right);
        leftMotor2.follow(leftMotor1);
        rightMotor2.follow(rightMotor1);
    }

    public VictorSPX getLeftMotor() {
        return leftMotor1;
    }

    public VictorSPX getRightMotor() {
        return rightMotor1;
    }

    // public ADIS16448_IMU getGyro() {
    // return gyro;
    // }

    public void getEncoderPosition() {
        int encoderPositionLeft = leftMotor1.getSelectedSensorPosition();
        System.out.println(encoderPositionLeft);
        int encoderPositionRight = rightMotor1.getSelectedSensorPosition();
        System.out.println(encoderPositionRight);
    }

    public void togglethrottleMode() {
        throttleMode = !throttleMode;
        SmartDashboard.putBoolean("status/BabyModeDisabled", throttleMode);
    }

    public void cruiseControl() {
        autoUpdateSpeed(0.4, -0.4);
    }

    public void setThrottleDirectionConstant() {
        throttleDirectionConstant *= -1;
        throttleForward = !throttleForward;
        SmartDashboard.putBoolean("status/foward", throttleForward);
    }

    public void stopDriveMotors() {
        leftMotor1.set(ControlMode.PercentOutput, 0);
        leftMotor2.set(ControlMode.PercentOutput, 0);
        rightMotor1.set(ControlMode.PercentOutput, 0);
        rightMotor2.set(ControlMode.PercentOutput, 0);
    }

    public void setDrivingOffSpeed() {
        drivingOffSpeed = !drivingOffSpeed;
        // SmartDashboard.putBoolean("DB/String 7", drivingOffSpeed);
    }

    public void updateRightSpeed() {
        rightMotor1.set(ControlMode.PercentOutput, -0.5);
        rightMotor2.follow(rightMotor1);
    }

    public void stopRightSpeed() {
        rightMotor1.set(ControlMode.PercentOutput, 0);
        rightMotor2.follow(rightMotor1);
    }

    public void updateLeftSpeed() {
        leftMotor1.set(ControlMode.PercentOutput, 0.5);
        leftMotor2.follow(leftMotor1);
    }

    public void stopLeftSpeed() {
        leftMotor1.set(ControlMode.PercentOutput, 0);
        leftMotor2.follow(leftMotor1);
    }

    int leftControlCount = 0;
    public void leftControl() {
        if (leftControlCount % 3 == 0) {
            updateLeftSpeed();
        } else if (leftControlCount % 3 == 1) {
            stopLeftSpeed();
        } else {}
        leftControlCount++;
    }

    int rightControlCount = 0;
    public void rightControl() {
        if (rightControlCount % 3 == 0) {
            updateRightSpeed();
        } else if (rightControlCount % 3 == 1) {
            stopRightSpeed();
        } else {
        }
    }

    public void startRush() {
        reset();
        rushing = true;
    }

    public void endRush() {
        rushing = false;
    }

    {
        leftControlCount++;
    }

}
