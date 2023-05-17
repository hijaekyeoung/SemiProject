package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.PlayerController;
import controller.TeamController;
import controller.VoteController;
import controller.gameResultController;

public class MainEntry {
	public static void main(String[] args) throws SQLException, IOException {
//		gameResultController.connect();
//		gameResultController.gameresultMenu();
//		PlayerController pcon = new PlayerController();
//	    pcon.dbConnect();
//	    pcon.menu();

		VoteController.connect();
		VoteController.menu();
		
//		TeamController.connect();
//		TeamController.menu();
	}
}
