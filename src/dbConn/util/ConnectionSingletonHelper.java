package dbConn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionSingletonHelper {
   private static Connection conn;

   private ConnectionSingletonHelper() {
   }

   public static Connection getConnection() {
      if (conn != null) {
         return conn;
      }
      try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "soccer", "oracle");

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         return conn;
      }
   }

   public static void close(Connection conn) {
      try {
         if (conn != null) {
            if (!conn.isClosed()) {
               conn.close();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void close(Statement stmt) {
      try {
         if(stmt != null) {
            if(!stmt.isClosed()) {
               stmt.close();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void close(PreparedStatement pstmt) {
      try {
         if(pstmt != null) {
            if(!pstmt.isClosed()) {
               pstmt.close();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void close(ResultSet rs) {
      try {
         if(rs != null) {
            if(!rs.isClosed()) {
               rs.close();
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public static void menu() {
      System.out.println("\n ============ PLAYER LIST ===========");
      System.out.println("\t 1. 선수 리스트");
      System.out.println("\t 2. 선수 정보 추가 ");
      System.out.println("\t 3. 선수 정보 수정 ");
      System.out.println("\t 4. 선수 정보 삭제 ");
      System.out.println("\t 5. 조건에 의한 검색(ex> gno) ");
      System.out.println("\t 6. 프로그램 종료 ");
      System.out.println("\t >> 원하는 메뉴를 선택하세요.");
   }
}