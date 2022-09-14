import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc= new Scanner(System.in);
		int cmd = sc.nextInt();
		
		while(cmd > 5) {
			System.out.println("5 이하의 숫자만 입력해주세요.");
			
			cmd = sc.nextInt();
		}
		
		sc.close();
		
		System.out.println("입력된 명령어 : " + cmd);
		
		System.out.println("== 프로그램 끝 ==");
	}
}
