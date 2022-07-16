package gameElements;

import java.io.Serializable;
import java.util.ArrayList;

import adventureLogic.Adventure;
import adventureLogic.Scoreboard;

public class Data implements Serializable{
	
	private ArrayList<String> exploredRoomsNames;
	private ArrayList<String> emptiedRoomsNames;
	private ArrayList<String> unlockedRoomsNames;
	private ArrayList<String> roomsWithoutPersonNames;
	private String currentRoomName;
	private RunData currentRunData;
    private Inventory inventory;
	
	public ArrayList<String> getExploredRoomsNames(){
		return exploredRoomsNames;
	}
	
	public ArrayList<String> getEmptiedRoomsNames(){
		return emptiedRoomsNames;
	}
	
	public ArrayList<String> getUnlockedRoomsNames(){
		return unlockedRoomsNames;
	}
	
	public ArrayList<String> getRoomsWithoutPersonNames(){
		return roomsWithoutPersonNames;
	}
	
	public String getCurrentRoomName(){
		return currentRoomName;
	}
	
	public RunData getCurrentRunData() {
		return currentRunData;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public Data() {
		ArrayList<Room> rooms = Adventure.getRoomList();
		
		exploredRoomsNames = new ArrayList<String>();
		emptiedRoomsNames = new ArrayList<String>();
		unlockedRoomsNames = new ArrayList<String>();
	    roomsWithoutPersonNames = new ArrayList<String>();
		
		for(short i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).isAlreadyExplored()) {
				exploredRoomsNames.add(rooms.get(i).GetName());
			}
			
			if(rooms.get(i).isEmpty()) {
				emptiedRoomsNames.add(rooms.get(i).GetName());
			}
			
			if(!rooms.get(i).isLocked()) {
				unlockedRoomsNames.add(rooms.get(i).GetName());
			}
			
			if(!rooms.get(i).containsPerson()) {
				roomsWithoutPersonNames.add(rooms.get(i).GetName());
			}
		}
		
		currentRoomName = Adventure.getCurrentRoom().GetName();
		currentRunData = Scoreboard.getCurrentRunData();	
		inventory = Adventure.getInventory();
	}

	
}
