package lambda;

/**
 * メインクラス。処理の実装と実行を行う。
 */
public class ExecuteClass {
	public static void main(String[] args) {
		// 引数の( )を省略
		LambdaInterface sample = str -> {
			return "Hello, " + str;
		};

		// 出力用メッセージを用意します
		String message = "Lambda!";

		// 実行します。
		sample.doMethod(message); // Hello, Lambda!
	}
}
