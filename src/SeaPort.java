package P4;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

/*
 * File Name: SeaPort.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: SeaPort is a subclass of the superclass Thing.
 * There are many ports in the world and this class shows
 * which port you are at; included are docks, waiting list, all ships,
 * and people at the port. 
 */

final class SeaPort extends Thing {

	// Variables given
	ArrayList<Dock> docks;
	ArrayList<Ship> que;
	ArrayList<Ship> ships;
	ArrayList<Person> persons;

	// List of persons with particular skills at each port
	HashMap<String, ResourcePools> resourcePools;

	// Constructor
	public SeaPort(Scanner sc) {
		super(sc);
		this.setDocks(new ArrayList<>());
		this.setQue(new ArrayList<>());
		this.setShips(new ArrayList<>());
		this.setPersons(new ArrayList<>());
		this.setResourcePools(new HashMap<>());
	}

	// Generated Getters and Setters
	public ArrayList<Dock> getDocks() {
		return docks;
	}

	public void setDocks(ArrayList<Dock> docks) {
		this.docks = docks;
	}

	public ArrayList<Ship> getQue() {
		return que;
	}

	public void setQue(ArrayList<Ship> que) {
		this.que = que;
	}

	public ArrayList<Ship> getShips() {
		return ships;
	}

	public void setShips(ArrayList<Ship> ships) {
		this.ships = ships;
	}

	public ArrayList<Person> getPersons() {
		return persons;
	}

	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}

	public HashMap<String, ResourcePools> getResourcePools() {
		return resourcePools;
	}

	public void setResourcePools(HashMap<String, ResourcePools> resourcePools) {
		this.resourcePools = resourcePools;
	}

	// Matching skills required by job
	protected synchronized ArrayList<Person> getResources(Job job) {

		// Declarations
		ArrayList<Person> candidates;
		boolean areAllRequirementsMet;
		String workerLogLine;
		ResourcePools skillGroup;
		Person worker;
		HashMap<String, Integer> skillsMap;

		// Definitions
		candidates = new ArrayList<>();
		areAllRequirementsMet = true;
		workerLogLine = "";
		skillsMap = new HashMap<>();

		// Grab skills and count how many are in pool
		job.getRequirements().forEach((String skill) -> {
			skillsMap.merge(skill, 1, (a, b) -> a + b);
		});

		// Jump-out label
		outerLoop: for (String skill : job.getRequirements()) {

			// Puts all skill in new resource pool
			skillGroup = this.getResourcePools().get(skill);

			// Catch if there are no skills found
			if (skillGroup == null) {
				job.getStatusLog().append("Ship: " + job.getParentShip().getName() + " - needs qualified worker for "
						+ job.getName() + ")\n");
				this.returnResources(candidates);
				job.endJob();
				return new ArrayList<>();

				// Catch if there arnt enough workers for the job
			} else if (skillGroup.getPersonsInPool().size() < skillsMap.get(skill)) {
				job.getStatusLog().append("Ship: " + job.getParentShip().getName()
						+ " - needs more qualified worker for " + job.getName() + ")\n");
				this.returnResources(candidates);
				job.endJob();
				return new ArrayList<>();

			} else {
				// For all workers with the required skill
				for (Person person : skillGroup.getPersonsInPool()) {

					// If person isnt working
					if (!person.getIsWorking()) {
						skillGroup.reservePerson(person);
						candidates.add(person);
						continue outerLoop;
					}
				}

				// Keep looping if requirements arent met
				areAllRequirementsMet = false;
				break;
			}
		} // jump out of loop

		// Logic, if all cases are true; return worker
		if (areAllRequirementsMet) {
			workerLogLine += job.getName() + "\tShip_" + job.getParentShip().getName() + " \tReserving: ";

			for (int i = 0; i < candidates.size(); i++) {
				worker = candidates.get(i);

				if (i == 0) {
					workerLogLine += " ";
				} else if (i < candidates.size() - 1) {
					workerLogLine += ", ";
				} else {
					workerLogLine += " & ";
				}

				workerLogLine += worker.getName();
			}
			job.getStatusLog().append(workerLogLine + "\n");

			return candidates;
		} else {

			// return
			this.returnResources(candidates);
			return null;
		}
	}

	// Retruns worker to pool after the job is done and sets the flag for isWorking
	protected synchronized void returnResources(ArrayList<Person> resources) {
		resources.forEach((Person worker) -> {
			this.getResourcePools().get(worker.getSkill()).returnPerson(worker);
		});
	}

	// DivideWorkersBySkill method takes the persons array and creates contents
	// based on the persons skill. Than adds them to a new resource pool
	protected void divideWorkersBySkill() {
		ResourcePools myResourcePool;

		for (Person person : this.getPersons()) {
			myResourcePool = this.getResourcePools().get(person.getSkill());

			// If there are no skills that match input, than this will creates it
			if (myResourcePool == null) {
				myResourcePool = new ResourcePools(new ArrayList<>(), person.getSkill(), this.getName());
				this.getResourcePools().put(person.getSkill(), myResourcePool);
			}

			myResourcePool.addPerson(person);
		}
	}

	// Overriden toString Method given
	@Override
	public String toString() {

		String str = "\n\nSeaPort: " + super.toString();
		for (Dock md : docks)
			str += "\n\n - " + md;
		str += "\n\n\n -------- List of all ships in que --------";
		for (Ship ms : que)
			str += "\n - " + ms;
		str += "\n\n\n -------- List of all ships --------";
		for (Ship ms : ships)
			str += "\n - " + ms;
		str += "\n\n\n -------- List of all persons --------";
		for (Person mp : persons)
			str += "\n - " + mp;
		return str;
	} // end method toString
}