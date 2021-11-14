package shoot_the_alien.characters;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import shoot_the_alien.FloatingObject;
import shoot_the_alien.model.Image;
import shoot_the_alien.view.screens.frame.Window;

/**
 * The ammunation kit of the game.
 * <p>
 * When the ammunition icon appears on the screen, the gamer have a possibility
 * to catch it and reload your ammunition kit to more shoots. 
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 18/04/16
 */
public class Ammunition implements FloatingObject {

	
	
	/**
     * X coordinate.
     */
    public int x;
    /**
     * Y coordinate.
     */
    public int y;   
    /**
     * How fast the object should move? 
     */
    private int speed = 3;
    /**
     * The ammunition kit image.
     */
    private BufferedImage ammunationImage;
    
    /**
     * Ammunitions lines.
     * Where is starting location for the ammunition icon? ( X, Y )
     */
    public static int[][] ammunitionLines = {
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.25)},
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.40)},
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.65)} };
	
    /**
     * Indicate which is next alien line that the ammunition will  appear.
     */
    public static int nextAmmunitionLine = 0;
    /**
     * Used to load the ammunition's image.
     */
    private Image img = new Image();
    
    
    
    
    
    /**
     * Setup a new instance of Ammunition object.
     * 
     * @param posX Position on the axis X.
     * @param posY Position on the axis Y.
     */
    public Ammunition(int posX, int posY) { 
    	this.x = posX;
    	this.y = posY;
    	this.ammunationImage = img.getAmmunitionKitImg(); 
    }
    
    
    
    
    
    
    /**
     * Draw the ammunition icon on the screen.
     */
	@Override
	public void Draw(Graphics2D g2d) {		
		g2d.drawImage(ammunationImage, x, y, null);
	}

	
	/**
	 * Update the position of the ammunition icon.
	 */
	@Override
	public void Update() {
		this.x -= speed;
	}
	
	
	
	/**
     * @return The image of this ammunition kit instance.
     */
    @Override
    public BufferedImage getImage(){
    	return ammunationImage;
    }
	
	
    /**
     * Return the position X.
     */
    public int getX(){
    	return x;
    }
    
    
    /**
     * Return the position Y.
     */
    public int getY(){
    	return y;
    }
    
    
    
    
    
    
	

}
