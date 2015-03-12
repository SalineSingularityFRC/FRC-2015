package org.salinerobotics.library.controller;

//import edu.wpi.first.wpilibj.Joystick;

public interface SingularityController {
	//Joystick joystick;
	
	final static int LOGITECH = 0, XBOX = 1;
	
	public double getX();
	public double getY();
	public double getZ();
	
	public double getElevator();
	
	public double getLeftX();
	public double getRightX();
	public double getOuterIntake();
	public double getInnerIntake();
	
	public boolean getStart();
	
	public int getControllerType();;
}
