package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import dbConn.util.ConnectionSingletonHelper;

public class PlayerController {
	
	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static PreparedStatement pstmtInsert, pstmtDelete, pstmtUpdate;
	static PreparedStatement pstmt, pstmtSelectAll, pstmtSearchByTcode, pstmtSearchByPosition;
	static PreparedStatement pstmtSearchByAge;
	static ResultSet rs = null;
	static Connection conn = null;


	private String sqlInsert = "INSERT INTO PLAYER VALUES(?,?,?,?,?,?,?,?)";
	private String sqlDelete = "DELETE FROM PLAYER WHERE PNO = ? ";
	private String sqlUpdate = "UPDATE PLAYER SET TCODE = ?, UNO = ?, PNAME = ?, "
			+ "HEIGHT = ?, WEIGHT = ?, AGE = ?, POSITION = ? WHERE PNO = ?";
	private String sqlSelectAll = "SELECT * FROM PLAYER";
	private String sqlSearchByTcode = "SELECT P.PNO, T.TNAME, P.UNO, P.PNAME, P.HEIGHT,"
			+ "P.WEIGHT, P.AGE, P.POSITION FROM PLAYER P, TEAM T \r\n"
			+ "    WHERE (SELECT TCODE FROM TEAM WHERE TNAME = ?) = P.TCODE AND\r\n"
			+ "    P.TCODE = T.TCODE ORDER BY UNO ASC";
	private String sqlSearchByPosition = "select p.pno,t.tname,p.uno,p.pname,p.height,"
			+ "p.weight,p.age,p.position from player p, team t where p.position = ? and p.tcode = t.tcode";
	private String sqlSearchByAge = "select p.pno,t.tname,p.uno,p.pname,p.height,"
			+ "p.weight,p.age,p.position from player p, team t "
			+ "where p.tcode = t.tcode and age between ? and ? order by age asc";

