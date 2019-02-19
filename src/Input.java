import java.util.ArrayList;
/*
 *  this class contains the elements given as input in 
 *  jackson format
 *  The variables doctors and incomingPatients are
 *  arrays of types Doctor and Patient (this classes are
 *  defined in Doctor.java and Patient.java)
 */
public final class Input {
	private int simulationLength;
	private int nurses;
	private int investigators;
	private ArrayList<Doctor> doctors;
	private ArrayList<Patient> incomingPatients;
	private static final Input instance = new Input();
	
	private Input() {	
	}
	public static Input getInstance() {
		return instance;
	}
	public int getSimulationLength() {
		return this.simulationLength;
	}
	public void setSimulationLength(int simulationLength) {
		this.simulationLength = simulationLength;
	}
	public int getNurses() {
		return this.nurses;
	}
	public void setNurses(int nurses) {
		this.nurses = nurses;
	}
	public int getInvestigators() {
		return this.investigators;
	}
	public void setInvestigators(int investigators) {
		this.investigators = investigators;
	}
	public ArrayList<Doctor> getDoctors() {
		return this.doctors;
	}
	public void setDoctors(ArrayList<Doctor> doctors) {
		this.doctors = doctors;
	}
	public ArrayList<Patient> getIncomingPatients() {
		return this.incomingPatients;
	}
	public void setIncomingPatients(ArrayList<Patient> incomingPatients) {
		this.incomingPatients = incomingPatients;
	}
}
