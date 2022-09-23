package com.koreaIT.java.BAM.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	// 현재 날짜와 시간 리턴
	public static String getNowDateStr() {
		// 현재 날짜/시간
		LocalDateTime now = LocalDateTime.now();

		// 포맷팅
		String formatedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

		// 포맷팅 현재 날짜/시간 출력
		return formatedNow;
	}
}