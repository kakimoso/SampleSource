package javaSilever;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;

public class LocalDateTutorial {
	public static void main(String[] args) {
		
		// yyyy, mm, dd で初期化
		LocalDate ld1 = LocalDate.of(2017, 12, 07);
		
		// yyyy, Month(月を示す列挙型), dd で初期化
		LocalDate ld2 = LocalDate.of(2017, Month.DECEMBER, 8);
		
		// now()メソッドで現在時刻を取得
		LocalDate ld3 = LocalDate.now();
		
		// parse(String date)メソッドで文字列から日付を取得
		LocalDate ld4 = LocalDate.parse("2017-12-07");
		
		System.out.println("ld1 : " + ld1);
		System.out.println("ld2 : " + ld2);
		System.out.println("ld3 : " + ld3);
		System.out.println("ld4 : " + ld4);
		
		Period between = Period.between(ld1, ld2);
		Period until = ld1.until(ld2);
		System.out.println(between.getDays());
		System.out.println(until.getDays());
		
	}
}
