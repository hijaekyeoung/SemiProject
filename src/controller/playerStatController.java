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
import view.SoccerMenu;

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
   
//   public static void menu() {
//      System.out.println("\n ============ PLAYER LIST ===========");
//      System.out.println("\t 0. commit");
//      System.out.println("\t 1. 전체 선수 스탯 리스트"); // 골 값으로 desc 되어있음
//      System.out.println("\t 2. 선수 스탯 업데이트");
//      System.out.println("\t 3. 선수 스탯 리스트 삭제");
//      System.out.println("\t 4. 전체 선수 어시스트");
//      System.out.println("\t 5. 경기당 득점률");
//      System.out.println("\t 6. 경기당 어시스트율 순위");
//      
//      
//      
//      
//      System.out.println("\t 9. 메인메뉴로 돌아가기");
//      System.out.println("\t 10. commit");
//      
//      System.out.println("=====================================");
//      
//      
//   }
   
   public static void playerStat() throws SQLException{     
      
      while(true) {
         
         System.out.println();

         SoccerMenu.playerStatMenu();
         System.out.println();
         System.out.println("옵션 선택하세요.");
         System.out.println();
         System.out.print("옵션 입력: ");
         System.out.println();
         switch(sc.nextInt()) {
            case 0 : System.out.println("Commit 하시겠습니까? (Y/N)");
                   System.out.println("안하시려면 Rollback 됩니다.");
                   if(sc.next().equalsIgnoreCase("Y")) {
                      
                      conn.commit(); // 예외발생
                      selectAll();
                   }else {
                      conn.rollback();
                      selectAll();    
                   }
            case 1: selectAll(); break; 
            case 2: playerStatUpdate(); break; 
            case 3: delete(); break;
            case 4: selectAllAssists(); break;
            case 5: selectAllByGoalsPerGame(); break; // 
            case 6: selectAllByAssistsPerGame(); break;
            
            
            case 8: return;
            case 9: conn.commit();
                  System.out.println("성공적으로 완료 되었습니다.");
                  
           
                  
         
         }
      }// end while
   }
   
   
   public static void selectAll() throws SQLException {     // 1. 전체 선수 스탯 출력


         rs = stmt.executeQuery("SELECT rownum, pno,pname,tname,g_count,goal,assists,shots,shots_on_goal,"
               + "foul,ycard,rcard,offside FROM playerStat");
         
         ResultSetMetaData rsmd = rs.getMetaData();
         int count = rsmd.getColumnCount();
         
         System.out.println();
         System.out.println("============================== 전체 선수 스탯 리스트 =============================================================="); // 골 값으로 desc 되어있음
         System.out.println();
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
         System.out.println();
         System.out.println(" 순위\t고유번호\t선수이름\t소속팀\t 경기\t 득점\t어시스트\t 슈팅\t유효슈팅\t 파울\t옐로우\t 레드\t오프사이드");
         System.out.println("==================================================================================================================");
      }
      
   public static void playerStatUpdate() throws SQLException { // 2. 선수 스탯 업데이트
 	  
 	  selectAll(); // 전체 선수 스탯 리스트 함수
 	  System.out.println();
 	  
 	  
 	  System.out.print("수정할 선수 고유번호를 입력하시오.");
 	  int pno =sc.nextInt();

 	  
 	  System.out.print("수정할 goal (득점): "); 					
 	  int goal = sc.nextInt();
 	  System.out.print("수정할 assists (도움): "); 				
 	  int assists = sc.nextInt();
 	  System.out.print("수정할 shots (슈팅수): "); 				
 	  int shots = sc.nextInt();
 	  System.out.print("수정할 shots_on_goal (유효슈팅): "); 		
 	  int shots_on_goal = sc.nextInt();
 	  System.out.print("수정할 foul (파울): "); 					
 	  int foul = sc.nextInt();
 	  System.out.print("수정할 ycard (옐로우): "); 				
 	  int ycard = sc.nextInt();
 	  System.out.print("수정할 rcard (레드): "); 				
 	  int rcard = sc.nextInt();
 	  System.out.print("수정할 offside (오프사이드): "); 			
 	  int offside = sc.nextInt();
 	  	
 	  	pstmt = conn.prepareStatement("SELECT pno, pname, tname, g_count, "
 	  								+ "goal, assists, shots, shots_on_goal,"
 	  								+ "foul, ycard, rcard, offside FROM playerStat "
 	  								+ "WHERE pno = ?");
 	  	pstmt.setInt(1, pno);
 	  	rs = pstmt.executeQuery();
 	  	rs.next();
 	  	pno = rs.getInt(1);
 	  	String pname = rs.getString(2);	
 	  	String tname = rs.getString(3);
 	  	
 	  	
 	  	int g_count1 = rs.getInt(4);
 	  	int goal1 = rs.getInt(5);
 	  	int assists1 = rs.getInt(6);
 	  	int shots1 = rs.getInt(7);
 	  	int shots_on_goal1 = rs.getInt(8);
 	  	int foul1 = rs.getInt(9);
 	  	int ycard1 = rs.getInt(10);
 	  	int rcard1 = rs.getInt(11);
 	  	int offside1 = rs.getInt(12);
 	  	try { 

 	  		
 	  		pstmt = conn.prepareStatement("update playerStat set g_count=?, goal=?, "
						+ "assists=?,shots=?,shots_on_goal=?,foul=?, ycard=?, rcard=?, offside=? where pno=?");
 	  	
 	  		pstmt.setInt(1, g_count1 + 1);
 	  		pstmt.setInt(2, goal + goal1);
 	  		pstmt.setInt(3, assists + assists1);
 	  		pstmt.setInt(4, shots + shots1);
 	  		pstmt.setInt(5, shots_on_goal + shots_on_goal1);
 	  		pstmt.setInt(6, foul+foul1);
 	  		pstmt.setInt(7, ycard+ycard1);
 	  		pstmt.setInt(8, rcard +rcard1);
 	  		pstmt.setInt(9, offside + offside1);
 	  		pstmt.setInt(10, pno);
 	  		int result = pstmt.executeUpdate();
 	  		System.out.println(result + " 선수 스탯이 업데이트 되었습니다.");
 	  		
 	  		
 	  		
 	  		
 	  		
				
		} catch (Exception e) {e.printStackTrace();}
 	  	
 	  	
 	  	

   }

      
      public static void delete() throws SQLException { // 3. 선수 삭제
               
            selectAll();
            System.out.println("삭제할 선수의 고유번호를 입력하세요.");
            
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
      
      
      
//      selectByxxx();  득점왕, 도움왕, 슈팅1등, 유효1등,(전체, 특정팀(join))  
      
      public static void selectAllAssists() throws SQLException { // 4. 어시스트 전체 1 어시스트 이상 전체 출력!
            rs = stmt.executeQuery("select pname, tname, g_count, assists "
                  + "from playerStat "
                  + "where assists >=1 "
                  + "order by assists desc"); 
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            System.out.println("선수");
            System.out.println("선수이름\t소속팀\t경기\t어시스트");
            System.out.println("======================================");
            
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
            System.out.println("======================================");
         }
      
      
      
      
      public static void selectAllByGoalsPerGame() throws SQLException { // 5. 경기당 득점
            rs = stmt.executeQuery("select pname, tname, g_count, goal, round(goal/g_count,2) "
                  + "from playerStat "
                  + "order by round(goal/g_count,2) desc"
                  + " FETCH FIRST 10 ROW ONLY"); // 경기당 득점 1~10위 까지 출력!
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            System.out.println();
            System.out.println("선수이름\t소속팀\t경기\t득점\t경기당 득점");
            System.out.println("========================================");
            
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
            System.out.println("========================================");
         }
       
      public static void selectAllByAssistsPerGame() throws SQLException { // 6. 경기당 어시스트
            rs = stmt.executeQuery("select pname, tname, g_count, assists, round(assists/g_count,2) "
                  + "from playerStat where assists >=1 "
                  + "order by round(assists/g_count,2) desc"); //" FETCH FIRST 10 ROW ONLY"
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            System.out.println();
            System.out.println("선수이름\t소속팀\t경기\t어시스트\t경기당 어시스트");
            System.out.println("=============================================");
            
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
            System.out.println("=============================================");
         }
      
      
      
      

} // playerStatController end      

