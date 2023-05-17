package view;

import java.sql.SQLException;

import controller.playerStatController;
import dbConn.util.ConnectionSingletonHelper;

public class MainEntry {
	public static void main(String[] args) throws SQLException {
		System.out.println("축구 통계");
		
		playerStatController.connect();

		
		playerStatController.playerStat();
		
		
		
		
	}
}
