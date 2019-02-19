import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import entities.IllnessType;
import entities.State;
import entities.Urgency;
import entities.UrgencyEstimator;
/*
 * this class is an observable class, here is the start of
 * the process. Here the patients are taken by nurses and
 * sorted after their state and based on this information they
 * are sent in examinationsQueue. Then, using observe pattern
 * I'll do update to Consultation, Investigation and  PatientStatus
 * It is also a class which use singleton pattern
 * 
 * 
 * Here I have 4 LinkedLists:
 *  - examinationsQueue will be the list with the patients who are sent to
 *  consultation
 *  - investigationQueue will be used by observers Consultation and Investigation
 * I will explain the role of firstTriage and triageQueue below
 */

public final class Triage extends Observable{
	Input input;
	private LinkedList<Patient> triageQueue = new LinkedList<Patient>();
	private LinkedList<Patient> firstTriage = new LinkedList<Patient>();
	private Queue<Patient> examinationsQueue = new LinkedList<Patient>();
	LinkedList<Patient> investigationQueue = new LinkedList<Patient>();

	public static Triage triage = null;
	
	private Triage(Input input) {
		this.input = input;
	}

	public Input getInput() {
		return this.input;
	}
	public LinkedList<Patient> getTriageQueue() {
		return this.triageQueue;
	}
	public Queue<Patient> getExaminationsQueue() {
		return this.examinationsQueue;
	}
	public LinkedList<Patient> getInvestigationQueue() {
		return this.investigationQueue;
	}
	public void setInvestigationQueue(LinkedList<Patient> invQ) {
		this.investigationQueue = invQ;
	}
	/*
	 * the role of firstTriage is in this method where I set triageQueue
	 * I take all the patients with time equals with current round and I put
	 * them in firstTriage
	 * Then, I gave them Urgency status and I sorted them after severity
	 * After this I took the first "nurses" patients from firstTriage and
	 * I put them in triageQueue
	 * I didn't clean firstTriage because I need the remaining patients
	 * for the next round
	 */
	public void setTriageQueue() {
		for (int i = 0; i < input.getIncomingPatients().size(); i++) {
			if (input.getIncomingPatients().get(i).getTime() == Main.round) {
				input.getIncomingPatients().get(i).setConsultationState(State.TRIAGEQUEUE);
				firstTriage.add(input.getIncomingPatients().get(i));
			}
		}
		for (int i = 0; i < firstTriage.size(); i++) {
			firstTriage.get(i).urgency = UrgencyEstimator.getInstance().estimateUrgency(IllnessType.valueOf
	    			(firstTriage.get(i).state.illnessName), firstTriage.get(i).state.severity);                                                    
	    }

		FirstTriageComparator firstTriageComparator = new FirstTriageComparator();
	    Collections.sort(firstTriage, firstTriageComparator);  

		for (int i = 0; i < input.getNurses(); i++) {
			if (firstTriage.size() > 0) {
				Patient patient = firstTriage.remove(0);
				triageQueue.add(patient);
				patient.setConsultationState(State.EXAMINATIONSQUEUE);
				for (int j = 0; j < firstTriage.size(); j++) {
					if (input.getIncomingPatients().get(j) == patient) {
						input.getIncomingPatients().remove(j);
					}
				}
			}
		}
	}
	/*
	 * this method has the purpose to take a list of patients and sort it
	 * after urgency of the patients. Known that the list is already sorted
	 * after severity I used 4 for-s which loop the list in reverse order,
	 * and each for check for one urgency. To respect the priority I
	 * start with IMMEDIATE case and I finished with NON_URGENT 
	 */
	
	public LinkedList<Patient> sortAfterUrgency(LinkedList<Patient> buff) {
		LinkedList<Patient> sortedArray = new LinkedList<Patient>();

		for (int i = buff.size() - 1; i >= 0; i--) {
	    	if (buff.get(i).getUrgency() == Urgency.IMMEDIATE) {
	    		sortedArray.add(buff.get(i));
				buff.remove(i);
	    	}
	    }
		for (int i = buff.size() - 1; i >= 0; i--) {
	    	if (buff.get(i).getUrgency() == Urgency.URGENT) {
	    		sortedArray.add(buff.get(i));
				buff.remove(i);
	    	}
	    }
		for (int i = buff.size() - 1; i >= 0; i--) {
	    	if (buff.get(i).getUrgency() == Urgency.LESS_URGENT) {	
	    		sortedArray.add(buff.get(i));
				buff.remove(i);
	    	}
	    }
	    for (int i = buff.size() - 1; i >= 0; i--) {
	    	if (buff.get(i).getUrgency() == Urgency.NON_URGENT) {
	    		sortedArray.add(buff.get(i));
				buff.remove(i);
	    	}
	    }
	    return sortedArray;
	}
	/*
	 * In this method I set the examinationsQueue and I collect
	 * patients from triageQueue from the current round and the remaining patients
	 * in examinationsQueue
	 * After this I sorted them reverse alphabetically, after severity
	 * and after urgency. In this order because the priority is exactly
	 * the opposite
	 */
	public void setExaminationsQueue() {    		
		LinkedList<Patient> buff = new LinkedList<Patient>();

		buff.addAll(triageQueue);
		buff.addAll(examinationsQueue);
		triageQueue.clear();
		examinationsQueue.clear();
		ReverseNameComparator nameCmp = new ReverseNameComparator();
		Collections.sort(buff, nameCmp);
		PatientComparator patientComparator = new PatientComparator();
	    Collections.sort(buff, patientComparator);
	    buff = sortAfterUrgency(buff);
	    examinationsQueue = buff;
	}

	public static Triage getInstance(Input input) {
		if (triage == null) {
			triage = new Triage(input);
		}
		return triage;
	}
	public static Triage getInstance() {
		return triage;
	}
	public void update() {
	    this.setChanged();
	    this.notifyObservers();
	}
}
