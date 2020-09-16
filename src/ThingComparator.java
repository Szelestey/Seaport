package P4;

import java.util.Comparator;

/*
 * File Name: ThingComparator.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: ThingComparator allows us to change the 
 * way we sort the ArrayList's and satisfy the requirement
 * to implement comparators.
 */

final class ThingComparator implements Comparator<Thing> {

	// Variable
	String target;

	// Constructor
	ThingComparator(String target) {
		this.setTarget(target);
	}

	// Generated Getters and Setters
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	// Comparison logic
	@Override
	public int compare(Thing o1, Thing o2) {

		switch (this.getTarget()) {
		case "Weight":
			if (((Ship) o1).getWeight() == ((Ship) o2).getWeight()) {
				return 0;
			} else if (((Ship) o1).getWeight() > ((Ship) o2).getWeight()) {
				return 1;
			} else {
				return -1;
			}
		case "Length":
			if (((Ship) o1).getLength() == ((Ship) o2).getLength()) {
				return 0;
			} else if (((Ship) o1).getLength() > ((Ship) o2).getLength()) {
				return 1;
			} else {
				return -1;
			}
		case "Draft":
			if (((Ship) o1).getDraft() == ((Ship) o2).getDraft()) {
				return 0;
			} else if (((Ship) o1).getDraft() > ((Ship) o2).getDraft()) {
				return 1;
			} else {
				return -1;
			}
		case "Width":
			if (((Ship) o1).getWidth() == ((Ship) o2).getWidth()) {
				return 0;
			} else if (((Ship) o1).getWidth() > ((Ship) o2).getWidth()) {
				return 1;
			} else {
				return -1;
			}
		case "Name":
			return o1.getName().compareTo(o2.getName());
		default:
			return -1;
		}
	}
}
