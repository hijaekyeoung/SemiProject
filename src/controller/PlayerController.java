package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;

import dbConn.util.ConnectionSingletonHelper;
import model.PlayerVO;

public class PlayerController {
	
	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static PreparedStatement pstmtInsert, pstmtDelete, pstmtUpdate;
	static PreparedStatement pstmtSelectAll, pstmtSearchByTcode, pstmtSearchByPosition;
	static PreparedStatement pstmtSearchByAge;
	static ResultSet rs = null;
	static Connection conn = null;

	private String sqlInsert = "insert into player values(?,?,?,?,?,?,?,?)";
	private String sqlDelete = "delete from player where pno = ? ";
	private String sqlUpdate = "update player set tcode = ?, uno = ?, pname = ?, "
			+ "height = ?, weight = ?, age = ?, position = ? where pno = ?";
	private static String sqlSelectAll = "select * from player";
	private String sqlSearchByTcode = "select p.pno, t.tname, p.uno, p.pname, p.height,"
			+ "p.weight, p.age, p.position from player p, team t \r\n"
			+ "    where (select tcode from team where tname = ?) = p.tcode and\r\n"
			+ "    p.tcode = t.tcode order by uno asc";
	private String sqlSearchByPosition = "select * from player where position = ?";
	private String sqlSearchByAge = "select * from player where age between ? and ? order by age asc";
	
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
			//conn.setAutoCommit(false);
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
			ConnectionSingletonHelper.close(stmt);
			ConnectionSingletonHelper.close(conn);
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
			while(rs.next()) {
				int pno = rs.getInt("pno");
				int tcode =rs.getInt("tcode");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				String position = rs.getString("position");
				System.out.println(pno + "||" + tcode + "||" + uno + "||" + pname + "||" 
						+ position + "||" + height + "||" + weight + "||" + age);
				cnt++;
			}
			System.out.println("총" + cnt + "명의 선수가 검색되었습니다.");
			
		} catch (Exception e) { e.printStackTrace(); }
		
	}

	public void searchByPosition() {
		try {
			System.out.print("찾는 포지션 : ");
			String position = sc.next();
			pstmtSearchByPosition.setString(1, position);
			rs = pstmtSearchByPosition.executeQuery();
			int cnt = 0;
			while(rs.next()) {
				int pno = rs.getInt("pno");
				int tcode =rs.getInt("tcode");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				position = rs.getString("position");
				System.out.println(pno + "||" + tcode + "||" + uno + "||" + pname + "||" 
						+ position + "||" + height + "||" + weight + "||" + age);
				cnt++;
			}
			System.out.println("총" + cnt + "명의 선수가 검색되었습니다.");
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void searchByTcode() {
		try {
			System.out.print("팀명 : ");
			pstmtSearchByTcode.setString(1, sc.next());
			rs = pstmtSearchByTcode.executeQuery();
			int cnt = 0;
			while(rs.next()) {
				int pno = rs.getInt("pno");
				String tname = rs.getString("tname");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				String position = rs.getString("position");
				
				System.out.println(pno + "||" + tname + "||" + uno + "||" + pname + "||" 
						+ position + "||" + height + "||" + weight + "||" + age);
				cnt++;
			}
			System.out.println("총" + cnt + "명의 선수가 검색되었습니다.");
		} catch (Exception e) { e.printStackTrace(); }
		
	}

	public void deletePlayer() {
		try {
			System.out.println("삭제할 선수의 고유번호를 입력하세요");
			System.out.print("선수고유번호 : ");
			int pno = sc.nextInt();
			pstmtDelete.setInt(1, pno);
			int result = pstmtDelete.executeUpdate();
			System.out.println(result + "명의 선수가 은퇴하였습니다.");
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void updatePlayer() {
		try {
			System.out.println("수정할 선수의 고유번호로 수정을 진행합니다.");
			System.out.print("선수고유번호 : ");
			int pno = sc.nextInt();
			System.out.print("팀코드 : ");
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
		} catch (Exception e) { e.printStackTrace(); }
		
	}

	public void insertPlayer() {
		try {
			System.out.println("선수등록을 위해 아래와 같은 조건으로 입력하세요.");
			System.out.print("선수고유번호 : ");
			int pno = sc.nextInt();
			System.out.print("팀코드 : ");
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
			//selectAll();
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void selectAll() {
		try {
			rs = pstmtSelectAll.executeQuery(sqlSelectAll);
			
			System.out.println("=============== 선수 명단 ===============");
			while(rs.next()) {
				int pno = rs.getInt("pno");
				int tcode = rs.getInt("tcode");
				int uno = rs.getInt("uno");
				String pname = rs.getString("pname");
				int height = rs.getInt("height");
				int weight = rs.getInt("weight");
				int age = rs.getInt("age");
				String position = rs.getString("position");
				
				System.out.println(pno + "||" + tcode + "||" + uno + "||" + pname + "||" 
				+ position + "||" + height + "||" + weight + "||" + age);
			}
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	
}
