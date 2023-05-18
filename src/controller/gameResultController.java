package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import dbConn.util.ConnectionSingletonHelper;
import model.GameResultVO;

public class gameResultController { // controller

	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement pstmt = null;
	static Connection conn = null;

	// connect
	public static void connect() {
		try {
			conn = ConnectionSingletonHelper.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false); // 자동 커밋 끄기
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// connect end

	// close
	public static void close() {
		try {
			ConnectionSingletonHelper.close(rs);
			ConnectionSingletonHelper.close(stmt);
			ConnectionSingletonHelper.close(conn);
			ConnectionSingletonHelper.close(pstmt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// close end

	public static void selectAll() throws SQLException {
		rs = stmt.executeQuery("select * " + "from gameresult order by GDATE");
		System.out.println("----------------- 모 든   경 기   일 정 -------------------");
		System.out.println("   날짜    |경기 번호|   홈 팀    |   스코어    |   원정팀");
		System.out.println("-----------------------------------------------------------");
		while (rs.next()) {
			Date gdate = rs.getDate("GDATE");	
			int gno = rs.getInt("GNO");
			String ateam = rs.getString("ATEAM");
			String ascore = rs.getString("ASCORE");
			String bteam = rs.getString("BTEAM");
			String bscore = rs.getString("BSCORE");

			int len = 7 - (ateam.getBytes().length - 1) / 3;
			if (rs.getString("ateam").equals("수원FC")) {len =6;};
			
			if (ascore != null) {
				System.out.println(gdate+" |  NO."+String.format("%-3d |  %-" + len + "s  |  [%s] : [%s]  |  %-" + len + "s", gno, ateam, ascore, bscore, bteam) );	
			} 
			else System.out.println(gdate+" |  NO."+String.format("%-3d |  %-" + len + "s  |  경기 예정  |  %-" + len + "s", gno, ateam, bteam) );
		}
//		selectMenu();
		return;
	}// selectAll

	public static void selectTeam() throws SQLException {
		
		System.out.println("팀 이름을 입력해주세요.");
		String teamchoice = sc.next();
		System.out.println("------------- 팀 별   경 기   일 정 -------------");
		System.out.println("   날짜    |   홈 팀    |   스코어    |   원정팀");
		System.out.println("-------------------------------------------------");
		rs = stmt.executeQuery("select GDATE, ATEAM, ASCORE, BTEAM, BSCORE " + "from gameresult " + "where ATEAM = + "
				+ "'" + teamchoice + "'" + "or BTEAM =" + "'" + teamchoice + "'" + "order by GDATE");

		while (rs.next()) {
			Date gdate = rs.getDate("GDATE");
			String ateam = rs.getString("ATEAM");
			String ascore = rs.getString("ASCORE");
			String bteam = rs.getString("BTEAM");
			String bscore = rs.getString("BSCORE");
			
			int len = 7 - (ateam.getBytes().length - 1) / 3;
			if (rs.getString("ateam").equals("수원FC")) {len =6;};
			
			if (ascore != null) {
				System.out.println(gdate+" |  NO."+String.format("%-3d |  %-" + len + "s  |  [%s] : [%s]  |  %-" + len + "s", ateam, ascore, bscore, bteam) );	
			} else
				System.out.println(gdate + " " + ateam + " [경기 예정] " + bteam);
		}
		return;
	}// selectTeam end

	public static void selectMonth() throws SQLException {
		System.out.println("=== 월별 경기 조회 === \n");
		System.out.println("조회하실 월을 입력해주세요.");
//			String choicemonth = sc.next();
		try {
			pstmt = conn.prepareStatement("select GDATE, ATEAM, ASCORE, BTEAM, BSCORE" + "  from gameresult"
					+ "  where to_date(GDATE, 'YY/MM/DD') like '23/%'||?||'/%'" + "  order by GDATE");
			pstmt.setInt(1, sc.nextInt());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Date gdate = rs.getDate("GDATE");
				String ateam = rs.getString("ATEAM");
				String ascore = rs.getString("ASCORE");
				String bteam = rs.getString("BTEAM");
				String bscore = rs.getString("BSCORE");

				if (ascore != null) {
					System.out.println(gdate + " " + ateam + " [" + ascore + "]" + " : " + "[" + bscore + "] " + bteam);
				} else
					System.out.println(gdate + " " + ateam + " [경기 예정] " + bteam);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}// selectMonth end
	
	public static void selectWillplay() throws SQLException {
		System.out.println("-=-=-=-=-= 미진행 경기 일정 =-=-=-=-=-\n");
		try {
			rs = stmt.executeQuery("SELECT GDATE, ATEAM, ASCORE, BTEAM, BSCORE FROM GAMERESULT WHERE ASCORE IS NULL ORDER BY GDATE");
			while (rs.next()) {
				Date gdate = rs.getDate("GDATE");
				String ateam = rs.getString("ATEAM");
				String bteam = rs.getString("BTEAM");

				System.out.println(gdate + " " + ateam + " [경기 예정] " + bteam);
			}
		} catch (Exception e) { e.printStackTrace(); }
		return;
	}

	public static void selectRound() throws SQLException {
		System.out.println("-=-=-=-=-= 라운드별 경기 조회 =-=-=-=-=-\n");
		System.out.println("원하는 라운드를 입력해주세요");
		int W= sc.nextInt(); 
				
		try {
			rs = stmt.executeQuery("SELECT * FROM GAMERESULT WHERE GDATE BETWEEN TO_DATE(TO_DATE('23/02/17')+(7*"+W+"),'YY/MM/DD') AND TO_DATE(TO_DATE('23/02/17')+(7*("+(W+1)+")),'YY/MM/DD')");
			System.out.println(W +"라운드의 경기 결과입니다");
			while (rs.next()) {
				Date gdate = rs.getDate("GDATE");
				String ateam = rs.getString("ATEAM");
				String ascore = rs.getString("ASCORE");
				String bteam = rs.getString("BTEAM");
				String bscore = rs.getString("BSCORE");
				
				if (ascore != null) {
					System.out.println(gdate + " " + ateam + " [" + ascore + "]" + " : " + "[" + bscore + "] " + bteam);
				} else
					System.out.println(gdate + " " + ateam + " [경기 예정] " + bteam);
			}
		} catch (Exception e) { e.printStackTrace(); }
		return;
	}
	
	public static void updateResult() {
		System.out.println("=== 경기 일정 변경 === \n");
		System.out.println("결과 입력을 위한 경기 번호를 입력해주세요.");
		int gnum = sc.nextInt();
		System.out.println("HOME 팀의 스코어를 입력해주세요.");
		int homescore = sc.nextInt();
		System.out.println("AWAY 팀의 스코어를 입력해주세요.");
		int awayscore = sc.nextInt();

		try {
			pstmt = conn.prepareStatement("Update gameResult " + "Set ASCORE = ?, BSCORE = ?  WHERE GNO = ?");
			pstmt.setInt(3, gnum);
			pstmt.setInt(1, homescore);
			pstmt.setInt(2, awayscore);
			pstmt.executeUpdate();
			System.out.println("경기 결과 등록되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}// updateResult end

	public static void updateDate() {
		System.out.println("=== 경기 일정 변경 === \n");
		System.out.println("결과 입력을 위한 경기 번호를 입력해주세요.");
		int gnum = sc.nextInt();
		System.out.println("변경할 날짜를 입력해주세요. yy/dd/mm형식 ");
		String gdate = sc.next();
		try {
			pstmt = conn.prepareStatement("Update gameResult Set GDATE = To_DATE(?,'YY/MM/DD') WHERE GNO = ?");
			pstmt.setInt(2, gnum);
			pstmt.setString(1, gdate);
			pstmt.executeUpdate();
			System.out.println("경기 일정 변경되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertGame() {
		System.out.println("== 경기 일정 추가 === \n");
		System.out.println("경기 번호를 입력해주세요.");
		int gnum = sc.nextInt();
		System.out.println("경기 날짜를 입력해주세요.");
		String gdate = sc.next();
		System.out.println("HOME 팀의 이름 입력해주세요.");
		String hometeam = sc.next();
		System.out.println("AWAY 팀의 이름 입력해주세요.");
		String awayteam = sc.next();

		try {
			pstmt = conn
					.prepareStatement("INSERT INTO gameResult " + "(GNO, GDATE, ATEAM, BTEAM) " + "VALUES(?, ?, ?, ?)");
			pstmt.setInt(1, gnum);
			pstmt.setString(2, gdate);
			pstmt.setString(3, hometeam);
			pstmt.setString(4, awayteam);
			pstmt.executeUpdate();
			System.out.println("경기 일정 추가되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}// class end
