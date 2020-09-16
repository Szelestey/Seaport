package P4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 * File Name: World.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: World class is a subclass of the superclass Thing.
 * World reads from txt file line by line to arrange everything
 * into their rightful place.
 */

final class World extends Thing {

	// Variables // given
	ArrayList<SeaPort> ports;
	PortTime time;

	// Variable // Additional
	ArrayList<Thing> allThings;

	// Constructor
	public World(Scanner sc) {
		super(sc);
		this.setAllThings(new ArrayList<>());
		this.setPorts(new ArrayList<>());
		this.process(sc);
	}

	// Generated Getters and Setters
	public ArrayList<SeaPort> getPorts() {
		return ports;
	}

	public void setPorts(ArrayList<SeaPort> ports) {
		this.ports = ports;
	}

	public PortTime getTime() {
		return time;
	}

	public void setTime(PortTime time) {
		this.time = time;
	}

	public ArrayList<Thing> getAllThings() {
		return allThings;
	}

	public void setAllThings(ArrayList<Thing> allThings) {
		this.allThings = allThings;
	}

	/*
	 * Process is the meat and potatoes of the World class, by processing the data
	 * file and organizing it into a specific Thing in the world. And accepts blank
	 * lines in the data file and ignores them.
	 */
	private void process(Scanner sc) {

		// Variables
		String str;
		Scanner scr;

		// HashMaps as described in Project 2 goals
		HashMap<Integer, SeaPort> hmp = new HashMap<>();
		HashMap<Integer, Dock> hmd = new HashMap<>();
		HashMap<Integer, Ship> hms = new HashMap<>();

		while (sc.hasNextLine()) {
			// Removes the spaces and returns omitted string
			str = sc.nextLine().trim();

			// Avoid evaluating any blank lines if exist
			if (str.length() == 0) {
				continue;
			}

			// Scanner for each line's contents
			scr = new Scanner(str);

			// Reads
			if (scr.hasNext()) {

				switch (scr.next().trim()) {
				case "port":
					SeaPort sp = new SeaPort(scr);
					this.getAllThings().add(sp);
					this.getPorts().add(sp);
					hmp.put(sp.getIndex(), sp);// Implement Hashmap
					break;
				case "dock":
					Dock dk = new Dock(scr);
					this.getAllThings().add(dk);
					this.addThingToList(hmp, dk, "getDocks");
					hmd.put(dk.getIndex(), dk);
					break;
				case "pship":
					PassengerShip ps = new PassengerShip(scr, hmd, hmp);
					this.getAllThings().add(ps);
					this.addShipToParent(ps, hmd, hmp);
					hms.put(ps.getIndex(), ps);
					break;
				case "cship":
					CargoShip cs = new CargoShip(scr, hmd, hmp);
					this.getAllThings().add(cs);
					this.addShipToParent(cs, hmd, hmp);
					hms.put(cs.getIndex(), cs);
					break;
				case "person":
					Person pn = new Person(scr);
					this.getAllThings().add(pn);
					this.addThingToList(hmp, pn, "getPersons");
					break;
				case "job":
					Job newJob = new Job(scr, hms);
					this.getAllThings().add(newJob);
					this.addJobToShip(newJob, hms, hmd);
					break;
				default:
					break;
				}
			}
		}
	}

