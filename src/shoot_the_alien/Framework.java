package shoot_the_alien;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import shoot_the_alien.Game;
import shoot_the_alien.JoyStick;
import shoot_the_alien.Framework;
import shoot_the_alien.Stopwatch;
import shoot_the_alien.screens.InitialScreen;
<<<<<<< HEAD
import shoot_the_alien.screens.RankingScreen;
=======
>>>>>>> d52c1a8f22c28717c07518d6cfd13162591d83f1
import shoot_the_alien.screens.WinnerScreen;

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
    							MAIN_MENU, PLAYING, 
    							RESTART, RANKING,
    							GAMEOVER, WINNER}
    /**
     * Current state of the game.
     */
    public static GameState gameState;
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
     * Control the execution of the joystick controller.
     */
    private JoyStick joyStick = null;
    /**
     * A flag to control the execution of the GameOver sound.
     */
    private boolean playedTheGameOverSound = false;
    /**
     * Displays in a bar the game's status like the number of runaway aliens 
     * and remaining shoots.
     */
    private StatusBar runawayAliens, shootStatus;
    /**
     * Object of the initial screen.
     */
    private InitialScreen screenMainMenu;
    /**
     * Object of the winner screen.
     */
    private WinnerScreen screenWinner;
<<<<<<< HEAD
    /**
     * Object to buid the ranking sreen.
     */
    private RankingScreen screenRanking;
=======
>>>>>>> d52c1a8f22c28717c07518d6cfd13162591d83f1
    
    
    
    
    
    
    /** 
     * Construtor da classe Framework.
     */
    public Framework ()
    {
        super();
        
        this.screenMainMenu = new InitialScreen(this);
        this.screenWinner = new WinnerScreen();
<<<<<<< HEAD
        this.screenRanking = new RankingScreen();
        
        
        // Create the two statusbar on the top of the screen.
=======
        
>>>>>>> d52c1a8f22c28717c07518d6cfd13162591d83f1
        createStatusBar();              
        
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
     * Create the game's status bars.
     */
    private void createStatusBar(){
    	runawayAliens = new StatusBar();
    	runawayAliens.setBounds(10, 10, 350, 30);
    	runawayAliens.setMaximum(Game.MAX_ALIENS_RUNAWAY);
        
    	shootStatus = new StatusBar();
    	shootStatus.setBounds(Window.frameWidth - 360, 10, 350, 30);
    	shootStatus.setMaximum(Game.MAX_SHOOTS);
    	
        super.add(runawayAliens);
        super.add(shootStatus);
    }
 
    
    
    /**
     * Load content - images, sounds, ...
     * This method is intended to load files for this class, 
     * files for the actual game can be loaded in Game.java.
     */
    private void LoadContent()
    {  	        
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
                	
                	if(game != null)
                		setStatusBarsVisibility(false);
                	
                	screenMainMenu.checkButtonPressed();
                	
                break;
                case GAME_CONTENT_LOADING:
                	// Wait a time before start the game.
                	try {
						Thread.sleep(500);
					} catch (InterruptedException e) { }
                	
                	try{
	                	//if(!Framework.th_stopwatch.isAlive()) 
                		    Thread th_stopwatch = new Thread(stopWatch);
	                		th_stopwatch.start();
                	}catch (IllegalThreadStateException e) { }
                	
                	// Turn on the Stopwatch.
                	Stopwatch.isStopwatchRunning = true;               	
                	
                	setStatusBarsVisibility(true);
                	
                break;
                case PLAYING:
                	                	
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime);
                    
                    lastTime = System.nanoTime();
                break;
                case RESTART:
                	RestartGame();
                break;
                
                case RANKING:
                	
                	if(screenRanking.pnBaseTable == null)
                    	super.add(screenRanking.getPnTableBase());
                	
                	screenRanking.checkButtonPressed();
                break;
                
                case WINNER:	
                	
                	if(areStatusbarVisible())
                		setStatusBarsVisibility(false);
                	if(screenWinner.pnBaseFields == null)
<<<<<<< HEAD
                    	super.add(screenWinner.getPanelFields(game.getScore()));
=======
                    	super.add(screenWinner.getPanelFields());
>>>>>>> d52c1a8f22c28717c07518d6cfd13162591d83f1
                	
                	screenWinner.checkButtonPressed();
                	
                break;
                case GAMEOVER:
                	// Stop the timewatch.
                	if(!playedTheGameOverSound){
                		// Stop the timewatch.
                		Stopwatch.isStopwatchRunning = false;
                		
                		setStatusBarsVisibility(false);
                		
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
                screenMainMenu.drawInitialScreen(g2d);
            	
            break;
            case GAME_CONTENT_LOADING:
            	ShowLoadingMessage(g2d);	
            break;
            case GAMEOVER:
	            game.DrawGameOver(g2d);
	        break; 
            case WINNER:
            	screenWinner.drawWinnerScreen(g2d);
<<<<<<< HEAD
            break;
            case RANKING:
            	screenRanking.drawRankingScreen(g2d);
=======
>>>>>>> d52c1a8f22c28717c07518d6cfd13162591d83f1
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
    
    
    
    
    /**
     * @return <code>true</code> if both status bar are visible, 
     * <code>false</code> is are visible.
     */
    private boolean areStatusbarVisible(){
    	return (game.runawayAliensStatus.isVisible() && game.shootsStatus.isVisible());
    }
    
    
    
    /**
     * Set the visibility of the progress bars.
     * @param isVisibility
     */
    private void setStatusBarsVisibility(boolean isVisibility){
    	game.runawayAliensStatus.setVisible(isVisibility);
    	game.shootsStatus.setVisible(isVisibility);
    }
    

    
    
    
    /**
     * Starts a new game.
     */
    public void loadNewGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        game = new Game(runawayAliens, shootStatus);
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
