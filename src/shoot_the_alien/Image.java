package shoot_the_alien;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;



/**
 * Stores the informations and location about the images used on the game.
 * 
 * @author Luiz Eduardo da Costa
 */
public class Image {

	
	
	/** Buffer of the Background Menu's image. */
	private BufferedImage bfBackgroundMenu;
	
	/** Buffer of the Background's image. */
	private BufferedImage bfbackground;
	
	/** Buffer of the Logo Univás image.*/
	private BufferedImage bfUnivasLogo;
	
	/**Buffer of the Alien image.*/
	private BufferedImage bfAlien_1;
	
	/**Buffer of the Alien image.*/
	private BufferedImage bfAlien_2;
	
	/**Buffer of ammunition image.*/
	private BufferedImage bfAmmunition;
	
	/**Buffer of the Red border image.*/
	private BufferedImage bfRedBorder;
	
	/**Buffer of the sight image.*/
	private BufferedImage bfSight;
	
	/**Buffer of the New game button image.*/
	private BufferedImage bfBtnNewGame, bfBtnNewGame2;
	
	/**Buffer of the ranking button image.*/
	private BufferedImage bfBtnRanking, bfBtnRanking2;

	/**Buffer of the exit button image.*/
	private BufferedImage bfBtnExit, bfBtnExit2;
	
	
	
	
	/**
	 * The constructor load the images and stores them in a BufferedImage object.
	 */
	public Image(){
		loadContent();
	}
	

	
	
	
	/**
	 * Load the images in a BufferedImage object.
	 */
	private void loadContent(){
		
		// URL common to all files.
    	String url = "/shoot_the_alien/resources/images/";
		
		try {
        	URL sightImgUrl = this.getClass().getResource(url+"sight.png");
        	bfSight = ImageIO.read(sightImgUrl);
        	
        	URL backgroundMenuImg = this.getClass().getResource(url+"backgrounds/background_menu.gif");          
        	bfBackgroundMenu = ImageIO.read(backgroundMenuImg);
        	
        	URL ammunitionURL = this.getClass().getResource(url+"/ammunition_kit.png");          
        	bfAmmunition = ImageIO.read(ammunitionURL);
        	
        	URL backgroundImgUrl = this.getClass().getResource(url+"backgrounds/background.jpg");
            bfbackground = ImageIO.read(backgroundImgUrl);
            
            URL univas_logo_ImgUrl = this.getClass().getResource(url+"backgrounds/logo_base.png");
            bfUnivasLogo = ImageIO.read(univas_logo_ImgUrl);
            
            URL alienImgUrl_1 = this.getClass().getResource(url+"aliens/alien1.png");
            bfAlien_1 = ImageIO.read(alienImgUrl_1);
            
            URL alienImgUrl_2 = this.getClass().getResource(url+"aliens/alien2.png");
            bfAlien_2 = ImageIO.read(alienImgUrl_2);
            
            URL red_borderImgUrl = this.getClass().getResource(url+"backgrounds/red_border.png");
            bfRedBorder = ImageIO.read(red_borderImgUrl);

            URL btnNewGameUrl = this.getClass().getResource(url+"buttons/btn_newgame.png");
            bfBtnNewGame = ImageIO.read(btnNewGameUrl);
            URL btnNewGameUrl2 = this.getClass().getResource(url+"buttons/btn_newgame2.png");
            bfBtnNewGame2 = ImageIO.read(btnNewGameUrl2);
            
            URL btnRankingUrl = this.getClass().getResource(url+"buttons/btn_ranking.png");
            bfBtnRanking = ImageIO.read(btnRankingUrl);
            URL btnRankingUrl2 = this.getClass().getResource(url+"buttons/btn_ranking2.png");
            bfBtnRanking2 = ImageIO.read(btnRankingUrl2);
            
            URL btnExitUrl = this.getClass().getResource(url+"buttons/btn_exit.png");
            bfBtnExit = ImageIO.read(btnExitUrl);
            URL btnExitUrl2 = this.getClass().getResource(url+"buttons/btn_exit2.png");
            bfBtnExit2 = ImageIO.read(btnExitUrl2);
            
        	
		} catch (IOException e) {
			System.err.println("Erro ao carregar a imagem!\n\n"+e.getMessage());
		
		}catch (IllegalArgumentException ex) {
			// Image package not found.
			JOptionPane.showMessageDialog(null, "Pacote de imagens não encontrado!", "Univaders", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	
	
	
	public BufferedImage getSightImg(){		
		return bfSight;
	}

	
	public BufferedImage getBackgroundMenuImg(){
		return bfBackgroundMenu;
	}
	
	public BufferedImage getBackgroundImg(){
		return bfbackground;
	}

	
	public BufferedImage getRedborderImg(){
		return bfRedBorder;
	}
	
	
	public BufferedImage getUnivasLogoImg(){
		return bfUnivasLogo;
	}
	
	
	public BufferedImage getAlienImg1(){
		return bfAlien_1;
	}
	
	public BufferedImage getAlienImg2(){
		return bfAlien_2;
	}

	public BufferedImage getBtnNewGameImg(){
		return bfBtnNewGame;
	}
		
	public BufferedImage getBtnRankingImg(){
		return bfBtnRanking;
	}
	
	public BufferedImage getBtnExitImg(){
		return bfBtnExit;
	}

	public BufferedImage getBtnNewGameImg2(){
		return bfBtnNewGame2;
	}
		
	public BufferedImage getBtnRankingImg2(){
		return bfBtnRanking2;
	}
	
	public BufferedImage getBtnExitImg2(){
		return bfBtnExit2;
	}
	
	public BufferedImage getAmmunitionKitImg(){
		return bfAmmunition;
	}
	
	
	
	
}
