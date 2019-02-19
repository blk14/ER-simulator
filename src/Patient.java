import entities.InvestigationResult;
import entities.Urgency;
import entities.State;
/*
 * in this class I have the details of a patient
 * given in input, what's more is 
 *  - the enum InvestigationResult
 * which is updated after investigation by "er_tehnician", 
 *  - the enum consultationState which represent the state of
 * the patient after consultation
 *  - an int with number of rounds which the patient will spend
 * in hospital
 *  - and some booleans which helps me to see exactly when should I
 *  print the patient and when I should not
 */
public final class Patient {
	private int id;
	private String name;
	private int age;
	private int time;
	Status state;
	Urgency urgency;
	private InvestigationResult investigationResult = InvestigationResult.NOT_DIAGNOSED;
	private State consultationState = State.NON;
	private boolean delete = false;
	private boolean diagnosed = false;
	private boolean homeFromHospital = false;
	private int roundsHospitalized;
	
	public boolean getHomeFromHospital() {
		return this.homeFromHospital;
	}
	public void setHomeFromHospital(boolean buff) {
		this.homeFromHospital = buff;
	}
	public boolean getDelete() {
		return this.delete;
	}
	public void setDelete(boolean del) {
		this.delete = del;
	}
	public State getConsultationState() {
		return this.consultationState;
	}
	public int getRoundsHospitalize() {
		return this.roundsHospitalized;
	}
	public void setRoundsHospitalized(int rounds) {
		this.roundsHospitalized = rounds;
	}
	public void setConsultationState(State state) {
		this.consultationState = state;
	}
	public InvestigationResult getInvRes() {
		return this.investigationResult;
	}
	public void setInvRes(InvestigationResult inv) {
		this.investigationResult = inv;
	}
	public boolean getDiagnosed() {
		return this.diagnosed;
	}
	public void setDiagnosed(boolean diagnosed) {
		this.diagnosed = diagnosed;
	}
	public Urgency getUrgency() {
		return this.urgency;
	}
	public void setUrgency(Urgency urgency) {
		this.urgency = urgency;
	}
	public int getId() {
		return this.id;
	}
	public void setid(int id) {
		this.id= id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return this.age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getTime() {
		return this.time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Status getState() {
		return this.state;
	}
	public void setState(Status state) {
		this.state = state;
	}
}
