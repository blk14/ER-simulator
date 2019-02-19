import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;

import entities.IllnessType;
import entities.InvestigationResult;
import entities.State;

/*
 * this is an observer class and it has the purpose to
 * take patients from examinationsQueue, to associate each one
 * with a doctor and to do the consultation
 * It is also a class which use singleton pattern
 * 
 * For it I have lists with all the illnesses and before to start the
 * rounds when I know the doctors given in input I call the method setQueuesOfDoctors
 * and I put in this lists the doctors who are able to treat that illness
 */
public final class Consultation implements Observer{
	private static Consultation consultation = null;
	PatientStatus patientStatus = PatientStatus.getInstance();
	Triage triage = Triage.getInstance();
	private LinkedList<Doctor> abdominalPain = new LinkedList<Doctor>();
	private LinkedList<Doctor> allergicReaction = new LinkedList<Doctor>();
	private LinkedList<Doctor> brokenBones = new LinkedList<Doctor>();
	private LinkedList<Doctor> burns = new LinkedList<Doctor>();
	private LinkedList<Doctor> carAccident = new LinkedList<Doctor>();
	private LinkedList<Doctor> cuts = new LinkedList<Doctor>();
	private LinkedList<Doctor> foodPoisoning = new LinkedList<Doctor>();
	private LinkedList<Doctor> heartAttack = new LinkedList<Doctor>();
	private LinkedList<Doctor> heartDisease = new LinkedList<Doctor>();
	private LinkedList<Doctor> highFever = new LinkedList<Doctor>();
	private LinkedList<Doctor> pneumonia = new LinkedList<Doctor>();
	private LinkedList<Doctor> sportInjuries = new LinkedList<Doctor>();
	private LinkedList<Doctor> stroke = new LinkedList<Doctor>();
	
	private Consultation() {
		
	}

