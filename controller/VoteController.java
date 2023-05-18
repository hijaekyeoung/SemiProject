package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dbConn.util.ConnectionSingletonHelper;
import model.VoteVO;

public class VoteController {

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

	public static void voteMenu() throws SQLException, IOException {

		while (true) {
			System.out.println("\n=-=-=-=-=-=-=-= JDBC Query =-=-=-=-=-=-=-=");
			System.out.println("\t 0.ROLLBACK");
			System.out.println("\t 1.투표 진행 보기");
			System.out.println("\t 2.경기 결과 보기");
			System.out.println("\t 3.투표 하기");
			System.out.println("\t 4.경기 추가하기");
			System.out.println("\t 8.메인메뉴로 돌아가기");
			System.out.println("\t 9.COMMIT");
			System.out.println("\t >> 원하는 메뉴를 선택하세요. ");

			switch (sc.nextInt()) {
			case 0:
				System.out.println("롤백 합니다.");
				conn.rollback();
				break;
			case 1:
				selectByGno();
				break;
			case 2:
				selectByGnoEnd();
				break;
			case 3:
				update();
				break;
			case 4:
				insert();
				break;
			case 8:
				return;
			case 9:
				conn.commit();
				System.out.println("성공적으로 완료 되었습니다.");
				break;
			}
		}

	}// end menu

	public static void selectByGno() throws SQLException {
		pstmt = conn.prepareStatement(
				"select g.ateam, v.avote, g.bteam, v.bvote from vote v join gameresult g on v.gno= g.gno where v.gno = ?");
		System.out.print("검색할 경기 번호를 입력: ");
		int gno = sc.nextInt();
		pstmt.setInt(1, gno);
		rs = pstmt.executeQuery();
		if (!(rs.next())) {
			System.out.println("없는 경기 입니다.");
			return;
		}

		System.out.println("Ateam : " + rs.getString("ateam") + "\tAvote : " + rs.getInt("avote") + "\tBteam : "
				+ rs.getString("bteam") + "\tBvote : " + rs.getInt("bvote"));
	}

	public static void selectByGnoEnd() throws SQLException { // 경기 결과가 나온 이후
		pstmt = conn.prepareStatement(
				"select g.ateam, v.avote, g.ascore, g.bteam, v.bvote, g.bscore from vote v join gameresult g on v.gno= g.gno where v.gno = ?");
		System.out.print("검색할 경기 번호를 입력: ");
		int gno = sc.nextInt();
		pstmt.setInt(1, gno);
		rs = pstmt.executeQuery();
		if (!(rs.next())) {
			System.out.println("없는 경기 입니다.");
			return;
		}

		System.out.println("Ateam : " + rs.getString("ateam") + "\tAvote : " + rs.getInt("avote") + "\tAscore : "
				+ rs.getInt("ascore") + "\tBteam : " + rs.getString("bteam") + "\tBvote : " + rs.getInt("bvote")
				+ "\tBscore : " + rs.getInt("bscore"));
	}

	public static void update() throws SQLException { // 투표
		System.out.println("투표할 경기 번호를 입력해주세요.");
		int gno = sc.nextInt();
		System.out.println("투표할 팀을 선택해주세요.(1. Home Team  2. Away Team)");
		int tvote = sc.nextInt();

		pstmt = conn.prepareStatement("SELECT avote, bvote FROM vote WHERE GNO = ?");
		pstmt.setInt(1, gno);
		rs = pstmt.executeQuery();
		rs.next();
		int avote = rs.getInt(1);
		int bvote = rs.getInt(2);

		if (tvote == 1) {
			avote++;
		} else if (tvote == 2) {
			bvote++;
		} else {
			return;
		}

		try {
			pstmt = conn.prepareStatement("UPDATE vote SET avote = ?, bvote = ? WHERE gno = ?");
			pstmt.setInt(1, avote);
			pstmt.setInt(2, bvote);
			pstmt.setInt(3, gno);
			int result = pstmt.executeUpdate();

			System.out.println(result + "개 데이터가 변경 되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// insert
	public static void insert() throws SQLException {

		System.out.println("gno : ");
		int gno = sc.nextInt();

		try {
			pstmt = conn.prepareStatement("INSERT INTO VOTE(GNO) VALUES(?)");
			pstmt.setInt(1, gno);

			int result = pstmt.executeUpdate();
			System.out.println(result + "개 데이터가 추가 되었습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}