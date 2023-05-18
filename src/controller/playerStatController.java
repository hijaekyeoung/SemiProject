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
import model.PlayerStatVO;

public class playerStatController {
	
	
	static Scanner sc = new Scanner(System.in);
	static Statement stmt = null;
	static ResultSet rs = null;
	static PreparedStatement pstmt = null;
	static Connection conn = null;
	
	public static void connect() {
		try {
			conn = ConnectionSingletonHelper.getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false); // 자동커밋 끄기, 주석처리를 하면 커밋이 된다.

		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
///////////// close //////////////////////////////////	
	
	public static void close() {
		try {
			
			ConnectionSingletonHelper.close(rs);
			ConnectionSingletonHelper.close(stmt);
			ConnectionSingletonHelper.close(pstmt);
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void selectAll() throws SQLException {
//        rs = stmt.executeQuery("SELECT * FROM playerStat");
	      
	      rs = stmt.executeQuery("SELECT rownum, pno,pname,tname,g_count,goal,assists,shots,shots_on_goal,"
	      		+ "foul,ycard,rcard,offside FROM playerStat");
	      
	      ResultSetMetaData rsmd = rs.getMetaData();
	      int count = rsmd.getColumnCount();
	      System.out.println(" \t선수");
	      System.out.println(" 순위\t고유번호\t선수이름\t소속팀\t 경기\t 득점\t어시스트\t 슈팅\t유효슈팅\t 파울\t옐로우\t 레드\t오프사이드");
	      System.out.println();
	      
	      while(rs.next()) {
	         for (int i =1; i<=count; i++) {

	            switch(rsmd.getColumnType(i)) {
	            		
	               case Types.NUMERIC:
	               case Types.INTEGER:
	                  System.out.print("  "+rs.getInt(i)+ "\t");
	                  break;
	                  
	               case Types.FLOAT:
	                  System.out.print(rs.getFloat(i)+ "\t");
	                  break;
	                  
	               case Types.DOUBLE:
	                  System.out.print(rs.getDouble(i)+ "\t");
	                  break;   
	               
	               case Types.CHAR:  
	                  System.out.print(rs.getString(i)+ "\t");
	                  break;   
	                  
	               case Types.DATE:
	                  System.out.print(rs.getDate(i)+ "\t");
	                  break;   
	                  
	                  default :
	                     System.out.print(rs.getString(i)+ "\t");
	                     break; 
	            }
	         }
	         System.out.println();
	         
	      }
	   }
		

	   
	   public static void delete() throws SQLException {
		   		
		      selectAll();
		      System.out.println("삭제할 선수의 고유번호를 입력하세요.");
		      sc.nextLine();
		      int pno = sc.nextInt();
		      
		      try {
		         pstmt = conn.prepareStatement("DELETE from playerStat WHERE pno = ?");
		         pstmt.setInt(1, pno);;
		         pstmt.executeUpdate();
		         System.out.println(pno + "번 선수의 정보가 삭제 되었습니다.");
		         
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		      
		   }
	   
	   
	   
//	   selectByxxx();  득점왕, 도움왕, 슈팅1등, 유효1등,(전체, 특정팀(join))  
	   
	   public static void selectAllAssists() throws SQLException { // 경기당 득점
		      rs = stmt.executeQuery("select pname, tname, g_count, assists "
		      		+ "from playerStat "
		      		+ "order by assists desc");
		      
		      ResultSetMetaData rsmd = rs.getMetaData();
		      int count = rsmd.getColumnCount();
		      System.out.println("선수");
		      System.out.println("선수이름\t소속팀\t경기\t어시스트");
		      
		      while(rs.next()) {
		         for (int i =1; i<=count; i++) { //i<=count;
		      
		            switch(rsmd.getColumnType(i)) {
		            
		               case Types.NUMERIC:
		               case Types.INTEGER:
		                  System.out.print("  "+rs.getInt(i)+ "\t");
		                  break;
		                  
		               case Types.FLOAT:
		                  System.out.print(rs.getFloat(i)+ "\t\t");
		                  break;
		                  
		               case Types.DOUBLE:
		                  System.out.print(rs.getDouble(i)+ "\t\t");
		                  break;   
		               
		               case Types.CHAR:  
		                  System.out.print(rs.getString(i)+ "\t\t");
		                  break;   
		                  
		               case Types.DATE:
		                  System.out.print(rs.getDate(i)+ "\t\t");
		                  break;   
		                  
		                  default :
		                     System.out.print(rs.getString(i)+ "\t");
		                     break; 
		            }
		         }
		         
		         System.out.println();
		         
		      }
		   }
	   
	   
	   
	   
	   public static void selectAllByGoalsPerGame() throws SQLException { // 경기당 득점
		      rs = stmt.executeQuery("select pname, tname, g_count, goal, round(goal/g_count,2) "
		      		+ "from playerStat "
		      		+ "order by round(goal/g_count,2) desc");
		      
		      ResultSetMetaData rsmd = rs.getMetaData();
		      int count = rsmd.getColumnCount();
		      System.out.println("선수");
		      System.out.println("선수이름\t소속팀\t경기\t득점\t경기당 득점");
		      
		      while(rs.next()) {
		         for (int i =1; i<=count; i++) { //i<=count;
		      
		            switch(rsmd.getColumnType(1)) {
		            
		               case Types.NUMERIC:
		               case Types.INTEGER:
		                  System.out.print("  "+rs.getInt(i)+ "\t");
		                  break;
		                  
		               case Types.FLOAT:
		                  System.out.print(rs.getFloat(i)+ "\t\t");
		                  break;
		                  
		               case Types.DOUBLE:
		                  System.out.print(rs.getDouble(i)+ "\t\t");
		                  break;   
		               
		               case Types.CHAR:  
		                  System.out.print(rs.getString(i)+ "\t\t");
		                  break;   
		                  
		               case Types.DATE:
		                  System.out.print(rs.getDate(i)+ "\t\t");
		                  break;   
		                  
		                  default :
		                     System.out.print(rs.getString(i)+ "\t");
		                     break; 
		            }
		         }
		         
		         System.out.println();
		         
		      }
		   }
	   
	   
	   public static void selectAllByAssistsPerGame() throws SQLException { // 경기당 어시스트
		      rs = stmt.executeQuery("select pname, tname, g_count, assists, round(assists/g_count,2) "
		      		+ "from playerStat "
		      		+ "order by round(assists/g_count,2) desc");
		      
		      ResultSetMetaData rsmd = rs.getMetaData();
		      int count = rsmd.getColumnCount();
		      System.out.println("선수");
		      System.out.println("선수이름\t소속팀\t경기\t어시스트\t경기당 어시스트");
		      
		      while(rs.next()) {
		         for (int i =1; i<=count; i++) { //i<=count;
		      
		            switch(rsmd.getColumnType(1)) {
		            
		               case Types.NUMERIC:
		               case Types.INTEGER:
		                  System.out.print("  "+rs.getInt(i)+ "\t");
		                  break;
		                  
		               case Types.FLOAT:
		                  System.out.print(rs.getFloat(i)+ "\t\t");
		                  break;
		                  
		               case Types.DOUBLE:
		                  System.out.print(rs.getDouble(i)+ "\t\t");
		                  break;   
		               
		               case Types.CHAR:  
		                  System.out.print(rs.getString(i)+ "\t\t");
		                  break;   
		                  
		               case Types.DATE:
		                  System.out.print(rs.getDate(i)+ "\t\t");
		                  break;   
		                  
		                  default :
		                     System.out.print(rs.getString(i)+ "\t");
		                     break; 
		            }
		         }
		         
		         System.out.println();
		         
		      }
		   }
	   

		
} // playerStatController end		