	public static Consultation getInstance() {
		if (consultation == null) {
			return consultation = new Consultation();
		}
		return consultation;
	}


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		triage = (Triage) o;
		startConsultation();
	}
	/*
	 * In this method I take all the patients from examinationsQueue
	 * and I add them in a list from PatientStautus of consulted patients
	 * Then in function of what illness the patient has I choose a doctor from
	 * a list or another. Here I have multiple options
	 * 
	 *  - the patient was not diagnosed and he is for the first time in
	 *  examinationsQueue -> I peek the first doctor from list of his illness
	 *  and call the methods doConsultation and takenDoctors (details below)
	 *  with patient and doctor as parameters
	 *  
	 *  - the patient was diagnosed, so it means that he came with results
	 *  from er_tehnician: 
	 *    	- in case in which he has to be operated I'm
	 *  	looking for a doctor "isSurgeon", then I call the same methods
	 *  	with that doctor and current patient as parameters
	 *  	- if there is not that kind of doctor I will send the patient
	 *  	to other hospital
	 *  	- if he does not need to be operated I did the same thing like
	 *  	for not diagnosed
	 */
	public void startConsultation() {
		while (!triage.getExaminationsQueue().isEmpty()) {
			boolean ok = false;
			Patient patient = triage.getExaminationsQueue().poll();
			Doctor doctor;
			patientStatus.getConsulted().add(patient);

				switch (IllnessType.valueOf(patient.state.illnessName)) {
				case ABDOMINAL_PAIN:
					if (!patient.getDiagnosed()) {
						doctor = this.abdominalPain.removeFirst();
						this.abdominalPain.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, abdominalPain);
					} else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.abdominalPain
									.size(); i++) {
								if (this.abdominalPain.get(i)
										.isSurgeon) {
									doctor = this.abdominalPain
											.remove(i);
									this.abdominalPain
									.addLast(doctor);
									doConsultation(doctor,
											patient);
									takenDoctor(doctor,
											abdominalPain);
									ok = true;
									break;
								}
							}
							if (!ok)
							patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.abdominalPain.removeFirst();
							this.abdominalPain.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, abdominalPain);
						}
					}
					break;
				case ALLERGIC_REACTION:
					if (!patient.getDiagnosed()) {
						doctor = this.allergicReaction.removeFirst();
						this.allergicReaction.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, allergicReaction);

					} else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.allergicReaction
									.size(); i++) {
								if (this.allergicReaction.get(i)
										.isSurgeon) {
									doctor = this
											.allergicReaction.remove(i);
									this.allergicReaction
									.addLast(doctor);
									doConsultation(doctor,
											patient);
									takenDoctor(doctor,
											allergicReaction);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
								ok = false;
								break;
						} else {
							doctor = this.allergicReaction.removeFirst();
							this.allergicReaction.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, allergicReaction);
						}
					}
					break;
				case BROKEN_BONES:
					if (!patient.getDiagnosed()) {
						doctor = this.brokenBones.removeFirst();
						this.brokenBones.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, brokenBones);
					} else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.brokenBones
									.size(); i++) {
								if (this.brokenBones
										.get(i).isSurgeon) {
									doctor = this
										.brokenBones.remove(i);
									this.brokenBones
									.addLast(doctor);
									doConsultation(doctor,
											patient);
									takenDoctor(doctor,
											brokenBones);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.brokenBones.removeFirst();
							this.brokenBones.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, brokenBones);
						}
					}
					break;

				case BURNS:
					if (!patient.getDiagnosed()) {
						doctor = this.burns.removeFirst();
						this.burns.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, burns);
					} else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.burns.size(); i++) {
								if (this.burns.get(i).isSurgeon) {
									doctor = this.burns.remove(i);
									this.burns.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, burns);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.burns.removeFirst();
							this.burns.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, burns);
						}
					}
					break;

				case CAR_ACCIDENT:
					if (!patient.getDiagnosed()) {
						doctor = this.carAccident.removeFirst();
						this.carAccident.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, carAccident);
					} else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.carAccident
									.size(); i++) {
								if (this.carAccident.get(i).isSurgeon) {
									doctor = this.carAccident.remove(i);
									this.carAccident.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, carAccident);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.carAccident.removeFirst();
							this.carAccident.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, carAccident);
						}
					}
					break;

				case CUTS:
					if (!patient.getDiagnosed()) {
						doctor = this.cuts.removeFirst();
						this.cuts.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, cuts);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.cuts.size(); i++) {
								if (this.cuts.get(i).isSurgeon) {
									doctor = this.cuts.remove(i);
									this.cuts.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, cuts);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.cuts.removeFirst();
							this.cuts.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, cuts);
						}
					}
					break;

				case FOOD_POISONING:
					if (!patient.getDiagnosed()) {
						doctor = this.foodPoisoning.removeFirst();
						this.foodPoisoning.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, foodPoisoning);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.foodPoisoning
									.size(); i++) {
								if (this.foodPoisoning.get(i).isSurgeon) {
									doctor = this.foodPoisoning.remove(i);
									this.foodPoisoning.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, foodPoisoning);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.foodPoisoning.removeFirst();
							this.foodPoisoning.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, foodPoisoning);
						}
					}
					break;

				case HEART_ATTACK:
					if (!patient.getDiagnosed()) {
						doctor = this.heartAttack.removeFirst();
						this.heartAttack.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, heartAttack);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.heartAttack.size(); i++) {
								if (this.heartAttack.get(i).isSurgeon) {
									doctor = this.heartAttack.remove(i);
									this.heartAttack.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, heartAttack);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.heartAttack.removeFirst();
							this.heartAttack.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, heartAttack);
						}
					}
					break;

				case HEART_DISEASE:
					if (!patient.getDiagnosed()) {
						doctor = this.heartDisease.removeFirst();
						this.heartDisease.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, heartDisease);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.heartDisease
									.size(); i++) {
								if (this.heartDisease.get(i).isSurgeon) {
									doctor = this.heartDisease.remove(i);
									this.heartDisease.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, heartDisease);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.heartDisease.removeFirst();
							this.heartDisease.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, heartDisease);
						}
					}
					break;

				case HIGH_FEVER:
					if (!patient.getDiagnosed()) {
						doctor = this.highFever.removeFirst();
						this.highFever.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, highFever);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.highFever
									.size(); i++) {
								if (this.highFever.get(i).isSurgeon) {
									doctor = this.highFever.remove(i);
									this.highFever.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, highFever);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.highFever.removeFirst();
							this.highFever.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, highFever);
						}
					}
					break;

				case PNEUMONIA:
					if (!patient.getDiagnosed()) {
						doctor = this.pneumonia.removeFirst();
						this.pneumonia.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, pneumonia);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.pneumonia
									.size(); i++) {
								if (this.pneumonia.get(i).isSurgeon) {
									doctor = this.pneumonia.remove(i);
									this.pneumonia.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, pneumonia);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.pneumonia.removeFirst();
							this.pneumonia.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, pneumonia);
						}
					}
					break;

				case SPORT_INJURIES:
					if (!patient.getDiagnosed()) {
						doctor = this.sportInjuries.removeFirst();
						this.sportInjuries.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, sportInjuries);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.sportInjuries
									.size(); i++) {
								if (this.sportInjuries.get(i).isSurgeon) {
									doctor = this.sportInjuries.remove(i);
									this.sportInjuries.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, sportInjuries);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.sportInjuries.removeFirst();
							this.sportInjuries.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, sportInjuries);
						}
					}
					break;

				case STROKE:
					if (!patient.getDiagnosed()) {
						doctor = this.stroke.removeFirst();
						this.stroke.addLast(doctor);
						doConsultation(doctor, patient);
						takenDoctor(doctor, stroke);
					}  else {
						if (patient.getInvRes() == InvestigationResult
								.OPERATE) {
							for (int i = 0; i < this.stroke
									.size(); i++) {
								if (this.stroke.get(i).isSurgeon) {
									doctor = this.stroke.remove(i);
									this.stroke.addLast(doctor);
									doConsultation(doctor, patient);
									takenDoctor(doctor, stroke);
									ok = true;
									break;
								}
							}
							if (!ok) patient.setConsultationState(State
									.OTHERHOSPITAL);
							ok = false;
							break;
						} else {
							doctor = this.stroke.removeFirst();
							this.stroke.addLast(doctor);
							doConsultation(doctor, patient);
							takenDoctor(doctor, stroke);
						}
					}
					break;

				default:
					break;
				
				}
			
		}
	}
	/*
	 * In this method the doctor is face-to-face with patient
	 * If the patient is for the first time in consultation (isn't diagnosed)
	 * the doctor compare the severity with his maxForTreatment and decides
	 * if the patient has to go to investigation (in this case he add the patient
	 * in the queue for investigation from Triage.java) or to send home
	 * 
	 * if the patient was diagnosed the doctors checks the investigation result:
	 * - TREATMENT: he sent the patient home
	 * - OPERATE: I set C1 and C2 in function of doctor's type and I reduce severity
	 * and calculate the number of rounds of hospitalization after given formulas
	 * - HOSPITALIZE: I set C1 and C2 and I calculate the nr of rounds of hospitalization
	 * if the patient needs hospitalization I add him on the doctor's list of hospitalized
	 * patients and on a list of hospitalized patients from PatientStatus.java 
	 */
	private void doConsultation(Doctor doctor, Patient patient) {
		double C1 = 0;
		double C2 = 0;
		if (!patient.getDiagnosed()) {
			if (patient.getState().getSeverity() <= doctor.getMaxForTreatment()) {
				//TODO pacientul este trimis acasa
				switch (doctor.type) {
				case "CARDIOLOGIST":
					patient.setConsultationState(State.HOME_CARDIO);
					break;
				case "ER_PHYSICIAN":
					patient.setConsultationState(State.HOME_ERPHYSICIAN);
					break;
				case "GASTROENTEROLOGIST":
					patient.setConsultationState(State.HOME_GASTRO);
					break;
				case "GENERAL_SURGEON":
					patient.setConsultationState(State.HOME_SURGEON);
					break;
				case "INTERNIST":
					patient.setConsultationState(State.HOME_INTERNIST);
					break;
				case "NEUROLOGIST":
					patient.setConsultationState(State.HOME_NEURO);
					break;
				default:
					break;
				}
			} else {
				this.triage.getInvestigationQueue().add(patient);
				patient.setConsultationState(State.INVESTIGATIONSQUEUE);
			}
			patient.setDiagnosed(true);
		} else {
			switch (patient.getInvRes()) {
			case TREATMENT:
				switch (doctor.type) {
				case "CARDIOLOGIST":
					patient.setConsultationState(State.HOME_CARDIO);
					break;
				case "ER_PHYSICIAN":
					patient.setConsultationState(State.HOME_ERPHYSICIAN);
					break;
				case "GASTROENTEROLOGIST":
					patient.setConsultationState(State.HOME_GASTRO);
					break;
				case "GENERAL_SURGEON":
					patient.setConsultationState(State.HOME_SURGEON);
					break;
				case "INTERNIST":
					patient.setConsultationState(State.HOME_INTERNIST);
					break;
				case "NEUROLOGIST":
					patient.setConsultationState(State.HOME_NEURO);
					break;
				default:
					break;
				}
				break;
			case OPERATE:
				switch (doctor.type) {
				case "CARDIOLOGIST":
					patient.setConsultationState(State.OPERATED_CARDIO);
					C1 = 0.4;
					C2 = 0.1;
					break;
				case "ER_PHYSICIAN":
					patient.setConsultationState(State.OPERATED_ERPHYSICIAN);
					C1 = 0.1;
					C2 = 0.3;
					break;
				case "GENERAL_SURGEON":
					patient.setConsultationState(State.OPERATED_SURGEON);
					C1 = 0.2;
					C2 = 0.2;
					break;
				case "NEUROLOGIST":
					patient.setConsultationState(State.OPERATED_NEURO);
					C1 = 0.5;
					C2 = 0.1;
					break;
				default:
					break;
				}
				patient.getState().setSeverity((int) Math.round(((patient.getState().getSeverity()
						- Math.round(patient.getState().getSeverity() * C2)))));

				patient.setRoundsHospitalized((Math.max(
						(int) Math.round(patient.getState().getSeverity() * C1), 3)));

				patientStatus.getHospitalized().add(patient);
				doctor.getHospitalized().add(patient);
				break;
			case HOSPITALIZE:
				switch (doctor.type) {
				case "CARDIOLOGIST":
					patient.setConsultationState(State.HOSPITALIZED_CARDIO);
					C1 = 0.4;
					C2 = 0.1;
					break;
				case "ER_PHYSICIAN":
					patient.setConsultationState(State.HOSPITALIZED_ERPHYSICIAN);
					C1 = 0.1;
					C2 = 0.3;
					break;
				case "GASTROENTEROLOGIST":
					patient.setConsultationState(State.HOSPITALIZED_GASTRO);
					C1 = 0.5;
					break;
				case "GENERAL_SURGEON":
					patient.setConsultationState(State.HOSPITALIZED_SURGEON);
					C1 = 0.2;
					C2 = 0.2;
					break;
				case "INTERNIST":
					patient.setConsultationState(State.HOSPITALIZED_INTERNIST);
					C1 = 0.01;
					break;
				case "NEUROLOGIST":
					patient.setConsultationState(State.HOSPITALIZED_NEURO);
					C1 = 0.5;
					C2 = 0.1;
					break;
				default:
					break;
				}

				patient.setRoundsHospitalized((Math.max(
						(int) Math.round(patient.getState().getSeverity() * C1), 3))); 

				patientStatus.getHospitalized().add(patient);
				doctor.getHospitalized().add(patient);
				break;
			default:
				break;
			}
		}
	}
	
