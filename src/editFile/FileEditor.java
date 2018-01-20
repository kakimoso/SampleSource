package editFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * <p>
 * 入力したディレクトリ内のファイルの拡張子を.txtにする恐ろしいプログラム
 * </p>
 * <p>
 * 本当にガチでマジであぶない
 * </p>
 * <p>
 * 怖すぎるから入力パスの一個下のディレクトリ内までしか見ない
 * </p>
 * <p>
 * コマンドライン引数で起点となるパスを指定できる
 * </p>
 */
public class FileEditor {

	// デフォルトの起点パス
	private static String PATH = "";
	// デフォルトの処理対象拡張子
	private static String EXTENSION = "";

	public static void main(String[] args) {
		messageFrame("処理開始");
		messageFrame("起点パス　/　" + PATH);
		messageFrame("処理対象拡張子" + EXTENSION);
		
		int outCount = 0;

		// 起点パス編集ゾーン -------------------------------------------

		// コマンドライン引数にパスが指定されていれば、起点パスをそれに変更する。なければデフォルトのパスを起点とする
		if (args.length > 0) {
			PATH = args[0];
		}

		// 起点パス編集ゾーン終了 ----------------------------------------

		Scanner scanner = new Scanner(System.in);

		// 拡張子を指定するか聞くゾーン ------------------------------------

		// 入力がちゃんとされないと抜け出せない拡張子変更ゾーン
		while (true) {
			messageFrame("拡張子を変更しますか？ y/n");
			String changeYN = scanner.nextLine();
			if (changeYN != null && !changeYN.isEmpty()) {
				if (changeYN.equals("y")) {
					EXTENSION = scanner.nextLine();
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

		// 処理対象拡張子が.xxxの形式か判断する。そうでなければ例外を投げる。
		if (!EXTENSION.matches(Pattern.quote(".") + "[a-z]+")) {
			throw new RuntimeException("入力された拡張子が不正です。");
		}

		// 拡張子ゾーン終了 ---------------------------------------------

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
		
		messageFrame(firstTarget + "を対象として処理を行います。よろしいですか？");
		messageFrame("do:y / exit:n");

		while (true) {
			String comfirm = scanner.nextLine();
			if (comfirm != null && !comfirm.isEmpty()) {
				if (comfirm.equals("y")) {
					break;
				} else {
					System.exit(0);
				}
			} else {
				messageFrame("入力してください");
			}
		}

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

		// 初期ディレクトリにあるファイルの拡張子を変更する
		outCount += changeExtension(fileList, EXTENSION);

		// 初期ディレクトリ内のフォルダ内のファイルの拡張子を変更する
		directoriesLoop : for (File file : directoryList) {
			messageFrame(file.getAbsolutePath() + "を対象として処理を行います。よろしいですか？");
			messageFrame("do:y / skip:n");

			while (true) {
				String comfirm = scanner.nextLine();
				if (comfirm != null && !comfirm.isEmpty()) {
					if (comfirm.equals("y")) {
						break;
					} else {
						// そのディレクトリを飛ばす。次のループへ
						continue directoriesLoop;
					}
				} else {
					messageFrame("入力してください");
				}
			}
			File[] tempFileList = file.listFiles();
			outCount += changeExtension(tempFileList, EXTENSION);
		}
		
		messageFrame("処理件数 : " + outCount);

	}

	/**
	 * ディレクトリ内のファイルのうち、指定された拡張子のファイルの拡張子を.txtに変更する
	 * 
	 * @param fileList
	 * @param targetExtension
	 */
	private static int changeExtension(File[] fileList, String targetExtension) {
		int count = 0;
		for (File file : fileList) {
			if (file.isFile()) {
				String fileName = file.getName();
				String originalPath = file.getParent() + "\\";
				String[] splittedFileName = fileName.split(Pattern.quote("."));
				if (splittedFileName[1].equals(targetExtension.substring(1))) {
					count++;
					String simpleFileName = splittedFileName[0];
					simpleFileName += ".txt";
					String newFilePath = originalPath + simpleFileName;
					File newFile = new File(newFilePath);
					file.renameTo(newFile);
				}
			}
		}
		return count;
	}

	/**
	 * メッセージフレーム。メッセージを表示するだけ
	 * 
	 * @param message
	 *            表示するメッセージ
	 */
	private static void messageFrame(String message) {
		System.out.println("[FileEditor] : " + message);
	}
}