	public void dbConnect() {
		try {
			conn = ConnectionSingletonHelper.getConnection();
			pstmtInsert = conn.prepareStatement(sqlInsert);
			pstmtDelete = conn.prepareStatement(sqlDelete);
			pstmtUpdate = conn.prepareStatement(sqlUpdate);
			pstmtSelectAll = conn.prepareStatement(sqlSelectAll);
			pstmtSearchByTcode = conn.prepareStatement(sqlSearchByTcode);
			pstmtSearchByPosition = conn.prepareStatement(sqlSearchByPosition);
			pstmtSearchByAge = conn.prepareStatement(sqlSearchByAge);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void close() {
		try {
			ConnectionSingletonHelper.close(rs);
			ConnectionSingletonHelper.close(pstmtInsert);
			ConnectionSingletonHelper.close(pstmtDelete);
			ConnectionSingletonHelper.close(pstmtUpdate);
			ConnectionSingletonHelper.close(pstmtSelectAll);
			ConnectionSingletonHelper.close(pstmtSearchByTcode);
			ConnectionSingletonHelper.close(pstmtSearchByPosition);
			ConnectionSingletonHelper.close(pstmtSearchByAge);
			ConnectionSingletonHelper.close(pstmt);
			ConnectionSingletonHelper.close(stmt);
			ConnectionSingletonHelper.close(conn);
		} catch (Exception e) { e.printStackTrace(); }
	}

	public static void playerMenu() {
		System.out.println("\n============= PLAYER LIST =============");
		System.out.println("1. 선수 전체 정보\t\t\t ");
		System.out.println("2. 선수 정보 추가\t\t\t ");
		System.out.println("3. 선수 정보 수정\t\t\t ");
		System.out.println("4. 선수 정보 삭제\t\t\t ");
		System.out.println("5. 특정 조건 검색\t\t\t ");
		System.out.println("6. 메인메뉴로 돌아가기\t\t\t ");
		System.out.println("=======================================");
		System.out.print("메뉴선택 >> ");
	}
	
	public void menu() throws SQLException {
		
		while(true) {
			System.out.println();
			playerMenu();
			
			switch(sc.nextInt()) {
			case 1: 
				selectAll();
				break;
			case 2:
				insertPlayer();
				break;
			case 3:
				updatePlayer();
				break;
			case 4:
				deletePlayer();
				break;
			case 5:
				selectOption();
				break;
			default:
				return;
			}
		}
	} 
	
	public void selectAll() {
		try {
			rs = pstmtSelectAll.executeQuery(sqlSelectAll);
			
			int cnt = 0;
			System.out.println(String.format("%-4s | %-4s | %-3s | %-13s | %-3s | %-4s | %-4s | %-4s", "선수번호","팀코드","등번호","이름","포지션","키","몸무게","나이"));
			System.out.println("-----------------------------------------------------------------------------------");
			
			while(rs.next()) {
				int pno = rs.getInt("pno");
				int tcode = rs.getInt("tcode");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				String position = rs.getString("position");
			
				int len = 14 - (pname.getBytes().length - 2) / 3;
				int len2 = 10 - (tcode - 1) / 3;
				System.out.println(String.format("%-8d | %-7d | %-6d | %-" + len + "s | %-7s | %-4d | %-7d | %-7d", pno, tcode, uno, pname, position, height, weight, age));
				System.out.println("-----------------------------------------------------------------------------------");
				cnt++;
			}
			System.out.println("총" + cnt + "명의 선수가 검색되었습니다.");
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void insertPlayer() {
		try {
			String sql = "SELECT PNO FROM PLAYER WHERE PNO = ? ";
			pstmt = conn.prepareStatement(sql);
			System.out.println("선수등록을 위해 아래와 같은 조건으로 입력하세요.");
			System.out.print("선수고유번호 : ");
			int pno = sc.nextInt();
			pstmt.setInt(1, pno);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				System.out.println("이미 존재하는 선수번호가 있습니다.");
				System.out.println("선수메뉴로 돌아갑니다.");
				return;
			} else {
				System.out.print("팀코드(100 ~1200 중 선택) : ");
				int tcode = sc.nextInt();
				System.out.print("등번호 : ");
				int uno = sc.nextInt();
				System.out.print("이름 : ");
				String panme = sc.next();
				System.out.print("포지션 : ");
				String position = sc.next();
				System.out.print("키 : ");
				int height = sc.nextInt();
				System.out.print("몸무게 : ");
				int weight = sc.nextInt();
				System.out.print("나이 : ");
				int age = sc.nextInt();

				pstmtInsert.setInt(1, pno);
				pstmtInsert.setInt(2, tcode);
				pstmtInsert.setInt(3, uno);
				pstmtInsert.setString(4, panme);
				pstmtInsert.setInt(5, height);
				pstmtInsert.setInt(6, weight);
				pstmtInsert.setInt(7, age);
				pstmtInsert.setString(8, position);
				int result = pstmtInsert.executeUpdate();
				System.out.println(result + "명의 선수가 새롭게 등록되었습니다.");
			}
			// System.out.print("선수고유번호 : ");

			// selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updatePlayer() {
		try {
			String sql = "SELECT PNO FROM PLAYER WHERE PNO = ? ";
			pstmt = conn.prepareStatement(sql);
			System.out.println("선수 정보 수정을 진행합니다.");
			System.out.print("선수고유번호 : ");
			int pno = sc.nextInt();
			pstmt.setInt(1, pno);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				System.out.println("존재하는 선수번호가 없습니다.");
				System.out.println("선수메뉴로 돌아갑니다.");
				return;
			} else {
				System.out.print("팀코드(100 ~1200 중 선택) : ");
				int tcode = sc.nextInt();
				System.out.print("등번호 : ");
				int uno = sc.nextInt();
				System.out.print("이름 : ");
				String pname = sc.next();
				System.out.print("키 : ");
				int height = sc.nextInt();
				System.out.print("몸무게 : ");
				int weight = sc.nextInt();
				System.out.print("나이 : ");
				int age = sc.nextInt();
				System.out.print("포지션 : ");
				String position = sc.next();
				
				pstmtUpdate.setInt(1, tcode);
				pstmtUpdate.setInt(2, uno);
				pstmtUpdate.setString(3, pname);
				pstmtUpdate.setInt(4, height);
				pstmtUpdate.setInt(5, weight);
				pstmtUpdate.setInt(6, age);
				pstmtUpdate.setString(7, position);
				pstmtUpdate.setInt(8, pno);
				int result = pstmtUpdate.executeUpdate();
				System.out.println(result + "명의 선수가 수정되었습니다.");
			}
			
		} catch (Exception e) { e.printStackTrace(); }
		
	}

	public void deletePlayer() {
		try {
			String sql = "SELECT PNO FROM PLAYER WHERE PNO = ? ";
			pstmt = conn.prepareStatement(sql);
			System.out.println("삭제할 선수의 고유번호를 입력하세요");
			System.out.print("선수고유번호 : ");
			int pno = sc.nextInt();
			
			
			pstmt.setInt(1, pno);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				System.out.println("존재하는 선수가 없습니다.");
				System.out.println("선수메뉴로 돌아갑니다.");
				return;
			} else {
				pstmtDelete.setInt(1, pno);
				int result = pstmtDelete.executeUpdate();
				System.out.println(result + "명의 선수가 은퇴하였습니다.");
			}
			
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void selectOption() {
		try {
			while(true) {

				playerMenu();
				
				switch (sc.nextInt()){
				case 1: 
					searchByTcode();
					break;
				case 2:
					searchByPosition();
					break;
				case 3:
					searchByAge();
					break;
				case 4:
					return;
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void searchByTcode() {
		try {
			System.out.println("──────────── 구 단     목 록 ──────────────────");
			System.out.println(" 강원 │ 광주 │ 대구 │ 대전 │ 서울 │ 수원삼성");
			System.out.println(" 울산 │ 인천 │ 전북 │ 제주 │ 포항 │ 수원FC");
			System.out.println("───────────────────────────────────────────────");
			System.out.print("팀명 : ");
			pstmtSearchByTcode.setString(1, sc.next());
			rs = pstmtSearchByTcode.executeQuery();
			int cnt = 0;
			System.out.println(String.format("%-4s | %-10s | %-3s | %-13s | %-3s | %-4s | %-4s | %-4s", "선수번호","팀","등번호","이름","포지션","키","몸무게","나이"));
			System.out.println("-----------------------------------------------------------------------------------");

			while(rs.next()) {
				int pno = rs.getInt("pno");
				String tname = rs.getString("tname");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				String position = rs.getString("position");
				
				int len = 14 - (pname.getBytes().length - 2) / 3;
				int len2 = 10 - (tname.getBytes().length - 2) / 3;
				if (tname.equals("수원FC")) {len2 =9;};
				System.out.println(String.format("%-8d | %-" + len2 + "s | %-6d | %-" + len + "s | %-7s | %-4d | %-7d | %-7d", pno, tname, uno, pname, position, height, weight, age));
				System.out.println("-----------------------------------------------------------------------------------");
				cnt++;
			}
			System.out.println("총" + cnt + "명의 선수가 검색되었습니다.");
		} catch (Exception e) { e.printStackTrace(); }
		
	}
	
	public void searchByPosition() {
		try {
			System.out.print("찾는 포지션(FW,GK,DF,MF) : ");
			String position = sc.next().toUpperCase();;
			pstmtSearchByPosition.setString(1, position);
			rs = pstmtSearchByPosition.executeQuery();
			int cnt = 0;
			System.out.println(String.format("%-4s | %-10s | %-3s | %-13s | %-3s | %-4s | %-4s | %-4s", "선수번호","팀","등번호","이름","포지션","키","몸무게","나이"));
			System.out.println("-----------------------------------------------------------------------------------");

			while(rs.next()) {
				int pno = rs.getInt("pno");
				String tname = rs.getString("tname");
				//int tcode =rs.getInt("tcode");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				position = rs.getString("position");
				
				int len = 14 - (pname.getBytes().length - 2) / 3;
				int len2 = 10 - (tname.getBytes().length - 2) / 3;
				if (tname.equals("수원FC")) {len2 =9;};
				System.out.println(String.format("%-8d | %-" + len2 + "s | %-6d | %-" + len + "s | %-7s | %-4d | %-7d | %-7d", pno, tname, uno, pname, position, height, weight, age));
				System.out.println("-----------------------------------------------------------------------------------");
				cnt++;
			}
			System.out.println("총" + cnt + "명의 선수가 검색되었습니다.");
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void searchByAge() { // 나이대 검색
		try {
			System.out.println("나이대 검색");
			System.out.print("시작나이 : ");
			int startAge = sc.nextInt();
			System.out.print("끝나이 : " );
			int endAge = sc.nextInt();
			pstmtSearchByAge.setInt(1, startAge);
			pstmtSearchByAge.setInt(2, endAge);
			rs = pstmtSearchByAge.executeQuery();
			int cnt = 0;
			System.out.println(String.format("%-4s | %-10s | %-3s | %-13s | %-3s | %-4s | %-4s | %-4s", "선수번호","팀","등번호","이름","포지션","키","몸무게","나이"));
			System.out.println("-----------------------------------------------------------------------------------");

			while(rs.next()) {
				int pno = rs.getInt("pno");
				String tname =rs.getString("tname");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				String position = rs.getString("position");
				
				int len = 14 - (pname.getBytes().length - 2) / 3;
				int len2 = 10 - (tname.getBytes().length - 2) / 3;
				if (tname.equals("수원FC")) {len2 =9;};
				System.out.println(String.format("%-8d | %-" + len2 + "s | %-6d | %-" + len + "s | %-7s | %-4d | %-7d | %-7d", pno, tname, uno, pname, position, height, weight, age));
				System.out.println("-------------------------------------------------------------------------------------");
				System.out.println("-------------------------------------------------------------------------------------");
				cnt++;

			}
			System.out.println("총" + cnt + "명의 선수가 검색되었습니다.");
			
		} catch (Exception e) { e.printStackTrace(); }
		
	}

}
