/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import static frc.robot.Ports.*;

import edu.wpi.first.cameraserver.CameraServer;//Leave this
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DriverStation;//Leave this
// import com.analog.adis16448.frc.ADIS16448_IMU; // Gyro import, leave in


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  private boolean isDriverControlling;
  private double matchTime;//undefined right now
  private Boolean endgameInit;//if this giving an error ignore it


  public final DriveTrain driveTrain = new DriveTrain(LEFT_DRIVETRAIN_1, LEFT_DRIVETRAIN_2, RIGHT_DRIVETAIN_1,
      RIGHT_DRIVETAIN_2, GYRO_PORT);

  public final BallSpitter ballSpitter =  new BallSpitter(5);

  private final LambdaJoystick joystick1 = new LambdaJoystick(0, driveTrain::updateSpeed);
  private final LambdaJoystick joystick2 = new LambdaJoystick(1, ballSpitter::updateSpeed);

  //private MotionProfiling path = new MotionProfiling(driveTrain, setupLeft, setupRight); (Setup left/right are path/trajectory files)

  @Override
  public void robotInit() {
  

  }
  @Override
  public void robotPeriodic() {

    joystick1.addButton(1, driveTrain::setThrottleDirectionConstant);// flips heading
    joystick1.addButton(3, driveTrain::togglethrottleMode);// Switches throttlemode
    

  }
 
  @Override
  public void autonomousInit() {
    driveTrain.startAuto();

  }

  @Override
  public void autonomousPeriodic() {
      //Hey this is fun
      //Get back to work
      //No
      //Stop having conversations in the code
      //No
      //sigh
    
  }


  @Override
  public void teleopInit() {
    isDriverControlling = true;
    driveTrain.stopAuto();

  }

  @Override
  public void teleopPeriodic() {
    joystick1.listen();
    joystick2.listen();
    // endgameInit = (30 >= matchTime) ? true : false;
    // if (endgameInit = true) {

    // }
    

    
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

