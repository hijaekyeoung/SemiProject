package view;


import java.sql.SQLException;
import java.io.IOException;
import java.sql.SQLException;

import controller.PlayerController;
import controller.TeamController;
import controller.VoteController;
import controller.gameResultController;
import controller.playerStatController;

public class MainEntry {
	public static void main(String[] args) throws SQLException, IOException {
//		playerStatController.connect();
//		playerStatController.playerStat();
		playerStatController.connect();
		playerStatController.playerStat();
		gameResultController.connect();
		gameResultController.gameresultMenu();
		PlayerController pcon = new PlayerController();
	  pcon.dbConnect();
	  pcon.menu();

		VoteController.connect();
		VoteController.selectByGno();
//		VoteController.selectByGnoEnd();
		
		TeamController.connect();
		TeamController.menu();
	}
}



