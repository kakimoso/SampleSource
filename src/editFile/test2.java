package editFile;

import java.io.File;

public class test2 {
	public static void main(String[] args) {
		File file = new File("C:\\Users\\kimos\\Desktop\\sacrifice\\util");
		File[] listFiles = file.listFiles();
		for(File filea: listFiles) {
			System.out.println(filea.getName());
		}
	}
}
