package P4;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * File Name: ResourcePools.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: ResourcePools is added to assist with the new implementations of Project4. 
 * This creates the last panel and implemnets 4 panels withint that relays how many 
 * people with skills are currently available
 */

public class ResourcePools {

	// Variables
	ArrayList<Person> personsInPool;
	int availablePersons, totalPersons;
	String skill, parentPort;

	// GUI elements
	JPanel rowPanel;
	JLabel portLabel, skillLabel, countLabel, totalLabel;

	// Constructor
	protected ResourcePools(ArrayList<Person> personsInPool, String skill, String parentPort) {
		this.setPersonsInPool(personsInPool);
		this.setTotalPersons(this.getPersonsInPool().size());
		this.setAvailablePersons(this.getPersonsInPool().size());
		this.setSkill(skill);
		this.setParentPort(parentPort);
	}

	// Generated Getters and Setters
	public ArrayList<Person> getPersonsInPool() {
		return personsInPool;
	}

	public void setPersonsInPool(ArrayList<Person> personsInPool) {
		this.personsInPool = personsInPool;
	}

	public int getAvailablePersons() {
		return availablePersons;
	}

	public void setAvailablePersons(int availablePersons) {
		this.availablePersons = availablePersons;
	}

	public int getTotalPersons() {
		return totalPersons;
	}

	public void setTotalPersons(int totalPersons) {
		this.totalPersons = totalPersons;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getParentPort() {
		return parentPort;
	}

	public void setParentPort(String parentPort) {
		this.parentPort = parentPort;
	}

	public JPanel getRowPanel() {
		return rowPanel;
	}

	public void setRowPanel(JPanel rowPanel) {
		this.rowPanel = rowPanel;
	}

	public JLabel getPortLabel() {
		return portLabel;
	}

	public void setPortLabel(JLabel portLabel) {
		this.portLabel = portLabel;
	}

	public JLabel getSkillLabel() {
		return skillLabel;
	}

	public void setSkillLabel(JLabel skillLabel) {
		this.skillLabel = skillLabel;
	}

	public JLabel getCountLabel() {
		return countLabel;
	}

	public void setCountLabel(JLabel countLabel) {
		this.countLabel = countLabel;
	}

	public JLabel getTotalLabel() {
		return totalLabel;
	}

	public void setTotalLabel(JLabel totalLabel) {
		this.totalLabel = totalLabel;
	}

	// GUI constructor for resource panel
	protected JPanel getPoolAsPanel() {

		String job = this.getSkill().substring(0, 1).toUpperCase() + this.getSkill().substring(1);

		// Layout
		this.rowPanel = new JPanel(new GridLayout(1, 3));
		this.skillLabel = new JLabel(job, JLabel.CENTER);
		this.portLabel = new JLabel(this.getParentPort(), JLabel.CENTER);
		this.countLabel = new JLabel(String.valueOf(this.getAvailablePersons()), JLabel.CENTER);
		this.totalLabel = new JLabel(String.valueOf(this.getTotalPersons()), JLabel.CENTER);

		// Add background color
		this.rowPanel.setBackground(Color.WHITE);

		// Add single border line for simplicity
		this.skillLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.portLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.countLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		this.totalLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));

		// Add labels to panel
		this.rowPanel.add(this.skillLabel);
		this.rowPanel.add(this.portLabel);
		this.rowPanel.add(this.countLabel);
		this.rowPanel.add(this.totalLabel);

		return this.rowPanel;
	}

	// Add available/total workers
	protected void addPerson(Person person) {
		this.getPersonsInPool().add(person);
		this.setAvailablePersons(this.getPersonsInPool().size());
		this.setTotalPersons(this.getPersonsInPool().size());
	}

	// Setting people up to start working
	protected void reservePerson(Person person) {
		person.setIsWorking(true);
		this.setAvailablePersons(this.getAvailablePersons() - 1);
		this.countLabel.setText(String.valueOf(this.getAvailablePersons()));
	}

	// Setting workers to available and adding 1 to the panel
	protected void returnPerson(Person person) {
		person.setIsWorking(false);
		this.setAvailablePersons(this.getAvailablePersons() + 1);
		this.countLabel.setText(String.valueOf(this.getAvailablePersons()));
	}
}
