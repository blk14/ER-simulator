import java.util.Comparator;

public final class FirstTriageComparator implements Comparator<Patient> {

	@Override
	public int compare(Patient o1, Patient o2) {
		// TODO Auto-generated method stub
		return o2.state.severity - o1.state.severity;
	}

}
