package com.koreaIT.java.BAM;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		Scanner sc= new Scanner(System.in);
		
		while(true) {
			System.out.printf("명령어 : ");
			String cmd = sc.nextLine();
			
			if(cmd.equals("exit")) {
				break;
			} else {
				System.out.println("명령어가 존재하지 않습니다.");
			}
		}
		
		System.out.println("== 프로그램 끝 ==");
		sc.close();
	}
}
