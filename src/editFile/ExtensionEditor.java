package editFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * <p>
 * 入力したディレクトリ内のファイルの拡張子を軒並み変える恐ろしいプログラム
 * </p>
 * <p>
 * 怖すぎるから入力パスの一個下のディレクトリ内までしか見ない
 * </p>
 * <p>
 * コマンドライン引数で起点となるパスを指定できる
 * </p>
 */
public class ExtensionEditor {

	// デフォルトの起点パス
	private static String PATH = "";
	// 処理対象拡張子
	private static String TARGET_EXTENSION = ".txt";
	// 変更先拡張子
	private static String DIST_EXTENSION = ".doc";
	// 拡張子変更関数の処理分岐定数
	private static final int TARG_EXT_FLG = 1;
	private static final int DIST_EXT_FLG = 2;

	public static void main(String[] args) {
		try {
			int outCount = 0;

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

			Scanner scanner = new Scanner(System.in);
			messageFrame("処理開始");
			messageFrame("起点パス " + PATH);
			messageFrame("処理対象拡張子" + TARGET_EXTENSION);
			messageFrame("変更先拡張子 " + DIST_EXTENSION);

			// 拡張子を指定するか聞くゾーン ------------------------------------

			changeConfig(TARG_EXT_FLG, scanner);
			changeConfig(DIST_EXT_FLG, scanner);

			// 拡張子ゾーン終了 ---------------------------------------------

			// ファイル処理 -------------------------------------------------

			// ファイルを扱うのはFileクラス。File（String path）でパスを指定するとそいつを扱えるFileインスタンスができる

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

			// 実行確認

			messageFrame("設定確認 --- \" " + TARGET_EXTENSION + " \" を \" " + DIST_EXTENSION + " \" に変更します。");

			messageFrame(firstTarget + "を対象として処理を行います。よろしいですか？");
			messageFrame("do:y / skip:n / exit:e");

			File directory = new File(PATH + targetDirectory);
			// Fileクラスのインスタンスメソッド listFiles は、そのインスタンスがもつ全ファイルをFileインスタンスとして配列に格納する
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
						// 初期ディレクトリにあるファイルの拡張子を変更する
						outCount += changeExtension(fileList, TARGET_EXTENSION);
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

			// 初期ディレクトリ内のフォルダ内のファイルの拡張子を変更する
			directoriesLoop: for (File file : directoryList) {
				messageFrame(file.getAbsolutePath() + "を対象として処理を行います。よろしいですか？");
				messageFrame("do:y / skip:n / exit:e");

				while (true) {
					messageFrame("入力待機中");
					String comfirm = scanner.nextLine();
					if (comfirm != null && !comfirm.isEmpty()) {
						if (comfirm.equals("y")) {
							break;
						} else if (comfirm.equals("n")) {
							// そのディレクトリを飛ばす。次のループへ
							continue directoriesLoop;
						} else if (comfirm.equals("e")) {
							break directoriesLoop;
						} else {
							messageFrame("入力値が不正です。");
							messageFrame("do:y / skip:n / exit:e");
						}
					} else {
						messageFrame("入力してください");
					}
				}
				File[] tempFileList = file.listFiles();
				outCount += changeExtension(tempFileList, TARGET_EXTENSION);
			}

			messageFrame("処理件数 : " + outCount);
		} catch (Exception e) {
			e.printStackTrace();
			messageFrame("異常終了します。最初からやり直してください。");
		} finally {
			messageFrame("処理を終了します。");
		}

	}

	/**
	 * ディレクトリ内のファイルのうち、指定された拡張子のファイルの拡張子を指定されたものに変更する
	 * 
	 * @param fileList
	 *            処理するディレクトリ.listFiles()したもの
	 * @param targetExtension
	 *            処理対象拡張子
	 * @return count 拡張子を変更した件数
	 */
	private static int changeExtension(File[] fileList, String targetExtension) {
		int count = 0;
		for (File file : fileList) {
			if (file.isFile()) {
				String fileName = file.getName();
				String originalPath = file.getParent() + "\\";
				String[] splittedFileName = fileName.split(Pattern.quote("."));
				// ファイルのうち、引数で受け取った処理対象拡張子に合致するもののみ変更する
				if (splittedFileName[1].equals(targetExtension.substring(1))) {
					count++;
					String simpleFileName = splittedFileName[0];
					simpleFileName += DIST_EXTENSION;
					String newFilePath = originalPath + simpleFileName;
					File newFile = new File(newFilePath);
					file.renameTo(newFile);
				}
			}
		}
		return count;
	}

	/**
	 * 処理する拡張子の設定を変更する
	 * 
	 * @param targetFlug
	 *            変更する設定を選択する<br>
	 *            1:処理対象拡張子<br>
	 *            2:変更先拡張子
	 * @param scanner
	 *            スキャナー
	 */
	private static void changeConfig(int targetFlug, Scanner scanner) {
		String checkExt = "";

		switch (targetFlug) {
		case TARG_EXT_FLG:
			while (true) {
				messageFrame("処理の対象とする拡張子を変更しますか？ y/n");
				String changeYN = scanner.nextLine();
				if (changeYN != null && !changeYN.isEmpty()) {
					if (changeYN.equals("y")) {
						messageFrame(".xxx の形式で入力してください。");
						TARGET_EXTENSION = scanner.nextLine();
						break;
					} else if (changeYN.equals("n")) {
						break;
					} else {
						messageFrame("入力値が不正です。");
					}
				} else {
					messageFrame("入力してください。");
				}
			}
			checkExt = TARGET_EXTENSION;
			break;
		case DIST_EXT_FLG:
			while (true) {
				messageFrame("変更先の拡張子を変更しますか？ y/n");
				String changeYN = scanner.nextLine();
				if (changeYN != null && !changeYN.isEmpty()) {
					if (changeYN.equals("y")) {
						messageFrame(".xxx の形式で入力してください。");
						DIST_EXTENSION = scanner.nextLine();
						break;
					} else if (changeYN.equals("n")) {
						break;
					} else {
						messageFrame("入力値が不正です。");
					}
				} else {
					messageFrame("入力してください。");
				}
			}
			checkExt = DIST_EXTENSION;
			break;
		}

		// 拡張子が.xxxの形式か判断する。そうでなければ例外を投げる。
		if (!checkExt.matches(Pattern.quote(".") + "[a-zA-Z]+")) {
			throw new RuntimeException("入力された拡張子が不正です。");
		} else {
			messageFrame("現在 " + checkExt + "に設定されています。");
		}
	}

	/**
	 * メッセージフレーム。メッセージを表示するだけ
	 * 
	 * @param message
	 *            表示するメッセージ
	 */
	private static void messageFrame(String message) {
		System.out.println("[ExtensionEditor] : " + message);
	}
}
