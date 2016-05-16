package shoot_the_alien.view.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import shoot_the_alien.Framework;
import shoot_the_alien.Framework.GameState;
import shoot_the_alien.model.Image;
import shoot_the_alien.model.JoyStick;
import shoot_the_alien.view.screens.frame.Window;

public class GameOverScreen {

	
	 /**
	 * Red border to game over.
	 */
    private BufferedImage red_borderImg;
    private BufferedImage backgroundImg;
    
    
	private Image objImage;
	
	private int score = 0;
	
	
	
	
	/**
	 * The constructor.
	 */
	public GameOverScreen() {

		loadContents();
	}
	
	
	
	/**
	 * It sets the final score.
	 */
	public void setFinalScore(int finalScore){
		this.score = finalScore;
	}
	
	
	
	/**
	 * It loads all the media contents: images, sounds, etc.
	 */
	private void loadContents(){
		objImage = new Image();
		red_borderImg = objImage.getRedborderImg();
		backgroundImg = objImage.getBackgroundImg();
	}
	
	
	
	 /**
     * Draw the game over screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition Current mouse position.
     */
    public void DrawGameOver(Graphics2D g2d)
    {    
    	g2d.setFont(new Font("monospaced", Font.BOLD, 25)); 
    	
        // Draw the Game Over image.
    	g2d.drawImage(backgroundImg, 0, 0, Window.frameWidth, Window.frameHeight, null);
        g2d.drawImage(red_borderImg, 0, 0, Window.frameWidth, Window.frameHeight, null);
        
        g2d.setColor(Color.red);
        g2d.drawString("Game Over!!!", Window.frameWidth / 2 - 50, (int)(Window.frameHeight / 2)-40);
        g2d.drawString("Pontuação final: "+score, Window.frameWidth / 2 - 140, (int)(Window.frameHeight / 2));
        g2d.drawString("Pressione 3 ou ESPAÇO para continuar", Window.frameWidth / 2 - 250, (int)(Window.frameHeight /2) + 50);
    }
	
    
    
    
    /**
     * Wait for a button press to return to the Main menu Screen.
     */
    public void waitButtonPressed(){
    	boolean isSelectedPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_CONFIRM);
    	
    	if(isSelectedPressed){
    		//playedTheGameOverSound = false;
    		Framework.gameState = GameState.MAIN_MENU;
    	}
    }
    
    
    
	
	
}
