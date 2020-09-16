package P4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 * File Name: Ship.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: Ship is the parent class to CargoShip and
 * PassengerShip. Ship is a subclass to the superclass Thing.
 * Returns everything but jobs because its not being included
 * until project 3. Still meets requirements by including it 
 * in the variables given.
 */

class Ship extends Thing {

	// Variables given
	PortTime arrivalTime, dockTime;
	double draft, length, weight, width;
	ArrayList<Job> jobs;

	// Variables for Dock and Port
	SeaPort port;
	Dock dock;
	HashMap<Integer, Dock> hmd;

	// Constructor
	public Ship(Scanner sc, HashMap<Integer, Dock> hmd, HashMap<Integer, SeaPort> hmp) {
		super(sc);
		if (sc.hasNextDouble()) {
			this.setDraft(sc.nextDouble());
		}
		if (sc.hasNextDouble()) {
			this.setLength(sc.nextDouble());
		}
		if (sc.hasNextDouble()) {
			this.setWeight(sc.nextDouble());
		}
		if (sc.hasNextDouble()) {
			this.setWidth(sc.nextDouble());
		}
		this.setJobs(new ArrayList<>());
		this.setPort(hmd, hmp);
		this.setHmd(hmd);
		this.setDock();
	}

	// Generated Getters and Setters
	public PortTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(PortTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public PortTime getDockTime() {
		return dockTime;
	}

	public void setDockTime(PortTime dockTime) {
		this.dockTime = dockTime;
	}

	public double getDraft() {
		return draft;
	}

	public void setDraft(double draft) {
		this.draft = draft;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public ArrayList<Job> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}

	public SeaPort getPort() {
		return port;
	}

	public void setPort(HashMap<Integer, Dock> hmd, HashMap<Integer, SeaPort> hmp) {
		this.port = hmp.get(this.getParent());

		if (this.port == null) {
			Dock pier = hmd.get(this.getParent());
			this.port = hmp.get(pier.getParent());
		}
	}

	public Dock getDock() {
		return dock;
	}

	public void setDock() {
		if (this.getHmd().containsKey(this.getParent())) {
			this.dock = this.getHmd().get(this.getParent());
		} else {
			this.dock = null;
		}
	}

	public void setDock(Dock dock) {
		this.dock = dock;
	}

	public HashMap<Integer, Dock> getHmd() {
		return hmd;
	}

	public void setHmd(HashMap<Integer, Dock> hmd) {
		this.hmd = hmd;
	}

	// Overriden toString Method
	@Override
	public String toString() {
		String str;

		str = super.toString() + "\n\tWeight: " + this.getWeight() + "\n\tLength: " + this.getLength() + "\n\tWidth: "
				+ this.getWidth() + "\n\tDraft: " + this.getDraft() + "\n\tJobs:";

		if (this.getJobs().isEmpty()) {
			str += " None";
		} else {
			for (Job newJob : this.getJobs()) {
				str += "\n" + newJob.toString();
			}
		}

		return str;
	}
}