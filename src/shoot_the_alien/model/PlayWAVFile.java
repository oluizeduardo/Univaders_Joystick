package shoot_the_alien.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	//public static final String INTRO_WARRIOR = "resources/shoot_the_alien/resources/audio/intro_warrior.wav";
	// When building the .jar file, after that, change the address for this format:
	//public static final String INTRO_WARRIOR = "audio/intro_warrior.wav";
	public static final String INTRO_WARRIOR = "resources/shoot_the_alien/resources/audio/intro_warrior.wav";
	public static final String SHOOT_LASER = "resources/shoot_the_alien/resources/audio/shoot_laser.wav";
	public static final String GAME_OVER = "resources/shoot_the_alien/resources/audio/game_over.wav";
	public static final String CLICK = "resources/shoot_the_alien/resources/audio/click.wav";
	public static final String WINNER = "resources/shoot_the_alien/resources/audio/winner.wav";
	
	/*public static final String INTRO_WARRIOR = "audio/intro_warrior.wav";
	public static final String SHOOT_LASER = "audio/shoot_laser.wav";
	public static final String GAME_OVER = "audio/game_over.wav";
	public static final String CLICK = "audio/click.wav";
	public static final String WINNER = "audio/winner.wav";
	*/
//	private File file = null;
	
	private Clip clip = null;
	/**
	 * The adress of the file.
	 */
	private String filePath = null;
	/**
	 * How many times the soung must to replay.
	 */
	private int loop;
	
	
	private static Map<String, byte[]> contents = new HashMap<String, byte[]>();
	
	static {
		contents.put(INTRO_WARRIOR, readFile(INTRO_WARRIOR));
		contents.put(SHOOT_LASER, readFile(SHOOT_LASER));
		contents.put(GAME_OVER, readFile(GAME_OVER));
		contents.put(CLICK, readFile(CLICK));
		contents.put(WINNER, readFile(WINNER));
	}
	
	
	
	/**
	 * Sets the parameters.
	 * 
	 * @param filePath The path of the file.
	 * @param loop How many times do you want to play this song?
	 */
	public PlayWAVFile(String path, int loop) {
		
		this.filePath = path;
		this.loop = (loop - 1);// It starts to count in 0.
		
//		file = new File(filePath);
	}
	
	@Override
	public void run() {
		playSound();
	}
	
	private void playSound(){
		try {
		    clip = AudioSystem.getClip();
//			clip.open(AudioSystem.getAudioInputStream(file));
			clip.open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(contents.get(filePath))));
			clip.loop(loop);
			
			Thread.sleep(clip.getMicrosecondLength());
			
		} catch (Exception e) {
			System.err.println("Não foi possível executar o Som WAV!! \n"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private static byte[] readFile(String fileName) {
		File file = new File(fileName);
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			
			ByteArrayOutputStream buf = new ByteArrayOutputStream();

			System.out.println("Total file size to read (in bytes) : " + fis.available());

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = fis.read(data, 0, data.length)) != -1) {
			  buf.write(data, 0, nRead);
			}

			buf.flush();

			return buf.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
}
