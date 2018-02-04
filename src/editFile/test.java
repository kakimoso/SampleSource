package editFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;


/**
 * 入力されたものが.xxxの形式かチェックする正規表現のテスト
 * 
 * @author user 私だ
 *
 */
public class test {
	public static void main(String[] args) {
		System.out.println("処理開始");
		Scanner scanner = new Scanner(System.in);

		try {
			File inputFile = new File("");
			File outputDirectory = new File("");
			File outputFile = null;
			boolean directoryExistsFlg = false;
			
			if (!outputDirectory.exists()) {
				outputDirectory.mkdirs();
				directoryExistsFlg = true;
			} else {
				directoryExistsFlg = true;
			}
			
			if (directoryExistsFlg) {
				outputFile = new File(outputDirectory + "\\" + "AbstractCollection_out.txt");
			} else {
				throw new RuntimeException();
			}
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

			boolean skipFlg = false;

			String content = br.readLine();

			while (content != null) {

				if (content.contains("/**")) {
					skipFlg = true;
				}

				if (!skipFlg) {
					System.out.println(content);
					bw.write(content);
					bw.newLine();
				}

				if (content.contains("*/")) {
					skipFlg = false;
				}

				content = br.readLine();
			}

			br.close();
			bw.close();
		} catch (Exception e) {
			System.out.println("なんかエラー");
		}

		System.out.println("処理終了");
	}
}
