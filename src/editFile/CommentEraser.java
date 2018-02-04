package editFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommentEraser {

	// デフォルトの起点パス
	private static String PATH = "C:\\Users\\user\\Desktop\\sacrifice\\";
	// 出力パス
	private static String OUTPUT_PATH = "";
	// 処理対象
	private static String TARGET_EXTENSION = ".txt";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// 起点パス編集ゾーン -------------------------------------------

		// コマンドライン引数にパスが指定されていれば、起点パスをそれに変更する。なければデフォルトのパスを起点とする
		if (args.length > 0) {
			PATH = args[0];
		}

		// 起点パス編集ゾーン終了 ----------------------------------------

		// 階層が大丈夫そうか判断するゾーン ---------------------------------

		// "\"が５コ以上ないと怖いのでだめです。
		int directoryCount = 0;
		for (int i = 0; i < PATH.length(); i++) {
			char ch = PATH.charAt(i);
			if (ch == '\\') {
				directoryCount++;
			}
		}

		if (directoryCount < 5) {
			throw new RuntimeException("階層が上すぎて怖いです。定数のパスには\\を５コ以上含むディレクトリを指定してください。");
		}

		// 階層判断ゾーン終了 -------------------------------------------

		// （定数で指定したディレクトリ内の）処理対象フォルダを指定する
		messageFrame("指定済みパス内で処理対象とするディレクトリを入力してください。");
		String targetDirectory = "";
		// 入力されるまで抜けられません
		while (true) {
			messageFrame("入力待機中");
			targetDirectory = scanner.nextLine();
			if (targetDirectory != null && !targetDirectory.isEmpty()) {
				break;
			} else {
				messageFrame("入力してください。");
			}
		}

		String firstTarget = PATH + targetDirectory;

		// 指定パス内にoutputディレクトリを作成する ---------------------------

		File outputPath = new File(firstTarget + "\\output");
		boolean directoryExistsFlg = false;
		if (!outputPath.exists()) {
			directoryExistsFlg = outputPath.mkdir();
		} else {
			directoryExistsFlg = true;
		}

		if (!directoryExistsFlg) {
			throw new RuntimeException();
		} else {
			OUTPUT_PATH = outputPath.toString();
		}

		// 指定パス内にoutputディレクトリ作成完了

		messageFrame(firstTarget + "を対象として処理を行います。よろしいですか？");
		messageFrame("do:y / skip:n / exit:e");

		File directory = new File(firstTarget);
		File[] fileList = directory.listFiles();
		// フォルダのリストをつくる
		List<File> directoryList = new ArrayList<File>();
		for (File file : fileList) {
			if (file.isDirectory()) {
				directoryList.add(file);
			}
		}

		while (true) {
			messageFrame("入力待機中");
			String comfirm = scanner.nextLine();
			if (comfirm != null && !comfirm.isEmpty()) {
				if (comfirm.equals("y")) {
					// 初期ディレクトリにあるファイルを編集しoutputフォルダに出力する

					break;
				} else if (comfirm.equals("e")) {
					messageFrame("処理を終了します。");
					System.exit(0);
				} else if (comfirm.equals("n")) {
					break;
				} else {
					messageFrame("入力値が不正です。");
					messageFrame("do:y / exit:n");
				}
			} else {
				messageFrame("入力してください");
			}
		}
	}

	private static void eraseJavaDoc(File[] fileList) {
		for (File file : fileList) {
			if (file.isFile()) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PATH + file.getName()));

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
			}
		}
	}

	/**
	 * メッセージフレーム。メッセージを表示するだけ
	 * 
	 * @param message
	 *            表示するメッセージ
	 */
	private static void messageFrame(String message) {
		System.out.println("[CommentEditor] : " + message);
	}
}
