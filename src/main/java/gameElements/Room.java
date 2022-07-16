package gameElements;

import java.util.HashMap;
import java.util.Map;

import adventureLogic.Adventure;
import adventureLogic.App;
import adventureLogic.MainFrame;
import adventureLogic.Scoreboard;
import types.AdvObjectsNames;
import types.Directions;

public class Room {

	private String name;

	public String GetName() {
		return name;
	}

	private String description;
	private boolean isAlreadyExplored = false;

	Map<Directions, String> adjacentRooms = new HashMap<Directions, String>();

	private AdvObjectsNames objectInRoom = AdvObjectsNames.NONE;
	private boolean isEmpty = false;

	private boolean containsPerson = false;
	
	private boolean isLocked = false;

	public Room(String name, String look, String northRoom, String southRoom, String eastRoom, String westRoom,
			AdvObjectsNames objectInRoom, boolean containsPerson, boolean isLocked) {
		this.name = name;
		this.description = look;

		adjacentRooms.put(Directions.NORTH, northRoom);
		adjacentRooms.put(Directions.SOUTH, southRoom);
		adjacentRooms.put(Directions.WEST, westRoom);
		adjacentRooms.put(Directions.EAST, eastRoom);

		if(objectInRoom != AdvObjectsNames.NONE)
			isEmpty = false;
		
		this.objectInRoom = objectInRoom;
		
		this.containsPerson = containsPerson;
		this.isLocked = isLocked;
	}

	public String getAdjacentRoom(Directions direction) {
		return adjacentRooms.get(direction);
	}

	public void enterRoom() {
		
		if(!isLocked) {
			MainFrame.updateOutputText("\n" + name + "\n");

			if (!isAlreadyExplored) {
				MainFrame.updateOutputText(description + "\n\n");
				isAlreadyExplored = true;
				Scoreboard.getCurrentRunData().increaseScore(3);
			}

			Adventure.setCurrentRoom(this);
		}
		else {
			MainFrame.updateOutputText("\n La stanza è chiusa a chiave, devo usare un grimaldello se "
					+ "voglio entrare.");
		}
		
	}

	public void look() {
		MainFrame.updateOutputText(description + "\n\n");
	}

	public void getItemInRoom() {
		
		if (isEmpty || objectInRoom == AdvObjectsNames.NONE) {
			MainFrame.updateOutputText("\nNon c'è nulla che mi interessi in questa stanza");
			Scoreboard.getCurrentRunData().increaseScore(-1);
		} else if (objectInRoom == AdvObjectsNames.OBJECTIVE)
		{
			App.win();
		}
		else
		{
			Adventure.getInventory().addObject(objectInRoom);
			isEmpty = true;
		}
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public boolean isAlreadyExplored() {
		return isAlreadyExplored;
	}
	
	public boolean isLocked() {
		return isLocked;
	}
	
	public void setIsLocked(boolean value) {
		isLocked = value;
	}
	
	public boolean containsPerson() {
		return containsPerson;
	}

	public void setContainsPerson(boolean value) {
		containsPerson = value;
	}
	
	public void setIsAlreadyExplored(boolean value) {
		isAlreadyExplored = value;
	}

	public void setIsEmpty(boolean value) {
		isEmpty = value;
	}

}
