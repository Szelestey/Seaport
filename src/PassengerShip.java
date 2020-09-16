package P4;

import java.util.HashMap;
import java.util.Scanner;

/*
 * File Name: PassengerShip.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: PassengerShip is a subclass of the superclass Ship.
 * The passenger ship has to account for rooms, occupied rooms,
 * and passengers. 
 */

final class PassengerShip extends Ship {

	// Variables given
	int numberOfOccupiedRooms;
	int numberOfPassengers;
	int numberOfRooms;

	// Constructor
	public PassengerShip(Scanner sc, HashMap<Integer, Dock> hmd, HashMap<Integer, SeaPort> hmp) {
		super(sc, hmd, hmp);
		if (sc.hasNextInt())
			this.setNumberOfOccupiedRooms(sc.nextInt());
		if (sc.hasNextInt())
			this.setNumberOfPassengers(sc.nextInt());
		if (sc.hasNextInt())
			this.setNumberOfRooms(sc.nextInt());
	} // end end Scanner constructor

	// Generated Getters and Setters
	public int getNumberOfOccupiedRooms() {
		return numberOfOccupiedRooms;
	}

	public void setNumberOfOccupiedRooms(int numberOfOccupiedRooms) {
		this.numberOfOccupiedRooms = numberOfOccupiedRooms;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	// Overriden toString Method
	@Override
	public String toString() {
		return "Passenger Ship: " + super.toString() + "\n\tOccupied Rooms: " + this.getNumberOfOccupiedRooms()
				+ "\n\tPassengers: " + this.getNumberOfPassengers() + "\n\tRooms: " + this.getNumberOfRooms();
	}
}