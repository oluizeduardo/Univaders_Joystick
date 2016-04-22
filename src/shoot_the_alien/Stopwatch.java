package shoot_the_alien;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;


/**
 * Create a stopwatch (chronometer), a sistem to measure the playing time.
 * This class implements Runnable and must to work in a Thread object.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 02/04/16
 */
public class Stopwatch extends JLabel implements Runnable {


	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Sets the minutes of the stopwatch.
	 */
	private static int MINUTES = 2;
	/**
	 * Sets the seconds of the stopwatch.
	 */
    private static int SECONDS = 1;
	/**
	 * Control the execution of the stopwatch.
	 */
	public static boolean isStopwatchRunning = false;
	/**
	 * Save the value of the initial minute.
	 */
	private static int save_min;
	/**
	 * Save the value of the initial minute.
	 */
	private static int save_sec;
	
	
	
	
	
	/**
	 * The constructor of the class.
	 * Save the initial values of the stopwatch.
	 * The saved values will be used when is necessary to restart the chronometer.
	 */
	public Stopwatch() {
		Stopwatch.save_min = MINUTES;
		Stopwatch.save_sec = SECONDS;
		
		super.setText("00:00");		
		super.setHorizontalAlignment(JLabel.CENTER);
		super.setForeground(Color.red);
		super.setFont(new Font("Lucida Sans", Font.BOLD, 70));
		super.setVisible(false);
	}
	
	
	
	
	/**
	 * Run the updating of the chronometer.
	 */
	@Override
	public void run() {
		while(true){
			uptadeStopwatch();
		}
	}
	
	
	
	
	/**
	 * Sets the right format of the timer on the stopwatch. 
	 */
	private void uptadeStopwatch() {
		
		// Don't delete this row! I don't know why, but without it the stopwatch do not work right.
		System.out.println();
		
		if(Stopwatch.isStopwatchRunning){
			
			// If is running, is visible.
			setVisible(true);
			
			if(MINUTES == 0 && SECONDS == 0){
				isStopwatchRunning = false;
				Framework.gameState = Framework.GameState.GAMEOVER;
						
			}else{
				if(MINUTES >= 0){
					
					if(SECONDS > 0){
						
						--SECONDS;
					}else{
						SECONDS = 59;
						--MINUTES;
					}
				}
			}

			String mStr = (MINUTES < 10 ? "0"+MINUTES : ""+MINUTES);
			String sStr = (SECONDS < 10 ? "0"+SECONDS : ""+SECONDS);
			
			super.setText(mStr+":"+sStr);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) { }
		}else{
			setVisible(false);
			restart();
		}
	}
	
	
	

	
	/**
	 * Restart to the initial values of the stopwatch.
	 */
	public static void restart(){
		MINUTES = save_min;
		SECONDS = save_sec;
	}
	
	
	
	
	
	
	
}
