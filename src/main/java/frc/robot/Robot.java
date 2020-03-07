/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Ports.*;

<<<<<<< Updated upstream
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation;
=======
import java.util.Set;

import edu.wpi.first.cameraserver.CameraServer;//Leave this
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;//Leave this
// import com.analog.adis16448.frc.ADIS16448_IMU; // Gyro import, leave in

>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
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
=======
  
    joystick1.addButton(1, driveTrain::setThrottleDirectionConstant);// flips heading
    joystick1.addButton(3, driveTrain::togglethrottleMode);// Switches throttlemode

    CameraServer.getInstance().startAutomaticCapture();
    

  }
  @Override
  public void robotPeriodic() {


>>>>>>> Stashed changes
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
<<<<<<< Updated upstream

=======
    driveTrain.autoUpdateSpeed();
   // ballSpitter.autoSpeed(-.01);
   //we need to set the motor speed to a small amount during autonomous
//if time > 10
    //After 10 seconds we are going to set the BallSpitter to (.5) power
>>>>>>> Stashed changes
  }

  @Override
  public void autonomousPeriodic() {
<<<<<<< Updated upstream
    // joystick1.listen();
    // joystick2.listen();
=======
    double autoRemainingTime = Timer.getMatchTime();
    if (autoRemainingTime <= 5){
      driveTrain.dateSpeed();
      ballSpitter.autoSpeed(0.5);
    }

    

  //leftMotor1
  // driveTrain.startAuto();
  
    
>>>>>>> Stashed changes
  }


  @Override
  public void teleopInit() {
    driveTrain.dateSpeed();
    isDriverControlling = true;
<<<<<<< Updated upstream
    joystick1.listen();
    joystick2.listen();
=======
    ballSpitter.neutral();
    // driveTrain.stopAuto();

>>>>>>> Stashed changes
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
