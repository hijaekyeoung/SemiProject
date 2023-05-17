package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dbConn.util.ConnectionSingletonHelper;

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
		
		public static void selectByGno() throws SQLException {
	        pstmt = conn.prepareStatement("select g.ateam, v.avote, g.bteam, v.bvote from vote v join gameresult g on v.gno= g.gno where v.gno = ?");
	        System.out.print("검색할 경기 번호를 입력: ");
	        int gno = sc.nextInt();
	        pstmt.setInt(1, gno);
	        rs = pstmt.executeQuery();
	        if(!(rs.next())){
	        	System.out.println("없는 경기 입니다.");
	        	return;
	        }
	        
	        System.out.println("Ateam : " + rs.getString("ateam") + "\tAvote : " + rs.getInt("avote") + "\tBteam : " + rs.getString("bteam") + "\tBvote : " + rs.getInt("bvote") );
	    }
		
		public static void selectByGnoEnd() throws SQLException { //경기 결과가 나온 이후
	        pstmt = conn.prepareStatement("select g.ateam, v.avote, g.ascore, g.bteam, v.bvote, g.bscore from vote v join gameresult g on v.gno= g.gno where v.gno = ?");
	        System.out.print("검색할 경기 번호를 입력: ");
	        int gno = sc.nextInt();
	        pstmt.setInt(1, gno);
	        rs = pstmt.executeQuery();
	        if(!(rs.next())){
	        	System.out.println("없는 경기 입니다.");
	        	return;
	        }
	        
	        System.out.println("Ateam : " + rs.getString("ateam") + "\tAvote : " + rs.getInt("avote") + "\tAscore : " + rs.getInt("ascore") + "\tBteam : " + rs.getString("bteam") + "\tBvote : " + rs.getInt("bvote")+ "\tBscore : " + rs.getInt("bscore")  );
	    }
		
//		private static void update() { // 투표 
//			System.out.println("투표할 경기 번호를 입력해주세요.");
//			int gno = sc.nextInt();
//			System.out.println("투표할 ");
//			
//			try {
//				pstmt = conn.prepareStatement(
//						"UPDATE team SET TWIN = ?, TDRAW = ?, TLOSE = ?, TSCORE =?, CONCEDED =?  WHERE TNAME = ?");
//				pstmt.setString(1, twin);
//				pstmt.setString(2, tdraw);
//				pstmt.setString(3, tlose);
//				pstmt.setString(4, tscore);
//				pstmt.setString(5, conceded);
//				pstmt.setString(6, tname);
//				int result = pstmt.executeUpdate();
//				System.out.println(result + "개 데이터가 변경 되었습니다.");
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

}