	/*
	 * Method that reduces code repetition, selecting proper SeaPort arraylist to
	 * get docks or persons Generated suppresswarnings to convert object to
	 * arraylist
	 */
	@SuppressWarnings("unchecked")
	private <T extends Thing> void addThingToList(HashMap<Integer, SeaPort> hmp, T newThing, String methodName) {

		// Variables
		SeaPort sPort;
		ArrayList<T> thingsList;
		Method getList;

		// Variable Assignment
		sPort = hmp.get(newThing.getParent());

		try {
			// Either SeaPort.class.getPersons() or SeaPort.class.getDocks();
			getList = SeaPort.class.getDeclaredMethod(methodName);

			// SuppressWarnings
			thingsList = (ArrayList<T>) getList.invoke(sPort); // newPort.getList()

			if (sPort != null) {
				thingsList.add(newThing);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			System.out.println("Error: " + ex);
		}
	}

	// Linking Jobs to Ships
	private void addJobToShip(Job newJob, HashMap<Integer, Ship> hms, HashMap<Integer, Dock> hmd) {

		Dock Dk;
		Ship newShip = hms.get(newJob.getParent());

		if (newShip != null) {
			newShip.getJobs().add(newJob);
		} else {
			Dk = hmd.get(newJob.getParent());
			Dk.getShip().getJobs().add(newJob);
		}
	}

	// Linking a ship to its parent
	private void addShipToParent(Ship ship, HashMap<Integer, Dock> hmd, HashMap<Integer, SeaPort> hmp) {
		SeaPort myPort;
		Dock dk = hmd.get(ship.getParent());

		if (dk == null) {
			myPort = hmp.get(ship.getParent());
			myPort.getShips().add(ship);
			myPort.getQue().add(ship);
		} else {
			myPort = hmp.get(dk.getParent());
			dk.setShip(ship);
			myPort.getShips().add(ship);
		}
	}

	@SuppressWarnings("unchecked")
	protected <T extends Thing> DefaultMutableTreeNode toTree() {

		// Declarations
		DefaultMutableTreeNode mainNode, parentNode, childNode;
		Method getList;
		HashMap<String, String> classMethodMap;
		ArrayList<T> thingsList;

		// Definitions
		mainNode = new DefaultMutableTreeNode("World");
		classMethodMap = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("Docks", "getDocks");
				put("Ships", "getShips");
				put("Que", "getQue");
				put("Persons", "getPersons");
			}
		};

		for (SeaPort sPort : this.getPorts()) {
			parentNode = new DefaultMutableTreeNode("SeaPort: " + sPort.getName());
			mainNode.add(parentNode);

			for (HashMap.Entry<String, String> pair : classMethodMap.entrySet()) {
				try {
					// Reflection-related stuff
					getList = SeaPort.class.getDeclaredMethod(pair.getValue());
					thingsList = (ArrayList<T>) getList.invoke(sPort);

					// Acquire each port's children and add them to the port parent
					childNode = this.addBranch(pair.getKey(), thingsList);
					parentNode.add(childNode);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException ex) {
					System.out.println("Error: " + ex);
				}
			}
		}

		return mainNode;
	}

	/*
	 * Setup reflection to manipulate above class to assemble a sub-branch of each
	 * seaport.
	 */
	private <T extends Thing> DefaultMutableTreeNode addBranch(String title, ArrayList<T> thingsList) {

		// Declarations
		String newThingName, childTitle;
		DefaultMutableTreeNode parentNode, childNode;
		Dock thisDock;
		Ship mooredShip, ship;

		// Definitions
		parentNode = new DefaultMutableTreeNode(title);

		for (T newThing : thingsList) {
			newThingName = newThing.getName() + " (" + newThing.getIndex() + ")";
			childNode = new DefaultMutableTreeNode(newThingName);

			// Dock and Ship share the same data so instanceof is implemented
			if (newThing instanceof Dock) { // Is Dock instance
				thisDock = (Dock) newThing;
				mooredShip = thisDock.getShip();

				if (thisDock.getShip() != null) {
					childTitle = mooredShip.getName() + " (" + mooredShip.getIndex() + ")";
					childNode.add(new DefaultMutableTreeNode(childTitle));
				}
			} else if (newThing instanceof Ship) {
				ship = (Ship) newThing;

				if (!ship.getJobs().isEmpty()) {
					for (Job newJob : ship.getJobs()) {
						childTitle = newJob.getName();
						childNode.add(new DefaultMutableTreeNode(childTitle));
					}
				}
			}

			parentNode.add(childNode);
		}

		return parentNode;
	}

	// Overridden toString method
	@Override
	public String toString() {
		String str = "\n----- The world ----------\n";

		for (SeaPort seaPort : this.getPorts()) {
			str += seaPort.toString() + "\n";
		}
		return str;
	}
}