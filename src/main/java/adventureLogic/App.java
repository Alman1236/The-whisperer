package adventureLogic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gameElements.Inventory;
import gameElements.Room;
import types.AdvObjectsNames;

public class App {

	public static void main(String[] args) {
        //FileManager.deleteData();

		Parser.defineCommands();

		MainFrame frame = new MainFrame();

		AudioPlayerThread musicThread = new AudioPlayerThread();
		musicThread.run();

		startAdventure();
	}

	static public void startAdventure() {
		defineRooms();
		MainFrame.clear();
		Adventure.setInventory(new Inventory());
		MainFrame.updateInventoryUI();
		
		FileManager.loadData();

		Scoreboard.getCurrentRunData().onStartingToPlay();
		Adventure.getCurrentRoom().enterRoom();
	}

	static public void win() {
		MainFrame.updateOutputText("\n o-----------o VITTORIA! o-----------o");
		Scoreboard.getCurrentRunData().increaseScore(20);
		Scoreboard.getCurrentRunData().printCurrentRunData();
		Scoreboard.getCurrentRunData().setPlayerWonTrue();
		Parser.askForName();
	}
	
	static private void defineRooms() {

		ArrayList<Room> rooms = new ArrayList<Room>();

		try {
			String url = "jdbc:h2:./database";
			Connection connection = DriverManager.getConnection(url, "aldo", "");
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM ROOMS");

			while (rs.next()) {
				rooms.add(new Room(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), getObjectFromName(rs.getString(7)), rs.getBoolean(8), rs.getBoolean(9)));
			}

			rs.close();
			stm.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		Adventure.setRoomList(rooms);
		Adventure.setCurrentRoom(Adventure.GetRoomByName("Entrata"));
	}

	private static AdvObjectsNames getObjectFromName(String name) {
		if (name == null) {
			return AdvObjectsNames.NONE;
		} else if (name.equals("pick")) {
			return AdvObjectsNames.PICK;
		}
		else if (name.equals("win")) {
			return AdvObjectsNames.OBJECTIVE;
		} else if (name.equals("knife")) {
			return AdvObjectsNames.KNIFE;
		}

		return AdvObjectsNames.NONE;
	}
}
