package view;

import java.sql.SQLException;

import controller.PlayerController;
import controller.gameResultController;



public class MainEntry {
	public static void main(String[] args) throws SQLException {
		gameResultController.connect();
		gameResultController.gameresultMenu();
		PlayerController pcon = new PlayerController();
	    pcon.dbConnect();
	    pcon.menu();
	}
}
