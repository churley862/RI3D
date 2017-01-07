// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.
package org.usfirst.frc2017.RI3D.subsystems;

import org.usfirst.frc2017.RI3D.Constants;
import org.usfirst.frc2017.RI3D.RobotMap;
import org.usfirst.frc2017.RI3D.commands.*;
import org.usfirst.frc2017.util.Loop;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem implements Loop {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final SpeedController rightMotor2 = RobotMap.driveTrainRightMotor2;
    private final SpeedController rightMotor1 = RobotMap.driveTrainRightMotor1;
    private final SpeedController leftMotor2 = RobotMap.driveTrainLeftMotor2;
    private final SpeedController leftMotor1 = RobotMap.driveTrainLeftMotor1;
    private final RobotDrive robotDrive = RobotMap.driveTrainRobotDrive;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS


    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    private double currentLeftPower = 0;
    private double currentRightPower = 0;
    private double leftPower = 0;
    private double rightPower = 0;
    private double lasttime;
    
    
    @Override
    public void onStart() {
        lasttime = Timer.getFPGATimestamp();
    }
    
    public void teleop(Joystick left, Joystick right) {
        synchronized(this) {
            leftPower = left.getRawAxis(0);
            rightPower = right.getRawAxis(0);
        }
    }
    
    public void set(double left, double right) {
        leftMotor1.set(left);
        leftMotor2.set(left);
        rightMotor1.set(right);
        rightMotor2.set(right);
    }
    
    @Override
    public void onLoop() {
        double rp;
        double lp;
        
        synchronized(this) {
            rp = rightPower;
            lp = leftPower;
        }
        
        double now = Timer.getFPGATimestamp();
        double delta = now - lasttime;
        lasttime = now;
             
        double maxChange = Constants.maxRampRate * delta;
        double deltaRight = rp - currentRightPower;
        double deltaLeft = lp - currentLeftPower;
        
        if (deltaRight > maxChange) deltaRight = maxChange;
        if (deltaRight < -maxChange) deltaRight = -maxChange;
        if (deltaLeft > maxChange) deltaLeft = maxChange;
        if (deltaLeft < -maxChange) deltaLeft = -maxChange;
        
        currentRightPower += deltaRight;
        currentLeftPower += deltaLeft;
        
        if (currentRightPower > 0 && rp < 0) currentRightPower = 0;
        if (currentRightPower < 0 && rp > 0) currentRightPower = 0;
        if (currentLeftPower > 0 && lp < 0) currentLeftPower = 0;
        if (currentLeftPower < 0 && lp > 0) currentLeftPower = 0;

        set(currentRightPower, currentLeftPower);
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        set(0, 0);
    }    
}

