/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * An example command.  You can replace me with your own command.
 */
public class Lift extends Command {
  boolean lifting;
  boolean secondDirection;//true is up, false is down.
  int liftingTo;//0 is lowered, 1 is 2nd level, 2 is 3rd level
  final double second = 9.75;
  final double third = 15.0;
  public Lift() {
    // requires(Robot.m_subsystem);
    lifting = false;
    liftingTo=0;
    requires(Robot.lift);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    int pov = OI.controller.getPOV();//gets the d-pad
    double speed = 1.0;//the speed we want the lift to go at.

    if(!lifting){
      if((pov==270 || OI.button9.get()) && Robot.lift.getSpark().getEncoder().getPosition()>0){//Checks if the left button is pressed, and if the lift isn't already at it's lowest level.
        Robot.lift.getSpark().set(-speed);
        lifting = true;
        liftingTo = 0;
      }
      else if((pov==90 || OI.button8.get())){//right button, 2rd level.
        if(liftingTo==2){//checks if we're at 3rd level, if so, then go down
          Robot.lift.getSpark().set(-speed);
          secondDirection = false;
        }
        else{//If we're at 1st level, go up.
          Robot.lift.getSpark().set(speed);
          secondDirection = true;
        }
        lifting = true;
        liftingTo = 1;
      }
      else if((pov==0 || OI.button7.get()) && Robot.lift.getSpark().getEncoder().getPosition()<third*36){//Top button on D-pad, and makes sure the lift isn't already at third level.
        Robot.lift.getSpark().set(speed);     
        lifting = true;
        liftingTo = 2;
      }
    }
    else if(pov==180 || OI.button10.get()){//bottom of d-pad, stops the lift.
      stopMoving();
      System.out.println("Emergency Stop");
    }
    else{
      if(liftingTo==0 && Robot.lift.getSpark().getEncoder().getPosition()<=0){//has arrived at first level
        stopMoving();
        System.out.println("Reached 1st level");
      }
      else if(liftingTo==1 && ((secondDirection && Robot.lift.getSpark().getEncoder().getPosition()>=second*36) || (!secondDirection && Robot.lift.getSpark().getEncoder().getPosition()<=second*36))){//arrived at 2nd level
        stopMoving();
        System.out.println("Reached 2nd level");
      }
      else if(liftingTo==2 && Robot.lift.getSpark().getEncoder().getPosition()>=third*36){//arrived at 3rd level.
        stopMoving();
        System.out.println("Reached 3rd level");
      }
      
    }
    // if(OI.controller.getBButton()){
    //   Robot.lift.getSpark().set(-0.5);
    // }
    // else{
    //   Robot.lift.getSpark().set(0.0);
    // }
  }

  void stopMoving(){
    Robot.lift.getSpark().set(0);
    lifting = false;
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
