package adventureLogic;

import java.util.ArrayList;

import gameElements.Inventory;
import gameElements.Room;
import types.Directions;

public class Adventure {
	
	private static Room currentRoom;
	
    private static ArrayList<Room> roomList;
    
    private static Inventory inventory;
    
    private static Directions comingFrom;
    
    public static void setRoomList(ArrayList<Room> roomList) {
		Adventure.roomList = roomList;
	}
    
    public static ArrayList<Room> getRoomList() {
		return Adventure.roomList;
	}
       
	public static Room getCurrentRoom() {
		return currentRoom;
	}

	public static void setCurrentRoom(Room currentRoom) {
		Adventure.currentRoom = currentRoom;
	}
	
	
	public static void walk(Directions direction) {
		String targetRoom = currentRoom.getAdjacentRoom(direction);
		
		if(currentRoom.containsPerson() && direction != comingFrom) {
			inventory.onBeingSpotted();
		}
		else {
			setComingFrom(direction);
			
			if(targetRoom == null || targetRoom.isBlank()) {
				MainFrame.updateOutputText("\nDi lì c'è un muro\n");
			}
			else{
				Room room = GetRoomByName(targetRoom);
				room.enterRoom();
			}
		}
		
	}
	
	public static Room GetRoomByName(String name) {
		for(short i = 0; i < roomList.size(); i++) {
			if(roomList.get(i).GetName().equals(name)) {
				return roomList.get(i);
			}
		}
		return null;
	}

	public static Inventory getInventory() {
		return inventory;
	}

	public static void setInventory(Inventory inventory) {
		Adventure.inventory = inventory;
	}
	
	private static void setComingFrom(Directions directionWalked) {
		if(directionWalked == Directions.NORTH) {
			comingFrom = Directions.SOUTH;
		}else if(directionWalked == Directions.SOUTH) {
			comingFrom = Directions.NORTH;
		}else if(directionWalked == Directions.EAST) {
			comingFrom = Directions.WEST;
		}else if(directionWalked == Directions.WEST) {
			comingFrom = Directions.EAST;
		}
	}
}
