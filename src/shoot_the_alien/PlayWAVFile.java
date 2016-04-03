package shoot_the_alien;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * This class can play a sound file with the extension .wav
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 30/03/16
 *
 */
public class PlayWAVFile implements Runnable {

	// When is developing the game, use this type of adress:
	//public static final String INTRO_WARRIOR = "src/shoot_the_alien/resources/audio/intro_warrior.wav";
	// When building the .jar file, after that, change the address for this format:
	//public static final String INTRO_WARRIOR = "audio/intro_warrior.wav";
	public static final String INTRO_WARRIOR = "src/shoot_the_alien/resources/audio/intro_warrior.wav";
	public static final String SHOOT_LASER = "src/shoot_the_alien/resources/audio/shoot_laser.wav";
	public static final String GAME_OVER = "src/shoot_the_alien/resources/audio/game_over.wav";
	
	private File file = null;
	
	private Clip clip = null;
	/**
	 * The adress of the file.
	 */
	private String filePath = null;
	/**
	 * How many times the soung must to replay.
	 */
	private int loop = 1;
	
	

	
	
	
	/**
	 * Sets the parameters.
	 * 
	 * @param filePath The path of the file.
	 * @param loop How many times do you want to play this song?
	 */
	public PlayWAVFile(String path, int loop) {
		
		this.filePath = path;
		this.loop = (loop - 1);// It starts to count in 0.
		
		file = new File(filePath);
	}
	
	
	
	@Override
	public void run() {
		playSound();
	}
	
	
	
	private void playSound(){
		try {
		    clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(file));
			clip.loop(loop);
			
			Thread.sleep(clip.getMicrosecondLength());
			
		} catch (Exception e) {
			System.err.println("Não foi possível executar o Som WAV!! "+e.getMessage());
		}
	}
	
	
}
