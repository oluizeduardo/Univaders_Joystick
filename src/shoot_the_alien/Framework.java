package shoot_the_alien;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import shoot_the_alien.Game;
import shoot_the_alien.JoyStick;
import shoot_the_alien.Framework;
import shoot_the_alien.Stopwatch;

/**
 * Framework that controls the game (Game.java) that created it, update it 
 * and draw it on the screen.
 * 
 * @author www.gametutorial.net
 * @author Luiz Eduardo da Costa
 * @category Game
 * @version 2.0
 */
public class Framework extends Canvas {
    
  
	private static final long serialVersionUID = 1L;

    /**
     * Time of one second in nanoseconds.
     * 1 second = 1 000 000 000 nanoseconds
     */
    public static final long secInNanosec = 1000000000L;
    
    /**
     * Time of one millisecond in nanoseconds.
     * 1 millisecond = 1 000 000 nanoseconds
     */
    public static final long milisecInNanosec = 1000000L;
    
    /**
     * FPS - Frames per second
     * How many times per second the game should update?
     * 
     * Increase or decrease this value moves at the speed of aliens.
     */
    private final int GAME_FPS = 60;// Initial value: 60.
    /**
     * Pause between updates. It is in nanoseconds.
     */
    private final long GAME_UPDATE_PERIOD = secInNanosec / GAME_FPS;
    
    /**
     * Possible states of the game.
     */
    public static enum GameState{STARTING, VISUALIZING, 
    							GAME_CONTENT_LOADING, 
    							MAIN_MENU, OPTIONS, 
    							PLAYING, RESTART, GAMEOVER}
    /**
     * Current state of the game.
     */
    public static GameState gameState;
    /**
     * Possible button selected.
     */
    public static enum ButtonSelected {NEW_GAME, RANKING, EXIT};   
    /**
     * The button currently selected.
     */
    public static ButtonSelected currentlyButtonSelected = ButtonSelected.NEW_GAME;
    /**
     * An array of ButtonSelected.
     */
    private ButtonSelected [] mainButtons = ButtonSelected.values();
    /**
     * Elapsed game time in nanoseconds.
     */
    private long gameTime;
    /**
     * It is used for calculating elapsed time.
     */
    private long lastTime;
    /**
     * Object of the actual game.
     */
    private Game game;
    /**
     * Background image of the main menu.
     */
    private BufferedImage background_main_menu;    
    /**
     * Object BufferedImage to open the image of New Game button.
     */
    private BufferedImage imgBtnNewGame, imgBtnNewGame2; 
    /**
     * Object BufferedImage to open the image of Ranking button.
     */
    private BufferedImage imgBtnRanking, imgBtnRanking2;
    /**
     * Object BufferedImage to open the image of Exit button.
     */
    private BufferedImage imgBtnExit, imgBtnExit2;
    /**
     * Control the execution of the joystick controller.
     */
    private JoyStick joyStick = null;
    /**
     * A flag to control the execution of the GameOver sound.
     */
    private boolean playedTheGameOverSound = false;
    
    
    
    
    
    
    /** 
     * Construtor da classe Framework.
     */
    public Framework ()
    {
        super();

        // Get the instance of the joystick class.
    	this.joyStick = JoyStick.getInstance();
    	
    	// Check if any controller was found.
    	if(joyStick.thereAreControllers()){
    		
    		new Thread(joyStick).start();

            // We must start the game in a new thread to work the configurations of UI.
            Thread gameThread = new Thread() {
                @Override
                public void run(){
                    GameLoop();
                }
            };
            gameThread.start();
            
            gameState = GameState.VISUALIZING;
            
    	}else{
    		
    		joyStick.showControllerNotFound();
    	}
    }
    
    
 
    
    
