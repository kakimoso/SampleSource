package editFile;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 入力されたものが.xxxの形式かチェックする正規表現のテスト
 * @author user 私だ
 *
 */
public class test {
	public static void main(String[] args) {
		System.out.println("処理開始");
		Scanner scanner = new Scanner(System.in);
		System.out.println("入力待機中");
		String test = scanner.nextLine();
		if (test.matches(Pattern.quote(".") + "[a-z]+")) {
			System.out.println("マッチ");
		} else {
			System.out.println("アンマッチ");
		}
		System.out.println("処理終了");
	}
}
