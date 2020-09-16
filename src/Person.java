package P4;

import java.util.Scanner;

/*
 * File Name: Person.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: Person is the sublass of a superclass Thing.
 * Each person has a specific skill and located at a seaport
 */

final class Person extends Thing {

	// Variables given
	String skill;

	// Check if person is working
	boolean isWorking;

	// Constructor
	public Person(Scanner sc) {
		super(sc);
		if (sc.hasNext()) {
			this.setSkill(sc.next());
		} else {
			this.setSkill("Error");
		}
		this.setIsWorking(false);
	} // end end Scanner constructor

	// Generated Getters and Setters
	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public boolean getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	// Overridden toString Method
	@Override
	public String toString() {

		return "Person: " + super.toString() + " " + this.getSkill();
	}
}