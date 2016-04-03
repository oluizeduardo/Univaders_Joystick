package shoot_the_alien;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


/**
 * This class to build a new alien.
 * 
 * @author www.gametutorial.net
 * @author Luiz Eduardo da Costa
 * @category Game
 * @version 2.0
 */
public class Alien {
    
    /**
     * How much time must pass in order to create a new alien?
     */
    public static long timeBetweenAliens = (long) (Framework.secInNanosec / 2);
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
                                       {Framework.frameWidth, (int)(Framework.frameHeight * 0.40), -5, 90},
                                       {Framework.frameWidth, (int)(Framework.frameHeight * 0.50), -3, 30},
                                       {Framework.frameWidth, (int)(Framework.frameHeight * 0.55), -3, 10},
                                       {Framework.frameWidth, (int)(Framework.frameHeight * 0.65), -4, 40},
                                       {Framework.frameWidth, (int)(Framework.frameHeight * 0.78), -5, 50}
                                      };
    
   
    /*
    public static int[][] alienLines = {
            {(int)(Framework.frameWidth * 0.05), 0, -5, 90},
            {(int)(Framework.frameWidth * 0.25), 0, -3, 30},
            {(int)(Framework.frameWidth * 0.58), 0, -3, 10},
            {(int)(Framework.frameWidth * 0.68), 0, -4, 40},
            {(int)(Framework.frameWidth * 0.80), 0, -5, 50}
           };
    */
    
    
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
    public void Update()
    {
    	 x += speed;
       // y -= speed;
    }
    
    
    
    
    /**
     * Draw the alien to the screen.
     * @param g2d Graphics2D
     */
    public void Draw(Graphics2D g2d)
    {
        g2d.drawImage(alienImage, x, y, null);
    }

    
}
