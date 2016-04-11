package shoot_the_alien;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import shoot_the_alien.Alien;

/**
 * The actual game.
 * 
 * @author www.gametutorial.net
 * @author Luiz Eduardo da Costa
 * @category Game Control
 * @version 2.0
 */
public class Game {
    
	
	/**
	 * Maximum number of fugitives.
	 */
	private static final int MAX_ALIENS_RUNAWAY = 100;
    /**
     * This object is used to generate a random number.
     */
    private Random random;      
    /**
     * ArrayList of the aliens that will appear on the screen.
     */
    private ArrayList<Alien> aliens;
    /**
     * A buffer of the background image.
     */
    private BufferedImage backgroundImg;  
    /**
     * A buffer of the footer image Univás logo.
     */
    private BufferedImage univas_logo; 
    /**
     * Alien images.
     */
    private BufferedImage alienImg_1, alienImg_2; 
    /**
     * Last time of the shoot.
     */
    private long lastTimeShoot;    
    /**
     * The time which must elapse between shots.
     */
    private long timeBetweenShots;
    /**
     * Red border to game over.
     */
    private BufferedImage red_borderImg;
    
    
    /**  How many aliens leave the screen alive? */
    private int runawayAliens = 0;
    /** How many aliens the player killed?   */
    private int killedAliens = 0;
    /** For each killed alien, the player gets points.   */
    private int score = 0; 
    /** How many times a player is shot?   */
    private int shoots = 0;
    
    // It said if the button was pressed or not.
    private boolean isSelectPressed = false;
    private boolean isShootPressed = false;
    private boolean isRestartPressed = false;
    
    
    
    
    
    /**
     * The constructor of the class.
     */
    public Game()
    {
        Framework.gameState = Framework.GameState.GAME_CONTENT_LOADING;
        
        Thread threadForInitGame = new Thread() {
            @Override
            public void run(){
                // Sets variables and objects for the game.
                Initialize();
                // Load game files (images, sounds, ...)
                LoadContent();
                // Chage the state of the game. Let's start to play!
                Framework.gameState = Framework.GameState.PLAYING;
            }
        };
        threadForInitGame.start();
    }
    
    
    
    
    
