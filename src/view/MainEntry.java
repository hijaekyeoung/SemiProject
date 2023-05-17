package view;

import java.sql.SQLException;

import controller.PlayerController;
import controller.TeamController;
import controller.VoteController;
import controller.gameResultController;
import controller.playerStatController;

import java.io.IOException;

public class MainEntry {

	public static void main(String[] args) throws SQLException, IOException {
		
		SoccerMenu mainMenu = new SoccerMenu();
		PlayerController pcon = new PlayerController();
		pcon.dbConnect();
		TeamController.connect();
		VoteController.connect();
		playerStatController.connect();
		gameResultController.connect();
		mainMenu.soccerMenu();
		pcon.close();
		TeamController.close();
		VoteController.close();
		playerStatController.close();
		gameResultController.close();
	
	}


}
