package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class  BallSpitter {

    private final VictorSPX spitterWheel;
    private final double speed = -0.5;

    BallSpitter(final int spitterPort) {
        spitterWheel = new VictorSPX(5);
        
  
    }

    



    public void neutral() {
        spitterWheel.set(ControlMode.PercentOutput, 0);
    }

    public void updateSpeed(final LambdaJoystick.ThrottlePosition throttlePosition){
        spitterWheel.set(ControlMode.PercentOutput, throttlePosition.y);
        
        
    }
    public void autoSpeed(final double percent){
        spitterWheel.set(ControlMode.PercentOutput, percent);
        
        
    }
}
