package view;

import java.sql.SQLException;

import controller.PlayerController;

public class MainEntry {
	public static void main(String[] args) throws SQLException {
		PlayerController pcon = new PlayerController();
		pcon.dbConnect();
		pcon.menu();
	}
}
