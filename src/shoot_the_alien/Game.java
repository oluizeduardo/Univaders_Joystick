package shoot_the_alien;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import shoot_the_alien.characters.Alien;
import shoot_the_alien.characters.Ammunition;
import shoot_the_alien.model.Image;
import shoot_the_alien.model.JoyStick;
import shoot_the_alien.model.PlayWAVFile;
import shoot_the_alien.model.Stopwatch;
import shoot_the_alien.view.StatusBar;
import shoot_the_alien.view.screens.frame.Window;

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
	public static final int MAX_ALIENS_RUNAWAY = 50;
	/**
	 * Maximum number of shoots.
	 */
	public static final int MAX_SHOOTS = 100;
	/**
	 * Limit shoots to appear the ammunition kit on the screen.
	 * This constante is to use when
	 */
	private static final int LIMIT_TO_APPEAR_AMMUNITION = 30;
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
     * The ammunition kit.
     */
    private Ammunition ammunition = null;    
    
    /**  How many aliens leave the screen alive? */
    private int runawayAliens = 0;
    /** How many aliens the player killed?   */
    private int killedAliens = 0;
    /** For each killed alien, the player gets points.   */
    private int score = 0; 
    /** How many times a player is shot?   */
    private int shoots = MAX_SHOOTS;
    
    // It said if the button was pressed or not.
    private boolean isSelectPressed = false;
    private boolean isShootPressed = false;
    private boolean isGetKitBtnPressed = false;
    
    /**Progress bar which will display the status of the game.*/
    public StatusBar runawayAliensStatus, shootsStatus;
    
    
    
    
    
    /**
     * The constructor of the class.
     */
    public Game(StatusBar aliensBar, StatusBar shootsBar)
    {  	    	
    	// Set the status bars.
    	this.runawayAliensStatus = aliensBar;   	
    	this.shootsStatus = shootsBar;
    	
    	
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
        shoots = MAX_SHOOTS;
    }
    
    
    
    
    /**
     * Load the files of the game (images, sounds...)
     */
    private void LoadContent()
    {
    	Image objImage = new Image();
    	
    	backgroundImg = objImage.getBackgroundImg();
    	univas_logo = objImage.getUnivasLogoImg();
    	alienImg_1 = objImage.getAlienImg1();
    	alienImg_2 = objImage.getAlienImg2();
    }
    
       
    
    
    /**
     * Restart game - reset some variables.
     */
    public void restartGame()
    {
        // Removes all the floating objects from this list.
        aliens.clear();
        // Set last alien time to zero.
        Alien.lastAlienTime = 0;      
        score = 0;
        shoots = MAX_SHOOTS;
        runawayAliens = 0;
        killedAliens = 0;
        lastTimeShoot = 0;
        ammunition = null;
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
    	checkButtonsPressed();
    	
    	// Creates a new alien, if it's the time, and add it to the array list.
        if(System.nanoTime() - Alien.lastAlienTime >= Alien.timeBetweenAliens)
        {
        	 
        	// Create a new alien and add it on the list of aliens.
        	createNewAlien();
            
        	// Used to don't exceed the bounds of the array.
            if(Alien.nextAlienLines >= Alien.alienLines.length)
                Alien.nextAlienLines = 0;
            
            Alien.lastAlienTime = System.nanoTime();
        	
        	// Check if it is necessary appear a new ammunition kit icon on the screen.
        	if(shoots < MAX_SHOOTS){
        		if((shoots % LIMIT_TO_APPEAR_AMMUNITION) == 0){
	        		createNewAmmunitionKit();	        		
	        	}
        	}
        }        
        
        updateAliensOnTheScreen();     
        
        if(ammunition != null){
        	ammunition.Update();
        	
        	if(ammunition.x < 0 - ammunition.getImage().getWidth()){
        		ammunition = null;
        	}
        }
        
        // Does it was pressed the button to get the ammunition kit?
        if(isGetKitBtnPressed){
        	
        	// The ammunition icon is on the screen?
        	if(ammunition != null){
        		
        		// Check if the ammunition kit was captured.
        		if(isAmmunitionCaptured()){
        			
        			// Play the sound of shoot.
            		PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.CLICK, 1);
            		Thread t = new Thread(pf);
            		t.start();
            		
            		shoots = MAX_SHOOTS;
            		ammunition = null;// Desappear the ammunition icon.
            		isGetKitBtnPressed = false;// Flag to control this block.
        		}
        	}
        }
        
        
        // Does player shoots?
        if(isShootPressed)
        {
        
        	// Check if there are ammunition to shoot.
        	if(shoots > 0){
        		if(System.nanoTime() - lastTimeShoot >= timeBetweenShots){
            		
            		// Play the sound of shoot.
        			PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.SHOOT_LASER, 1);
            		Thread t = new Thread(pf);
            		t.start();
        			
        			// Count one new shoot.
        			shoots = shoots <= 0 ? 0 : --shoots;
        			
        			checkSomeAlienDead();
                    
        			lastTimeShoot = System.nanoTime();
            	}
        	}
        }
        // When 'MAX_ALIENS_RUNAWAY' aliens runaway, the game ends.
        if(runawayAliens >= MAX_ALIENS_RUNAWAY){
        	Framework.gameState = Framework.GameState.GAMEOVER;
        }else{
        	// Check if the stopwatch is over.
            if(Stopwatch.isOver()){
            	Framework.gameState = Framework.GameState.WINNER;
            }
        }
    }
    
    
    
    
    
    /**
     * Check if the ammunition kit was captured.
     */
    private boolean isAmmunitionCaptured(){
    	int pos_x = ammunition.getX();
    	int pos_y = ammunition.getY();
    	int width = ammunition.getImage().getWidth();
    	int height = ammunition.getImage().getHeight();
    	
    	Rectangle rec = new Rectangle(pos_x, pos_y, width, height);
    
    	Point p = JoyStick.getInstance().getJoystickPosition();
    	
    	return rec.contains(p);
    }
    
    
    

    
    
    /**
     * Do a loop on the list and update all aliens on the screen.
     */
    private void updateAliensOnTheScreen(){
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
    private void checkSomeAlienDead(){
    	
        for(int i = 0; i < aliens.size(); i++)
        {
        	
        	Alien currentAlien = (Alien) aliens.get(i);
        	
        	int x = currentAlien.x;
        	int y = currentAlien.y;
        	int width = currentAlien.getImage().getWidth();
        	int height = currentAlien.getImage().getHeight();
        	
        	Rectangle rec = new Rectangle(x, y, width, height);
        
        	Point p = JoyStick.getInstance().getJoystickPosition();
        	
        	// We check, if the sight was over the aliens body, when player has shot.
        	if(rec.contains(p)){

        		 killedAliens++;
                 score += currentAlien.score;
                
                // Remove the alien from the array list.
                aliens.remove(i);
                
                // The alien dead was found, so leave the loop.
                break;
            }
        }
    }
    
    
    
    
    
    
    /**
     * Create a new icon of the ammunition kit.
     */
    private void createNewAmmunitionKit(){
    	// The object will be null when is the first instance or when the icon desapear from the screen.
		if(ammunition == null){
			
			int x = Ammunition.ammunitionLines[Ammunition.nextAmmunitionLine][0] + random.nextInt(200);
	    	int y = Ammunition.ammunitionLines[Ammunition.nextAmmunitionLine][1] ;
	    	
			ammunition = new Ammunition(x, y);// Create a new ammunition object.
			
			if(Ammunition.nextAmmunitionLine < Ammunition.ammunitionLines.length - 1)
				Ammunition.nextAmmunitionLine++;
			else{
				Ammunition.nextAmmunitionLine = 0;
			}
		}
    }
    
    
    
    
    
    
    /**
     * Create a new Alien and add it on the list of aliens.
     */
    private void createNewAlien(){
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
    	
    	// Here we increase nextAlienLines so that next alien will be created in next line.
        Alien.nextAlienLines++;
    }
    
    
    
    
    
        
    
    
    /**
     * Verifies if any of the mains buttons of the controller were pressed.
     * This method must to be used while the game is running.
     */
    private void checkButtonsPressed(){
    	
    	this.isSelectPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_SELECT);
    	this.isShootPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_SHOOT);
    	this.isGetKitBtnPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_GET_KIT);
    	
    	if(isSelectPressed){
    		Stopwatch.isStopwatchRunning = false;    		
    		Framework.gameState = Framework.GameState.MAIN_MENU;
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
        
        if(ammunition != null){
        	ammunition.Draw(g2d);
        }
        
        // Draw all the aliens on the screen.
        for(int i = 0; i < aliens.size(); i++)
            aliens.get(i).Draw(g2d);
      
        g2d.setFont(new Font("monospaced", Font.BOLD, 25));  
        shootsStatus.setValue(shoots);
        runawayAliensStatus.setValue(MAX_ALIENS_RUNAWAY - runawayAliens);
     	runawayAliensStatus.setString("UNIVÁS "+runawayAliens+"/"+MAX_ALIENS_RUNAWAY);
    	shootsStatus.setString("TIROS "+shoots+"/"+MAX_SHOOTS);
        
    	
        // Update the sight gun image on the screen.
    	JoyStick.getInstance().drawSight(g2d);
    	
    	// Draw the Univás logo in the lower left corner.
        g2d.drawImage(univas_logo, 0, Window.frameHeight - (univas_logo.getHeight() + 10), 250, 70, null);
    }
    

    
    /**
     * Return the currently score.
     */
    public int getScore(){
    	return score;
    }
    
    
    /**
     * It returns the number of killed aliens.
     */
    public int getKilledAliens(){
    	return killedAliens;
    }
    
    
    
}
