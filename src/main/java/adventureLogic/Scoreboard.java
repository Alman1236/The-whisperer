package adventureLogic;

import java.util.ArrayList;

import gameElements.RunData;

public class Scoreboard {
	
	private static ArrayList<RunData> scoreboard = new ArrayList<RunData>();
    private static RunData currentRunData;
    
    //if you complete a run in less than speedrun_treshold, that run is considered
    //a speedrun.
    private static final int SPEEDRUN_TRESHOLD_IN_MILLISECONDS = 120000;
    
    private static final short GOOD_SCORE = 30;
    
    private static boolean needToFillScoreboard = true;
    
	public static void print() {
		MainFrame.clear();
		
		if(needToFillScoreboard)  {
			fillScoreboard();
			needToFillScoreboard = false;
		}
		
		printAllRuns();
		printSpeedruns();
		printBestRuns();
		
	}
	
	private static void fillScoreboard() {
		//scoreboard.clear();
	  
        RunData run2 = new RunData();
        run2.setPlayerName("Antonio");
        run2.increaseScore(11);
        run2.setPlayTime(194000);
        scoreboard.add(run2);
        
        RunData run3 = new RunData();
        run3.setPlayerName("Michele");
        run3.setPlayerWonTrue();
        run3.increaseScore(GOOD_SCORE + 4);
        run3.setPlayTime(SPEEDRUN_TRESHOLD_IN_MILLISECONDS - 1000);
        scoreboard.add(run3);
        
        RunData run4 = new RunData();
        run4.setPlayerName("Marco");
        run4.increaseScore(25);
        run4.setPlayTime(SPEEDRUN_TRESHOLD_IN_MILLISECONDS/2);
        scoreboard.add(run4);
        
        RunData run5 = new RunData();
        run5.setPlayerName("Aldo");
        run5.setPlayerWonTrue();
        run5.increaseScore(GOOD_SCORE - 21);
        run5.setPlayTime(985000);
        scoreboard.add(run5);
        
        RunData run6 = new RunData();
        run6.setPlayerName("Francesco");
        run6.increaseScore(GOOD_SCORE * 2);
        run6.setPlayTime(SPEEDRUN_TRESHOLD_IN_MILLISECONDS * 1.5f);
        scoreboard.add(run6);
		
		
	}
	
	private static void printAllRuns() {
		MainFrame.updateOutputText("\n\no--------o SCOREBOARD o--------o\n");
		scoreboard.forEach( (record)  -> record.print());
	}
	
	private static void printSpeedruns() {
		MainFrame.updateOutputText("\no--------o SPEEDRUNS o--------o\n");
		scoreboard
		.stream()
		.filter( (record) -> record.getPlayTime() < SPEEDRUN_TRESHOLD_IN_MILLISECONDS
				&& record.getPlayerWon() == true)
		.forEach( (record)  -> record.print() );
	}
	
	private static void printBestRuns() {
		MainFrame.updateOutputText("\no--------o BEST RUNS o--------o\n");
		scoreboard
		.stream()
		.filter( (record) -> 
				record.getPlayTime() < SPEEDRUN_TRESHOLD_IN_MILLISECONDS * 2
				&& record.getPlayerWon() == true
				&& record.getScore() >= GOOD_SCORE)
		.forEach( (record)  -> record.print() );
	}

	public static ArrayList<RunData> getScoreboard() {
		return scoreboard;
	}

	public static RunData getCurrentRunData() {
		return currentRunData;
	}
	
	public static void setCurrentRunData(RunData rundata) {
		currentRunData = rundata;
	}

	public static void saveRunData() {
		scoreboard.add(currentRunData);
	}

}
