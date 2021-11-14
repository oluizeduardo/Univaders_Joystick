package shoot_the_alien;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A standard to floating objects of the game.
 * It works as a polimorfism way to draw many objects on the screen.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 18/04/16
 */
public interface FloatingObject {

	/**
	 * Draw the object on the screen using a Graphic2D object.
	 * @param g2d
	 */
	void Draw(Graphics2D g2d);
	
	/**
	 * Update the object on the screen.
	 */
	void Update();
	
	/**
	 * Returns the image of the FloatingObject instance.
	 * @return
	 */
	BufferedImage getImage();
	
}
