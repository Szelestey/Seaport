package P4;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;

/*
 * File Name: Job.java
 * Date: Dec 12, 2019
 * Author: Alexander Szelestey
 * Purpose: New implemented class, Takes one ship at a time 
 * to perform a job and each type of ship has different 
 * required jobs and implements into GUI.
 */

final class Job extends Thing implements Runnable {

	// Variables given
	double duration;
	ArrayList<String> requirements;

	// Thread-related fields
	enum Status {
		RUNNING, SUSPENDED, WAITING, DONE
	};

	Ship parentShip;
	SeaPort parentPort;
	boolean isSuspended, isCancelled, isFinished, isEndex;
	Status status;
	Thread jobThread;
	ArrayList<Person> workers;

	// GUI elements
	JTextArea statusLog, workerLog;
	JButton cancelButton, suspendButton;
	JProgressBar jobProgress;
	JPanel rowPanel;
	JLabel rowLabel, rowShipLabel;
	JScrollPane jspRowPanel;

	// Constructor
	protected Job(Scanner sc, HashMap<Integer, Ship> hms) {
		super(sc);
		if (sc.hasNextDouble()) {
			this.setDuration(sc.nextDouble());
		}

		this.setRequirements(new ArrayList<>());
		while (sc.hasNext()) {
			this.getRequirements().add(sc.next());
		}

		this.setParentShip(hms.get(this.getParent()));
		this.setParentPort(this.getParentShip().getPort());
		this.setWorkers(new ArrayList<>());
		this.setIsSuspended(false);
		this.setIsCancelled(false);
		this.setIsFinished(false);
		this.setStatus(Status.SUSPENDED);
		this.setJobThread(new Thread(this, this.getName() + "(" + this.getParentShip().getName() + ")"));
		this.setIsEndex(false);
	}

	// Generated Getters and Setters
	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public ArrayList<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(ArrayList<String> requirements) {
		this.requirements = requirements;
	}

	public Ship getParentShip() {
		return parentShip;
	}

	public void setParentShip(Ship parentShip) {
		this.parentShip = parentShip;
	}

	public SeaPort getParentPort() {
		return parentPort;
	}

	public void setParentPort(SeaPort parentPort) {
		this.parentPort = parentPort;
	}

	public boolean getIsSuspended() {
		return isSuspended;
	}

	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public boolean getIsFinished() {
		return isFinished;
	}

	public void setIsFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	public boolean getIsEndex() {
		return isEndex;
	}

