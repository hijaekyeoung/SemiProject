package dbConn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSingletonHelper {

	   private static Connection conn;
	   private ConnectionSingletonHelper() { }
	   
	   public static Connection getConnection() {
	      if (conn !=null) {
	         return conn;
	      }
	      try {
	         Class.forName("oracle.jdbc.OracleDriver");
	         conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","project", "oracle");
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         return conn;
	      }
	   }

	   public static void close() throws SQLException  {
	      if( conn!=null) {
	         if(!conn.isClosed()) {
	            conn.close();
	         }
	      }
	   }
	   public static void menu() {
			System.out.println("\n-=-=-=-=-= 메뉴 선택 =-=-=-=-=-");
			System.out.println("\t 0. rollback ");
			System.out.println("\t 1. 일정 조회 [사용자] ");
			
			System.out.println("\t 3. 경기 결과 등록 [관리자]");
			System.out.println("\t 4. 경기 일정 변경 [관리자]");
			System.out.println("\t 5. 경기 일정 추가[관리자]");
			System.out.println("\t 5. X ");
			System.out.println("\t 6. 프로그램 종료 ");
			System.out.println("\t 9. commit ");
			System.out.println("\t >> 원하는 메뉴 선택하세요. ");
	   
	   
	}
	   
}

