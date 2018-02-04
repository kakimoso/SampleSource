package editFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CommentEraser {

	// デフォルトの起点パス
	private static String PATH = "";
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

		// 処理対象とする拡張子を選択するゾーン -------------------------------------

		messageFrame("編集対象とするファイルの拡張子は現在 " + TARGET_EXTENSION + "に設定されています。");
		messageFrame("変更しますか？ y / n");

		String changeInput = "";
		boolean extensionChangeFlg = false;

		while (true) {
			messageFrame("入力待機中");
			changeInput = scanner.nextLine();
			if (changeInput != null && changeInput.isEmpty()) {
				if (changeInput.equals("y")) {
					extensionChangeFlg = true;
					break;
				} else if (changeInput.equals("n")) {
					break;
				} else {
					messageFrame("入力値が不正です。");
				}
			} else {
				messageFrame("入力してください。");
			}
		}

		// 拡張子変更時のみ条件分岐・定数編集
		if (extensionChangeFlg) {
			// （定数で指定したディレクトリ内の）処理対象フォルダを指定する
			messageFrame("編集対象とするファイルの拡張子を入力してください。");
			String targetExtension = "";
			// 入力されるまで抜けられません
			while (true) {
				messageFrame("入力待機中");
				targetExtension = scanner.nextLine();
				if (targetExtension != null && !targetExtension.isEmpty()) {
					break;
				} else {
					messageFrame("入力してください。");
				}
			}

			TARGET_EXTENSION = targetExtension;
		}

		// 拡張子選択ゾーン終了 ------------------------------------------------

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
					eraseJavaDoc(fileList);
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

	/**
	 * java docの記述を削除し、起点パス内のoutputフォルダに出力する
	 * 
	 * @param fileList
	 *            java docを削除したいファイルの配列
	 */
	private static void eraseJavaDoc(File[] fileList) {
		for (File file : fileList) {
			if (file.isFile()) {
				// ファイルの拡張子が対象でなかった場合スキップする
				String fileName = file.getName();
				String[] splittedFileName = fileName.split(Pattern.quote("."));
				String extension = splittedFileName[1];
				if(!extension.equals(TARGET_EXTENSION.substring(1))) {
					continue;
				}
				try {
					// ファイル入出力の用意
					BufferedReader br = new BufferedReader(new FileReader(file));
					BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_PATH + file.getName()));

					// その行の出力を飛ばすフラグ
					boolean skipFlg = false;

					// 一行目を入力
					String content = br.readLine();

					// 入力行がnullになるまで繰り返す
					while (content != null) {

						// java docの開始時にskipFlgをtrueにする
						if (content.contains("/**")) {
							skipFlg = true;
						}

						// skipFlgがfalseなら出力する
						if (!skipFlg) {
							System.out.println(content);
							bw.write(content);
							bw.newLine();
						}

						// java docの終了時にskipFlgをfalseにする
						if (content.contains("*/")) {
							skipFlg = false;
						}

						// 次の行の入力
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
