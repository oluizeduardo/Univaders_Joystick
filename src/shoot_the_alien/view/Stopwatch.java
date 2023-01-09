package shoot_the_alien.view;

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
	 * It controls the execution of the stopwatch.
	 */
	public static boolean isRunning = false;
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
		
		setVisible(true);
		
		while(Stopwatch.isRunning){
			uptadeStopwatch();
		}
		setVisible(false);
		restart();
	}
	
	/**
	 * Sets the right format of the timer on the stopwatch. 
	 */
	private void uptadeStopwatch() {

		if(MINUTES == 0 && SECONDS == 0){
			isRunning = false;
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
		
	}
	
	/**
	 * Restart to the initial values of the stopwatch.
	 */
	public static void restart(){
		MINUTES = save_min;
		SECONDS = save_sec;
	}
	
	/**
	 * Check if the stopwatch is over.
	 */
	public static boolean isOver(){
		return (MINUTES == 0 && SECONDS == 0);
	}
	
	public static void turnOn() {
		isRunning = true;
	}
	
	public static void turnOff() {
		isRunning = false;
	}
}
