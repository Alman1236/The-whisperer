package adventureLogic;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayerThread extends Thread{

	private static final String musicPath = "/music.wav";
	private static boolean isPlaying = false;
	private static Clip clip;

	public void run() {
		playMusic();
	}
	
	public AudioPlayerThread()  {
		
		AudioInputStream audioInputStream;
		try {
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
	        File file = new File(path + musicPath);
	        audioInputStream = AudioSystem.getAudioInputStream(file);
			
	        clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			
		} catch (UnsupportedAudioFileException 
				| IOException 
				| LineUnavailableException e) {		
			e.printStackTrace();
		}
		
	}

	public static void playMusic() {

		if(!isPlaying) {
			isPlaying = true;
			clip.start();	
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}

	public static void stopMusic()  {
		
		if(isPlaying) {
			clip.stop();
			//clip.close();
			isPlaying = false;
		}	
	}
	
	public void interrupt() {
		stopMusic();
	}

}
