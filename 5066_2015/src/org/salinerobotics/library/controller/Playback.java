//package org.salinerobotics.library.controller;
//
//import org.usfirst.frc.team5066.robot.Player;
//
//public class Playback implements SingularityController {
//
//	Player player;
//	long time;
//	
//	public Playback(Player player) {
//		this.player = player;
//	}
//	
//	public void resetTime() {
//		time = System.currentTimeMillis();
//	}
//	
//	@Override
//	public double getX() {
//		return player.get(time - System.currentTimeMillis(), Player.MOTION_X, false);
//	}
//
//	@Override
//	public double getY() {
//		return player.get(time - System.currentTimeMillis(), Player.MOTION_Y, false);
//	}
//
//	@Override
//	public double getZ() {
//		return player.get(time - System.currentTimeMillis(), Player.MOTION_Z, false);
//	}
//
//	@Override
//	public double getElevator() {
//		return player.get(time - System.currentTimeMillis(), Player.ELEVATOR, false);
//	}
//
//	@Override
//	public double getLeftX() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public double getRightX() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public double getLeftY() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public double getRightY() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public boolean getStart() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public int getControllerType() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//}