	public void setIsEndex(boolean isEndex) {
		this.isEndex = isEndex;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Thread getJobThread() {
		return jobThread;
	}

	public void setJobThread(Thread jobThread) {
		this.jobThread = jobThread;
	}

	public ArrayList<Person> getWorkers() {
		return workers;
	}

	public void setWorkers(ArrayList<Person> workers) {
		this.workers = workers;
	}

	public JTextArea getStatusLog() {
		return statusLog;
	}

	public void setStatusLog(JTextArea statusLog) {
		this.statusLog = statusLog;
	}

	public JTextArea getWorkerLog() {
		return workerLog;
	}

	public void setWorkerLog(JTextArea workerLog) {
		this.workerLog = workerLog;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(JButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public JButton getIsSuspendButton() {
		return suspendButton;
	}

	public void setSuspendButton(JButton suspendButton) {
		this.suspendButton = suspendButton;
	}

	public JProgressBar getJobProgress() {
		return jobProgress;
	}

	public void setJobProgress(JProgressBar jobProgress) {
		this.jobProgress = jobProgress;
	}

	public JPanel getRowPanel() {
		return rowPanel;
	}

	public void setRowPanel(JPanel rowPanel) {
		this.rowPanel = rowPanel;
	}

	public JLabel getRowLabel() {
		return rowLabel;
	}

	public void setRowLabel(JLabel rowLabel) {
		this.rowLabel = rowLabel;
	}

	public JLabel getRowShipLabel() {
		return rowShipLabel;
	}

	public void setRowShipLabel(JLabel rowShipLabel) {
		this.rowShipLabel = rowShipLabel;
	}

	// TablePanel from seaportprogram is initiated here and invoked by startAllJobs
	protected JPanel getJobAsPanel() {

		// Definition
		this.rowPanel = new JPanel(new GridLayout(1, 4));

		// Job JLabel
		this.rowLabel = new JLabel(this.getName());
		this.rowLabel.setBorder(BorderFactory.createEtchedBorder());

		// Ship JLabel
		this.rowShipLabel = new JLabel("Ship_" + this.getParentShip().getName());
		this.rowShipLabel.setBorder(BorderFactory.createEtchedBorder());

		// JProgressBar
		this.jobProgress = new JProgressBar();
		this.jobProgress.setStringPainted(true);

		// JButton definitions
		this.suspendButton = new JButton("Suspend");
		this.cancelButton = new JButton("Cancel");

		// Suspend button handler
		this.suspendButton.addActionListener((ActionEvent e) -> {
			this.toggleSuspendFlag();
		});

		// Cancel button handler
		this.cancelButton.addActionListener((ActionEvent e) -> {
			this.toggleCancelFlag();

			// Scroll Panel
			this.jspRowPanel = new JScrollPane(this.rowPanel);
		});

		// Add to row panel
		this.rowPanel.add(this.rowLabel);
		this.rowPanel.add(this.rowShipLabel);
		this.rowPanel.add(this.jobProgress);
		this.rowPanel.add(this.suspendButton);
		this.rowPanel.add(this.cancelButton);

		return this.rowPanel;
	}

	// Start Thread
	protected void startJob() {
		this.setIsFinished(false);
		this.getJobThread().start();
	}

	// End Thread
	protected void endJob() {
		this.toggleCancelFlag();
		this.setIsEndex(true);
	}

	// Toggle between stop and run
	private void toggleSuspendFlag() {
		this.setIsSuspended(!this.getIsSuspended());
	}

	// Cancels Job
	private void toggleCancelFlag() {
		this.setIsCancelled(true);
		this.setIsFinished(true);
	}

	// Shows progress of each job
	private void showStatus(Status status) {
		switch (status) {
		case RUNNING:
			this.suspendButton.setBackground(Color.GREEN);
			this.suspendButton.setText("Running");
			break;
		case SUSPENDED:
			this.suspendButton.setBackground(Color.YELLOW);
			this.suspendButton.setText("Suspended");
			break;
		case WAITING:
			this.suspendButton.setBackground(Color.ORANGE);
			this.suspendButton.setText("Waiting");
			break;
		case DONE:
			this.suspendButton.setBackground(Color.RED);
			this.suspendButton.setText("Done");
			break;
		}
	}

	// Checks for open piers and availble workers
	private synchronized boolean isWaiting() {
		ArrayList<Person> dockWorkers;
		if (this.getParentPort().getQue().contains(this.getParentShip())) {
			return true;
		} else {
			if (!this.getRequirements().isEmpty()) {
				dockWorkers = this.getParentPort().getResources(this);
				if (dockWorkers == null) {
					return true;
				} else {
					this.setWorkers(dockWorkers);
					return false;
				}
			} else {
				return false;
			}
		}
	}

	// Runs through each job
	@Override
	public void run() {

		// Declarations
		long time, startTime, stopTime;
		double timeNeeded;
		ArrayList<Boolean> statusList;
		Ship newShipToMoor;
		Dock parentDock;
		String workerLogLine;

		// Definitions
		time = System.currentTimeMillis();
		startTime = time;
		stopTime = time + 1000 * (long) this.getDuration();
		timeNeeded = stopTime - time;
		workerLogLine = "";

		// Wait until there is available port
		synchronized (this.getParentPort()) {
			while (this.isWaiting()) {
				try {
					this.showStatus(Status.WAITING);
					this.getParentPort().wait();
				} catch (InterruptedException e) {
					System.out.println("Error: " + e);
				}
			}
		}

		// Execution of Buttons: Cancel and Suspeneded
		while (time < stopTime && !this.getIsCancelled()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Error: " + e);
			}

			if (!this.getIsSuspended()) {
				this.showStatus(Status.RUNNING);
				time += 100;
				this.jobProgress.setValue((int) (((time - startTime) / timeNeeded) * 100));
			} else {
				this.showStatus(Status.SUSPENDED);
			}

			if (this.getIsEndex()) {
				return;
			}
		}

		if (!this.getIsSuspended()) {
			this.jobProgress.setValue(100);
			this.showStatus(Status.DONE);
			this.setIsFinished(true);
		}

		// Move a new ship to pier and notify thread completions
		synchronized (this.getParentPort()) {

			if (!this.getRequirements().isEmpty() && !this.getWorkers().isEmpty()) {
				workerLogLine += this.getName() + "\tShip: " + this.getParentShip().getName() + "\tReturning: ";

				for (int i = 0; i < this.getWorkers().size(); i++) {
					if (i == 0) {
						workerLogLine += " ";
					} else if (i < this.getWorkers().size() - 1) {
						workerLogLine += ", ";
					} else {
						workerLogLine += " & ";
					}
					workerLogLine += this.getWorkers().get(i).getName();
				}
				this.getWorkerLog().append(workerLogLine + "\n");
				this.getParentPort().returnResources(this.getWorkers());
			}

			// Verifies all jobs are completed on the boat before sending it away
			statusList = new ArrayList<>();
			this.getParentShip().getJobs().forEach((job) -> {
				statusList.add(job.getIsFinished());
			});

			if (!statusList.contains(false)) {
				this.getStatusLog()
						.append("Port: " + this.getParentPort().getName() + "\tPier: "
								+ this.getParentShip().getDock().getName() + "\tShip: " + this.getParentShip().getName()
								+ "\tUnmooring\n");

				while (!this.getParentPort().getQue().isEmpty()) {
					newShipToMoor = this.getParentPort().getQue().remove(0);

					if (!newShipToMoor.getJobs().isEmpty()) { // If this new vessel has jobs
						parentDock = this.getParentShip().getDock(); // First dock is defined
						parentDock.setShip(newShipToMoor); // Set the ship to the dock
						newShipToMoor.setDock(parentDock); // Tell the ship of its new dock location

						this.getStatusLog()
								.append("Port: " + this.getParentPort().getName() + "\tPier: "
										+ this.getParentShip().getDock().getName() + "\tShip: "
										+ this.getParentShip().getName() + "\tMooring\n");
						break;
					}
				}
			}

			this.getParentPort().notifyAll();
		}
	}

	@Override
	public String toString() {
		String stringOutput;

		stringOutput = "\t\t" + super.toString() + "\n\t\tDuration: " + this.getDuration() + "\n\t\tRequirements:";

		if (this.getRequirements().isEmpty()) {
			stringOutput += "\n\t\t\t - None";
		} else {
			for (String requiredSkill : this.getRequirements()) {
				stringOutput += "\n\t\t\t - " + requiredSkill;
			}
		}

		return stringOutput;
	}
}