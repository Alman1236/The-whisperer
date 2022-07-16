package adventureLogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import gameElements.Room;
import gameElements.RunData;
import gameElements.Data;

import java.io.IOException;

public class FileManager {

	private static final String dataFileName = "data.dat";

	static public boolean doSavedDataExist() {
		Path path = Paths.get(dataFileName);

		if (Files.exists(path)) {
			return true;
		} else {
			return false;
		}
	}

	public static void saveAndQuit() {
		Scoreboard.getCurrentRunData().updatePlayTime();
		saveData();

		System.exit(0);
	}

	public static void saveData() {
		Data data = new Data();
		FileOutputStream outFile;

		try {
			outFile = new FileOutputStream(dataFileName);
			ObjectOutputStream outStream = new ObjectOutputStream(outFile);

			outStream.writeObject(data);
			outStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void deleteData() {

		File file = new File(dataFileName);
		file.delete();
	}

	public static void loadData() {

		//System.out.println("Dati salvati esistono? " + doSavedDataExist());

		if (doSavedDataExist()) {

			Data data = loadDataFile();

			setAlreadyExploredRooms(data);
			setAlreadyEmptiedRooms(data);
			setRoomsWithoutPerson(data);
			setUnlockedRooms(data);

			Scoreboard.setCurrentRunData(data.getCurrentRunData());
			Adventure.setInventory(data.getInventory());
			Adventure.setCurrentRoom(Adventure.GetRoomByName(data.getCurrentRoomName()));
		} else {
			Scoreboard.setCurrentRunData(new RunData());
		}

	}

	private static Data loadDataFile() {

		FileInputStream inFile;
		ObjectInputStream inStream;
		Data loadedData = null;

		try {
			inFile = new FileInputStream(dataFileName);
			inStream = new ObjectInputStream(inFile);

			loadedData = (Data) inStream.readObject();
			inStream.close();

		} catch (IOException | ClassNotFoundException e) {

			e.printStackTrace();
		}
		return loadedData;
	}

	private static int getIndexOfRoomsInRoomList(ArrayList<Room> roomList, String roomName) {

		for (int i = 0; i < roomList.size(); i++) {
			if (roomList.get(i).GetName().equals(roomName)) {
				return i;
			}
		}

		return -1;
	}

	private static void setAlreadyExploredRooms(Data data) {
		ArrayList<String> exploredRoomsNames = data.getExploredRoomsNames();
		ArrayList<Room> updatedRoomList = Adventure.getRoomList();

		for (short i = 0; i < exploredRoomsNames.size(); i++) {
			int index = getIndexOfRoomsInRoomList(updatedRoomList, exploredRoomsNames.get(i));

			if (index != -1) {
				updatedRoomList.get(index).setIsAlreadyExplored(true);
			}
		}

		Adventure.setRoomList(updatedRoomList);
	}

	private static void setAlreadyEmptiedRooms(Data data) {

		ArrayList<Room> updatedRoomList = Adventure.getRoomList();
		ArrayList<String> emptiedRoomsNames = data.getEmptiedRoomsNames();

		for (short i = 0; i < emptiedRoomsNames.size(); i++) {
			int index = getIndexOfRoomsInRoomList(updatedRoomList, emptiedRoomsNames.get(i));

			if (index != -1) {
				updatedRoomList.get(index).setIsEmpty(true);
			}
		}

		Adventure.setRoomList(updatedRoomList);
	}

	private static void setRoomsWithoutPerson(Data data) {

		ArrayList<Room> updatedRoomList = Adventure.getRoomList();
		ArrayList<String> roomsWithoutPersonNames = data.getRoomsWithoutPersonNames();

		for (short i = 0; i < roomsWithoutPersonNames.size(); i++) {
			int index = getIndexOfRoomsInRoomList(updatedRoomList, roomsWithoutPersonNames.get(i));

			if (index != -1) {
				updatedRoomList.get(index).setContainsPerson(false);
			}
		}

		Adventure.setRoomList(updatedRoomList);
	}

	private static void setUnlockedRooms(Data data) {

		ArrayList<Room> updatedRoomList = Adventure.getRoomList();
		ArrayList<String> unlockedRoomsNames = data.getUnlockedRoomsNames();

		for (short i = 0; i < unlockedRoomsNames.size(); i++) {
			int index = getIndexOfRoomsInRoomList(updatedRoomList, unlockedRoomsNames.get(i));

			if (index != -1) {
				updatedRoomList.get(index).setIsLocked(false);
			}
		}

		Adventure.setRoomList(updatedRoomList);
	}

}
