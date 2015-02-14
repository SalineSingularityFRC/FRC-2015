package org.salinerobotics.library;

import edu.wpi.first.wpilibj.Joystick;

public class SingularityController {
	public static final int LOGITECH = 0, XBOX = 1;

	Joystick joystick;
	int type;

	public void SingularityController(Joystick joystick, int type) {
		this.type = type;
		this.joystick = joystick;
	}

	public double getX() {
		return this.getX();
	}

	public double getY() {
		if (type == XBOX)
			return -this.getY();
		else
			return this.getY();
	}

	public double getLeftX() {
		switch (type) {
		case LOGITECH:
			return joystick.getX();
		case XBOX:
			return joystick.getRawAxis(XBOX);
		default:
			return 0;
		}
	}

	public double getLeftY() {
		switch (type) {
		case LOGITECH:
			return joystick.getY();
		case XBOX:
			return -joystick.getRawAxis(2);
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
}
