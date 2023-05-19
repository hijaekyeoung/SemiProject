package controller;

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

	// connect
	public static void connect() {

		try {
			conn = ConnectionSingletonHelper.getConnection();
			stmt = conn.createStatement();
//			conn.setAutoCommit(false); // 자동 커밋 끄기

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

	// selectAll
	public static void selectAll(int asc) throws SQLException {
		/*
		 K 리그 순위 결정 기준 
		 승점 > 다득점 > 득실차 > 다승 <-- 여기까지 구현 
		 > 승자승 > 실경기시간(ATP)순 > 추첨
		 */

		String srt = "";
		switch (asc) {
		case 1: // 순위정렬
			srt= "";
			break;
		case 2: // 승정렬
			srt = "ORDER BY TWIN DESC";
			break;
		case 3: // 무정렬
			srt = "ORDER BY TDRAW DESC";
			break;
		case 4: // 패정렬
			srt = "ORDER BY TLOSE DESC";
			break;
		default:// 이전으로
			return; 
		}
		
		rs = stmt.executeQuery(
				"SELECT ROWNUM AS 순위, TNAME AS 팀명, TWIN AS 승, TDRAW AS 무, TLOSE AS 패, TSCORE AS 득 ,CONCEDED AS 실, (TSCORE-CONCEDED) AS 득실차 FROM (SELECT * FROM TEAM ORDER BY (TWIN*3 + TDRAW) DESC, TSCORE DESC, TSCORE-CONCEDED DESC, TWIN DESC) A " + srt);

		ArrayList<TeamVO> list = new ArrayList();

		while (rs.next()) {
			TeamVO team = new TeamVO();
			team.setTrank(rs.getInt("순위"));
			team.setTname(rs.getString("팀명"));
			team.setTwin(rs.getInt("승"));
			team.setTlose(rs.getInt("패"));
			team.setTdraw(rs.getInt("무"));
			team.setTscore(rs.getInt("득"));
			team.setTconceded(rs.getInt("실"));
			team.setGd(rs.getInt("득실차"));
			list.add(team);
		}

		System.out.printf(" %-2s | %-8s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s\n", "순위", "팀명", "승", "무", "패", "득",
				"실", "득실차");
		System.out.println("--------------------------------------------------------------");
		int len;
		for (TeamVO team : list) {
			if (team.getTname().equals("수원삼성")) {
				len = 6;
			} else {
				len = 8;
			}
			System.out.printf(" %-4s | %-" + len + "s | %-4s | %-4s | %-4s | %-4s | %-4s | %-4s\n", team.getTrank(),
					team.getTname(), team.getTwin(), team.getTdraw(), team.getTlose(), team.getTscore(),
					team.getTconceded(), team.getGd());
			System.out.println("--------------------------------------------------------------");
		}

		System.out.println();
		System.out.printf("%-10s%-10s%-10s%-10s%-10s\n", "1. 순위 정렬", "2. 승 정렬", "3. 무 정렬", "4. 패 정렬", "8. 돌아가기");
		selectAll(sc.nextInt());
	}// end select all

	// update

	public static void update() {
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
		System.out.println("Home 팀을 입력해주세요. : ");
		String tname1 = sc.next();
		System.out.println("Away 팀를 입력해주세요. : ");
		String tname2 = sc.next();

		System.out.println("Home 팀 점수, Away 팀 점수 순서로 입력해주세요.");
		int score1 = sc.nextInt();
		int score2 = sc.nextInt();

		pstmt = conn.prepareStatement("SELECT TDRAW, TWIN, TLOSE, TSCORE, CONCEDED FROM TEAM WHERE TNAME = ?");
		pstmt.setString(1, tname1);
		rs = pstmt.executeQuery();
		rs.next();
		int tdraw1 = rs.getInt(1);
		int twin1 = rs.getInt(2);
		int tlose1 = rs.getInt(3);
		int tscore1 = rs.getInt(4);
		int conceded1 = rs.getInt(5);

		pstmt = conn.prepareStatement("SELECT TDRAW, TWIN, TLOSE, TSCORE, CONCEDED FROM TEAM WHERE TNAME = ?");
		pstmt.setString(1, tname2);
		rs = pstmt.executeQuery();
		rs.next();

		int tdraw2 = rs.getInt(1);
		int twin2 = rs.getInt(2);
		int tlose2 = rs.getInt(3);
		int tscore2 = rs.getInt(4);
		int conceded2 = rs.getInt(5);

		if (score1 == score2) { // 무승부
			tdraw1++;
			tdraw2++;
		} else if (score1 > score2) { // 1팀승리
			twin1++;
			tlose2++;
			tscore1 += (score1 - score2);
			conceded2 += (score1 - score2);
		} else { // 2팀승리
			tlose1++;
			twin2++;
			tscore2 += (score2 - score1);
			conceded1 += (score2 - score1);
		}

		try {
			pstmt = conn.prepareStatement(
					"UPDATE TEAM SET TWIN = ?, TDRAW = ?, TLOSE = ?, TSCORE =?, CONCEDED =?  WHERE TNAME = ?");
			pstmt.setInt(1, twin1);
			pstmt.setInt(2, tdraw1);
			pstmt.setInt(3, tlose1);
			pstmt.setInt(4, tscore1);
			pstmt.setInt(5, conceded1);
			pstmt.setString(6, tname1);
			int result = pstmt.executeUpdate();

			pstmt = conn.prepareStatement(
					"UPDATE TEAM SET TWIN = ?, TDRAW = ?, TLOSE = ?, TSCORE =?, CONCEDED =?  WHERE TNAME = ?");
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
	
	public static void selectByTname() throws SQLException {
		pstmt = conn.prepareStatement(
				"SELECT TNAME, TWIN, TDRAW, TLOSE, TSCORE ,CONCEDED, (TSCORE-CONCEDED) AS GD FROM TEAM WHERE TNAME = ?");
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

		System.out.printf(" %-2s | %-8s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s\n", "순위", "팀명", "승", "무", "패", "득",
				"실", "득실차");
		System.out.println("--------------------------------------------------------------");

		int len;
		for (TeamVO team : list) {
			if (team.getTname().equals("수원삼성")) {
				len = 6;
			} else {
				len = 8;
			}
			System.out.printf(" %-4s | %-" + len + "s | %-4s | %-4s | %-4s | %-4s | %-4s | %-4s\n", team.getTrank(),
					team.getTname(), team.getTwin(), team.getTdraw(), team.getTlose(), team.getTscore(),
					team.getTconceded(), team.getGd());
			System.out.println("--------------------------------------------------------------");
		}
		System.out.println();
	}

}
