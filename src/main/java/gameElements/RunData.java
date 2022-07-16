package gameElements;

import java.io.Serializable;

import adventureLogic.MainFrame;

public class RunData implements Serializable{
	private String playerName = "";
	private int score = 0;
	private double playTime = 0;
	private boolean playerWon = false;

	private long startTime;
	
	public int getScore() {
		return score;
	}
	
	public boolean getPlayerWon() {
		return playerWon;
	}
	
	public void setPlayerWonTrue() {
		playerWon = true;
	}
	
	public double getPlayTime() {
		return playTime;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String name) {
		playerName = name;
	}
	public void increaseScore(int value) {
		score += value;
	}
	
	public void onStartingToPlay() {
	    startTime = System.currentTimeMillis();
	}
	
	public void updatePlayTime() {
		long finishTime = System.currentTimeMillis();
		playTime += finishTime - startTime;
		startTime = finishTime;
	}
	
	public void printCurrentRunData() {
		updatePlayTime();
		MainFrame.updateOutputText("\nHai giocato per: " + playTime/1000 + " secondi\n");
		MainFrame.updateOutputText("Hai totalizzato: " + score + " punti\n");
		
	}
	
	public void print() {
		MainFrame.updateOutputText(playerName + " ha totalizzato: " + score 
				+ " punti in: " + playTime/60000 + " minuti\n");
	}

	public void setPlayTime(float value) {
		playTime = value;
	}
}
