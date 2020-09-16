package P4;

import java.util.HashMap;
import java.util.Scanner;

/*
 * File Name: CargoShip.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: CargoShip is a subclass of the 
 * superclass Ship. This class gives the information
 * needed for a cargo ship.
 */

final class CargoShip extends Ship {

	// Variables given
	double cargoValue;
	double cargoVolume;
	double cargoWeight;

	// Constructor
	public CargoShip(Scanner sc, HashMap<Integer, Dock> hmd, HashMap<Integer, SeaPort> hmp) {
		super(sc, hmd, hmp);
		if (sc.hasNextDouble())
			this.setCargoValue(sc.nextDouble());
		if (sc.hasNextDouble())
			this.setCargoVolume(sc.nextDouble());
		if (sc.hasNextDouble())
			this.setCargoWeight(sc.nextDouble());
	} // end end Scanner constructor

	// Generated Getters and Setters
	public double getCargoValue() {
		return cargoValue;
	}

	public void setCargoValue(double cargoValue) {
		this.cargoValue = cargoValue;
	}

	public double getCargoVolume() {
		return cargoVolume;
	}

	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume = cargoVolume;
	}

	public double getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(double cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	// Overriden toString Method
	@Override
	public String toString() {
		return "Cargo Ship: " + super.toString() + "\n\tCargo Value: " + this.getCargoValue() + "\n\tCargo Volume: "
				+ this.getCargoVolume() + "\n\tCargo Weight: " + this.getCargoWeight();
	}
}