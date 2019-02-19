import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.State;

public final class Main {
	public static int round;
	public static void main(String[] args) throws IOException {
	   ObjectMapper objectMapper = new ObjectMapper();
	   Input input = Input.getInstance();
	   ArrayList<Patient> patientStatus = new ArrayList<Patient>();

		try {
			input = objectMapper.readValue(new File(args[0]), Input.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
/*
 * I have a list of patients sorted alphabetically, which will
 * help me to print the patients who are in the process
 * process = begins with the stage "in triage queue" and ends
 * "sent home"
 */
		
		Triage triage = Triage.getInstance(input);
		patientStatus.addAll(input.getIncomingPatients());
		NameComparator cmp = new NameComparator();
		Collections.sort(patientStatus, cmp);

/*
 *  I added the observer in reverse order of the
 *  events
 *  Consultation->Investigation->PatientStatus
 */
		triage.addObserver(PatientStatus.getInstance());
		triage.addObserver(Investigation.getInstance());
		triage.addObserver(Consultation.getInstance());

/*
 * I set the queues of doctors before to start the game,
 * based on input
 */

		Consultation cons = Consultation.getInstance();
		cons.setQueuesOfDoctors(input);	
/*
 * Here I will start the rounds, at every single round I will
 * set Triage Queue and Examinations Queue, then I will update
 * the observer classes 
 */
		for (round = 0; round < input.getSimulationLength(); round++) {
			triage.setTriageQueue();
			triage.setExaminationsQueue();
			triage.update();
/*
 * I sort the hospitalized patients from PatientStatus,
 * they will be printed at "nurses treat patients"
 */
			Collections.sort(PatientStatus.getInstance().getHospitalized(), cmp);
/*
 * patientStatus is the sorted list from above. Every patient at initialization
 * has at enum "ConsultationState" the value NON which means that he does not have
 * to be printed
 */
		    System.out.println("~~~~ Patients in round " + (round + 1) + " ~~~~");

		    for (int j = 0; j < patientStatus.size(); j++) {
		    	if (patientStatus.get(j).getConsultationState().getValue() != "nop") {
		    		System.out.println(patientStatus.get(j).getName() + " is "
			    			+ patientStatus.get(j).getConsultationState().getValue());
			    	if (patientStatus.get(j).getHomeFromHospital()) {
			    		patientStatus.get(j).setConsultationState(State.HOME_DONE_TREATMENT);
			    	}
		    	}
		    }
		    System.out.println();
/*
 * indexNurses is a counter which will be incremented at every iteration.
 * I take the hospitalized patients from PatientStatus.java
 */
		    System.out.println("~~~~ Nurses treat patients ~~~~");
		    int nurses = input.getNurses();
		    int indexNurses = 0;
		    for (int i = 0; i < PatientStatus.getInstance().getHospitalized().size(); i++) {
		    	if (!PatientStatus.getInstance().getHospitalized().get(i).getHomeFromHospital()
		    			|| !PatientStatus.getInstance().getHospitalized().get(i).getDelete()) {
		    		if (PatientStatus
			    			.getInstance().getHospitalized().get(i).getRoundsHospitalize() == 1) {
		    			System.out.println("Nurse " + indexNurses + 
				    			" treated " + PatientStatus.getInstance().getHospitalized()
				    			.get(i).getName() + " and patient has " + PatientStatus
				    			.getInstance().getHospitalized().get(i).getRoundsHospitalize() + 
				    			" more round");
		    		} else {
		    			System.out.println("Nurse " + indexNurses + 
				    			" treated " + PatientStatus.getInstance().getHospitalized()
				    			.get(i).getName() + " and patient has " + PatientStatus
				    			.getInstance().getHospitalized().get(i).getRoundsHospitalize() + 
				    			" more rounds");
		    		}
		    		
			    	indexNurses ++;
			    	if (indexNurses == nurses) {
			    		indexNurses = 0;
			    	}
/*
 * set if it was for the last time printed
 */
			    	if (!PatientStatus.getInstance().getHospitalized().get(i).getDelete()
			    			&& PatientStatus.getInstance().getHospitalized().get(i).getHomeFromHospital()) {
			    		PatientStatus.getInstance().getHospitalized().get(i).setDelete(true);
			    	}
		    	} 
		    }
		    System.out.println();
/*
 * at this point I iterate through doctors' list and for each one
 * I will iterate through his patients.
 * After checking the doctor's type I will print a specific message based on
 * the boolean homeFromHospital of the patient
 */
		    System.out.println("~~~~ Doctors check their hospitalized patients and give verdicts ~~~~");
		    ArrayList<Doctor> doctors = input.getDoctors();

		    for (int i = 0; i < doctors.size(); i++) {
	    		NameComparator nameCmp = new NameComparator();
	    		Collections.sort(doctors.get(i).getHospitalized(), nameCmp);
	    		
		    	for (int j = 0; j < doctors.get(i).getHospitalized().size(); j++) {

		    		switch (doctors.get(i).getType()) {
					case "CARDIOLOGIST":
						if (doctors.get(i).getHospitalized()
								.get(j).getHomeFromHospital()) {
							System.out.println("Cardiologist sent "
								+ doctors
			    					.get(i).getHospitalized().get(j)
			    					.getName() + " home");
			    			doctors.get(i).getHospitalized().remove(j);
			    			j--;
						} else {
							System.out.println("Cardiologist says that "
						+ doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " should remain"
			    							+ " in hospital");
						}
						break;
					case "ER_PHYSICIAN":
						if (doctors.get(i).getHospitalized().get(j)
								.getHomeFromHospital()) {
							System.out.println("ERPhysician sent " + doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " home");
			    			doctors.get(i).getHospitalized().remove(j);
			    			j--;
						} else {
							System.out.println("ERPhysician says that " + doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " should remain"
			    							+ " in hospital");
						}
						break;
					case "GASTROENTEROLOGIST":
						if (doctors.get(i).getHospitalized().get(j)
								.getHomeFromHospital()) {
							System.out.println("Gastroenterologist sent " + doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " home");
			    			doctors.get(i).getHospitalized().remove(j);
			    			j--;
						} else {
							System.out.println("Gastroenterologist says that "
						+ doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " should remain"
			    							+ " in hospital");
						}
						break;
					case "GENERAL_SURGEON":
						if (doctors.get(i).getHospitalized().get(j)
								.getHomeFromHospital()) {
							System.out.println("General Surgeon sent " + doctors
			    					.get(i).getHospitalized().get(j)
			    					.getName() + " home");
			    			doctors.get(i).getHospitalized().remove(j);
			    			j--;
						} else {
							System.out.println("General Surgeon says that "
						+ doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " should remain"
			    							+ " in hospital");
						}
						break;
					case "INTERNIST":
						if (doctors.get(i).getHospitalized().get(j)
								.getHomeFromHospital()) {
							System.out.println("Internist sent " + doctors
			    					.get(i).getHospitalized().get(j)
			    					.getName() + " home");
			    			doctors.get(i).getHospitalized().remove(j);
			    			j--;
						} else {
							System.out.println("Internist says that " + doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " should remain"
			    							+ " in hospital");
						}
						break;
					case "NEUROLOGIST":
						if (doctors.get(i).getHospitalized().get(j)
								.getHomeFromHospital()) {
							System.out.println("Neurologist sent " + doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " home");
			    			doctors.get(i).getHospitalized().remove(j);
			    			j--;
						} else {
							System.out.println("Neurologist says that "
						+ doctors
			    					.get(i).getHospitalized().get(j).getName()
			    					+ " should remain"
			    							+ " in hospital");
						}
						break;
					default:
						break;
		    		}
		    	}
		    }
		    System.out.println();
		}
	}
}
