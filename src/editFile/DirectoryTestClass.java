package editFile;

import java.io.File;

public class DirectoryTestClass {
	
	private static final String PARENT_PATH = "C:\\Users\\user\\Desktop\\sacrifice\\";
	
	public static void main(String[] args) {
		
		String targetPath = PARENT_PATH + "util";
		
		File testFile = new File(targetPath);
		File[] listFiles = testFile.listFiles();
//		for(File file: listFiles) {
//			System.out.println(file.getName());
//		}
		
		// 一番深いディレクトリを考える
		// ファイルがあるか判別→ファイルのリストを作成→それに対してさらに判別・リスト作成・・・をして、最下層にたどり着いたら拡張子変更
		// したらいっこ上が含むフォルダに対して深堀りしていって・・・
		// やばい
		
		// フォルダがなければ
		
	}
	
	/**
	 * ファイルを受け取り、そのファイルが下層フォルダを含むか返す
	 * @param file ファイル
	 * @return フォルダを含んでいたらtrueを返す
	 */
	boolean isHaveDirectory(File file) {
		boolean isHaveDirectory = false;
		File[] listFiles = file.listFiles();
		for(File tempFile: listFiles) {
			if(file.isDirectory()) {
				isHaveDirectory = true;
			}
		}
		return isHaveDirectory;
	}
}
