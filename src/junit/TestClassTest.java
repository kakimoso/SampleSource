package junit;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class TestClassTest {
	
	private static final int YMD = 0; // 年月日 
	private static final int HHMM = 1; // 時刻

	@Test
	void testReturnTime() {
		// 実測値を取得
		TestClass testClass = new TestClass();
		String actual = testClass.returnTime(YMD);
		// 期待値を入力
		String expected = "2018年01月03日";
		// JUnitがもつメソッド assertEquals
		assertEquals(expected, actual);
	}

	@Test
	void testDisplayYear() {
		// 正常に動作しなかった場合は障害トレースを表示する
		try {
			TestClass testClass = new TestClass();
			testClass.displayYear();
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
}
