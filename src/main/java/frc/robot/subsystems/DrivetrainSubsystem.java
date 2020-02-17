/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
/**
 * DrivetrainSubsystem handles all subsystem level logic in the drivetrain
 * This includes talking to motor controller and driving logic
 */
public class DrivetrainSubsystem extends SubsystemBase {
  private VictorSP leftDriveFront, leftDriveBack;
  private VictorSP rightDriveFront, rightDriveBack;

  private SpeedControllerGroup left, right;

  //Constructs DrivetrainSubsystem
  public DrivetrainSubsystem() {
    //Sets motor controllers to PWM ports specified in Constants
    leftDriveFront = new VictorSP(Constants.kLeftDriveFrontPWM);
    leftDriveBack = new VictorSP(Constants.kLeftDriveBackPWM);
    rightDriveFront = new VictorSP(Constants.kRightDriveFrontPWM);
    rightDriveBack = new VictorSP(Constants.kRightDriveBackPWM);

    //Creates a group of motor controllers, makes things easier
    left = new SpeedControllerGroup(leftDriveFront, leftDriveBack);
    right = new SpeedControllerGroup(rightDriveFront, rightDriveBack);

    //Left and right side will spin in opposite directions when positive power is applied if not inverted
    right.setInverted(true);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Basic arcade drive, feed in joystick inputs
   */
  public void drive(double throttle, double turn){
    double leftPower = throttle + turn;
    double rightPower = throttle - turn;
    setPower(leftPower, rightPower);
  }

  /**
  * Power is in duty cycle, -1 to 1
  */
  public void setPower(double leftPower, double rightPower){
    left.set(leftPower);
    right.set(rightPower);
  }

  /**
   * Power is in volts, -12 to 12 volts
   */
  public void setVoltage(double leftPower, double rightPower){
    left.setVoltage(leftPower);
    right.setVoltage(rightPower);
  } 
}
