package lambda;

/**
 * 
 *
 */
public class LambdaClass {
	public LambdaInterface lambda;

	/**
	 * ロジックを定義したLambdaInterfaceインスタンスのセッタ
	 * @param lambda 今回はラムダ式でロジックを定義し、ここに渡します。
	 */
	public void setLambda(LambdaInterface lambda) {
		this.lambda = lambda;
	}

	/**
	 * メイン処理で定義された処理を実行するメソッド
	 * @param str 今回は System.out.println("Hello, " + str); という処理をメインで実装していますので、表示する文字列をこのメソッドに渡しています。
	 */
	public void helloLambda(String str) {
		this.lambda.lambdaLogic(str);
	}
}
