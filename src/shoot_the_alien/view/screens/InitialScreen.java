package shoot_the_alien.view.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import shoot_the_alien.Framework;
import shoot_the_alien.Framework.GameState;
import shoot_the_alien.control.AbstractJoyStick;
import shoot_the_alien.model.Image;
import shoot_the_alien.model.PlayWAVFile;
import shoot_the_alien.view.screens.frame.Window;


/**
 * Here are all the contents, setting and controlling of the main menu screen.
 * <p>
 * There is the controller methods of the change of the currentrly selected button
 * on the main menu.
 * <p>
 * Load and sets all the contents of the initial screen.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 05/05/16
 *
 */
public class InitialScreen implements InitialScreenListener {
	
	/**
	 * Used to load a specific image.
	 */
	private Image objImage = new Image();
	/**
     * Object BufferedImage to open an image
     */
	private BufferedImage imgBackground, imgBtnNewGame, 
						  imgBtnNewGame2, imgBtnRanking, 
						  imgBtnRanking2, imgBtnExit, imgBtnExit2;
	
	/**
     * Possible button selected.
     */
    private static enum ButtonSelected {NEW_GAME, RANKING, EXIT};   
    /**
     * An array of ButtonSelected.
     */
    private ButtonSelected [] mainMenuButtons = ButtonSelected.values();
    /**
     * The initial currently selected button.
     */
    private static ButtonSelected currentlyButtonSelected = ButtonSelected.NEW_GAME;
    /**
     * The index of the currently selected button on the main menu.
     */
    private int btnSelectedIndex = 0;
    /**
     * Used to control something on the screen.
     */
    private Framework framework;
    /**
     * Object used to load a sound file.
     */
    private PlayWAVFile soundFile;
	
    /**
     * Class constructor. 
     * 
     * @param fw Object Framework, used to control something.
     */
	public InitialScreen(Framework fw) { 
		
		this.framework = fw;
		AbstractJoyStick.getInstance().setInitialListener(this);
		loadContent();
	}
	
	/**
	 * Load all contents of the class, image, sounds, etc.
	 */
	private void loadContent(){
		imgBackground = objImage.getBackgroundMenuImg();
		imgBtnNewGame = objImage.getBtnNewGameImg();
    	imgBtnNewGame2 = objImage.getBtnNewGameImg2();
    	imgBtnRanking = objImage.getBtnRankingImg();
    	imgBtnRanking2 = objImage.getBtnRankingImg2();
    	imgBtnExit = objImage.getBtnExitImg();
    	imgBtnExit2 = objImage.getBtnExitImg2();
    	
    	soundFile = new PlayWAVFile(PlayWAVFile.CLICK, 1);
		
	}
	
	/**
	 * Draw the initial screen: background image, selected buttons and string message.
	 * All this are drawing in the game state MAIN_MENU.
     * @param g2d
     */
    public void drawInitialScreen(Graphics2D g2d){
    	
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Calibri", Font.BOLD, 45));
        g2d.drawImage(imgBackground, 0, 0, Window.frameWidth, Window.frameHeight, null);
        g2d.drawString("SISTEMAS DE INFORMAÇÃO - UNIVÁS", 10, Window.frameHeight - 10);
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, 16));
        g2d.drawString("Pressione START", Window.frameWidth - 200, Window.frameHeight - 10);
    
        drawSelectedButton(g2d);
    }
	
    /**
     * Draw the image of the currently button selected on the screen.
     * 
     * @param g2d Necessary to show the images on the screen.
     */
    private void drawSelectedButton(Graphics2D g2d){
    	
    	// Images of menu's button.
        BufferedImage imgNewGame=null;
    	BufferedImage imgRanking=null;
    	BufferedImage imgExit   =null;
    	
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
    
	public void playSound() {
		// Play the click sound.
		Thread th_click = new Thread(soundFile);
		th_click.start();
		
		// Wait a little time to change the button again.
		try {
			Thread.sleep(180);
		} catch (InterruptedException e1) { }
	}

   @Override
	public  void upPressed() {
		btnSelectedIndex = btnSelectedIndex == 0 ? btnSelectedIndex = mainMenuButtons.length - 1 : --btnSelectedIndex;                   		
		currentlyButtonSelected = mainMenuButtons[ btnSelectedIndex ];
    	playSound();
	}

   @Override
	public  void downPressed() {
		btnSelectedIndex = btnSelectedIndex == mainMenuButtons.length - 1 ? 0 : ++btnSelectedIndex;
		currentlyButtonSelected = mainMenuButtons[ btnSelectedIndex ];
    	playSound();
	}

   @Override
	public   void confirmed() {
		if(currentlyButtonSelected == ButtonSelected.NEW_GAME)
			framework.loadNewGame();
		
		if(currentlyButtonSelected == ButtonSelected.RANKING)
			Framework.gameState = GameState.RANKING;
			
		if(currentlyButtonSelected == ButtonSelected.EXIT)
			System.exit(0);
	}
}
