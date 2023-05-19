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
   
   public static void selectAllTest() throws SQLException{ // 1. 모든 필드 상위 전체팀 10명 출력 (1번은 전체 선수 출력)
	   
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY GOAL DESC"); // 전체 선수 출력 (골 기준)
			
	   ArrayList<PlayerStatVO> list = new ArrayList();
	   
	   while(rs.next()) {
		   PlayerStatVO playerStat = new PlayerStatVO();
		   playerStat.setPno(rs.getInt(1));  			// 고유번호
		   playerStat.setPname(rs.getString(2));		// 선수이름
		   playerStat.setTname(rs.getString(3));		// 소속팀
		   playerStat.setGcount(rs.getInt(4));			// 경기
		   playerStat.setGoal(rs.getInt(5));			// 득점
		   playerStat.setAssists(rs.getInt(6));			// 도움
		   playerStat.setShots(rs.getInt(7));			// 슈팅
		   playerStat.setShot_on_goal(rs.getInt(8));	// 유효슈팅
		   playerStat.setFoul(rs.getInt(9));			// 파울
		   playerStat.setYcard(rs.getInt(10));			// 경고 (옐로우)
		   playerStat.setRcard(rs.getInt(11));   		// 퇴장 (레드)
		   playerStat.setOffside(rs.getInt(12));		// 오프사이드
		   list.add(playerStat); 
	   }

	   System.out.printf(" %-2s | %-8s | %-7s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s\n", "고유번호", "선수이름", "소속팀", "경기", "득점", "도움","슈팅","유효슈팅","파울","경고","퇴장","오프사이드");
	   System.out.println("=======================================================================================================================");
	   System.out.println();

	   int len; // 선수이름 len
	   int len2; // 소속팀 len
	   for (PlayerStatVO playerStat : list) {
		   if(playerStat.getPname().getBytes().length == 15) { // 에르난데스, 팔로세비치
			   len = 6;
			   
		   }else if(playerStat.getPname().getBytes().length == 16) { // 유리 조나탄
			   len= 5;
			   
		   }else if(playerStat.getPname().getBytes().length == 6) { // 라스
			   len = 9;
			   
		   }else if(playerStat.getPname().getBytes().length == 12) { // 윤빛가람
			   len = 7;
			   
		   }else if(playerStat.getPname().getBytes().length == 13) { // 하파 실바
			   len = 7;
		   }  
		   
		   else {
			   len = 8;
		   }
		   
		   if (playerStat.getTname().equals("수원삼성")) {
				len2 = 6;
			} else {
				len2 = 8;
			}

			System.out.printf(" %-8d |  %-"+len+ "s | %-" + len2 + "s | %-5d | %-5d | %-5d | %-5d | %-8d | %-5d | %-5d | %-5d | %-5d \n", 

		   playerStat.getPno(),				// 고유번호
		   playerStat.getPname(),			// 선수이름
		   playerStat.getTname(),			// 소속팀
		   playerStat.getGcount(),			// 경기
		   playerStat.getGoal(),			// 득점
		   playerStat.getAssists(),			// 도움
		   playerStat.getShots(),			// 슈팅
		   playerStat.getShot_on_goal(),	// 유효슈팅
		   playerStat.getFoul(),			// 파울
		   playerStat.getYcard(),			// 경고
		   playerStat.getRcard(),			// 퇴장
		   playerStat.getOffside());			// 오프사이드
		   
		   System.out.println("--------------------------------------------------------------------------------------------------------------");
		   
	   }
		System.out.println();
   }// selectAllTest end

   public static void selectAll(int asc) throws SQLException{ // 1. 모든 필드 상위 전체팀 10명 출력 (1번은 전체 선수 출력)
	   
	   switch (asc) { // 
		case 1 :
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY GOAL DESC"); // 전체 선수 출력 (골 기준)
			break;
		case 2:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY G_COUNT DESC"); // 경기수 정렬 
			break;
		case 3:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY ASSISTS DESC FETCH FIRST 10 ROW ONLY"); // 도움 정렬
			break;
		case 4:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY SHOTS DESC FETCH FIRST 10 ROW ONLY"); // 슈팅 정렬
			break;
		case 5:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY SHOTS_ON_GOAL DESC FETCH FIRST 10 ROW ONLY"); // 유효슈팅 정렬
			break;
		case 6:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY FOUL DESC FETCH FIRST 10 ROW ONLY"); // 파울 정렬
			break;
		case 7:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY YCARD DESC FETCH FIRST 10 ROW ONLY"); // 옐로우 정렬
			break;
		case 8:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY RCARD DESC FETCH FIRST 10 ROW ONLY"); // 레드 정렬
			break;
		case 9:
			rs = stmt.executeQuery("SELECT PNO, PNAME,TNAME, G_COUNT, GOAL, ASSISTS, SHOTS, SHOTS_ON_GOAL, FOUL, YCARD, RCARD, OFFSIDE FROM PLAYERSTAT ORDER BY OFFSIDE DESC FETCH FIRST 10 ROW ONLY"); // 오프사이드 정렬
			break;

		default :
			return; //이전으로
		}

	   ArrayList<PlayerStatVO> list = new ArrayList();
	   
	   while(rs.next()) {
		   PlayerStatVO playerStat = new PlayerStatVO();
		   playerStat.setPno(rs.getInt(1));  			// 고유번호
		   playerStat.setPname(rs.getString(2));		// 선수이름
		   playerStat.setTname(rs.getString(3));		// 소속팀
		   playerStat.setGcount(rs.getInt(4));			// 경기
		   playerStat.setGoal(rs.getInt(5));			// 득점
		   playerStat.setAssists(rs.getInt(6));			// 도움
		   playerStat.setShots(rs.getInt(7));			// 슈팅
		   playerStat.setShot_on_goal(rs.getInt(8));	// 유효슈팅
		   playerStat.setFoul(rs.getInt(9));			// 파울
		   playerStat.setYcard(rs.getInt(10));			// 경고 (옐로우)
		   playerStat.setRcard(rs.getInt(11));   		// 퇴장 (레드)
		   playerStat.setOffside(rs.getInt(12));		// 오프사이드
		   list.add(playerStat); 
	   }

	   System.out.printf(" %-2s | %-8s | %-7s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s | %-3s\n", "고유번호", "선수이름", "소속팀", "경기", "득점", "도움","슈팅","유효슈팅","파울","경고","퇴장","오프사이드");
	   System.out.println("=======================================================================================================================");
	   System.out.println();

	   int len; // 선수이름 len
	   int len2; // 소속팀 len
	   for (PlayerStatVO playerStat : list) {
		   if(playerStat.getPname().getBytes().length == 15) { // 에르난데스, 팔로세비치
			   len = 6;
			   
		   }else if(playerStat.getPname().getBytes().length == 16) { // 유리 조나탄
			   len= 5;
			   
		   }else if(playerStat.getPname().getBytes().length == 6) { // 라스
			   len = 9;
			   
		   }else if(playerStat.getPname().getBytes().length == 12) { // 윤빛가람
			   len = 7;
			   
		   }else if(playerStat.getPname().getBytes().length == 13) { // 하파 실바
			   len = 7;
		   }  
		   
		   else {
			   len = 8;
		   }
		   
		   if (playerStat.getTname().equals("수원삼성")) {
				len2 = 6;
			} else {
				len2 = 8;
			}

			System.out.printf(" %-8d |  %-"+len+ "s | %-" + len2 + "s | %-5d | %-5d | %-5d | %-5d | %-8d | %-5d | %-5d | %-5d | %-5d \n", 

		   playerStat.getPno(),				// 고유번호
		   playerStat.getPname(),			// 선수이름
		   playerStat.getTname(),			// 소속팀
		   playerStat.getGcount(),			// 경기
		   playerStat.getGoal(),			// 득점
		   playerStat.getAssists(),			// 도움
		   playerStat.getShots(),			// 슈팅
		   playerStat.getShot_on_goal(),	// 유효슈팅
		   playerStat.getFoul(),			// 파울
		   playerStat.getYcard(),			// 경고
		   playerStat.getRcard(),			// 퇴장
		   playerStat.getOffside());			// 오프사이드
		   
		   System.out.println("--------------------------------------------------------------------------------------------------------------");
		   
	   }
		System.out.println();
		System.out.printf("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s", "\t0. 돌아가기\n", "\t1. 전체 선수\n", "\t2. 경기수 전체 출력\n", "\t3. 도움 상위 10명\n", "\t4. 슈팅 상위 10명\n", "\t5. 유효슈팅 상위 10명\n", "\t6. 파울 상위 10명\n","\t7. 옐로우카드 상위 10명\n","\t8. 퇴장 상위 10명\n", "\t9. 오프사이드 상위 10명\n");
		selectAll(sc.nextInt());
   }// selectAll end

   public static void playerStatUpdate() throws SQLException { // 2. 선수 스탯 업데이트    완성!
 	  
	  selectAllTest(); // 전체 선수 스탯 리스트 함수
 	  System.out.println();
 	  System.out.print("스탯을 갱신할 선수 고유번호를 입력하시오: ");
 	  int pno =sc.nextInt();
 	  System.out.print("직전 경기 득점: "); 					
 	  int goal = sc.nextInt();
 	  System.out.print("직전 경기 도움: "); 				
 	  int assists = sc.nextInt();
 	  System.out.print("직전 경기 슈팅수: "); 				
 	  int shots = sc.nextInt();
 	  System.out.print("직전 경기 유효슈팅수: "); 		
 	  int shots_on_goal = sc.nextInt();
 	  System.out.print("직전 경기 파울수: "); 					
 	  int foul = sc.nextInt();
 	  System.out.print("직전 경기 옐로우 카드: "); 				
 	  int ycard = sc.nextInt();
 	  System.out.print("직전 경기 레드 카드: "); 				
 	  int rcard = sc.nextInt();
 	  System.out.print("직전 경기 오프사이드: "); 			
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

 	  		System.out.println(result+ "명의 스탯이 업데이트 되었습니다.");

		} catch (Exception e) {e.printStackTrace();}
   }

      public static void delete() throws SQLException { // 3. 선수 삭제   완성!
            try {
               selectAllTest();
               System.out.println("삭제할 선수의 고유번호를 입력하세요.");
               System.out.print("선수고유번호 : ");
               int pno = sc.nextInt();
               pstmt = conn.prepareStatement("DELETE from playerStat WHERE pno = ?");
               pstmt.setInt(1, pno);;
               int result = pstmt.executeUpdate();
               System.out.println(result + "명 선수의 정보가 삭제 되었습니다.");
            } catch (Exception e) {e.printStackTrace();}
      }
      
      public static void selectAllAssists() throws SQLException { // 4. 어시스트 전체 1 어시스트 이상 전체 출력!
//    	  "SELECT PNAME,TNAME, G_COUNT, ASSISTS FROM PLAYERSTAT ORDER BY ASSISTS DESC FETCH FIRST 10 ROW ONLY"
//    	  "select pname, tname, g_count, assists from playerstat where assists >= 1 order by assists desc"
    	  
    	  String sql = "SELECT PNAME,TNAME, G_COUNT, ASSISTS FROM PLAYERSTAT ORDER BY ASSISTS DESC FETCH FIRST 10 ROW ONLY";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
         
          System.out.println(String.format("%-13s | %-1s | %-4s | %-2s", "이름","팀","경기","도움"));
        System.out.println("------------------------------------------------");
         while(rs.next()) {
            String pname = rs.getString("pname");
            String tname = rs.getString("tname");
            int g_count = rs.getInt("g_count");
            int assists = rs.getInt("assists");
            
            int len = 14 - (pname.getBytes().length - 1) / 3;
            int len2 = 12 - (tname.getBytes().length - 2) / 3; 
            if (tname.equals("수원FC")) {len2 =11;};
            System.out.println(String.format("%-"+len+"s | %-"+len2+"s | %-6d | %-2d", pname,tname,g_count,assists));
           System.out.println("------------------------------------------------");
         }
    }

      public static void selectAllByGoalsPerGame() throws SQLException { // 5. 경기당 득점 1~10 위까지
    	  
    	  String sql = "select pname, tname, g_count, goal, round(goal/g_count,2) from playerstat order by round(goal/g_count,2) desc FETCH FIRST 10 ROW ONLY";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
         
          System.out.println(String.format("%-13s | %-12s | %-4s | %-3s  |%-2s ", "이름","팀","경기","득점","경기당 득점"));
        System.out.println("-------------------------------------------------------------");
         while(rs.next()) {
            String pname = rs.getString("pname");
            String tname = rs.getString("tname");
            int g_count = rs.getInt("g_count");
            int goal = rs.getInt("goal");
            double goalsPerGame = rs.getDouble("round(goal/g_count,2)");
            
            int len = 14 - (pname.getBytes().length - 1) / 3;
            int len2 = 12 - (tname.getBytes().length - 2) / 3; 
            if (tname.equals("수원FC")) {len2 =11;};
  
            System.out.println(String.format("%-"+len+"s | %-"+len2+"s | %-6d | %-6d | %.2f", pname,tname,g_count,goal,goalsPerGame));
           System.out.println("-----------------------------------------------------------");
         }
    }
    	  
      public static void selectAllByAssistsPerGame() throws SQLException { // 6. 경기당 어시스트
    	  
    	  String sql = "select pname, tname, g_count, assists, round(assists/g_count,2) from playerstat order by round(assists/g_count,2) desc FETCH FIRST 10 ROW ONLY";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
         
          System.out.println(String.format("%-13s | %-12s | %-4s | %-3s  | %-3s ", "이름","팀","경기","도움","경기당 도움"));
          System.out.println("===========================================================");
         while(rs.next()) {
            String pname = rs.getString("pname");
            String tname = rs.getString("tname");
            int g_count = rs.getInt("g_count");
            int assists = rs.getInt("assists");
            double assistsPerGame = rs.getDouble("round(assists/g_count,2)");
            
            int len = 14 - (pname.getBytes().length - 1) / 3;
            int len2 = 12 - (tname.getBytes().length - 2) / 3; 
            if (tname.equals("수원FC")) {len2 =11;};
            System.out.println(String.format("%-"+len+"s | %-"+len2+"s | %-6d | %-6d | %.2f", pname,tname,g_count,assists,assistsPerGame));
           System.out.println("-----------------------------------------------------------");
         }  
     }
      
      public static void selectAllByShotsPerGame() throws SQLException { // 7. 경기당 슈팅
    	  
    	  String sql = "select pname, tname, g_count, shots, round(shots/g_count,2) from playerstat order by round(shots/g_count,2) desc FETCH FIRST 10 ROW ONLY";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
          
        System.out.println(String.format("%-13s | %-12s | %-2s | %-4s  |%-2s ", "이름","팀","경기","슈팅","경기당 슈팅"));
        System.out.println("-------------------------------------------------------------");
        
         while(rs.next()) {
            String pname = rs.getString("pname");
            String tname = rs.getString("tname");
            int g_count = rs.getInt("g_count");
            int shots = rs.getInt("shots");
            double shotsPerGame = rs.getDouble("round(shots/g_count,2)");
            
            int len = 14 - (pname.getBytes().length - 1) / 3;
            int len2 = 12 - (tname.getBytes().length - 2) / 3; 
            if (tname.equals("수원FC")) {len2 =11;};
            System.out.println(String.format("%-"+len+"s | %-"+len2+"s | %-4d | %-7d | %.2f", pname,tname,g_count,shots,shotsPerGame));
           System.out.println("-----------------------------------------------------------");
         }
      }
      
      public static void selectAllByShots_on_goalPerGame() throws SQLException { // 8. 경기당 유효슈팅
    	  
    	  String sql = "select pname, tname, g_count, SHOTS_ON_GOAL, round(SHOTS_ON_GOAL/g_count,2) from playerstat order by round(SHOTS_ON_GOAL/g_count,2) desc FETCH FIRST 10 ROW ONLY";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
         
          System.out.println(String.format("%-13s | %-12s | %-3s | %-3s  |%-2s ", "이름","팀","경기","유효슈팅","경기당 유효슈팅"));
        System.out.println("-------------------------------------------------------------");
         while(rs.next()) {
            String pname = rs.getString("pname");
            String tname = rs.getString("tname");
            int g_count = rs.getInt("g_count");
            int shots_On_Goal = rs.getInt("SHOTS_ON_GOAL");
            double shots_On_GoalPerGame = rs.getDouble("round(SHOTS_ON_GOAL/g_count,2)");
            
            int len = 14 - (pname.getBytes().length - 1) / 3;
            int len2 = 12 - (tname.getBytes().length - 2) / 3; 
            if (tname.equals("수원FC")) {len2 =11;};
            
            
            System.out.println(String.format("%-"+len+"s | %-"+len2+"s | %-5d | %-9d | %.2f", pname,tname,g_count,shots_On_Goal,shots_On_GoalPerGame));
           System.out.println("-----------------------------------------------------------");
         }
      }
} // playerStatController end      