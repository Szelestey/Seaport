package P4;

import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * File Name: SeaPortProgram.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: SeaPortProgam is what creates the GUI.
 * It implements things like Jpanels, Jframe, JtextArea, JtextField,
 * JButtons, Jlabel, JScrollPane, and JComboBox. 
 */

final class SeaPortProgram extends JFrame {
	private static final long serialVersionUID = 1L;

	// Variable given
	World world;

	// Variables used by GUI interface
	String title;
	int width;
	int height;

	// GUI fields
	JFrame seaPortFrame;
	JTree myTree;
	JTextArea jtaMain, jtaResults;
	JScrollPane jspResources, jspMain, jspTree, jspResults, jspTable;
	JPanel columnPanel, resourcesPanel, centerPanel, mainPanel, readPanel, treePanel, sortPanel, tablePanel,
			resultPanel, JPanel;
	JButton jbr, jbsrch, jbsrt;
	JLabel searchLbl, portLabel, skillLabel, countLabel, totalLabel;
	JTextField jtf;
	String[] jcbSearchOptions, jcbPortOptions, jcbQueOptions, jcbShipSpecsOptions;
	JComboBox<String> jcbSearch, jcbPort, jcbQue, jcbShipSpecs;

	// Lets user select from a data file
	JFileChooser jf;
	Scanner sc;

	// Constructor
	protected SeaPortProgram() {
		super("SeaPortProgram");
		this.setTitle("SeaPortProgram");
		this.setWidth(950);
		this.setHeight(950);
	}