   /**
     * Set variables and objects for the new game.
     */
    private void Initialize()
    {       
        random = new Random();
        aliens = new ArrayList<Alien>();
        lastTimeShoot = 0;
        timeBetweenShots = (int) (Framework.secInNanosec * 0.2);      
        runawayAliens = 0;
        killedAliens = 0;
        score = 0;
        shoots = 0;
    }
    
    
    
    
    /**
     * Load the files of the game (images, sounds...)
     */
    private void LoadContent()
    {
    	Image objImage = new Image();
    	
    	backgroundImg = objImage.getBackgroundImg();
    	univas_logo = objImage.getUnivasLogoImg();
    	red_borderImg = objImage.getRedborderImg();
    	alienImg_1 = objImage.getAlienImg1();
    	alienImg_2 = objImage.getAlienImg2();
    }
    
       
    
    
    /**
     * Restart game - reset some variables.
     */
    public void restartGame()
    {
        // Removes all the aliens from this list.
        aliens.clear();
        // We set last alien time to zero.
        Alien.lastAlienTime = 0;      
        score = 0;
        shoots = 0;
        runawayAliens = 0;
        killedAliens = 0;
        lastTimeShoot = 0;
    }
    
    
    
    
    
    
    /**
     * Update game logic.
     * 
     * Very important game's method.
     * 
     * @param gameTime The game time.
     * @param mousePosition Current mouse position.
     */
    public void UpdateGame(long gameTime)
    {
    	// Check if any main button was pressed while game is running.
    	CheckButtonsPressed();
    	
    	// Creates a new alien, if it's the time, and add it to the array list.
        if(System.nanoTime() - Alien.lastAlienTime >= Alien.timeBetweenAliens)
        {
        	 
        	// Create a new alien and add it on the list of aliens.
        	CreateNewAlien();
            
            // Here we increase nextAlienLines so that next alien will be created in next line.
            Alien.nextAlienLines++;
            
            // Used to don't exceed the bounds of the array.
            if(Alien.nextAlienLines >= Alien.alienLines.length)
                Alien.nextAlienLines = 0;
            
            Alien.lastAlienTime = System.nanoTime();
        }
        
        
        UpdateTheAliensOnTheScreen();

        
        // Does player shoots?
        if(isShootPressed)
        {
        	
        	if(System.nanoTime() - lastTimeShoot >= timeBetweenShots){
        		
        		// Play the sound of shoot.
        		PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.SHOOT_LASER, 1);
    			Thread t = new Thread(pf);
    			t.start();
    			
    			// Count one new shoot.
    			shoots++;
    			
    			
    			CheckSomeAlienDead();
    			
                
    			lastTimeShoot = System.nanoTime();
        	}
        }
        // When 'MAX_ALIENS_RUNAWAY' aliens runaway, the game ends.
        if(runawayAliens >= MAX_ALIENS_RUNAWAY)
            Framework.gameState = Framework.GameState.GAMEOVER;
    	
    }
    
    
    
    
    /**
     * Do a loop on the list and update all aliens on the screen.
     */
    private void UpdateTheAliensOnTheScreen(){
    	// Update all of the aliens.
        for(int i = 0; i < aliens.size(); i++)
        {
            
        	Alien currentAlien = aliens.get(i);

        	// Move the alien.
        	currentAlien.Update();
            
            // Checks if the alien leaves the screen and remove it..
            if(currentAlien.x < 0 - currentAlien.getImage().getWidth())
            {
                aliens.remove(currentAlien);
                runawayAliens++;
            }
        }
    }
    
    
    
    
    
    /**
     * Go over all the aliens and check if any of them was killed.
     */
    private void CheckSomeAlienDead(){
    	
        for(int i = 0; i < aliens.size(); i++)
        {
        	
        	int x = aliens.get(i).x;
        	int y = aliens.get(i).y;
        	int width = aliens.get(i).getImage().getWidth();
        	int height = aliens.get(i).getImage().getHeight();
        	
        	Rectangle rec = new Rectangle(x, y, width, height);
        
        	Point p = JoyStick.getInstance().getJoystickPosition();
        	
        	// We check, if the sight was over the aliens body, when player has shot.
        	if(rec.contains(p)){

                killedAliens++;
                score += aliens.get(i).score;
                
                // Remove the alien from the array list.
                aliens.remove(i);
                
                // The alien dead was found, so leave the loop.
                break;
            }
        }
    }
    
    
    
    
    
    /**
     * Create a new Alien and add it on the list of aliens.
     */
    private void CreateNewAlien(){
    	int x 	  = Alien.alienLines[Alien.nextAlienLines][0] + random.nextInt(200);
    	int y 	  = Alien.alienLines[Alien.nextAlienLines][1] ;
    	int speed = Alien.alienLines[Alien.nextAlienLines][2];
    	int score = Alien.alienLines[Alien.nextAlienLines][3];
    	
    	// Prepare a new objet Alien.
    	Alien newAlien = null;
    	
    	// Random a number.
    	int random = new Random().nextInt(10);
    	
    	// Check if the selected number is even or odd.
    	if((random % 2) == 0)
    		newAlien = new Alien(x, y, speed, score, alienImg_1);
    	else
    		newAlien = new Alien(x, y, speed * 2, score * 2, alienImg_2);
    	
    	// After created a new alien, must add it to the array list.
    	aliens.add(newAlien);
    }
    
    
    
    
    
        
    
    
    /**
     * Verifies if any of the mains buttons of the controller were pressed.
     * This method must to be used while the game is running.
     */
    private void CheckButtonsPressed(){
    	
    	this.isSelectPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_SELECT);
    	this.isShootPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_SHOOT);
    	this.isRestartPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_RESTART);

    	if(isSelectPressed){
    		Framework.gameState = Framework.GameState.MAIN_MENU;

    	}else{

    		if(isRestartPressed){
    			Framework.gameState = Framework.GameState.RESTART;
    		}
    	}
    }
    
    
   
 
    
    
    /**
     * Draw the game to the screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition current mouse position.
     */
    public void Draw(Graphics2D g2d)
    {
    	
    	// Draw the background image.
        g2d.drawImage(backgroundImg, 0, 0, Window.frameWidth, Window.frameHeight, null);
        
        // Draw all the aliens on the screen.
        for(int i = 0; i < aliens.size(); i++)
            aliens.get(i).Draw(g2d);
        
        // Draw the Univás logo in the lower left corner.
        g2d.drawImage(univas_logo, 0, Window.frameHeight - (univas_logo.getHeight() + 10), 250, 70, null);
      
        g2d.setFont(new Font("monospaced", Font.BOLD, 25));
        g2d.setColor(Color.MAGENTA);

        // Draw the scoreboard in the higher left corner.
        g2d.drawString("FUGITIVOS..: " + runawayAliens, 10, 20);
        g2d.drawString("CAPTURADOS.: " + killedAliens, 10, 45);
        g2d.drawString("TIROS......: " + shoots, 10, 70);
        g2d.drawString("PONTOS.....: " + score, 10, 95);
        
        // Update the sight image on the screen.
    	JoyStick.getInstance().drawSight(g2d);   
    }
    
    
    
    
    
    
    /**
     * Draw the game over screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition Current mouse position.
     */
    public void DrawGameOver(Graphics2D g2d)
    {
        Draw(g2d);
        
        // Draw the Game Over image.
        g2d.drawImage(red_borderImg, 0, 0, Window.frameWidth, Window.frameHeight, null);
        
        g2d.setColor(Color.red);
        g2d.drawString("Game Over!!!", Window.frameWidth / 2 - 50, (int)(Window.frameHeight / 2)-40);
        g2d.drawString("Pontuação final: "+score, Window.frameWidth / 2 - 140, (int)(Window.frameHeight / 2));
        g2d.drawString("Pressione SELECT ou ENTER para continuar", Window.frameWidth / 2 - 300, (int)(Window.frameHeight /2) + 50);
    }
    
    
    
    
    
    
    
    
}
