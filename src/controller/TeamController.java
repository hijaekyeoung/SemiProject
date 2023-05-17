package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import dbConn.util.ConnectionSingletonHelper;
import model.TeamVO;

public class TeamController {

	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement pstmt = null;
	static Connection conn = null;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	// connect
	public static void connect() {

		try {
			conn = ConnectionSingletonHelper.getConnection();
			stmt = conn.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// close
	public static void close() {
		try {
			ConnectionSingletonHelper.close(rs);
			ConnectionSingletonHelper.close(stmt);
			ConnectionSingletonHelper.close(pstmt);
			ConnectionSingletonHelper.close(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void menu() throws SQLException, IOException { 
		
		while(true) {
			System.out.println("\n=-=-=-=-=-=-=-= JDBC Query =-=-=-=-=-=-=-=");
			System.out.println("\t 0.ROLLBACK");
			System.out.println("\t 1.전체보기");
			System.out.println("\t 2.특정 팀 보기");
			System.out.println("\t 3.팀 정보 수정");
			System.out.println("\t 4.팀 정보 갱신(1게임 종료 후)");
			System.out.println("\t 8.메인메뉴로 돌아가기");
			System.out.println("\t 9.COMMIT");
			System.out.println("\t >> 원하는 메뉴를 선택하세요. ");
			
			switch(sc.nextInt()){
				case 0 :
					System.out.println("롤백 합니다.");
					conn.rollback();
					selectAll(1);
					break;
				case 1 :
					selectAll(1);
					break;
				case 2 :
					selectByTname();
					break;
				case 3 :
					update();
					break;
				case 4 :
					updateOnegame();
					break;
				case 8 :
					return;
				case 9 :
					conn.commit();
					System.out.println("성공적으로 완료 되었습니다.");
					break;
			}
		}
		
	}//end menu

	// selectAll
	public static void selectAll(int asc) throws SQLException {
		
		switch (asc) { // GD = 득실차
		case 1 :
			rs = stmt.executeQuery("SELECT TNAME, TWIN, TDRAW, TLOSE, TSCORE ,CONCEDED, (TSCORE-CONCEDED) AS GD FROM TEAM ORDER BY TWIN DESC"); // 승정렬 
			break;
		case 2:
			rs = stmt.executeQuery("SELECT TNAME, TWIN, TDRAW, TLOSE, TSCORE ,CONCEDED, (TSCORE-CONCEDED) AS GD FROM TEAM ORDER BY TLOSE DESC"); // 패정렬
			break;
		case 3:
			rs = stmt.executeQuery("SELECT TNAME, TWIN, TDRAW, TLOSE, TSCORE ,CONCEDED, (TSCORE-CONCEDED) AS GD FROM TEAM ORDER BY TDRAW DESC"); // 무정렬
			break;
		default :
			return; //이전으로
		}
        
        ArrayList<TeamVO> list = new ArrayList();
        
        while (rs.next()) {
        	TeamVO team = new TeamVO();
        	team.setTname(rs.getString("Tname"));
        	team.setTwin(rs.getInt("Twin"));        	
        	team.setTlose(rs.getInt("Tlose"));        	
        	team.setTdraw(rs.getInt("Tdraw"));        	
        	team.setTscore(rs.getInt("Tscore"));        	
        	team.setTconceded(rs.getInt("conceded"));
        	team.setGd(rs.getInt("gd"));
            list.add(team);
        }
        
        for (TeamVO team : list) {
        	System.out.println("TNAME : " + team.getTname() + "\tTWIN : " + team.getTwin() + "\tTDRAW : " + team.getTdraw() + "\tTLOSE : " + team.getTlose() + "\tTSCORE : " + team.getTscore() + "\tTCONCEDED : " + team.getTconceded()+ "\tGD : " + team.getGd());
        }
		
        System.out.println("0. 돌아가기\t 1. 승 정렬\t 2. 패 정렬\t 3. 무 정렬"); //재정렬
		selectAll(sc.nextInt());
		
	}// end select all

	// insert
//	public static void insert() throws SQLException {
//		rs = stmt.executeQuery("select max(tcode) from team");
//		rs.next();
//		int tcode = rs.getInt(1)+100; 
//		
//		System.out.println("tname : ");
//		String tname = sc.next();
//		System.out.println("twin : ");
//		String twin = sc.next();
//		System.out.println("tdraw : ");
//		String tdraw = sc.next();
//		System.out.println("tlose : ");
//		String tlose = sc.next();
//		System.out.println("tscore : ");
//		String tscore = sc.next();
//		System.out.println("conceded : ");
//		String conceded = sc.next();
//
//		try {
//			pstmt = conn.prepareStatement("INSERT INTO TEAM VALUES(?, ?, ?, ?, ?, ?, ?)");
//			pstmt.setInt(1, tcode);
//			pstmt.setString(2, tname);
//			pstmt.setString(3, twin);
//			pstmt.setString(4, tdraw);
//			pstmt.setString(5, tlose);
//			pstmt.setString(6, tscore);
//			pstmt.setString(7, conceded);
//
//			int result = pstmt.executeUpdate();
//			System.out.println(result + "개 데이터가 추가 되었습니다.");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}// end insert

	// update
	private static void update() {
		System.out.println("수정할 팀을 입력해주세요. : ");
		String tname = sc.next();

		System.out.println("승수를 입력해주세요. : ");
		String twin = sc.next();
		System.out.println("무수를 입력해주세요. : ");
		String tdraw = sc.next();
		System.out.println("패수를 입력해주세요. : ");
		String tlose = sc.next();
		System.out.println("득점을 입력해주세요. : ");
		String tscore = sc.next();
		System.out.println("실점을 입력해주세요. : ");
		String conceded = sc.next();

		try {
			pstmt = conn.prepareStatement(
					"UPDATE team SET TWIN = ?, TDRAW = ?, TLOSE = ?, TSCORE =?, CONCEDED =?  WHERE TNAME = ?");
			pstmt.setString(1, twin);
			pstmt.setString(2, tdraw);
			pstmt.setString(3, tlose);
			pstmt.setString(4, tscore);
			pstmt.setString(5, conceded);
			pstmt.setString(6, tname);
			int result = pstmt.executeUpdate();
			System.out.println(result + "개 데이터가 변경 되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 한게임 끝나면 전적 갱신
	public static void updateOnegame() throws SQLException {
		System.out.println("팀1을 입력해주세요. : "); 
		String tname1 = sc.next();
		System.out.println("팀2를 입력해주세요. : ");
		String tname2 = sc.next();
		
		System.out.println("팀1 점수, 팀2 점수 순서로 입력해주세요.");
		int score1= sc.nextInt();
		int score2= sc.nextInt();
		
		pstmt = conn.prepareStatement("SELECT TDRAW, TWIN, TLOSE, TSCORE, CONCEDED FROM TEAM WHERE TNAME = ?");
		pstmt.setString(1, tname1);
		rs = pstmt.executeQuery();
		rs.next();
		int tdraw1 = rs.getInt(1); int twin1 = rs.getInt(2); int tlose1 = rs.getInt(3); int tscore1 = rs.getInt(4); int conceded1 = rs.getInt(5);
		
		pstmt = conn.prepareStatement("SELECT TDRAW, TWIN, TLOSE, TSCORE, CONCEDED FROM TEAM WHERE TNAME = ?");
		pstmt.setString(1, tname2);
		rs = pstmt.executeQuery();
		rs.next();
		int tdraw2 = rs.getInt(1); int twin2 = rs.getInt(2); int tlose2 = rs.getInt(3); int tscore2 = rs.getInt(4); int conceded2 = rs.getInt(5);

		if(score1 == score2) { //무승부
			tdraw1++; tdraw2++;
		} else if(score1 > score2) { //1팀승리
			twin1++; tlose2++;
			tscore1 += (score1-score2);
			conceded2 += (score1-score2);
		} else { //2팀승리
			tlose1++; twin2++;
			tscore2 += (score2-score1);
			conceded1 += (score2-score1);
		}
		
		try {
			pstmt = conn.prepareStatement("UPDATE TEAM SET TWIN = ?, TDRAW = ?, TLOSE = ?, TSCORE =?, CONCEDED =?  WHERE TNAME = ?");
			pstmt.setInt(1, twin1);
			pstmt.setInt(2, tdraw1);
			pstmt.setInt(3, tlose1);
			pstmt.setInt(4, tscore1);
			pstmt.setInt(5, conceded1);
			pstmt.setString(6, tname1);
			int result = pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement("UPDATE TEAM SET TWIN = ?, TDRAW = ?, TLOSE = ?, TSCORE =?, CONCEDED =?  WHERE TNAME = ?");
			pstmt.setInt(1, twin2);
			pstmt.setInt(2, tdraw2);
			pstmt.setInt(3, tlose2);
			pstmt.setInt(4, tscore2);
			pstmt.setInt(5, conceded2);
			pstmt.setString(6, tname2);
			result += pstmt.executeUpdate();
			
			System.out.println(result + "개 데이터가 변경 되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	// delete
//	public static void delete() throws IOException, SQLException {
//		System.out.println("삭제할 팀의 팀번호를 입력하세요");
//		String tcode = sc.next();
//
//		try {
//			pstmt = conn.prepareStatement("delete team where tcode = ?");
//			pstmt.setString(1, tcode);
//
//			pstmt.executeUpdate();
//
//			System.out.println(tcode + "번 팀이 삭제되었습니다.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	public static void selectByTname() throws SQLException {
        pstmt = conn.prepareStatement("SELECT TNAME, TWIN, TDRAW, TLOSE, TSCORE ,CONCEDED, (TSCORE-CONCEDED) AS GD FROM TEAM WHERE TNAME = ?");
        System.out.print("검색할 팀의 이름를 입력: ");
        String tname = sc.next();
        pstmt.setString(1, tname);
        rs = pstmt.executeQuery();
        
        ArrayList<TeamVO> list = new ArrayList();
        
        while (rs.next()) {
        	TeamVO team = new TeamVO();
        	team.setTname(rs.getString("Tname"));
        	team.setTwin(rs.getInt("Twin"));        	
        	team.setTlose(rs.getInt("Tlose"));        	
        	team.setTdraw(rs.getInt("Tdraw"));        	
        	team.setTscore(rs.getInt("Tscore"));        	
        	team.setTconceded(rs.getInt("conceded"));
        	team.setGd(rs.getInt("gd"));
            list.add(team);
        }
        
        if (list.size() == 0) {
            System.out.println("없는 팀 입니다.");
            System.out.println();
            return;
        }
        
        for (TeamVO team : list) {
        	System.out.println("TNAME : " + team.getTname() + "\tTWIN : " + team.getTwin() + "\tTDRAW : " + team.getTdraw() + "\tTLOSE : " + team.getTlose() + "\tTSCORE : " + team.getTscore() + "\tTCONCEDED : " + team.getTconceded()+ "\tGD : " + team.getGd());
        }
    }

}