	// Generated Getters and Setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	// GUI Constructor
	private void constructGUI() {

		// Changed up layout to BoxLayout and GridLayout
		this.mainPanel = new JPanel(new BorderLayout());
		this.centerPanel = new JPanel(new GridLayout(4, 1, 5, 5));
		this.treePanel = new JPanel(new GridLayout(1, 10, 5, 5));
		this.tablePanel = new JPanel(new GridLayout(0, 1, 1, 1));
		this.readPanel = new JPanel(new GridLayout(1, 10, 5, 5));
		this.sortPanel = new JPanel(new GridLayout(1, 10, 5, 5));
		this.resultPanel = new JPanel(new GridLayout(1, 10, 5, 5));
		this.resourcesPanel = new JPanel(new GridLayout(0, 1, 1, 1));
		this.columnPanel = new JPanel(new GridLayout(0, 4));

		// Text Area display settings for a nicer look // Font given
		this.jtaMain = new JTextArea();
		this.jtaMain.setEditable(false); // Forbid editing JTextArea
		this.jtaMain.setFont(new Font("Monospaced", 0, 12)); // Insert Font as per rubric
		this.jtaMain.setLineWrap(true); // Precaution to encourage wrap

		// Use JScrollPane and JTextArea to display the internal data structure // given
		this.jspMain = new JScrollPane(this.jtaMain);

		// JTree results object
		this.myTree = new JTree();
		this.myTree.setModel(null);

		// Add JTree to JScrollPane
		this.jspTree = new JScrollPane(this.myTree);

		// Search results JTextArea // Font given
		this.jtaResults = new JTextArea();
		this.jtaResults.setEditable(false);
		this.jtaResults.setFont(new Font("Monospaced", 0, 12));
		this.jtaResults.setLineWrap(true);

		// Add resourcesPanel to JScrollPane
		this.jspResources = new JScrollPane(this.resourcesPanel);

		// Add jtaResults to JScrollPane
		this.jspResults = new JScrollPane(this.jtaResults);

		// ADD tablePanel to JScrollPane
		this.jspTable = new JScrollPane(this.tablePanel);

		// Add treePanel to JScrollPane
		this.jspTree.add(this.treePanel);

		// Add resultPanel to JScrollPane
		this.jspResults.add(this.resultPanel);

		// Create buttons to search, sort, and import
		this.jbr = new JButton("Select Data File");
		this.jbsrch = new JButton("Search");
		this.jbsrt = new JButton("Sort");

		// Search label display options
		this.searchLbl = new JLabel("Search targets:", JLabel.CENTER);
		this.jtf = new JTextField("", 10);

		// Specific drop downs assigned // Included null for cleaner look
		this.jcbPortOptions = new String[] { null, "All ports" };
		this.jcbPort = new JComboBox<>(this.jcbPortOptions);

		this.jcbSearchOptions = new String[] { null, "Person", "Index", "Skill", "Ship" };
		this.jcbSearch = new JComboBox<>(this.jcbSearchOptions);

		this.jcbQueOptions = new String[] { null, "Que", "Jobs" };
		this.jcbQue = new JComboBox<>(this.jcbQueOptions);

		this.jcbShipSpecsOptions = new String[] { null, "Name", "Weight", "Length", "Width", "Draft" };
		this.jcbShipSpecs = new JComboBox<>(this.jcbShipSpecsOptions);

		// Creates a title for resource pool
		this.portLabel = new JLabel("Skill", JLabel.CENTER);
		this.skillLabel = new JLabel("Port", JLabel.CENTER);
		this.countLabel = new JLabel("Available Workers", JLabel.CENTER);
		this.totalLabel = new JLabel("Total Workers", JLabel.CENTER);

		// Add single border line for simplicity
		this.skillLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.portLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.countLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.totalLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

		// Adds Labels to panel and panel to resources panel
		this.columnPanel.add(this.portLabel);
		this.columnPanel.add(this.skillLabel);
		this.columnPanel.add(this.countLabel);
		this.columnPanel.add(this.totalLabel);

		// Add UI options to read panel
		this.readPanel.add(this.jbr); // Read button first
		this.readPanel.add(this.searchLbl); // Search label "Search:"
		this.readPanel.add(this.jtf); // Search bar itself
		this.readPanel.add(this.jcbSearch); // Sorting options combo box
		this.readPanel.add(this.jbsrch); // Search button itself
		// Add UI options to sort panel
		this.sortPanel.add(this.jcbPort); // Sort SeaPort selection
		this.sortPanel.add(this.jcbQue); // Sort what selection box
		this.sortPanel.add(this.jcbShipSpecs); // Sort by selection box
		this.sortPanel.add(this.jbsrt); // Sort button itself

		// GUI panel setup
		mainPanel.add(this.readPanel, BorderLayout.NORTH);
		centerPanel.add(this.jspTree);
		centerPanel.add(this.jspTable);
		centerPanel.add(this.jspResources);
		centerPanel.add(this.jspResults);
		mainPanel.add(this.centerPanel, BorderLayout.CENTER);
		mainPanel.add(this.sortPanel, BorderLayout.AFTER_LAST_LINE);

		// Borders for all panels
		this.readPanel.setBorder(BorderFactory.createTitledBorder("Read and Search"));
		this.sortPanel.setBorder(BorderFactory.createTitledBorder("Sort"));
		this.jspTree.setBorder(BorderFactory.createTitledBorder("World Data Structures"));
		this.jspTable.setBorder(BorderFactory.createTitledBorder("Job List"));
		this.jspResults.setBorder(BorderFactory.createTitledBorder("Results"));
		this.jspResources.setBorder(BorderFactory.createTitledBorder("Resources in Pools"));

		// keeping it colorful
		this.tablePanel.setBackground(Color.WHITE);
		this.jspResources.setBackground(Color.WHITE);
		this.resourcesPanel.setBackground(Color.WHITE);

		// Handler for Combobox
		this.jcbQue.addActionListener((ActionEvent e) -> {
			this.provideProperSortOptions();
		});

		// Handler for reader button
		this.jbr.addActionListener((ActionEvent e) -> {
			this.readFile();
		});

		// Handler for search button
		this.jbsrch.addActionListener((ActionEvent e) -> {
			this.search();
		});

		// Handler for sort button
		this.jbsrt.addActionListener((ActionEvent e) -> {
			this.sort();
		});

		// seaPortFrame description
		this.seaPortFrame = new JFrame(this.getTitle());
		this.seaPortFrame.setContentPane(this.mainPanel);
		this.seaPortFrame.setSize(this.getWidth(), this.getHeight());
		this.seaPortFrame.setVisible(true);
		this.seaPortFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Allows que selection on ShipSpecs
	private void provideProperSortOptions() {
		this.jcbShipSpecs.removeAllItems();
		this.jcbShipSpecs.addItem("Name");
		if (this.jcbShipSpecs.getSelectedIndex() == 0) { // Is que == true
			this.jcbShipSpecs.addItem("Weight");
			this.jcbShipSpecs.addItem("Width");
			this.jcbShipSpecs.addItem("Length");
			this.jcbShipSpecs.addItem("Draft");
		}
	}

	// Auto select name
	private void provideProperSeaPortSortOptions() {
		this.jcbPort.removeAllItems();
		this.jcbPort.addItem("All ports");
		Collections.sort(this.world.getPorts(), new ThingComparator("Name"));
		if (this.world.getPorts().size() > 1) {
			for (SeaPort newPort : this.world.getPorts()) {
				this.jcbPort.addItem(newPort.getName());
			}
		}
	}

	/*
	 * readFile handles what happens after pressing the "Select Data File" button by
	 * giving you the option to choose a txt file only file.
	 */
	private void readFile() {

		// Variables
		int choice;
		FileReader fr;
		FileNameExtensionFilter filter;

		// Given // Starts at dot, the current directory // Reads only txt files
		this.jf = new JFileChooser(".");
		filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		this.jf.setFileFilter(filter);

		choice = this.jf.showOpenDialog(new JFrame());

		if (choice == JFileChooser.APPROVE_OPTION) {
			try {
				fr = new FileReader(this.jf.getSelectedFile());
				this.sc = new Scanner(fr);
			} catch (FileNotFoundException ex) {
				String message = "\"The system cannot find the file specified.";
				JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		// Clear all previous jobs
		if (this.world != null) {
			this.clearAllJobs();
		}

		// Creates new World and passes scanner content
		this.world = new World(this.sc);

		// Error message will display if any other format is choosen.
		if (this.world.getAllThings().isEmpty()) {
			// Reset text area
			this.jtaMain.setText("");
			this.jtaResults.setText("");
			this.myTree.setModel(null);
			this.clearAllJobs();
			this.world = null;
			String message = "The system cannot open the file choosen.";
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			this.jtaMain.setText(""); // Reset text area
			this.jtaResults.setText(""); // Clear past searches of old Worlds, etc
			this.myTree.setModel(new DefaultTreeModel(this.world.toTree()));
			this.provideProperSeaPortSortOptions();
			this.addResourcePools();
			this.startAllJobs();
		}
	}

	// Handler for the search button and appropriate error messages
	private void search() {

		// Prompts user to select data file
		if (this.world == null || this.sc == null) {
			String message = "Please Select Data File.";
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Variables and their Assignment
		String output = "";
		String input = this.jtf.getText().trim();
		int comboBox = this.jcbSearch.getSelectedIndex();

		// Prompts user to enter text into text feild
		if (input.equals("")) {
			String message = "Input is empty.\nPlease enter text into search targets: ";
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Switch statement gives each selection in the comboBox a purpose
		switch (comboBox) {
		case 0: // Blank
			break;
		case 1: // Person
			for (SeaPort port : this.world.getPorts()) {
				for (Person person : port.getPersons()) {
					if (person.getName().equals(input)) {
						output += "\t" + person.getName() + " \n\tID: " + person.getIndex() + "\n";
					}
				}
			}
			this.displayStatus(output, input);
			break;
		case 2: // Index
			output = this.display(comboBox, input);
			this.displayStatus(output, input);
			break;
		case 3: // Skill
			for (SeaPort port : this.world.getPorts()) {
				for (Person person : port.getPersons()) {
					if (person.getName().equals(input)) {
						output += "\t" + person.getName() + " \n\tSkill: " + person.getSkill() + "\n";
					}
				}
			}
			this.displayStatus(output, input);
			break;
		case 4: // Ship
			for (SeaPort port : this.world.getPorts()) {
				for (Ship ship : port.getShips()) {
					if (ship.getName().equals(input))
						output += "\t" + ship.getName() + " \n\tID: " + ship.getIndex() + "\n";
				}
			}
			this.displayStatus(output, input);
			break;
		default:
			break;
		}
	}

	/*
	 * String display reduces the amount of code in the switch statement
	 */
	private String display(int index, String target) {

		// Variables and Assignments
		Method getParam;
		String parameter;
		String output = "";
		String methodName = (index == 0) ? "getName" : "getIndex";

		try {
			// gets Name or index from Thing class
			getParam = Thing.class.getDeclaredMethod(methodName);
			for (Thing item : this.world.getAllThings()) {
				// Create Strings
				parameter = "" + getParam.invoke(item);
				if (parameter.equals(target)) {
					output += item.getName() + " " + item.getIndex() + " (" + item.getClass().getSimpleName() + ")\n";
				}
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			System.out.println("Error: " + ex);
		}
		return output;
	}

	/*
	 * DisplayStatus determines if there is no matching input. An error message will
	 * take its place. It also reduces the amount of code in the switch statement.
	 */
	private void displayStatus(String output, String target) {
		if (output.equals("")) {
			JOptionPane.showMessageDialog(null, "Warning: '" + target + "' not found.", "Warning",
					JOptionPane.WARNING_MESSAGE);
		} else {
			this.jtaResults.append("Search results for " + target + "\n" + output + "\n");
		}
	}

	@SuppressWarnings("unchecked")
	private void sort() {

		// Pop up message if user presses sort before selecting data file
		if (this.world == null || this.sc == null) {
			String message = "Select Data File to continue";
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Variables
		String sortPort, sortQue, sortShipSpecs, result, field, list;
		Method getField, getList;
		ArrayList<Thing> thingsList, newList;
		HashMap<String, String> hmShipSpecs, hmQue;

		// Using relevant terms to put data
		hmShipSpecs = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("Name", "getIndex");
				put("Weight", "getWeight");
				put("Length", "getLength");
				put("Width", "getWidth");
				put("Draft", "getDraft");
			}
		};

		hmQue = new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("Que", "getQue");
				put("Jobs", "getShips");
			}
		};

		// Definitions
		sortPort = this.jcbPort.getSelectedItem().toString();
		sortQue = this.jcbQue.getSelectedItem().toString();
		sortShipSpecs = this.jcbShipSpecs.getSelectedItem().toString();
		field = hmShipSpecs.get(sortShipSpecs);
		list = hmQue.get(sortQue);
		result = "";
		thingsList = new ArrayList<>();

		try {
			getList = SeaPort.class.getDeclaredMethod(list);

			if (sortQue.equals("Que") && !sortShipSpecs.equals("Name")) {
				getField = Ship.class.getDeclaredMethod(field);
			} else {
				getField = Thing.class.getDeclaredMethod(field);
			}

			if (sortPort.equals("All ports")) {
				sortPort = sortPort.toLowerCase();
				for (SeaPort newPort : world.getPorts()) {
					newList = (ArrayList<Thing>) getList.invoke(newPort);
					thingsList.addAll(newList);
				}
			} else {
				for (SeaPort newPort : this.world.getPorts()) {
					if (newPort.getName().equals(sortPort)) {
						newList = (ArrayList<Thing>) getList.invoke(newPort);
						thingsList.addAll(newList);
					}
				}
			}
			if (sortQue.equals("Jobs")) {
				ArrayList<Job> jobsList = new ArrayList<>();

				for (Iterator<Thing> iterator = thingsList.iterator(); iterator.hasNext();) {
					Ship newShip = (Ship) iterator.next();
					for (Job newJob : newShip.getJobs()) {
						jobsList.add(newJob);
					}
				}
				thingsList.clear(); // Remove all remaining Ship instances
				thingsList.addAll(jobsList); // Replace with Jobs
			}

			// Catch cases wherein there are no appropriate results
			if (thingsList.isEmpty()) {
				result += "> No results found.\n";
			} else {
				// Sort through the collected results
				Collections.sort(thingsList, new ThingComparator(sortShipSpecs));

				// Assemble results, displaying relevant data beside the entry in parentheses
				for (Thing newThing : thingsList) {
					result += "\t- " + newThing.getName() + " (" + getField.invoke(newThing) + ")\n";
				}
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			System.out.println("Error: " + ex);
		}

		// Format searches through addition of readable header
		this.jtaResults.append("Sort results by " + sortShipSpecs.toLowerCase() + ", that are in the " + sortQue
				+ " of " + sortPort + "\n" + result + "\n");
	}

	// Implement new Table
	private void startAllJobs() {
		for (SeaPort port : this.world.getPorts()) {

			for (Dock dk : port.getDocks()) {
				if (dk.getShip().getJobs().isEmpty()) {
					this.jtaResults.append("Port: " + port.getName() + "\tPier: " + dk.getName() + "\tShip: "
							+ dk.getShip().getName() + "\t\tUnmooring\n");
					dk.setShip(null);

					while (!port.getQue().isEmpty()) {
						Ship newShip = port.getQue().remove(0);
						if (!newShip.getJobs().isEmpty()) {
							dk.setShip(newShip);
							this.jtaResults.append("Port: " + port.getName() + "\tPier: " + dk.getName() + "\tShip: "
									+ dk.getShip().getName() + "\t\tMooring\n");
							break;
						}
					}
				}
				//
				dk.getShip().setDock(dk);
			}
			for (Ship ship : port.getShips()) {
				if (!ship.getJobs().isEmpty()) {
					for (Job job : ship.getJobs()) {
						this.tablePanel.add(job.getJobAsPanel());
						this.tablePanel.revalidate();
						this.tablePanel.repaint();

						job.setStatusLog(this.jtaResults);
						job.setWorkerLog(this.jtaResults);
						job.startJob();
					}
				}
			}
		}
	}

	// Clear Table Panel
	private void clearAllJobs() {
		this.tablePanel.removeAll();
		this.world.getAllThings().forEach((thing) -> {
		});
	}

	// Implement Resource Panel
	private void addResourcePools() {
		this.resourcesPanel.add(this.columnPanel);
		{
			for (SeaPort port : this.world.getPorts()) {
				port.divideWorkersBySkill();
				for (HashMap.Entry<String, ResourcePools> pair : port.getResourcePools().entrySet()) {
					JPanel row = pair.getValue().getPoolAsPanel();
					this.resourcesPanel.add(row);
				}
			}
		}
	}

	// Lets get GUI started
	public static void main(String[] args) {
		SeaPortProgram newCollection = new SeaPortProgram();
		newCollection.constructGUI();
	}
}