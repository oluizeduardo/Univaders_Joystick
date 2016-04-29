package shoot_the_alien;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class to build a new alien.
 * 
 * @author www.gametutorial.net
 * @author Luiz Eduardo da Costa
 * @version 2.0
 */
public class Alien implements FloatingObject{
    
    /**
     * How much time must pass in order to create a new alien?
     */
    public static long timeBetweenAliens = Framework.secInNanosec;
    /**
     * Last time when the alien was created.
     */
    public static long lastAlienTime = 0;
    
    /**
     * Alien lines.
     * Where is starting location for the alien? ( X, Y )
     * Speed of the alien?
     * How many points is a alien worth?
     */
    public static int[][] alienLines = {
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.40), 2, 90},
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.50), 3, 30},
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.55), 2, 10},
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.65), 3, 40},
                                       {Window.frameWidth, (int)(Window.frameHeight * 0.78), 2, 50}
                                      };
    
    
    /**
     * Indicate which is next alien line.
     */
    public static int nextAlienLines = 0;
    /**
     * X coordinate of the alien.
     */
    public int x;
    /**
     * Y coordinate of the alien.
     */
    public int y;
    
    /**
     * How fast the alien should move? 
     */
    private int speed;
    
    /**
     * How many points this alien is worth?
     */
    public int score;
    /**
     * Alien image.
     */
    private BufferedImage alienImage;

    
    
    
    
    /**
     * Creates new alien.
     * 
     * @param x Starting x coordinate.
     * @param y Starting y coordinate.
     * @param speed The speed of this alien.
     * @param score How many points this alien is worth?
     * @param img Image of the alien.
     */
    public Alien(int x, int y, int speed, int score, BufferedImage img)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.score = score;
        this.alienImage = img;             
    }
    
    
    
    
    /**
     * Move the alien.
     */
    @Override
    public void Update()
    {
    	 x -= speed;
    }
    
    
    
    
    
    /**
     * Draw the alien to the screen.
     * @param g2d Graphics2D
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(alienImage, x, y, null);
    }

    
    
    /**
     * @return The image of this Alien.
     */
    @Override
    public BufferedImage getImage(){
    	return alienImage;
    }





    
    
    
    
}
