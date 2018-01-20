package junit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

public class TestClass {
	
	private static final int HHMM = 0; // 時刻
	private static final int YMD = 1; // 年月日
	
	/**
	 * 現在時刻を引数に応じて加工し返します。
	 * @param editFlug 0 : 年月日をyyyy年MM月dd日の形式で加工します。<br>1 : 時刻をHH:mmの形式で加工します。
	 * @return 現在時刻を引数に応じた形式で加工した文字列
	 */
	public String returnTime(int editFlug) {
		String result = "";
		LocalDateTime ldt = LocalDateTime.now();
		// 引数が年月日なら
		if(editFlug == YMD) {
			DateTimeFormatter ymdFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
			result = ldt.format(ymdFormatter);
		} else if(editFlug == HHMM) {
			DateTimeFormatter hhmmFormatter = DateTimeFormatter.ofPattern("HH:mm");
			result = ldt.format(hhmmFormatter);
		}
		return result;
	}
	
	/**
	 * 現在の時を表示します。
	 */
	public void displayYear() {
		LocalDate ld = LocalDate.now();
		System.out.println(ld.get(ChronoField.HOUR_OF_DAY));
	}
}
