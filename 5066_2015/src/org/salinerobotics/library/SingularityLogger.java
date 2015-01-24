package org.salinerobotics.library;

//import apache
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SingularityLogger {
	//Use log(Log Level, Message);
	public static void log(String args,String args2) {
		
		SmartDashboard.putString("test", "["+args+"] "+args2+".");
	}
}
