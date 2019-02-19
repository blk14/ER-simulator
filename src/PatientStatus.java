import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import entities.State;
/*
 * This class has the purpose to "take care" of the
 * patients who are hospitalized
 * This is an observer class and it is updated
 * the last one
 * This class is using singleton pattern
 */
public final class PatientStatus implements Observer{
	private static final int T = 22;
	Triage triage = Triage.getInstance();
	static PatientStatus patientStatus = null;
	ArrayList<Patient> consulted = new ArrayList<Patient>();
	ArrayList<Patient> hospitalized = new ArrayList<Patient>();
	
	private PatientStatus() {
		
	}
	public static PatientStatus getInstance() {
		if (patientStatus == null) {
			return patientStatus = new PatientStatus();
		}
		return patientStatus;
	}
	public ArrayList<Patient> getHospitalized() {
		return this.hospitalized;
	}
	public ArrayList<Patient> getConsulted() {
		return this.consulted;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		triage = (Triage) o;
		doTreatment();
		checkTreatment();
	}
	/*
	 * In this method I reduce the severity of each hospitalized
	 * patient by T and the number of rounds by 1 
	 */
	public void doTreatment() {
		for (int i = 0; i < this.hospitalized.size(); i++) {
			this.hospitalized.get(i).getState().
			setSeverity(this.hospitalized.get(i).getState().getSeverity() - T);

			this.hospitalized.get(i).
			setRoundsHospitalized(this.hospitalized.get(i).getRoundsHospitalize() - 1);
		}
	}
	/*
	 * In this method I check if the patients has to continue to stay
	 * in the hospital or not.
	 * Each doctor checks all of his hospitalized patients and if the
	 * patient has to go home I set the boolean homeFromHospital
	 * true for that patient
	 */
	public void checkTreatment() {
		for (int i = 0; i < triage.getInput().getDoctors().size(); i++) {
			Doctor doctor = triage.getInput().getDoctors().get(i);
			for (int j = 0; j < doctor.getHospitalized().size(); j++) {
				Patient patient = doctor.getHospitalized().get(j);
				if (patient.getState().getSeverity() <= 0 || patient.getRoundsHospitalize() == 0) {
					patient.setHomeFromHospital(true);
				}
			}
		}
	}
}
