import java.util.ArrayList;
/*
 * this class has the attributes of a doctor that
 * are given in input, moreover there is an arrayList
 * of patients who are hospitalized by the current doctor
 * (current object)
 */
public final class Doctor {
	String type;
	boolean isSurgeon;
	int maxForTreatment;
	ArrayList<Patient> hospitalized = new ArrayList<Patient>();

	public ArrayList<Patient> getHospitalized() {
		return this.hospitalized;
	}
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean getIsSurgeon() {
		return this.isSurgeon;
	}
	public void setIsSurgeon(boolean isSurgeon) {
		this.isSurgeon = isSurgeon;
	}
	public int getMaxForTreatment() {
		return this.maxForTreatment;
	}
	public void setMaxForTreatment(int maxForTreatment) {
		this.maxForTreatment = maxForTreatment;
	}
}
