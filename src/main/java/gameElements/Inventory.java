package gameElements;

import java.io.Serializable;

import adventureLogic.Adventure;
import adventureLogic.MainFrame;
import adventureLogic.Parser;
import adventureLogic.Scoreboard;
import types.AdvObjectsNames;
import types.Directions;

public class Inventory implements Serializable {

	private byte numOfPicks = 1;
	private byte numOfKnives = 1;
	private byte numOfSmokeGrenades = 1;

	public byte getNumOfPicks() {
		return numOfPicks;
	}

	public void setNumOfPicks(byte numOfPicks) {
		this.numOfPicks = numOfPicks;
		MainFrame.updateInventoryUI();
	}

	public byte getNumOfKnives() {
		return numOfKnives;
	}

	public void setNumOfKnives(byte numOfKnives) {
		this.numOfKnives = numOfKnives;
		MainFrame.updateInventoryUI();
	}

	public byte getNumOfSmokeGrenades() {
		return numOfSmokeGrenades;
	}

	public void setNumOfSmokeGrenades(byte numOfSmokeGrenades) {
		this.numOfSmokeGrenades = numOfSmokeGrenades;
		MainFrame.updateInventoryUI();
	}

	public void useItem(String item) {
		if (item.equals("pick") || item.equals("grimaldello")) {
			usePick();
		} else if (item.equals("knife") || item.equals("coltello")) {
			useKnife();
		} else if (item.equals("smoke") || item.equals("granata") || item.equals("grenade")) {
			MainFrame.updateOutputText("\nMeglio conservarle per quando verrò scoperto");
		} else {
			MainFrame.updateOutputText("\nOggetto non riconosciuto, digita \"pick\""
					+ "o \"grimaldello\" per usare i grimaldelli;" + "\n\"knife\" o \"coltello\" per usare i coltelli;"
					+ "\nle granate fumogene vengono usate in automatico quando vieni scoperto");
		}
	}

	public void usePick() {

		if(numOfPicks < 1) {
			MainFrame.updateOutputText("\nLi ho finiti!");
			return;
		}
			
		
		Room room;
		boolean unlockedRoom = false;
		
		if (Adventure.getCurrentRoom().adjacentRooms.get(Directions.WEST) != null) {
			room = Adventure.GetRoomByName(Adventure.getCurrentRoom().adjacentRooms.get(Directions.WEST));

			if (room.isLocked()) {
				unlockedRoom = true;
				unlockRoom(Adventure.getCurrentRoom().adjacentRooms.get(Directions.WEST));
			}
				

		} 
		
		if (Adventure.getCurrentRoom().adjacentRooms.get(Directions.NORTH) != null) {

			room = Adventure.GetRoomByName(Adventure.getCurrentRoom().adjacentRooms.get(Directions.NORTH));

			if (room.isLocked()) {
				unlockedRoom = true;
				unlockRoom(Adventure.getCurrentRoom().adjacentRooms.get(Directions.NORTH));
				}
			
		} 
		
		if (Adventure.getCurrentRoom().adjacentRooms.get(Directions.EAST) != null) {

			room = Adventure.GetRoomByName(Adventure.getCurrentRoom().adjacentRooms.get(Directions.EAST));

			if (room.isLocked()) {
				unlockedRoom = true;
				unlockRoom(Adventure.getCurrentRoom().adjacentRooms.get(Directions.EAST));}
				
		} 
		
		if (Adventure.getCurrentRoom().adjacentRooms.get(Directions.SOUTH) != null) {
			room = Adventure.GetRoomByName(Adventure.getCurrentRoom().adjacentRooms.get(Directions.SOUTH));

			if (room.isLocked()) {
				unlockedRoom = true;
				unlockRoom(Adventure.getCurrentRoom().adjacentRooms.get(Directions.SOUTH));
				}
			
		}
	
		if(!unlockedRoom) {
			MainFrame.updateOutputText("\nLe stanze circostanti sono tutte aperte, non ho bisogno di"
					+ " usare grimaldelli!");
		}

	}
	
	public void unlockRoom(String roomName) {
		Room room = Adventure.GetRoomByName(roomName);
		room.setIsLocked(false);
		room.enterRoom();
		numOfPicks--;
		Scoreboard.getCurrentRunData().increaseScore(1);
		MainFrame.updateInventoryUI();
	}

	private void useKnife() {
		if(numOfKnives < 1) {
			MainFrame.updateOutputText("\nLi ho finiti!");
			return;
		}
		
		if(Adventure.getCurrentRoom().containsPerson()) {
			Adventure.getCurrentRoom().setContainsPerson(false);
			numOfKnives--;
			Scoreboard.getCurrentRunData().increaseScore(1);
			MainFrame.updateInventoryUI();
			MainFrame.updateOutputText("\nHo colpito la guardia, non darà più fastidio...");
		}else{
			MainFrame.updateOutputText("\nNon c'è nessuno nella stanza.");
		}

		MainFrame.updateInventoryUI();
	}

	public void onBeingSpotted() {
		if (numOfSmokeGrenades > 0) {
			numOfSmokeGrenades--;
			MainFrame.updateOutputText("\nStavo per essere scoperto, ma ho usato"
					+ " una granata fumogena per nascondermi di nuovo.\n Per poco non"
					+ "ci rimanevo secco! (HAI USATO 1 GRANATA FUMOGENA)\n");
			MainFrame.updateInventoryUI();
		} else {
			MainFrame.updateOutputText(
					"\nAccidenti! Ho finito le granate fumogene e sono stato scoperto...\n\nGAME OVER");
			Scoreboard.getCurrentRunData().printCurrentRunData();
			Parser.askForName();
		}
	}

	public void addObject(AdvObjectsNames obj) {
		if (obj == AdvObjectsNames.KNIFE) {
			numOfKnives++;
			MainFrame.updateInventoryUI();
			MainFrame.updateOutputText("\nHo raccolto un coltello");
		} else if (obj == AdvObjectsNames.PICK) {
			numOfPicks++;
			MainFrame.updateInventoryUI();
			MainFrame.updateOutputText("\nHo raccolto un grimaldello");
		}
	}
}
