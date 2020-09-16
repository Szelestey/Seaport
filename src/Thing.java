package P4;

import java.util.Scanner;

/*
 * File Name: Thing.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: Thing is the parent class to Dock,
 * Job, Person, SeaPort, Ship, and World. Comparable 
 * interface is used to order the object of the 
 * user-defined class. Not being used, but it returns 
 * pos, neg, and zero to compare specified object. 
 */

class Thing implements Comparable<Thing> {

	// Variables given
	int index;
	String name;
	int parent;

	// Constructor
	public Thing(Scanner sc) {
		if (sc.hasNext())
			this.setName(sc.next());
		if (sc.hasNextInt())
			this.setIndex(sc.nextInt());
		if (sc.hasNextInt())
			this.setParent(sc.nextInt());
	} // end end Scanner constructor

	// Generated Getters and Setters
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	// Overriden toString Method
	@Override
	public String toString() {
		return this.getName() + " " + this.getIndex();
	}

	// Comparabe Interface Method
	@Override
	public int compareTo(Thing th) {
		if ((th.getIndex() == this.getIndex()) && (th.getName().equals(this.getName()))
				&& (th.getParent() == this.getParent())) {
			return 1;
		} else {
			return 0;
		}
	}
}