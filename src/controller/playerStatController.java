package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Scanner;
import dbConn.util.ConnectionSingletonHelper;
import model.PlayerStatVO;
import model.TeamVO;
import view.SoccerMenu;

public class playerStatController {
   
   static Scanner sc = new Scanner(System.in);
   static Statement stmt = null;
   static ResultSet rs = null;
   static PreparedStatement pstmtSelectAll ,pstmt,selectAll;
   static Connection conn = null;
   
   
   
   static String sqlSelectAll = "SELECT * FROM PLAYERSTAT";
   
   
   
   
   public static void connect() {
      try {
         conn = ConnectionSingletonHelper.getConnection();
         stmt = conn.createStatement();
         
         pstmtSelectAll = conn.prepareStatement(sqlSelectAll);
         
         
         
         
         
         
         
         
         
         conn.setAutoCommit(false); // 자동커밋 끄기, 주석처리를 하면 커밋이 된다.

      } catch (Exception e) {e.printStackTrace();}   
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
   
   
   
//   public static void selectAll(int asc) throws SQLException{ // 모든 필드 상위 10명 출력
//	   
//	   switch (asc) { // GD = 득실차
//		case 1 :
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY G_COUNT DESC FETCH FIRST 10 ROW ONLY"); // 승정렬 
//			break;
//		case 2:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY GOAL DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//		case 3:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY ASSISTS DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//		case 4:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY SHOTS DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//		case 5:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY SHOTS_ON_GOAL DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//		case 6:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY FOUL DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//		case 7:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY YCARD DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//		case 8:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY RCARD DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//		case 9:
//			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD OFFSIDE FROM PLAYERSTAT ORDER BY OFFSIDE DESC FETCH FIRST 10 ROW ONLY"); // 패정렬
//			break;
//
//
//		default :
//			return; //이전으로
//		}
//	   
//	   ArrayList<PlayerStatVO> list = new ArrayList();
//	   
////	   SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, 
////	   SHOTS, SHOTS_ON_GOAL, 
////	   FOUL, YCARD, RCARD OFFSIDE 
////	   FROM PLAYERSTAT ORDER BY G_COUNT DESC
//	   
//	   while(rs.next()) {
//		   PlayerStatVO playerStat = new PlayerStatVO();
//		   playerStat.setPno(rs.getInt(1));
//		   playerStat.setPname(rs.getString(2));
//		   playerStat.setTname(rs.getString(3));
//		   playerStat.setGcount(rs.getInt(4));
//		   playerStat.setGoal(rs.getInt(5));
//		   playerStat.setAssists(rs.getInt(6));
//		   playerStat.setShots(rs.getInt(7));
//		   playerStat.setShot_on_goal(rs.getInt(8));
//		   playerStat.setFoul(rs.getInt(9));
//		   playerStat.setYcard(rs.getInt(10));
//		   playerStat.setRcard(rs.getInt(11));
//		   playerStat.setOffside(rs.getInt(12));
//		   list.add(playerStat);
//		   
//	   }
//	   
//	   System.out.println("고유번호\t선수이름\t소속팀\t 경기\t 득점\t어시스트\t 슈팅\t유효슈팅\t 파울\t옐로우\t 레드\t오프사이드");
//	   System.out.println("==================================================================================");
//	   System.out.println();
//	   
//	  
//	   
//	   for (PlayerStatVO playerStat : list) {
//  //  System.out.println("TNAME : " + team.getTname() + "\tTWIN : " + team.getTwin() + "\tTDRAW : " + team.getTdraw() + "\tTLOSE : " + team.getTlose() + "\tTSCORE : " + team.getTscore() + "\tTCONCEDED : " + team.getTconceded()+ "\tGD : " + team.getGd());
//		   System.out.println("");
//		   
//		   playerStat.getPno();
//		   playerStat.getPname();
//		   playerStat.getTname();
//		   playerStat.getGcount();
//		   playerStat.getGoal();
//		   playerStat.getAssists();
//		   playerStat.getShots();
//		   playerStat.getShot_on_goal();
//		   playerStat.getFoul();
//		   
//		   
//		   System.out.println("");
//		   
//		   
//	   }
//
//	   
//   }
   
   
   
   public static void selectAll() {
	   try {
		   rs = pstmtSelectAll.executeQuery(sqlSelectAll);
		   
		   int cnt = 0;
		   System.out.println("  선수이름     |  \t소속팀     | 경기 | 득점 | 도움 | 슈팅 | 유효슈팅 | 파울 | 경고 | 퇴장 | 오프사이드");
		   System.out.println("----------------------------------------------------------------------------------------------------");
		   while(rs.next()) {
			   
//				private int pno; // 선수고유번호
//				private String pname, tname; // 선수이름, 팀명
//				private int gcount, goal, assists, shots, // 출전경기수, 득점, 도움, 슈팅
//				shot_on_goal, foul, ycard, rcard, offside; // 유효슈팅, 파울, 경고, 퇴장, 오프사이드			   
			   
			   
			   String pname = rs.getString("pname");
			   String tname = rs.getString("tname");
			   int g_count = rs.getInt("g_count");
			   int goal = rs.getInt("goal");
			   int assists = rs.getInt("assists");
			   int shots = rs.getInt("shots");
			   int shots_on_goal = rs.getInt("shots_on_goal");
			   int foul = rs.getInt("foul");
			   int ycard = rs.getInt("ycard");
			   int rcard = rs.getInt("rcard");
			   int offside = rs.getInt("offside");
			   
//	            int len = 7 - (pname.getBytes().length - 2) / 10;
//	            int len2 =10 - (tname.getBytes().length - 1) / 10; 
	            
	            
//	            int len3 = 10 - (g_count - 1) / 3; 
//	            int len4 = 10 - (goal - 1) / 3; 
//	            int len5 = 10 - (assists - 1) / 3; 
//	            int len6 = 10 - (shots - 1) / 3; 
//	            int len7 = 10 - (shots_on_goal.getBytes().length - 1) / 3; 
//	            int len8 = 10 - (foul.getBytes().length - 1) / 3; 
//	            int len9 = 10 - (ycard.getBytes().length - 1) / 3; 
//	            int len10 = 10 - (rcard.getBytes().length - 1) / 3; 
//	            int len11 = 10 - (offside.getBytes().length - 1) / 3; 
	            
	            
//	            System.out.println(String.format("%-"+len+"s    | %-"+len2+"s | %-3d "
//	            		+ "| %-3d | %-2d | %-3d | %-6d | %-4d | %-2d | %-3d"
//	            		+ " | %-2d "
//	            		, pname,tname,g_count,goal,assists,shots,shots_on_goal,foul,ycard,rcard,offside)); // 11
	            
			   
				int len = 14 - (pname.getBytes().length - 1) / 3;  //  12
				int len2 = 10 - (tname.getBytes().length - 1) / 3; //  
				System.out.println(String.format("%-10s | %-" + len + "s | %-6d | %-" + len2 + "d | %-7d | %-4d | %-7d | %-7d | %-7d | %-7d | %-7d", pname,tname,g_count,goal,assists,shots,shots_on_goal,foul,ycard,rcard,offside));
				System.out.println("-----------------------------------------------------------------------------------");
	            

		   }
	} catch (Exception e) {e.printStackTrace();}
   }
   
   
   
   
   
//   public static void selectAll() throws SQLException {     // 1. 전체 선수 스탯 출력
//
//
//         rs = stmt.executeQuery("SELECT rownum, pno,pname,tname,g_count,goal,assists,shots,shots_on_goal,"
//               + "foul,ycard,rcard,offside FROM playerStat");
//         
//         ResultSetMetaData rsmd = rs.getMetaData();
//         int count = rsmd.getColumnCount();
//         
//         System.out.println();
//         System.out.println("============================== 전체 선수 스탯 리스트 =============================================================="); // 골 값으로 desc 되어있음
//         System.out.println();
//         System.out.println(" 순위\t고유번호\t선수이름\t소속팀\t 경기\t 득점\t어시스트\t 슈팅\t유효슈팅\t 파울\t옐로우\t 레드\t오프사이드");
//         System.out.println();
//         
//         while(rs.next()) {
//            for (int i =1; i<=count; i++) {
//
//               switch(rsmd.getColumnType(i)) {
//                     
//                  case Types.NUMERIC:
//                  case Types.INTEGER:
//                     System.out.print("  "+rs.getInt(i)+ "\t");
//                     break;
//                     
//                  case Types.FLOAT:
//                     System.out.print(rs.getFloat(i)+ "\t");
//                     break;
//                     
//                  case Types.DOUBLE:
//                     System.out.print(rs.getDouble(i)+ "\t");
//                     break;   
//                  
//                  case Types.CHAR:  
//                     System.out.print(rs.getString(i)+ "\t");
//                     break;   
//                     
//                  case Types.DATE:
//                     System.out.print(rs.getDate(i)+ "\t");
//                     break;   
//                     
//                     default :
//                        System.out.print(rs.getString(i)+ "\t");
//                        break; 
//               }
//            }
//            System.out.println();
//            
//
//            
//         }
//         System.out.println();
//         System.out.println(" 순위\t고유번호\t선수이름\t소속팀\t 경기\t 득점\t어시스트\t 슈팅\t유효슈팅\t 파울\t옐로우\t 레드\t오프사이드");
//         System.out.println("==================================================================================================================");
//      }
      
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

