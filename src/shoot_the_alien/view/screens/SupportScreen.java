package shoot_the_alien.view.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import shoot_the_alien.model.Image;
import shoot_the_alien.view.screens.frame.Window;


/**
 * This class build the screen to show the images of the enterprises 
 * that supported the developing of this project.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 18/05/16
 */
public class SupportScreen {

	/**
	 * It's used to load an image of Univás' logo.
	 */
	private BufferedImage imgUnivas;
	/**
	 * It's used to load an image in its address.
	 */
	private Image objImg;
	
	
	
	
	/**
	 * The constructor method.
	 */
	public SupportScreen() {
		loadContent();	
	}
	
	
	
	
	/**
	 * Load the images that will be used to bild this screen.
	 */
	private void loadContent(){
		objImg = new Image();
		
		imgUnivas = objImg.getUnivasLogoImg_2();
	}
	
	
	
	
	/**
	 * Draw the screen showing the images of the support. 
	 * 
	 * @param g2d
	 */
	public void drawSupportScreen(Graphics2D g2d){
		
    	g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, 35));
        g2d.drawString("APOIO...", 40, 60);
    	
        int x1 = ((Window.frameWidth / 2) - (imgUnivas.getWidth() / 2));
        int y1 = ((Window.frameHeight / 2) - (imgUnivas.getHeight() / 2));
                        
		g2d.drawImage(imgUnivas, x1, y1, imgUnivas.getWidth(), imgUnivas.getHeight(), null);         
	}
	
	
	
	
}
