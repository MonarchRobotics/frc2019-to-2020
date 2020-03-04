/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * An example command.  You can replace me with your own command.
 */
public class DriveMecanum extends Command {
  public DriveMecanum() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.drivetrain);
  }

  // // Called just before this Command runs the first time
  // @Override
  // protected void initialize() {
  // }


  public double accelerationCurve(double raw)
  {
    double iReturn = Math.sin(Math.PI/2*raw);
    return iReturn;
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
    double x, y, twist;
    x = accelerationCurve(OI.deadZone(OI.controller.getX(GenericHID.Hand.kLeft), RobotMap.getTranslationaldeadzone()));
    y = accelerationCurve(OI.deadZone(OI.controller.getY(GenericHID.Hand.kLeft), RobotMap.getTranslationaldeadzone()));
    twist = accelerationCurve(OI.deadZone(OI.controller.getX(GenericHID.Hand.kRight), RobotMap.getRotationaldeadzone()));

    System.out.println("X:"+x);
    Robot.drivetrain.getDrivetrain().driveCartesian(x, -y, twist);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // // Called once after isFinished returns true
  // @Override
  // protected void end() {
  // }

  // // Called when another command which requires one or more of the same
  // // subsystems is scheduled to run
  // @Override
  // protected void interrupted() {
  // }
}
