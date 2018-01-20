package editFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>入力したディレクトリ以下全ファイルの拡張子を.txtにする恐ろしいプログラム</p>
 * <p>本当にガチでマジであぶない</p>
 */
public class FileEditor {
	
	// 変えるときは本当に慎重にね。変えないで済むように使おうね。
	private static final String PATH = "C:\\Users\\kimos\\Desktop\\sacrifice\\";
	
	public static void main(String[] args) {
		
		// 階層が大丈夫そうか判断するゾーン
		int directoryCount = 0;
		for(int i = 0; i < PATH.length(); i++) {
			char ch = PATH.charAt(i);
			if(ch == '\\') {
				directoryCount++;
			}
		}
		
		if(directoryCount < 5) {
			throw new RuntimeException("階層が上すぎて怖いです。定数のパスには\\を５コ以上含むディレクトリを指定してください。");
		}
		
		// ファイルを扱うのはFileクラス。File（String path）でパスをしていするとそいつを扱えるFileインスタンスができる
		File directory = new File(PATH + "\\util - コピー");
		// Fileクラスのインスタンスメソッド listFiles は、そのインスタンスがもつ全ファイルをFileインスタンスとして配列に格納する
		File[] fileList = directory.listFiles();
		// ディレクトリのリストをつくる
		List<File> directoryList = new ArrayList<File>();
		for(File file: fileList) {
			if(file.isDirectory()) {
				directoryList.add(file);
			}
		}
		
		// 初期ディレクトリにあるファイルの拡張子を変更する
		changeExtension(fileList);
		
		for(File file: directoryList) {
			File[] tempFileList = file.listFiles();
			changeExtension(tempFileList);
		}
	}
	
	/**
	 * 引数で受け取ったファイル配列内のファイルの拡張子をすべて.txtにする
	 */
	private static void changeExtension(File[] fileList) {
		for(File file: fileList) {
			if(file.isFile()) {
				// ファイル名変更ゾーン
				String fileName = file.getName();
				String originalPath = file.getParent() + "\\";
				String[] splittedFileName = fileName.split(Pattern.quote("."));
				String simpleFileName = splittedFileName[0];
				simpleFileName += ".txt";
				String newFilePath = originalPath + simpleFileName;
				File newFile = new File(newFilePath);
				file.renameTo(newFile);
			}
		}
	}
	
}
