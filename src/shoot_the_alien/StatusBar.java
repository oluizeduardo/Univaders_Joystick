package shoot_the_alien;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;


/**
 * Use an object JProgressBar to build a statusbar.
 * <p>
 * The object StatusBar change its color and displays a String message.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 22/04/16
 */
public class StatusBar extends JProgressBar{

	
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * A border to apply in the progress bar.
	 */
	private Border BORDER = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,null,new Color(0, 0, 0));
	
	
	
	
	/**
	 * The constructor of the class. Build a new statusbar.
	 */
	public StatusBar() {
		build();
	}
	
	
	
	
	
	/**
	 * It configures and build a new progress bar.
	 */
	private void build(){
        setStringPainted(true);
        setBorderPainted(true);
        setBackground(Color.white);
        setFont(new Font("Verdana", Font.BOLD, 17));
        setBorder(BORDER);
        setVisible(false);
        setUI(new BasicProgressBarUI() {
                protected Color getSelectionBackground() { return Color.BLACK; }
                protected Color getSelectionForeground() { return Color.BLUE; }
              
		});
	}
	
	
	
	
	/**
	 * Put a different color on the bar depending on the input value
	 * 
	 * @param value The value which will aply on the progress bar object.
	 */
	@Override
	public void setValue(int value) {
		
		Color color = null;
		
		int max = getMaximum();
		int difference = (int) max / 3;
		
		if(value >= 0 && value <= difference){
			color = Color.RED.brighter();
		}else{
			if(value > difference && value <= (2 * difference)){
				color = Color.YELLOW.brighter();
			}else{
				if(value > (2 * difference) && value <= max){
					color = Color.GREEN.brighter();
				}
			}
		}
		if(color != null)
			setForeground(color);
		super.setValue(value);
	}
	
	
	
	
	
	
}
