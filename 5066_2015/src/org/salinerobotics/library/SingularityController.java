package org.salinerobotics.library;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class SingularityController {
	public static final int LOGITECH = 0, XBOX = 1;

	Joystick joystick;
	int type;

	public SingularityController(Joystick joystick, int type) {
		this.type = type;
		this.joystick = joystick;
	}

	public double getX() {
		return joystick.getX();
	}

	public double getY() {
		if (type == XBOX)
			return -joystick.getY();
		else
			return joystick.getY();
	}
	
	public double getZ() {
		switch (type) {
		case LOGITECH:
			return joystick.getZ();
		case XBOX:
			return joystick.getRawAxis(4);
		default:
			return 0;
		}
	}
	
	public double getLeftX() {
		switch (type) {
		case LOGITECH:
			return joystick.getX();
		case XBOX:
			return joystick.getRawAxis(0);
		default:
			return 0;
		}
	}

	public double getLeftY() {
		switch (type) {
		case LOGITECH:
			return joystick.getY();
		case XBOX:
			return -joystick.getRawAxis(1);
		default:
			return 0;
		}
	}

	public double getRightX() {
		switch (type) {
		case LOGITECH:
			return joystick.getX();
		case XBOX:
			return joystick.getRawAxis(4);
		default:
			return 0;
		}
	}

	public double getRightY() {
		switch (type) {
		case LOGITECH:
			return joystick.getY();
		case XBOX:
			return -joystick.getRawAxis(5);
		default:
			return 0;
		}
	}
	
	public boolean getStart() {
		switch (type) {
		case LOGITECH:
			return joystick.getRawButton(1);
		case XBOX:
			return joystick.getRawButton(8);
		default:
			return false;
		}
	}
	
	public boolean getA() {
		switch (type) {
		case LOGITECH:
			return joystick.getRawButton(1);
		case XBOX:
			return joystick.getRawButton(1);
		default:
			return false;
		}
	}
}
