import java.util.Comparator;

public final class DoctorComparator implements Comparator<Doctor>{

	@Override
	public int compare(Doctor o1, Doctor o2) {
		// TODO Auto-generated method stub
		for (int i = 0; i < o1.getType().length(); i++) {
			if (o1.getType().charAt(i) - o2.getType().charAt(i) != 0) {
				return o1.getType().charAt(i) - o2.getType().charAt(i);
			}
		}
		return 0;
	}
}