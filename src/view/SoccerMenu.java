package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import controller.PlayerController;
import controller.TeamController;
import controller.VoteController;
import controller.gameResultController;
import controller.playerStatController;

public class SoccerMenu {
	static Scanner sc = new Scanner(System.in);
	
	public void soccerMenu() throws SQLException, IOException {
		
		while(true) {
			System.out.println("\n============= MAIN MENU ===============");
			System.out.println("1. 선수");
			System.out.println("2. 선수스탯");
			System.out.println("3. 구단");
			System.out.println("4. 경기일정 및 결과");
			System.out.println("5. 팬 투표");
			System.out.println("=======================================");
			System.out.print("번호선택 >> ");
			switch(sc.nextInt()) {
			case 1:
				PlayerController pcon = new PlayerController();
				pcon.menu();
				break;
			case 2:
				playerStatController.playerStat();
				break;
			case 3:
				TeamController.menu();
				break;
			case 4:
				gameResultController.gameresultMenu();
				break;
			case 5:
				VoteController.voteMenu();
				break;
			default: System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			}
		}
		
	}
}
