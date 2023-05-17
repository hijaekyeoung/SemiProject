package view;

import java.io.IOException;
import java.sql.SQLException;

import controller.TeamController;
import controller.VoteController;

public class MainEntry {
	public static void main(String[] args) throws SQLException, IOException {
		VoteController.connect();
		VoteController.selectByGno();
//		VoteController.selectByGnoEnd();
		
		TeamController.connect();
		TeamController.menu();
	}
}
