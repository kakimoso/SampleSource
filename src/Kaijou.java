
public class Kaijou {
	public static void main(String[] args) {
		System.out.println(returnkaijou(5));
	}

	private static int returnkaijou(int num) {
		if (num == 0) {
			return 1;
		} else {
			return num * returnkaijou(num - 1);
		}
	}
}
