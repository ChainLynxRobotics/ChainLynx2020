package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
* Creates a new RampSubsystem.
*/
public class RampSubsystem extends SubsystemBase {
  private WPI_VictorSPX rampMotor;

  public RampSubsystem() {
    rampMotor = new WPI_VictorSPX(Constants.kRampMotor);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setPower(double power){
    rampMotor.set(power);
  }
}
