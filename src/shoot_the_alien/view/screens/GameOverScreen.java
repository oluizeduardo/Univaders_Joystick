package shoot_the_alien.view.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import shoot_the_alien.Framework;
import shoot_the_alien.Framework.GameState;
import shoot_the_alien.control.AbstractJoyStick;
import shoot_the_alien.model.Image;
import shoot_the_alien.view.screens.frame.Window;

/**
 * It builds the gameover screen.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 16/05/16
 */
public class GameOverScreen implements ConfirmButtonListener {
	
	/**
	 * Background of the screen.
	 */
    private BufferedImage backgroundImg;
    /**
   	 * Image of the Cancel button.
   	 */
    private BufferedImage imgBtnCancelar;
    /**
     * It is used to open an image.
     */
	private Image objImage;
	/**
	 * The winner's final score.
	 */
	private int score = 0;
	/**
	 * The number of dead aliens.
	 */
	private int killedAliens = 0;
	
	/**
	 * The constructor.
	 */
	public GameOverScreen() {
		AbstractJoyStick.getInstance().addConfirmListener(this);
		loadContents();
	}
	
	/**
	 * It sets the final score.
	 */
	public void setFinalScore(int finalScore){
		this.score = finalScore;
	}
	
	/**
	 * It sets the number of dead aliens.
	 */
	public void setNumberOfKilledAliens(int kills){
		this.killedAliens = kills;
	}
	
	/**
	 * It loads all the media contents: images, sounds, etc.
	 */
	private void loadContents(){
		objImage = new Image();
		backgroundImg = objImage.getBackgroundGameOver();
		imgBtnCancelar = objImage.getBtnCancelImg2();
	}
	
	 /**
     * Draw the game over screen.
     * 
     * @param g2d Graphics2D
     * @param mousePosition Current mouse position.
     */
    public void DrawGameOver(Graphics2D g2d)
    {   
        // Draw the Game Over image.
    	g2d.drawImage(backgroundImg, 0, 0, Window.frameWidth, Window.frameHeight, null);
        
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("monospaced", Font.BOLD, 25));
        g2d.drawString("Você falhou na missão e a UNIVÁS foi atacada...", Window.frameWidth / 2 - 350, (int)(Window.frameHeight / 2)-45);
        
        g2d.setFont(new Font("monospaced", Font.BOLD, 30));
        g2d.drawString("Aliens Mortos: "+killedAliens, Window.frameWidth / 2 - 120, (int)(Window.frameHeight / 2)+20);
        g2d.drawString("Pontuação final: "+score, Window.frameWidth / 2 - 150, (int)(Window.frameHeight / 2)+80);
        
        int btn_x = ((Window.frameWidth / 2) - (imgBtnCancelar.getWidth() / 2));
    	int btn_y = (Window.frameHeight / 2) + 220;
		
        g2d.drawImage(imgBtnCancelar, btn_x, btn_y, imgBtnCancelar.getWidth(), imgBtnCancelar.getHeight(), null);
    	
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, 16));
        g2d.drawString("Pressione BOTÃO 3", Window.frameWidth - 200, Window.frameHeight - 10);
    }

    /**
     * Wait for a button press to return to the Main menu Screen.
     */
	@Override
	public void confirmPressed() {
		if(Framework.gameState.equals(Framework.GameState.GAMEOVER)) {
			Framework.gameState = GameState.SUPPORT;
		}
	}
}
