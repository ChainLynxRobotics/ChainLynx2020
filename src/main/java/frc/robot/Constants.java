package frc.robot;

//As of 2/29/20 these constants are all confined to the Constants section of the code
public class Constants {
    
    // Distances
    public static final int WHEEL_DIAMETER_INCHES = 6;
    public static final double WHEEL_BASE_WIDTH_FEET = 2.25;
    public static final double WHEEL_DIAMETER_METERS = 0.1524;
    public static final double FINAL_DISTANCE_FROM_ROCKET_INCHES = 16;

    // Maximum Speeds
    public static final double MAX_VELOCITY_FEET_SECONDS = 20;
    public static final double MAX_VELOCITY_METERS = 20 * 12 * 2.54; // randome value
    public static final double MAX_ACCELERATION_METERS = 10.0; // random value
    public static final double MAX_JERK_METERS = 10; // random value

    // Camera
    public static final double DISTANCE_OFF_GROUND_CAMERA_INCHES = 1;
    public static final double DISTANCE_FROM_FRONT_LEFT_CORNER_INCHES = 1;
    public static final double DISTANCE_FROM_FRONT_INCHES = 1; 
    public static final int WIDTH_OF_CAMERA_PIXELS = 1920;
    public static final int HEIGHT_OF_CAMERA_PIXELS = 1080;  

    // PID
    public double P = 0;
    public double I = 0;
    public double D = 0;
    
    // Other
    public static final int ENCODER_TICKS_PER_REVOLUTION = 4096;
}