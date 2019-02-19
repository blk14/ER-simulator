import java.util.Comparator;

public final class PatientComparator implements Comparator<Patient> {

	@Override
	public int compare(Patient o1, Patient o2) {
		// TODO Auto-generated method stub
		return o1.state.severity - o2.state.severity;
	}
}
