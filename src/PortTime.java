package P4;

/*
 * File Name: PortTime.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: PortTime purpose is to keep track
 * of when the ships come into the sea port.
 */

final class PortTime {

	// Variables given
	int time;

	// Constructor
	public PortTime(int time) {
		this.setTime(time);
	}

	// Generated Getters and Setters
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	// Overriden toString Method
	@Override
	public String toString() {
		return "Time: " + this.getTime();
	}
}