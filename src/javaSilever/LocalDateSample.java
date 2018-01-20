package javaSilever;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class LocalDateSample {
	public static void main(String[] args) {
		
		
		System.out.println("2017, 12, 32 で初期化してみる");
		// yyyy, mm, dd で初期化
		LocalDate yyyymmdd = LocalDate.of(2017, 12, 32);
		
		// yyyy, Month(月を示す列挙型), dd で初期化
		LocalDate yyyyMonthdd = LocalDate.of(2018, Month.JANUARY, 16);
		
		// now()メソッドで現在時刻を取得
		LocalDate now = LocalDate.now();
		
		// parse(String date)メソッドで文字列から日付を取得
		LocalDate strDate = LocalDate.parse("2018-12-11");
		
		// 内容の確認
		System.out.println("yyyymmdd : " + yyyymmdd); // 2017-12-11
		System.out.println("yyyyMonthdd : " + yyyyMonthdd); // 2018-01-16
		System.out.println("now : " + now); // 2017-12-11(実行時の日付)
		System.out.println("strDate : " + strDate); // 2018-12-11
		
		
		System.out.println("\r\n----- 加工確認 -----\r\n");
		
		// 加減算・加工

		// 各単位ごとにメソッドが用意されている
		LocalDate tempDate = yyyymmdd.plusDays(10);
		LocalDate tempDate2 = yyyymmdd.plus(10, ChronoUnit.DAYS);
		LocalDate tempMonth = yyyymmdd.minusMonths(1);
		LocalDate tempWeek = yyyymmdd.plusWeeks(1);
		LocalDate tempYear = yyyymmdd.minusYears(1);
		
		System.out.println("yyyymmdd : " + yyyymmdd);     // 2017-12-11
		System.out.println("tempDate : " + tempDate);     // 2017-12-21
		System.out.println("tempDate2 : " + tempDate2);   // 2017-12-21
		System.out.println("tempMonth : " + tempMonth);   // 2017-11-11
		System.out.println("tempWeek : " + tempWeek);     // 2017-12-18
		System.out.println("tempYear : " + tempYear);     // 2016-12-11	

		
		// 変換のフォーマットを作成
		DateTimeFormatter sampleFormatO = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		DateTimeFormatter sampleFormatF = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		DateTimeFormatter sampleFormatS = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		DateTimeFormatter sampleFormatA = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("en", "US"));
		
		// 変換し変数に格納
		String formattedDateBasic = yyyymmdd.format(DateTimeFormatter.BASIC_ISO_DATE);
		String formattedDate1 = yyyymmdd.format(sampleFormatO);
		String formattedDate2 = yyyymmdd.format(sampleFormatF);
		String formattedDate3 = yyyymmdd.format(sampleFormatS);
		String formattedDate4 = yyyymmdd.format(sampleFormatA);
		
		// 内容の確認
		System.out.println(formattedDateBasic);
		System.out.println(formattedDate1);
		System.out.println(formattedDate2);
		System.out.println(formattedDate3);
		System.out.println(formattedDate4);
		
		System.out.println("\r\n----- 差を求める -----\r\n");
		
		// 日付の差を求める
		
		// Periodクラスのbetweenメソッド
		Period between = Period.between(yyyymmdd, yyyyMonthdd);
		
		// LocalDateクラスのuntilメソッド
		Period until = yyyymmdd.until(yyyyMonthdd);
		
		// betweenもuntilも同じ結果
		System.out.println("between : " + between.getDays() + " , until : " + until.getDays()); // 5
		System.out.println("between : " + between.getMonths() + " , until : " + until.getMonths()); // 1
		
		// 年の差は365日を区切りに出力される
		Period between2 = Period.between(yyyymmdd, strDate); // between2に2017-12-11 と 2018-12-11 の差を代入		
		System.out.println(
				"between : " + between.getYears() + " , between2 : " + between2.getYears()
				);	// 0, 1

	}
}
