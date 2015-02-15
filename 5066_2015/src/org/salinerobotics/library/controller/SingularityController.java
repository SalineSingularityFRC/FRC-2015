package org.salinerobotics.library.controller;

//import edu.wpi.first.wpilibj.Joystick;

public interface SingularityController {
	//Joystick joystick;
	
	public double getX();
	public double getY();
	public double getZ();
	
	public double getLeftX();
	public double getRightX();
	public double getLeftY();
	public double getRightY();
	
	public boolean getStart();
}
