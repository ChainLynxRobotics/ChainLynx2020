/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Ports.*;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private boolean isDriverControlling;
  private double matchTime;
  private Boolean endgameInit;

   double getMatchTime() {
     return matchTime;
   }

  public final DriveTrain driveTrain = new DriveTrain(LEFT_DRIVETRAIN_1, LEFT_DRIVETRAIN_2, RIGHT_DRIVETAIN_1,
      RIGHT_DRIVETAIN_2, GYRO_PORT);

  private final LambdaJoystick joystick1 = new LambdaJoystick(0, driveTrain::updateSpeed);
  private final LambdaJoystick joystick2 = new LambdaJoystick(1);

  @Override
  public void robotInit() {
    CameraServer.getInstance().startAutomaticCapture();
    CameraServer.getInstance().startAutomaticCapture();
    joystick1.addButton(1, driveTrain::setThrottleDirectionConstant);// flips heading
    joystick1.addButton(3, driveTrain::togglethrottleMode);// Switches throttlemode
    joystick1.addButton(4, driveTrain::stopDriveMotors, driveTrain::restartDriveMotors);;
    joystick2.addButton(1, driveTrain::stopDriveMotors, driveTrain::restartDriveMotors);
  }
  @Override
  public void robotPeriodic() {
    CameraServer.getInstance().startAutomaticCapture();
    CameraServer.getInstance().startAutomaticCapture();
  }
  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {

  }

  @Override
  public void autonomousPeriodic() {
    // joystick1.listen();
    // joystick2.listen();
  }


  @Override
  public void teleopInit() {
    isDriverControlling = true;
    joystick1.listen();
    joystick2.listen();
  }

  @Override
  public void teleopPeriodic() {
    joystick1.listen();
    joystick2.listen();

    endgameInit = (30 >= matchTime) ? true : false;
    if (endgameInit = true) {

    }    
  }

  @Override
  public void testInit(){

  }

  @Override
  public void testPeriodic(){
    
  }

  
  public void changeDriverControl() {
    this.isDriverControlling = !isDriverControlling;
  }
}
