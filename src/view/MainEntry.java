package view;

import java.sql.SQLException;
import controller.gameResultController;



public class MainEntry {
	public static void main(String[] args) throws SQLException {
		gameResultController.connect();
		gameResultController.menu();
		System.out.println("축구 통계");


	}
}
