package view;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import controller.PlayerController;
import controller.TeamController;
import controller.VoteController;
import controller.gameResultController;
import controller.playerStatController;
import model.GameResultVO;

public class SoccerMenu {
	static Scanner sc = new Scanner(System.in);
	PlayerController pcon = new PlayerController();
//	VoteController vcon = new VoteController();

	public void soccerMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("---------------------------------------");
			System.out.printf("%s\t1. 선수\t\t%15s\n", "|", "|");
			System.out.printf("%s\t2. 선수스탯\t%15s\n", "|", "|");
			System.out.printf("%s\t3. 구단\t\t%15s\n", "|", "|");
			System.out.printf("%s\t4. 경기일정 및 결과\t%15s\n", "|", "|");
			System.out.printf("%s\t5. 팬 투표\t%15s\n", "|", "|");
			System.out.println("---------------------------------------");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
			switch (sc.nextInt()) {

			case 1:
				playerMenu();
				break;
			case 2:
				playerStat();
				break;
			case 3:
				teamMenu();
				break;
			case 4:
				gameresultMenu();
				break;
			case 5:
				voteMenu();
				break;
			default:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			}
		}

	}

	public static void voteMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("\n---------------------------------------");
			System.out.printf("%s\t1.투표 진행 보기\t%15s\n", "|", "|");
			System.out.printf("%s\t2.경기 결과 보기\t%15s\n", "|", "|");
			System.out.printf("%s\t3.투표 하기\t%15s\n", "|", "|");
			System.out.printf("%s\t4.경기 추가하기\t%15s\n", "|", "|");
			System.out.printf("%s\t8.메인메뉴로 돌아가기\t%15s\n", "|", "|");
			System.out.println("---------------------------------------");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");

			switch (sc.nextInt()) {
			case 1:
				gameResultController.selectAll();
				VoteController.selectByGno();
				break;
			case 2:
				gameResultController.selectAll();
				VoteController.selectByGnoEnd();
				break;
			case 3:
				gameResultController.selectAll();
				VoteController.update();
				break;
			case 4:
				VoteController.insert();
				break;
			case 8:
				return;
			}
		}

	}// end menu

	public static void teamMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("\n---------------------------------------");
			System.out.printf("%s\t1.전체 보기\t%15s\n", "|", "|");
			System.out.printf("%s\t2.특정 팀 보기\t%15s\n", "|", "|");
			System.out.printf("%s\t3.팀 정보 수정\t%15s\n", "|", "|");
			System.out.printf("%s\t4.팀 정보 갱신\t%15s\n", "|", "|");
			System.out.printf("%s\t8.메인메뉴로 돌아가기\t%15s\n", "|", "|");
			System.out.println("---------------------------------------");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
			switch (sc.nextInt()) {
			case 1:
				TeamController.selectAll(1);
				break;
			case 2:
				TeamController.selectByTname();
				break;
			case 3:
				TeamController.update();
				break;
			case 4:
				TeamController.updateOnegame();
				break;
			case 8:
				return;
			}
		}

	}// end menu
	
	
	// *//menu class
		public static void gameresultMenu() throws SQLException {

			while (true) {
				System.out.println("\n---------------------------------------");
				System.out.printf("%s\t1.일정 조회 [사용자]\t%15s\n", "|", "|");
				System.out.printf("%s\t2.경기 결과 등록 [관리자]\t%7s\n", "|", "|");
				System.out.printf("%s\t3.경기 일정 변경 [관리자]\t%7s\n", "|", "|");
				System.out.printf("%s\t4.경기 일정 추가[관리자]\t%7s\n", "|", "|");
				System.out.printf("%s\t8.메인메뉴로 돌아가기\t%15s\n", "|", "|");
				System.out.println("---------------------------------------");
				System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
				switch (sc.nextInt()) {
				case 1:
					gameSelectMenu();
					break; // (
				case 2:
					gameResultController.updateResult();
					break;
				case 3:
					gameResultController.updateDate();
					break;
				case 4:
					gameResultController.insertGame();
					break;
				case 8:
					return;

				}// switch
			} // end while
		}// menu end

		public static void gameSelectMenu() throws SQLException {

			
			while (true) {
				System.out.println("\n---------------------------------------");
				System.out.printf("%s\t1.모든 경기 일정\t%15s\n", "|", "|");
				System.out.printf("%s\t2.팀 일정 조회\t%15s\n", "|", "|");
				System.out.printf("%s\t3.월별 일정 조회\t%15s\n", "|", "|");
				System.out.printf("%s\t8.경기메뉴로 돌아가기\t%15s\n", "|", "|");
				System.out.println("---------------------------------------");
				System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
				switch (sc.nextInt()) {

				case 1:
					gameResultController.selectAll();
					break;
				case 2:
					gameResultController.selectTeam();
					break;
				case 3:
					gameResultController.selectMonth();
					break;
				case 8:
					return;
				}// switch
			} // end while
		}// menu end
		

		public static void playerMenuList() {
			System.out.println("\n---------------------------------------");
			System.out.printf("%s\t1.선수 리스트\t%15s\n", "|", "|");
			System.out.printf("%s\t2.선수 정보 추가\t%15s\n", "|", "|");
			System.out.printf("%s\t3.선수 정보 수정\t%15s\n", "|", "|");
			System.out.printf("%s\t4.선수 정보 삭제\t%15s\n", "|", "|");
			System.out.printf("%s\t5.특정 조건 검색\t%15s\n", "|", "|");
			System.out.printf("%s\t8.경기메뉴로 돌아가기\t%15s\n", "|", "|");
			System.out.println("---------------------------------------");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
		}
		public void playerMenu() throws SQLException {
			
			while(true) {
				System.out.println();
				playerMenuList();
				switch(sc.nextInt()) {
				case 1: 
					pcon.selectAll();
					break;
				case 2:
					pcon.insertPlayer();
					break;
				case 3:
					pcon.updatePlayer();
					break;
				case 4:
					pcon.deletePlayer();
					break;
				case 5:
					selectOption();
					break;
				default:
					return;
				}
			}
		}
		
		public void playerSelectMenu() {
			System.out.println("\n---------------------------------------");
			System.out.printf("%s\t1.특정 팀에 소속된 선수목록\t%7s\n", "|", "|");
			System.out.printf("%s\t2.특정 포지션에 속한 선수목록\t%7s\n", "|", "|");
			System.out.printf("%s\t3.특정 나이대에 속한 선수목록\t%7s\n", "|", "|");
			System.out.printf("%s\t4.선수메뉴로 돌아가기\t%15s\n", "|", "|");
			System.out.printf("%s\t8.경기메뉴로 돌아가기\t%15s\n", "|", "|");
			System.out.println("---------------------------------------");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
		}
		
		public void selectOption() {
			try {
				while(true) {
					playerSelectMenu();
					
					switch (sc.nextInt()){
					case 1:  
						pcon.searchByTcode();
						break;
					case 2:
						pcon.searchByPosition();
						break;
					case 3:
						pcon.searchByAge();
						break;
					case 8:
						return;
					}
				}
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		
		public static void playerStatMenu() {
			System.out.println("\n---------------------------------------");
			System.out.printf("%s\t1.선수 스탯 리스트\t%15s\n", "|", "|");
			System.out.printf("%s\t2.선수 스탯 업데이트\t%15s\n", "|", "|");
			System.out.printf("%s\t3.선수 스탯 리스트 삭제\t%7s\n", "|", "|");
			System.out.printf("%s\t4.선수 어시스트 순위\t%15s\n", "|", "|");
			System.out.printf("%s\t5.경기당 득점률 순위\t%15s\n", "|", "|");
			System.out.printf("%s\t6.경기당 어시스트율 순위\t%7s\n", "|", "|");
			
			System.out.printf("%s\t7.경기당 슈팅수 순위\t%7s\n", "|", "|");
			System.out.printf("%s\t8.경기당 유효슈팅 순위\t%7s\n", "|", "|");
			System.out.printf("%s\t9.경기당 파울 순위\t%7s\n", "|", "|");
			System.out.printf("%s\t10.경기당 옐로우 순위\t%7s\n", "|", "|");
			System.out.printf("%s\t11.경기당 레드카드 순위\t%7s\n", "|", "|");
			System.out.printf("%s\t12.경기당 오프사이드 순위\t%7s\n", "|", "|");
			
			System.out.printf("%s\t13.메인메뉴로 돌아가기\t%15s\n", "|", "|");
			System.out.println("---------------------------------------");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
		}
		
		public static void playerStat() throws SQLException{
			
//			PlayerStatVO vo = new PlayerStatVO(0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			
			
			while(true) {
				playerStatMenu();
				
				switch(sc.nextInt()) {
					
					case 1: playerStatController.selectAll(); break;	
					case 2: playerStatController.playerStatUpdate(); break;
					case 3: playerStatController.delete(); break;
					case 4: playerStatController.selectAllAssists(); break;
					case 5: playerStatController.selectAllByGoalsPerGame(); break;
					case 6: playerStatController.selectAllByAssistsPerGame(); break;
					
					
					case 8: return;
					
					

				}
			}// end while
		}
		
		

}