/*
 * In this method I put the doctors in the lists for illnesses
 */
	public void setQueuesOfDoctors(Input input) {

		ArrayList<Doctor> doctors = new ArrayList<Doctor>();
		doctors = (ArrayList<Doctor>) input.getDoctors().clone();

		for (int i = 0; i < doctors.size(); i++) {
			switch(doctors.get(i).type) {
			case "CARDIOLOGIST":
				this.heartAttack.add(doctors.get(i));
				this.heartDisease.add(doctors.get(i));
				break;
			case "ER_PHYSICIAN":
				this.allergicReaction.add(doctors.get(i));
				this.brokenBones.add(doctors.get(i));
				this.burns.add(doctors.get(i));
				this.carAccident.add(doctors.get(i));
				this.cuts.add(doctors.get(i));
				this.highFever.add(doctors.get(i));
				this.sportInjuries.add(doctors.get(i));
				break;
			case "GASTROENTEROLOGIST":
				this.abdominalPain.add(doctors.get(i));
				this.allergicReaction.add(doctors.get(i));
				this.foodPoisoning.add(doctors.get(i));
				break;
			case "GENERAL_SURGEON":
				this.abdominalPain.add(doctors.get(i));
				this.burns.add(doctors.get(i));
				this.carAccident.add(doctors.get(i));
				this.cuts.add(doctors.get(i));
				this.sportInjuries.add(doctors.get(i));
				break;
			case "INTERNIST":
				this.abdominalPain.add(doctors.get(i));
				this.allergicReaction.add(doctors.get(i));
				this.foodPoisoning.add(doctors.get(i));
				this.heartDisease.add(doctors.get(i));
				this.highFever.add(doctors.get(i));
				this.pneumonia.add(doctors.get(i));
				break;
			case "NEUROLOGIST":
				this.stroke.add(doctors.get(i));
				break;
			default:
				break;
			}
		}
	}
	/*
	 * In this method I take the doctor given as parameter from all the
	 * list in which he is and I put him to the end of it
	 */
	private void takenDoctor(Doctor doctor, LinkedList<Doctor> takenIll) { 

		for (int i = 0; i < abdominalPain.size(); i++) {
			if (abdominalPain != takenIll) {
				if (doctor == abdominalPain.get(i)) {
					abdominalPain.remove(i);
					abdominalPain.addLast(doctor);
					break;
				}
			}
			
		}
		
		for (int i = 0; i < allergicReaction.size(); i++) {
			if (allergicReaction != takenIll) {
				if (doctor == allergicReaction.get(i)) {
					allergicReaction.remove(i);
					allergicReaction.addLast(doctor);
					break;
				}
			}
		}
		
		for (int i = 0; i < brokenBones.size(); i++) {
			if (brokenBones != takenIll) {
				if (doctor == brokenBones.get(i)) {
					brokenBones.remove(i);
					brokenBones.addLast(doctor);
					break;
				}
			}
		}
		
		for (int i = 0; i < burns.size(); i++) {
			if (burns != takenIll) {
				if (doctor == burns.get(i)) {
					burns.remove(i);
					burns.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < carAccident.size(); i++) {
			if (carAccident != takenIll) {
				if (doctor == carAccident.get(i)) {
					carAccident.remove(i);
					carAccident.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < cuts.size(); i++) {
			if (cuts != takenIll) {
				if (doctor == cuts.get(i)) {
					cuts.remove(i);
					cuts.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < foodPoisoning.size(); i++) {
			if (foodPoisoning != takenIll) {
				if (doctor == foodPoisoning.get(i)) {
					foodPoisoning.remove(i);
					foodPoisoning.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < heartAttack.size(); i++) {
			if (heartAttack != takenIll) {
				if (doctor == heartAttack.get(i)) {
					heartAttack.remove(i);
					heartAttack.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < heartDisease.size(); i++) {
			if (heartDisease != takenIll) {
				if (doctor == heartDisease.get(i)) {
					heartDisease.remove(i);
					heartDisease.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < highFever.size(); i++) {
			if (highFever != takenIll) {
				if (doctor == highFever.get(i)) {
					highFever.remove(i);
					highFever.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < pneumonia.size(); i++) {
			if (pneumonia != takenIll) {
				if (doctor == pneumonia.get(i)) {
					pneumonia.remove(i);
					pneumonia.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < sportInjuries.size(); i++) {
			if (sportInjuries != takenIll) {
				if (doctor == sportInjuries.get(i)) {
					sportInjuries.remove(i);
					sportInjuries.addLast(doctor);
					break;
				}
			}
		}
		for (int i = 0; i < stroke.size(); i++) {
			if (stroke != takenIll) {
				if (doctor == stroke.get(i)) {
					stroke.remove(i);
					stroke.addLast(doctor);
					break;
				}
			}
		}
	}

}
