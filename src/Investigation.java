import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import entities.InvestigationResult;
import entities.State;
import entities.Urgency;
/*
 * This class is also an observer class, it is updated after
 * Consultation. It is using singleton pattern
 * Here I have the instance of Triage because I need
 * investigationQueue
 */
@SuppressWarnings("deprecation")
public final class Investigation implements Observer{
	Triage triage = Triage.getInstance();
	private final int C1 = 75;
	private final int C2 = 40;
	static Investigation investigation = null;

	private Investigation() {
	}

	public static Investigation getInstance() {
		if (investigation == null) {
			return investigation = new Investigation();
		}
		return investigation;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		triage = (Triage) o;
		prepareQueue();
		startInvestigation();
	}
	/*
	 * Here to be sure that the order in the investigationQueue
	 * is based on urgency, I sort it for the first time after severity,
	 * then after urgency
	 */
	public void prepareQueue() {
		 PatientComparator patientComparator = new PatientComparator();
		 Collections.sort(triage.getInvestigationQueue(), patientComparator);
		 ArrayList<Patient> buff = new ArrayList<Patient>();

		 buff.addAll(triage.getInvestigationQueue());
		 triage.getInvestigationQueue().clear();

		 for (int i = buff.size() - 1; i >= 0; i--) {
		    	if (buff.get(i).getUrgency() == Urgency.IMMEDIATE) {
			    	triage.getInvestigationQueue().add(buff.get(i));
					buff.remove(i);
		    	}
		    }
			for (int i = buff.size() - 1; i >= 0; i--) {
		    	if (buff.get(i).getUrgency() == Urgency.URGENT) {
		    		triage.getInvestigationQueue().add(buff.get(i));
					buff.remove(i);
		    	}
		    }
			for (int i = buff.size() - 1; i >= 0; i--) {
		    	if (buff.get(i).getUrgency() == Urgency.LESS_URGENT) {
		    		triage.getInvestigationQueue().add(buff.get(i));
					buff.remove(i);
		    	}
		    }
		    for (int i = buff.size() - 1; i >= 0; i--) {
		    	if (buff.get(i).getUrgency() == Urgency.NON_URGENT) {
		    		triage.getInvestigationQueue().add(buff.get(i));
					buff.remove(i);
		    	}
		    }
	}
	/*
	 * in this method i did the investigation, I took "investigators"
	 * patients from the queue and I compare with C1 and C2 their
	 * severity, based on the comparison I set the result
	 * and I did an update of the patient state
	 */
	public void startInvestigation() {
		for (int i = 0; i < triage.input.getInvestigators(); i++) {
			Patient patient = triage.getInvestigationQueue().poll();
			if (patient != null) {
				if (patient.getState().getSeverity() > C1) {
					patient.setInvRes(InvestigationResult.OPERATE);
					patient.setConsultationState(State.EXAMINATIONSQUEUE);
					triage.getExaminationsQueue().add(patient);
				}
				if (patient.getState().getSeverity() > C2
						&& patient.getState().getSeverity() <= C1) {
					patient.setInvRes(InvestigationResult.HOSPITALIZE);
					patient.setConsultationState(State.EXAMINATIONSQUEUE);
					triage.getExaminationsQueue().add(patient);
				}
				if (patient.getState().getSeverity() <= C2) {
					patient.setInvRes(InvestigationResult.TREATMENT);
					patient.setConsultationState(State.EXAMINATIONSQUEUE);
					triage.getExaminationsQueue().add(patient);
				}
			}
			
		}
	}
}
