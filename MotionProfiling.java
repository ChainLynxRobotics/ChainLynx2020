// package frc.robot;

// import java.io.File;
// import java.io.IOException;

// import jaci.pathfinder.*;
// import jaci.pathfinder.followers.EncoderFollower;


// import com.analog.adis16448.frc.ADIS16448_IMU;

// import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

// import com.ctre.phoenix.motorcontrol.can.VictorSPX;

// public class MotionProfiling {
//     private DriveTrain driveTrain;
//     private final double wheelBaseWidth = 2.25; // Width in feet
//     private final double wheelDiameter = 0.1524; // meters
//     private final VictorSPX leftMotor;
//     private final VictorSPX rightMotor;
//     private final int encoderTicksPerRevolution = 4096;
//     private final double maxVelocity = 13; // ft/s
//     private EncoderFollower left;
//     private EncoderFollower right;

//     public MotionProfiling(DriveTrain driveTrain, String setupLeft, String setupRight) throws IOException {
//         this.driveTrain = driveTrain;
//         leftMotor = driveTrain.getLeftMotor();
//         rightMotor = driveTrain.getRightMotor();
//         // pathweaver has an error with mixing up left and right
//         Trajectory trajectoryLeft = PathfinderFRC.getTrajectory(setupRight);
//         Trajectory trajectoryRight = PathfinderFRC.getTrajectory(setupLeft);

//         left = new EncoderFollower(trajectoryLeft);
//         right = new EncoderFollower(trajectoryRight);

//         left.configureEncoder(((BaseMotorController) leftMotor).getSelectedSensorPosition(), encoderTicksPerRevolution,
//                 wheelDiameter);
//         right.configureEncoder(rightMotor.getSelectedSensorPosition(), encoderTicksPerRevolution, wheelDiameter);

//         left.configurePIDVA(0.9, 0.0, 0.0, 1 / maxVelocity, 0); //Filler PID vals
//         right.configurePIDVA(0.9, 0.0, 0.0, 1 / maxVelocity, 0);
//     }
//     public void update() { 
//         double l = left.calculate(leftMotor.getSelectedSensorPosition());
//         double r = right.calculate(rightMotor.getSelectedSensorPosition());
//         double gyroHeading = driveTrain.getGyro().getGyroAngleY(); // Assuming the gyro is giving a
//                                                                      // value in degrees
//         double desiredHeading = -Pathfinder.r2d(left.getHeading());  // Should also be in degrees

//         double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
//         double turn = 0.8 * (-1.0/80.0) * angleDifference;

//         driveTrain.autoUpdateSpeed(l + turn, r - turn);
//     }

//     public boolean isFinished() {
//         if (left.isFinished() && right.isFinished()) {
//             return true;
//         } else {
//             return false;       
//         }      
//     }

//     public void reset(){
//         left.reset();
//         right.reset();
//         left.configureEncoder(leftMotor.getSelectedSensorPosition(), encoderTicksPerRevolution, wheelDiameter); 
//         right.configureEncoder(rightMotor.getSelectedSensorPosition(), encoderTicksPerRevolution, wheelDiameter);
//     }
// }
