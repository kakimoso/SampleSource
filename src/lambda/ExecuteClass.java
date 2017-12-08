package lambda;

/**
 *　メインクラス。処理の実装と実行を行う。
 */
public class ExecuteClass {
	public static void main(String[] args) {
		// メッセージを用意します
		String str = "Lambda!";
		// ラムダ式。ロジックを記述します。
		LambdaInterface lambda = (String message) -> {
			System.out.println("Hello," + message);
		};
		// ロジック実行クラスのインスタンスを生成
		LambdaClass lc = new LambdaClass();
		// ロジックを実装したインスタンスをロジック実行クラスのセッタでセット
		lc.setLambda(lambda);
		
		// 実行
		lc.helloLambda(str);
	}
}