    /**
     * Load content - images, sounds, ...
     * This method is intended to load files for this class, 
     * files for the actual game can be loaded in Game.java.
     */
    private void LoadContent()
    {  	
    	Image objImage = new Image();
    	
    	// Load the initial image background.
    	background_main_menu = objImage.getBackgroundMenuImg();
    	imgBtnNewGame = objImage.getBtnNewGameImg();
    	imgBtnNewGame2 = objImage.getBtnNewGameImg2();
    	imgBtnRanking = objImage.getBtnRankingImg();
    	imgBtnRanking2 = objImage.getBtnRankingImg2();
    	imgBtnExit = objImage.getBtnExitImg();
    	imgBtnExit2 = objImage.getBtnExitImg2();
    	
    	// Yeah! It's about two hours playing the sound of background =)
        PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.INTRO_WARRIOR, 120);
        new Thread(pf).start();
    }
    
    
    
    
    
    
    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is up to date 
     * and then the game is drawn on the screen again.
     */
    private void GameLoop()
    {
        // This two variables are used in VISUALIZING state of the game. 
    	// We used them to wait some time so that we get correct frame/window resolution.
        long visualizingTime = 0;
        long lastVisualizingTime = System.nanoTime();
        
        // This variables are used for calculating the time that defines for how long we should 
        // put thread to sleep to meet the GAME_FPS.
        long beginTime, timeTaken, timeLeft;
        
        
        while(true)
        {
            beginTime = System.nanoTime();
            
            switch (gameState)
            {
            
	            case VISUALIZING:
	            	
	                // On Ubuntu OS (when I tested on my old computer) this.getWidth() method doesn't return the correct value 
	            	// immediately (eg. for frame that should be 800px width, returns 0 than 790 and at last 798px). 
	                // So we wait one second for the window/frame to be set to its correct size. Just in case we
	                // also insert 'this.getWidth() > 1' condition in case when the window/frame size wasn't set in time,
	                // so that we although get approximately size.
	                if(this.getWidth() > 1 && visualizingTime > secInNanosec)
	                {                    	
	                    Window.frameWidth = this.getWidth();
	                    Window.frameHeight = this.getHeight();

	                    // When we get size of frame we change status.
	                    gameState = GameState.STARTING;
	                }
	                else
	                {
	                    visualizingTime += System.nanoTime() - lastVisualizingTime;
	                    lastVisualizingTime = System.nanoTime();
	                }	                
	            break;                
                case STARTING: 
                    // Load files - images, sounds, ...
                    this.LoadContent();

                    // When all things that are called above finished, 
                    // we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case MAIN_MENU:
                	Stopwatch.isStopwatchRunning = false;
                	
                	checkButtonPressed();
                	
                break;
                case GAME_CONTENT_LOADING:
                	// Wait a time before start the game.
                	try {
						Thread.sleep(500);
					} catch (InterruptedException e) { }
                	

                	try{
	                	if(!Framework.th_stopwatch.isAlive())               		
	                		Framework.th_stopwatch.start();
                	}catch (IllegalThreadStateException e) { }
                	
                	Stopwatch.isStopwatchRunning = true;
                		
                break;
                case PLAYING:
                	                	
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime);
                    
                    lastTime = System.nanoTime();
                break;
                case RESTART:
                	RestartGame();
                break;
                case GAMEOVER:
                	// Stop the timewatch.
            		//Stopwatch.isStopwatchRunning = false;
                	if(!playedTheGameOverSound){
                		// Stop the timewatch.
                		Stopwatch.isStopwatchRunning = false;
                		
                		// Play the sound of the Game Over.
                		PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.GAME_OVER, 2);
                		Thread t = new Thread(pf);
                		t.start();
                        
                        playedTheGameOverSound = true;
                	}
                	
                	boolean isSelectedPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_SELECT);
                	
                	if(isSelectedPressed){
                		playedTheGameOverSound = false;
                		gameState = GameState.MAIN_MENU;
                	}
                		
                break;
				default:
					gameState = GameState.MAIN_MENU;
					break;
                
            }
            
            // Repaint the screen.
            this.repaint();
            
            // Here we calculate the time that defines for how long we should put thread to sleep to meet the GAME_FPS.
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (GAME_UPDATE_PERIOD - timeTaken) / milisecInNanosec; // In milliseconds
            
            // If the time is less than 10 milliseconds, then we will put thread to sleep for 10 millisecond so that some other thread can do some work.
            if (timeLeft < 10) 
                timeLeft = 10; //set a minimum
            try {            	
                 //Provides the necessary delay and also yields control so that other thread can do work.
                 Thread.sleep(timeLeft);
            } catch (InterruptedException ex) { }
        }
    }
    
    
    
    
    
    
    /**
     * Draw the game to the screen. It is called through repaint() method in GameLoop() method.
     */
    @Override
    public void Draw(Graphics2D g2d)
    {
        switch (gameState)
        {
	        case PLAYING:
	        	game.Draw(g2d);
	            
	        break;
            case MAIN_MENU:
            	ShowInitialMessage(g2d);
                
            break;
            case GAME_CONTENT_LOADING:
            	ShowLoadingMessage(g2d);
            	
            break;
            case GAMEOVER:
	            game.DrawGameOver(g2d);
	        break;
            default:
            	ShowLoadingMessage(g2d);
			break;
           
        }
    }
    
    
    
    
    /**
     * Show this message when the game is loading.
     * @param g2d
     */
    private void ShowLoadingMessage(Graphics2D g2d){
    	g2d.setColor(Color.white);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, 35));
        g2d.drawString("CARREGANDO...", Window.frameWidth / 2 - 120, Window.frameHeight / 2);
    }
    
    
    
    
    
    
    /**The index of the currently selected button on the main menu.*/
    private int btnSelectedIndex = 0;
    
    
    /**
     * Check which button was pressed and do anything.
     */
    private void checkButtonPressed(){
    	boolean isStartPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_START);               	
    	boolean isUpPressed = JoyStick.getInstance().checkPOVPressed(JoyStick.BTN_UP);
        boolean isDownPressed = JoyStick.getInstance().checkPOVPressed(JoyStick.BTN_DOWN);
        	
        mainButtons = ButtonSelected.values();
        
        
    	if(isStartPressed){
    		if(currentlyButtonSelected == ButtonSelected.NEW_GAME)
    			LoadNewGame();
    		if(currentlyButtonSelected == ButtonSelected.EXIT)
    			System.exit(0);
    	}
    	
    	if(isUpPressed || isDownPressed){
    		if(isDownPressed){
        		
        		btnSelectedIndex = btnSelectedIndex == mainButtons.length - 1 ? 0 : ++btnSelectedIndex;
        		currentlyButtonSelected = mainButtons[ btnSelectedIndex ];
        	}
        	if(isUpPressed){
        		
        		btnSelectedIndex = btnSelectedIndex == 0 ? btnSelectedIndex = mainButtons.length - 1 : --btnSelectedIndex;                   		
        		currentlyButtonSelected = mainButtons[ btnSelectedIndex ];
        	}
        	
        	PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.CLICK, 1);
    		Thread t = new Thread(pf);
    		t.start();
        	
        	// Wait a little time to change the button again.
			try {
				Thread.sleep(180);
			} catch (InterruptedException e1) { }
    	}
    }
    
    
    
    
    
    
    // Images of menu's button.
    BufferedImage imgNewGame=null;
	BufferedImage imgRanking=null;
	BufferedImage imgExit   =null;
    
    /**
     * Draw the image of the currently button selected on the screen.
     * @param g2d
     */
    private void drawSelectedButton(Graphics2D g2d){
    	
    	switch (currentlyButtonSelected) {
			case NEW_GAME:
				imgNewGame=imgBtnNewGame2;
		    	imgRanking=imgBtnRanking;
		    	imgExit   =imgBtnExit;
				break;
	
			case RANKING:
				imgNewGame=imgBtnNewGame;
		    	imgRanking=imgBtnRanking2;
		    	imgExit   =imgBtnExit;
				break;
			case EXIT:
				imgNewGame=imgBtnNewGame;
		    	imgRanking=imgBtnRanking;
		    	imgExit   =imgBtnExit2;
				break;
			default:
				currentlyButtonSelected = ButtonSelected.NEW_GAME;
				break;
		}
    	int btn_x = (Window.frameWidth / 2) - (imgBtnNewGame.getWidth() / 2);
    	int btn_y = (Window.frameHeight / 2) ;
    	
    	g2d.drawImage(imgNewGame, btn_x, btn_y, imgBtnNewGame.getWidth(), imgBtnNewGame.getHeight(), null); 
    	g2d.drawImage(imgRanking, btn_x, (int) (btn_y*1.25), imgBtnRanking.getWidth(), imgBtnRanking.getHeight(), null); 
    	g2d.drawImage(imgExit,    btn_x, (int) (btn_y*1.50), imgBtnExit.getWidth(), imgBtnExit.getHeight(), null); 
    	
    }
    
    
    
    
    
    
    
    
    /**
     * Show this message when the gamestate is MAIN_MENU.
     * @param g2d
     */
    private void ShowInitialMessage(Graphics2D g2d){
    	
    	g2d.setColor(Color.red);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, 18));
    	
    	g2d.drawImage(background_main_menu, 0, 0, Window.frameWidth, Window.frameHeight, null);
   
    	drawSelectedButton(g2d);
    	
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, 25));
        g2d.drawString("SISTEMAS DE INFORMAÇÃO - UNIVÁS - 2016", 10, Window.frameHeight - 10);
    }
    

    
    
    
    /**
     * Starts a new game.
     */
    private void LoadNewGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        game = new Game();
    }
    
    
  
    /**
     *  Restart game - reset game time and call RestartGame() method 
     *  of game object so that reset some variables.
     */
    private void RestartGame()
    {
    	Stopwatch.restart(); 
    	
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        game.restartGame();
                
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }

    
    
    
    
}
