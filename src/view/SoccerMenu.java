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
	PlayerController pcon = new PlayerController();
//	VoteController vcon = new VoteController();

	public void soccerMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("---------------- 메인메뉴 ----------------");
			System.out.printf("|%-35s|\n","1. 선수　　 　　");
			System.out.printf("|%-35s|\n","2. 선수 스탯　　");
			System.out.printf("|%-35s|\n","3. 구단　　 　　");
			System.out.printf("|%-35s|\n","4. 경기 및 결과　");
			System.out.printf("|%-35s|\n","5. 팬 투표　　　");
			System.out.printf("|%-35s|\n","8. 프로그램 종료");
			System.out.println("------------------------------------------");
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

			System.out.println("-----------------투표메뉴-----------------");
			System.out.printf("|%-35s|\n","1. 투표 진행 보기");
			System.out.printf("|%-35s|\n","2. 경기 결과 보기");
			System.out.printf("|%-35s|\n","3. 투표 하기　　 ");
			System.out.printf("|%-35s|\n","4. 승부예측 추가");
			System.out.printf("|%-35s|\n","8. 프로그램 종료");
			System.out.println("------------------------------------------");
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
			System.out.println("-----------------팀메뉴-------------------");
			System.out.printf("|%-32s|\n","1. 전체 보기　　　　　");
			System.out.printf("|%-32s|\n","2. 특정 팀 보기　　　　");
			System.out.printf("|%-32s|\n","3. 팀 정보 수정　　　　");
			System.out.printf("|%-32s|\n","4. 경기결과 입력　　　");
			System.out.printf("|%-32s|\n","8. 메인메뉴로 돌아가기");
			System.out.println("------------------------------------------");
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
			System.out.printf("%s\t2.팀별 경기 일정 \t%15s\n", "|", "|");
			System.out.printf("%s\t3.월별 경기 일정 \t%15s\n", "|", "|");
			System.out.printf("%s\t4.미진행 경기 일정 \t%15s\n", "|", "|");
			System.out.printf("%s\t5.라운드별 경기 일정\t%15s\n", "|", "|");
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
			case 4:
				gameResultController.selectWillplay(); // 미진행 경기 일정
				break;
			case 5:
				gameResultController.selectRound();
				break;
			case 8:
				return;
			}// switch
		} // end while
	}// menu end
	

		public static void playerMenuList() {
			System.out.println("---------------- 선수메뉴 ----------------");
			System.out.printf("|%-32s|\n","1. 선수 리스트　　　　");
			System.out.printf("|%-32s|\n","2. 선수 정보 추가　　　");
			System.out.printf("|%-32s|\n","3. 선수 정보 수정　　　");
			System.out.printf("|%-32s|\n","4. 선수 정보 삭제　　　");
			System.out.printf("|%-32s|\n","5. 특정 조건 검색　　　");
			System.out.printf("|%-32s|\n","8. 메인메뉴로 돌아가기");
			System.out.println("------------------------------------------");
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
			System.out.println("------------ 특정조건선수검색 ------------");
			System.out.printf("|%-29s|\n","1. 특정 팀에 소속된 선수목록　");
			System.out.printf("|%-29s|\n","2. 특정 포지션에 속한 선수목록");
			System.out.printf("|%-29s|\n","3. 특정 나이대에 속한 선수목록");
			System.out.printf("|%-29s|\n","4. 선수메뉴로 돌아가기 　　　");
			System.out.printf("|%-29s|\n","8. 메인메뉴로 돌아가기 　　　");
			System.out.println("------------------------------------------");
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
					case 4:
						return;
					case 8:
						soccerMenu();
					}
				}
			} catch (Exception e) { e.printStackTrace(); }
		}
		
		
		public static void plyaerStatMenu() {
			System.out.println("------------------------------------------");
			System.out.printf("|%-29s|\n","1.선수 스탯 리스트　　　　　 ");
			System.out.printf("|%-29s|\n","2.선수 어시스트 순위　　　　");
			System.out.printf("|%-29s|\n","3. 특정 나이대에 속한 선수목록");
			System.out.printf("|%-29s|\n","4.선수 스탯 리스트 삭제　　　");
			System.out.printf("|%-29s|\n","5.경기당 득점률 순위　　　　");
			System.out.printf("|%-29s|\n","6.경기당 어시스트율 순위　　");
			System.out.printf("|%-32s|\n","8. 메인메뉴로 돌아가기");
			System.out.println("------------------------------------------");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
		}
		
		public static void playerStat() throws SQLException{
			
//			PlayerStatVO vo = new PlayerStatVO(0, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0);
			
			
			while(true) {
				plyaerStatMenu();
				
				switch(sc.nextInt()) {
					
					case 1: playerStatController.selectAll(); break;		 
					case 2: playerStatController.selectAllAssists(); break;
//					case 3: update(); break;	 
					case 4: playerStatController.delete(); break;
					case 5: playerStatController.selectAllByGoalsPerGame(); break;
					case 6: playerStatController.selectAllByAssistsPerGame(); break;
					case 8: return;

				}
			}// end while
		}
		
		

}
