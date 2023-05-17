package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Scanner;

import dbConn.util.CloseHelper;
import dbConn.util.ConnectionSingletonHelper;
import model.GameResultVO;

public class gameResultController { // controller 
	
	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement pstmt = null;
	static Connection conn = null;
	
	//connect
	public static void connect() {
		try {
			conn = ConnectionSingletonHelper.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false); //자동 커밋 끄기
		} catch (Exception e) {e.printStackTrace();}
	}//connect end
	
	//close
	public static void close() {
		try {
			CloseHelper.close(rs); CloseHelper.close(stmt); CloseHelper.close(conn); CloseHelper.close(pstmt);
 		} catch (Exception e) {e.printStackTrace();}
	}//close end
	
	//*//menu class
	public static void menu() throws SQLException {            
		GameResultVO vo = new GameResultVO(); //GameResultVO 클래스 주석처리 확인
		while(true) {
			System.out.println();
			ConnectionSingletonHelper.menu();
			switch (sc.nextInt()) {
			case 0 : System.out.println("Commit 하시겠습니까?(Y/N) "); //case0 안건드렸음
					System.out.println("안하시려면 Rollback 됩니다.");
					if(sc.next().equalsIgnoreCase("Y")){
						selectAll(); 
					} else {
						conn.rollback();
						selectAll(); 
					}
					break;
			
			case 1 : selectMenu();  break; //(
			case 3 : updateResult(); break;
			case 4 : updateDate(); break;
			case 5 : insertGame(); break; 

//			case 5 : selectByGNO(vo.getClassName()); break;
//			case 6 : close(); System.out.println("프로그램 종료합니다");
//					System.exit(0); break;
			case 9 : conn.commit();
					System.out.println("성공적으로 완료 되었습니다."); break;
			}// switch
		}//end while
	}// menu end
	
	public static void selectMenu() throws SQLException {            
			
			System.out.println("\n-=-=-=-=-= 일정 조회 =-=-=-=-=-");
			System.out.println("\t 1. 모든 경기 일정 ");
			System.out.println("\t 2. 팀 일정 조회");
			System.out.println("\t 3. 월별 일정 조회 ");
			System.out.println("\t 4. 레코드 삭제 ");
			System.out.println("\t 5. 조건에 의한 검색(ex>gname) ");
			System.out.println("\t 6. 프로그램 종료 ");
			System.out.println("\t 9. 뒤로 가기 ");
			System.out.println("\t >> 원하는 메뉴 선택하세요. ");
			while(true) {
			switch (sc.nextInt()) {
			
			case 1 : selectAll();  break; 
			case 2 : selectTeam(); 
			case 3 : selectMonth(); break;
//			case 4 : delete(vo.getClassName()); break;
//			case 5 : selectByGNO(vo.getClassName()); break;
			case 6 : close(); System.out.println("프로그램 종료합니다");
					System.exit(0); break;
			case 9 : menu();
			}// switch
		}//end while
	}// menu end
	
	
	public static void selectAll() throws SQLException {
		rs = stmt.executeQuery("select * "
				+ "from gameresult order by GDATE");
		
		while(rs.next()) {
			Date gdate = rs.getDate("GDATE");
			int gno = rs.getInt("GNO");
			String ateam = rs.getString("ATEAM");
			String ascore = rs.getString("ASCORE");
			String bteam = rs.getString("BTEAM");
			String bscore = rs.getString("BSCORE");
			
			System.out.println("===모든 경기 일정=== \n");
			if(ascore!=null) {
				System.out.println(gdate+" NO."+gno+" "+ateam + " [" + ascore + "]" + " : " + "[" + bscore + "] " + bteam);
			}
			else 
				System.out.println(gdate+" NO."+gno+" "+ateam + " [경기 예정] " + bteam);
			}
			lastmessage();
		}//selectAll
	
	public static void selectTeam () throws SQLException {
		System.out.println("=== 팀별 경기 조회 === \n");
		System.out.println("팀 이름을 입력해주세요.");
		String teamchoice = sc.next();
		rs = stmt.executeQuery("select GDATE, ATEAM, ASCORE, BTEAM, BSCORE "
				+ "from gameresult "
				+ "where ATEAM = + " + "'"+ teamchoice + "'" 
				+ "or BTEAM =" + "'" + teamchoice + "'" + "order by GDATE");
		
		while(rs.next()) {
			Date gdate = rs.getDate("GDATE");
			String ateam = rs.getString("ATEAM");
			String ascore = rs.getString("ASCORE");
			String bteam = rs.getString("BTEAM");
			String bscore = rs.getString("BSCORE");
		
			if(ascore != null) {
				System.out.println(gdate+" "+ateam + " [" + ascore + "]" + " : " + "[" + bscore + "] " + bteam);
			}
			else 
				System.out.println(gdate+" "+ateam + " [경기 예정] " + bteam);
			}
			lastmessage();
			}//selectTeam end
	
	public static void selectMonth() throws SQLException {
		System.out.println("=== 월별 경기 조회 === \n");
		System.out.println("조회하실 월을 입력해주세요.");
//			String choicemonth = sc.next();
		try {
			pstmt = conn.prepareStatement("select GDATE, ATEAM, ASCORE, BTEAM, BSCORE"
					+ "  from gameresult"
					+ "  where to_date(GDATE, 'YY/MM/DD') like '23/%'||?||'/%'"
					+ "  order by GDATE");
			pstmt.setInt(1, sc.nextInt());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Date gdate = rs.getDate("GDATE");
				String ateam = rs.getString("ATEAM");
				String ascore = rs.getString("ASCORE");
				String bteam = rs.getString("BTEAM");
				String bscore = rs.getString("BSCORE");
			
			if(ascore != null) {
				System.out.println(gdate+" "+ateam + " [" + ascore + "]" + " : " + "[" + bscore + "] " + bteam);
			}
			else 
				System.out.println(gdate+" "+ateam + " [경기 예정] " + bteam);
			}
			lastmessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//selectMonth end
	
	public static void updateResult() {
		System.out.println("=== 경기 일정 변경 === \n");
		System.out.println("결과 입력을 위한 경기 번호를 입력해주세요."); int gnum = sc.nextInt();
		System.out.println("HOME 팀의 스코어를 입력해주세요."); int homescore = sc.nextInt();
		System.out.println("AWAY 팀의 스코어를 입력해주세요."); int awayscore = sc.nextInt();
		
		try {
			pstmt = conn.prepareStatement("Update gameResult "
					+ "Set ASCORE = ?, BSCORE = ?  WHERE GNO = ?");
			pstmt.setInt(3, gnum);
			pstmt.setInt(1, homescore);
			pstmt.setInt(2, awayscore);
			pstmt.executeUpdate();
			System.out.println("경기 결과 등록되었습니다.");
			
			lastmessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//updateResult end
	
	public static void updateDate() {
		System.out.println("=== 경기 일정 변경 === \n");
		System.out.println("결과 입력을 위한 경기 번호를 입력해주세요."); int gnum = sc.nextInt();
		System.out.println("변경할 날짜를 입력해주세요. yy/dd/mm형식 "); String gdate = sc.next();
		try {
			pstmt = conn.prepareStatement("Update gameResult Set GDATE = To_DATE(?,'YY/MM/DD') WHERE GNO = ?");
			pstmt.setInt(2, gnum);
			pstmt.setString(1,gdate);
			pstmt.executeUpdate();
			System.out.println("경기 일정 변경되었습니다.");
			
			lastmessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertGame() {
		System.out.println("== 경기 일정 추가 === \n");
		System.out.println("경기 번호를 입력해주세요."); int gnum = sc.nextInt();
		System.out.println("경기 날짜를 입력해주세요."); String gdate = sc.next();
		System.out.println("HOME 팀의 이름 입력해주세요."); String hometeam = sc.next();
		System.out.println("AWAY 팀의 이름 입력해주세요."); String awayteam = sc.next();
		
		try {
			pstmt = conn.prepareStatement("INSERT INTO gameResult "
					+ "(GNO, GDATE, ATEAM, BTEAM) "
					+ "VALUES(?, ?, ?, ?)");
			pstmt.setInt(1, gnum);
			pstmt.setString(2, gdate);
			pstmt.setString(3, hometeam);
			pstmt.setString(4, awayteam);
			pstmt.executeUpdate();
			System.out.println("경기 일정 추가되었습니다.");
			
			lastmessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void lastmessage() throws SQLException {
		System.out.println();
		//		System.out.println("\t 7. 뒤로가기 ");	
		System.out.println("\t 8. 시작화면(메인메뉴) ");
		System.out.println("\t 9. 프로그램 종료 ");
		switch (sc.nextInt()) {
//		case 7 : break; 
		case 8 : menu(); break;  
		case 9 : close(); System.out.println("프로그램 종료합니다");
				System.exit(0); break;
		}
		System.out.println();
	}

}//class end