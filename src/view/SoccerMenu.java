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
	static PlayerController pcon = new PlayerController();

	public static void soccerMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("┌─────────────── 메인 메뉴 ───────────────┐");
			System.out.printf("│%-35s│\n","1. 선수　　 　　");
			System.out.printf("│%-35s│\n","2. 선수 스탯　　");
			System.out.printf("│%-35s│\n","3. 구단　　 　　");
			System.out.printf("│%-34s│\n","4. 경기 일정 및 결과");
			System.out.printf("│%-35s│\n","5. 팬 투표　　　");
			System.out.printf("│%-35s│\n","9. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");
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
			case 9:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
				continue;
			}
		}

	}

	public static void voteMenu() throws SQLException, IOException {

		while (true) {

			System.out.println("┌─────────────── 투표 메뉴 ───────────────┐");
			System.out.printf("│%-35s│\n","1. 투표 진행 보기");
			System.out.printf("│%-35s│\n","2. 투표 결과 보기");
			System.out.printf("│%-35s│\n","3. 투표 하기　　 ");
			System.out.printf("│%-33s│\n","4. 투표 경기 추가　　 ");
			System.out.printf("│%-32s│\n","8. 메인메뉴로 돌아가기");
			System.out.printf("│%-35s│\n","9. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");

			switch (sc.nextInt()) {
			case 1:
				gameResultController.selectWillplay();
				VoteController.selectByGno();
				break;
			case 2:
				gameResultController.selectAll();
				VoteController.selectByGnoEnd();
				break;
			case 3:
				gameResultController.selectWillplay();
				VoteController.update();
				break;
			case 4:
				gameResultController.selectWillplay();
				VoteController.insert(0);
				break;
			case 8:
				return;
			case 9:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
				continue;
				
			}
		}
	}// end menu

	public static void teamMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("┌──────────────── 팀메뉴 ─────────────────┐");
			System.out.printf("│%-32s│\n","1. 전체 보기　　　　　");
			System.out.printf("│%-32s│\n","2. 특정 팀 보기　　　　");
			System.out.printf("│%-32s│\n","3. 팀 정보 수정　　　　");
			System.out.printf("│%-32s│\n","4. 경기결과 입력　　　");
			System.out.printf("│%-32s│\n","8. 메인메뉴로 돌아가기");
			System.out.printf("│%-35s│\n","9. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");
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
			case 9:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
				continue;	
			}
		}
	}// end menu
	
	// *//menu class
	public static void gameresultMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("┌─────────── 경기 일정 및 결과 ───────────┐");
			System.out.printf("│%-35s│\n","1. 경기 일정 조회");
			System.out.printf("│%-35s│\n","2. 경기 결과 등록");
			System.out.printf("│%-35s│\n","3. 경기 일정 변경");
			System.out.printf("│%-35s│\n","4. 경기 일정 추가");
			System.out.printf("│%-32s│\n","8. 메인메뉴로 돌아가기");
			System.out.printf("│%-35s│\n","9. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
			switch (sc.nextInt()) {
			case 1:
				gameSelectMenu();
				break; // (
			case 2:
				gameResultController.selectWillplay(); gameResultController.updateResult();
				break;
			case 3:
				gameResultController.selectWillplay(); gameResultController.updateDate();
				break;
			case 4:
				gameResultController.selectWillplay();gameResultController.insertGame();
				break;
			case 8:
				return;
			case 9:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
				continue;	

			}// switch
		} // end while
	}// menu end

	public static void gameSelectMenu() throws SQLException, IOException {
		
		while (true) {
			System.out.println("┌──────────── 경기 일정 조회 ─────────────┐");
			System.out.printf("│%-35s│\n","1. 모든 경기 일정");
			System.out.printf("│%-35s│\n","2. 팀별 경기 일정");
			System.out.printf("│%-35s│\n","3. 월별 경기 일정 ");
			System.out.printf("│%-34s│\n","4. 미진행 경기 일정 ");
			System.out.printf("│%-33s│\n","5. 라운드별 경기 일정 ");
//			System.out.printf("│%-30s│\n","7. 일정/결과 메뉴로 돌아가기");
			System.out.printf("│%-32s│\n","7. 이전 메뉴로 돌아가기");
			System.out.printf("│%-32s│\n","8. 메인메뉴로 돌아가기");
			System.out.printf("│%-35s│\n","9. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");
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
			case 7:
				return; 
			case 8:
				soccerMenu(); break;
			case 9:
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			default:
				System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
				continue;
			}// switch
		} // end while
	}// menu end
	
		public static void playerMenuList() {
			System.out.println("┌─────────────── 선수 메뉴 ───────────────┐");
			System.out.printf("│%-32s│\n","1. 선수 리스트　　　　");
			System.out.printf("│%-32s│\n","2. 선수 정보 추가　　　");
			System.out.printf("│%-32s│\n","3. 선수 정보 수정　　　");
			System.out.printf("│%-32s│\n","4. 선수 정보 삭제　　　");
			System.out.printf("│%-32s│\n","5. 특정 조건 검색　　　");
			System.out.printf("│%-32s│\n","8. 메인메뉴로 돌아가기");
			System.out.printf("│%-35s│\n","9. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
		}
		public static void playerMenu() throws SQLException {
			
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
				case 8:
					return;
				case 9:
					System.out.println("프로그램을 종료합니다.");
					System.exit(0);
				default:
					System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
					continue;
				}
			}
		}
		
		public static void playerSelectMenu() {
			System.out.println("┌───────────── 특정 조건 검색 ────────────┐");
			System.out.printf("│%-29s│\n","1. 특정 팀에 소속된 선수목록　");
			System.out.printf("│%-29s│\n","2. 특정 포지션에 속한 선수목록");
			System.out.printf("│%-29s│\n","3. 특정 나이대에 속한 선수목록");
			System.out.printf("│%-29s│\n","7. 선수메뉴로 돌아가기 　　　");
			System.out.printf("│%-29s│\n","8. 메인메뉴로 돌아가기 　　　");
			System.out.printf("│%-35s│\n","9. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");
			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
		}
		
		public static void selectOption() {
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
					case 7:
						return;
					case 8:
						soccerMenu();
					case 9:
						System.out.println("프로그램을 종료합니다.");
						System.exit(0);
					default:
						System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
						continue;	
					}
				}
			} catch (Exception e) { e.printStackTrace(); }
		}

		public static void playerStatMenu() { 
			System.out.println("┌─────────────── 선수 스탯 ───────────────┐");
			System.out.printf("│%-34s│\n","1. 선수 스탯 리스트");
			System.out.printf("│%-33s│\n","2. 선수 스탯 정보 수정");
			System.out.printf("│%-33s│\n","3. 선수 스탯 정보 삭제");
			System.out.printf("│%-33s│\n","4. 선수 어시스트 순위  ");
			System.out.printf("│%-33s│\n","5. 경기당 득점률 순위");
			System.out.printf("│%-31s│\n","6. 경기당 어시스트율 순위");
			System.out.printf("│%-33s│\n","7. 경기당 슛팅률 순위");
			System.out.printf("│%-31s│\n","8. 경기당 유효슛팅률 순위");
			System.out.printf("│%-32s│\n","9. 메인메뉴로 돌아가기");
			System.out.printf("│%-35s│\n","0. 프로그램 종료");
			System.out.println("└─────────────────────────────────────────┘");

			System.out.println("\t>> 원하는 메뉴를 선택하세요. ");
		}
		
		public static void playerStat() throws SQLException{
			while(true) {
				playerStatMenu();
			
				switch(sc.nextInt()) {	
					case 1: playerStatController.selectAll(1); break;	
					case 2: playerStatController.playerStatUpdate(); break;
					case 3: playerStatController.delete(); break;
					case 4: playerStatController.selectAllAssists(); break;
					case 5: playerStatController.selectAllByGoalsPerGame(); break;
					case 6: playerStatController.selectAllByAssistsPerGame(); break;
					case 7: playerStatController.selectAllByShotsPerGame(); break;
					case 8: playerStatController.selectAllByShots_on_goalPerGame(); break;
					case 9: return;
					case 0:
						System.out.println("프로그램을 종료합니다.");
						System.exit(0);
					default:
						System.out.println("\t>>  ※ 잘못 입력하셨습니다 ※");
						continue;
				}
			}// end while
		}

}
