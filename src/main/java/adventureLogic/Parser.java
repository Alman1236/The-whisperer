package adventureLogic;

import java.util.ArrayList;

import gameElements.Command;
import types.Commands;
import types.Directions;

public class Parser {

	private static final String MESSAGGIO_COMANDO_NON_VALIDO = "\nHai inserito un comando non valido, digita \"help\" per visualizzare la lista.\n\n";
	private static boolean waitingForName = false;
	private static Command[] commands;
	
	public static void parse(String input) {
	
		if(waitingForName) {
			registerName(input);
		}
		else {
			input = input.toLowerCase();
			String[] inputs = input.split(" ");
			boolean insertedValidCommand = false;
			
			if (inputs.length == 0) {
				return;
			}
			
			for (byte i = 0; i < commands.length; i++) {
				if (commands[i].GetAliases().contains(inputs[0]))
				{
					if(inputs.length > 1)
						handleCommand(commands[i].getIdentifier(), inputs[1]);
					else	
						handleCommand(commands[i].getIdentifier(), "");
					
					insertedValidCommand = true;
				}
			}
			
			if(!insertedValidCommand) {
				MainFrame.updateOutputText(MESSAGGIO_COMANDO_NON_VALIDO);
			}
		}
	}
	
	public static void askForName() {
		waitingForName = true;
		MainFrame.updateOutputText("\nInserisci il tuo nome per continuare:\n");
	}
	
	private static void registerName(String name) {
		waitingForName = false;
		Scoreboard.getCurrentRunData().setPlayerName(name);
		Scoreboard.saveRunData();
		
		FileManager.deleteData();
		App.startAdventure();
	}

	private static void handleCommand(Commands commandType, String parameter) {

		switch (commandType) {

		case LOOK:
			Adventure.getCurrentRoom().look();
			break;

		case INVENTORY:
			MainFrame.showOrHideInventory();
			break;

		case NORTH:
			Adventure.walk(Directions.NORTH);
			break;

		case WEST:
			Adventure.walk(Directions.WEST);
			break;

		case EAST:
			Adventure.walk(Directions.EAST);
			break;

		case SOUTH:
			Adventure.walk(Directions.SOUTH);
			break;

		case QUIT:
			FileManager.saveAndQuit();
			break;

		case RESET_DATA:
			FileManager.deleteData();
			App.startAdventure();
		 break;

		case SCOREBOARD:
			Scoreboard.print();
			break;

		case STOP_MUSIC:
			AudioPlayerThread.stopMusic();
			break;

		case RESUME_MUSIC:
			AudioPlayerThread.playMusic();
			break;

		case HELP:
			MainFrame.printHelp();
			break;
			
		case USE:
			Adventure.getInventory().useItem(parameter);
			break;
			
		case CLEAR:
			MainFrame.clear();
			break;
			
	    case PICKUP:
			Adventure.getCurrentRoom().getItemInRoom();
			break;
			
		case SCORE:
			Scoreboard.getCurrentRunData().printCurrentRunData();
			break;
			
		default:
			break;
		}
	}
	
	public static void defineCommands() {

		ArrayList<String> lookAliases = new ArrayList<String>();
		lookAliases.add("look");
		lookAliases.add("see");
		lookAliases.add("watch");
		lookAliases.add("l");
		Command look = new Command(Commands.LOOK, lookAliases);

		ArrayList<String> inventoryAliases = new ArrayList<String>();
		inventoryAliases.add("inventory");
		inventoryAliases.add("inv");
		inventoryAliases.add("i");
		Command inventory = new Command(Commands.INVENTORY, inventoryAliases);

		ArrayList<String> northAliases = new ArrayList<String>();
		northAliases.add("north");
		northAliases.add("n");
		Command goNorth = new Command(Commands.NORTH, northAliases);

		ArrayList<String> southAliases = new ArrayList<String>();
		southAliases.add("south");
		southAliases.add("s");
		Command goSouth = new Command(Commands.SOUTH, southAliases);

		ArrayList<String> westAliases = new ArrayList<String>();
		westAliases.add("west");
		westAliases.add("w");
		Command goWest = new Command(Commands.WEST, westAliases);

		ArrayList<String> eastAliases = new ArrayList<String>();
		eastAliases.add("east");
		eastAliases.add("e");
		Command goEast = new Command(Commands.EAST, eastAliases);

		ArrayList<String> quitAliases = new ArrayList<String>();
		quitAliases.add("quit");
		quitAliases.add("q");
		Command quit = new Command(Commands.QUIT, quitAliases);

		ArrayList<String> resetAliases = new ArrayList<String>();
		resetAliases.add("reset");
		resetAliases.add("restart");
		resetAliases.add("r");
		Command reset = new Command(Commands.RESET_DATA, resetAliases);

		ArrayList<String> helpAliases = new ArrayList<String>();
		helpAliases.add("help");
		helpAliases.add("h");
		helpAliases.add("?");
		Command help = new Command(Commands.HELP, helpAliases);
		
		ArrayList<String> stopMusicAliases = new ArrayList<String>();
		stopMusicAliases.add("stop");
		Command stop = new Command(Commands.STOP_MUSIC, stopMusicAliases);
		
		ArrayList<String> resumeMusicAliases = new ArrayList<String>();
		resumeMusicAliases.add("play");
		resumeMusicAliases.add("resume");
		Command resume = new Command(Commands.RESUME_MUSIC, resumeMusicAliases);

		ArrayList<String> clearAliases = new ArrayList<String>();
		clearAliases.add("clear");
		clearAliases.add("c");
		Command clear = new Command(Commands.CLEAR, clearAliases);
		
		ArrayList<String> scoreboardAliases = new ArrayList<String>();
		scoreboardAliases.add("scoreboard");
		Command scoreboard = new Command(Commands.SCOREBOARD, scoreboardAliases);
		
		ArrayList<String> useAliases = new ArrayList<String>();
		useAliases.add("use");
		useAliases.add("u");
		Command use = new Command(Commands.USE, useAliases);
		
		ArrayList<String> scoreAliases = new ArrayList<String>();
		scoreAliases.add("score");
		Command score = new Command(Commands.SCORE, scoreAliases);
		
		ArrayList<String> pickAliases = new ArrayList<String>();
		pickAliases.add("pickup");
		pickAliases.add("pick");
		pickAliases.add("p");
		Command pick = new Command(Commands.PICKUP, pickAliases);

		commands = new Command[] { look, inventory, goNorth, goEast, goWest, 
				goSouth, quit, reset, help, stop, resume,clear,use, score, pick, scoreboard };
	}
}