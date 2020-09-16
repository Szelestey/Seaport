package P4;

import java.util.Scanner;

/*
 * File Name: Dock.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: Dock is a sublass of the superclass Thing.
 * This class aquires and prints dock information.
 */

final class Dock extends Thing {

	// Variables given
	Ship ship;

	// Constructor
	public Dock(Scanner sc) {
		super(sc);
	}

	// Generated Getters and Setters
	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	// Overriden toString Method
	@Override
	public String toString() {
		String str = "Dock: " + super.toString() + "\n\t";

		if (this.getShip() == null) {
			str += "EMPTY";
		} else {
			str += this.getShip().toString();
		}

		return str;
	}
}