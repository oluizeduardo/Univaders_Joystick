package shoot_the_alien;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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
     * Width of the frame.
     */
    public static int frameWidth;
    /**
     * Height of the frame.
     */
    public static int frameHeight;

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
    private final int GAME_FPS = 60;
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
     * A flag to control the execution of the GameOver sound.
     */
    private boolean playedTheGameOverSound = false;
    /**
     * Control the execution of the joystick controller.
     */
    private JoyStick joyStick = null;
    
    
    
    
    
    
    
    /** 
     * Construtor da classe Framework.
     */
    public Framework ()
    {
        super();

        
    	this.joyStick = new JoyStick();
    	
    	// Verify if any controller was found.
    	if(joyStick.thereAreControllers()){
    		
    		new Thread(joyStick).start();
    		
    		// Yeah! It's about two hours playng the sound of background =)
            PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.INTRO_WARRIOR, 120);
            new Thread(pf).start();

            
            gameState = GameState.VISUALIZING;
            
            //We start game in new thread.
            // Esse trecho precisa estar dentro de um Thread para funcionar as configuraÃ§Ãµes de UI.
            Thread gameThread = new Thread() {
                @Override
                public void run(){
                    GameLoop();
                }
            };
            gameThread.start();
            
    	}else{
    		
    		JOptionPane.showMessageDialog(null, "Nenhum controle encontrado!!");
    		System.exit( 0 );
    	}
    }
    
    
 
    
    
    /**
     * Load content - images, sounds, ...
     * This method is intended to load files for this class, 
     * files for the actual game can be loaded in Game.java.
     */
    private void LoadContent()
    {  	
    	
    	// Load the initial image background.
        try
        {
            URL backgroundMenuImg = this.getClass().getResource("/shoot_the_alien/resources/images/background_menu.png");
            
            background_main_menu = ImageIO.read(backgroundMenuImg);
        }
        catch (IOException ex) {
            Logger.getLogger(Framework.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    /**
     * In specific intervals of time (GAME_UPDATE_PERIOD) the game/logic is updated 
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
	                    frameWidth = this.getWidth();
	                    frameHeight = this.getHeight();
	
	                    // When we get size of frame we change status.
	                    gameState = GameState.STARTING;
	                }
	                else
	                {
	                    visualizingTime += System.nanoTime() - lastVisualizingTime;
	                    lastVisualizingTime = System.nanoTime();
	                }	                
	            break;
                case PLAYING:
                    gameTime += System.nanoTime() - lastTime;
                    
                    game.UpdateGame(gameTime, mousePosition());
                    
                    lastTime = System.nanoTime();
                break;
                case GAMEOVER:
                	
                	if(!playedTheGameOverSound){
                		// Play the sound of the Game Over.
                    	PlayWAVFile pf = new PlayWAVFile(PlayWAVFile.GAME_OVER, 2);
                    	Thread t = new Thread(pf);
                        t.start();
                        
                        playedTheGameOverSound = true;
                	}
                	
                break;
                case RESTART:
                	this.restartGame();
                break;
                case GAME_CONTENT_LOADING:
                	// Wait a time before start the game.
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) { }
                break;
                case STARTING: 
                    // Load files - images, sounds, ...
                    this.LoadContent();

                    // When all things that are called above finished, 
                    // we change game status to main menu.
                    gameState = GameState.MAIN_MENU;
                break;
                case MAIN_MENU:
                	                	
                	if(JoyStick.isButtonPressed){
                		newGame();
                	}
                	
                	
                	// Restart this flag to can play the sound again.
                	playedTheGameOverSound = false;                	
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
                game.Draw(g2d, mousePosition());
            break;
            case GAMEOVER:
                game.DrawGameOver(g2d, mousePosition());
            break;
            case MAIN_MENU:
            	
            	int x = (frameWidth / 2) - 150;
            	int y = (frameHeight / 2);
            	
            	g2d.setColor(Color.red);
                g2d.setFont(new Font("Lucida Sans", Font.BOLD, 18));
            	
            	g2d.drawImage(background_main_menu, 0, 0, frameWidth, frameHeight, null);       
                g2d.drawString("Pressione START para iniciar o jogo. . .", x, (int) (y*1.40)); 
 
                g2d.setColor(Color.white);
                g2d.setFont(new Font("Lucida Sans", Font.BOLD, 25));
                g2d.drawString("SISTEMAS DE INFORMÇÃO - UNIVÁS - 2016", 10, frameHeight - 10);
                
                
            break;
            case OPTIONS:
                //...
            break;
            case GAME_CONTENT_LOADING:

                g2d.setColor(Color.white);
                g2d.setFont(new Font("Lucida Sans", Font.BOLD, 35));
                g2d.drawString("CARREGANDO...", frameWidth / 2 - 120, frameHeight / 2);
            break;
           
        }
    }
    
    
    
    
    
    /**
     * Starts a new game.
     */
    private void newGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        game = new Game();
    }
    
    
    
    
    
    
    
    /**
     *  Restart game - reset game time and call RestartGame() method of game object so that reset some variables.
     */
    private void restartGame()
    {
        // We set gameTime to zero and lastTime to current time for later calculations.
        gameTime = 0;
        lastTime = System.nanoTime();
        game.RestartGame();
                
        // We change game status so that the game can start.
        gameState = GameState.PLAYING;
    }
    
    
    
    
    
    /**
     * Returns the position of the mouse pointer in game frame/window.
     * If mouse position is null than this method return 0,0 coordinate.
     * 
     * @return Point of mouse coordinates.
     */
    private Point mousePosition()
    {
    	Point mp = null;
    	
        try
        {
            mp = this.getMousePosition();
        }
        catch (Exception e)
        {
            mp = new Point(300, 300);
        }
        return mp;
    }
    
    

    
    
    
    
}
