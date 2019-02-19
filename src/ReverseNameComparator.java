import java.util.Comparator;

public final class ReverseNameComparator implements Comparator<Patient>{


	public int compare(Patient o1, Patient o2) {
		// TODO Auto-generated method stub
		int size = Math.min(o1.getName().length(), o2.getName().length());
		for (int i = 0; i < size; i++) {
			if (o1.getName().charAt(i) - o2.getName().charAt(i) != 0) {
				return o1.getName().charAt(i) - o2.getName().charAt(i);
			}
		}
		return 0;
	}

}

