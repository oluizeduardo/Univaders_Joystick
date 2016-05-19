package shoot_the_alien.model;

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
	
	/** Buffer of the Background Ranking image. */
	private BufferedImage bfBackgroundRanking;
	
	/** Buffer of the Background's image. */
	private BufferedImage bfbackground, bfbackground_winner;
	
	/** Buffer of the Background's GameOver image. */
	private BufferedImage bfbkgdGameOver;
	
	/** Buffer of the Logo Univás image.*/
	private BufferedImage bfUnivasLogo;
	
	/** Buffer of the Univás Logo with background white.*/
	private BufferedImage bfUnivasLogo2;
	
	/** Buffer of the image with the logo of the enterprise supporting the developing.*/
	private BufferedImage bfSupportLogo;
	
	/**Buffer of the Alien image.*/
	private BufferedImage bfAlien_1;
	
	/**Buffer of the Alien image.*/
	private BufferedImage bfAlien_2;
	
	/**Buffer of ammunition image.*/
	private BufferedImage bfAmmunition;
	
	/**Buffer of the sight image.*/
	private BufferedImage bfSight;
	
	/**Buffer of the New game button image.*/
	private BufferedImage bfBtnNewGame, bfBtnNewGame2;
	
	/**Buffer of the ranking button image.*/
	private BufferedImage bfBtnRanking, bfBtnRanking2;

	/**Buffer of the exit button image.*/
	private BufferedImage bfBtnExit, bfBtnExit2;
	
	/**Buffer of the Save button image.*/
	private BufferedImage bfBtnSave, bfBtnSave2;
	
	/**Buffer of the Cancel button image.*/
	private BufferedImage bfBtnCancel, bfBtnCancel2;
	
	
	
	
	
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
    	String strUrl = "/shoot_the_alien/resources/images/";
		URL url;
    	
		try {
			url = this.getClass().getResource(strUrl+"sight.png");
        	bfSight = ImageIO.read(url);
        	
        	url = this.getClass().getResource(strUrl+"backgrounds/background_menu.gif");          
        	bfBackgroundMenu = ImageIO.read(url);
        	
        	url = this.getClass().getResource(strUrl+"backgrounds/bkgd_ranking.gif");          
        	bfBackgroundRanking = ImageIO.read(url);
        	
        	url = this.getClass().getResource(strUrl+"/ammunition_kit.png");          
        	bfAmmunition = ImageIO.read(url);
        	
        	url = this.getClass().getResource(strUrl+"backgrounds/background.jpg");
            bfbackground = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"backgrounds/bkgd_winner.gif");
            bfbackground_winner = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"logos/logo_base.png");
            bfUnivasLogo = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"logos/logo_base.gif");
            bfUnivasLogo2 = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"logos/logo_LinkMG.jpg");
            bfSupportLogo = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"backgrounds/bkgd_gameover.gif");
            bfbkgdGameOver = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"aliens/alien1.png");
            bfAlien_1 = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"aliens/alien2.png");
            bfAlien_2 = ImageIO.read(url);

            url = this.getClass().getResource(strUrl+"buttons/btn_newgame.png");
            bfBtnNewGame = ImageIO.read(url);
            url = this.getClass().getResource(strUrl+"buttons/btn_newgame2.png");
            bfBtnNewGame2 = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"buttons/btn_ranking.png");
            bfBtnRanking = ImageIO.read(url);
            url = this.getClass().getResource(strUrl+"buttons/btn_ranking2.png");
            bfBtnRanking2 = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"buttons/btn_exit.png");
            bfBtnExit = ImageIO.read(url);
            url = this.getClass().getResource(strUrl+"buttons/btn_exit2.png");
            bfBtnExit2 = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"buttons/btn_save.png");
            bfBtnSave = ImageIO.read(url);
            url = this.getClass().getResource(strUrl+"buttons/btn_save2.png");
            bfBtnSave2 = ImageIO.read(url);
            
            url = this.getClass().getResource(strUrl+"buttons/btn_cancel.png");
            bfBtnCancel = ImageIO.read(url);
            url = this.getClass().getResource(strUrl+"buttons/btn_cancel2.png");
            bfBtnCancel2 = ImageIO.read(url);
            
        	
		} catch (IOException e) {
			System.err.println("Erro ao carregar a imagem!\n\n"+e.getMessage());
		
		}catch (IllegalArgumentException ex) {
			// Image package not found.
			JOptionPane.showMessageDialog(null, "Erro ao carregar pacote de imagens!\n\n"
					+ "- Verifique o endereço do pacote de imagens\n"
					+ "- Os arquivos de imagens devem manter o mesmo nome", "Univaders", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	
	
	
	public BufferedImage getSightImg(){		
		return bfSight;
	}

	
	public BufferedImage getBackgroundMenuImg(){
		return bfBackgroundMenu;
	}
	
	public BufferedImage getBackgroundRankingImg(){
		return bfBackgroundRanking;
	}
	
	public BufferedImage getBackgroundImg(){
		return bfbackground;
	}

	public BufferedImage getBackgroundWinnerImg(){
		return bfbackground_winner;
	}
	
	public BufferedImage getBackgroundGameOver(){
		return bfbkgdGameOver;
	}	
	
	public BufferedImage getUnivasLogoImg(){
		return bfUnivasLogo;
	}
	
	public BufferedImage getUnivasLogoImg_2(){
		return bfUnivasLogo2;
	}
	
	public BufferedImage getSupportLogoImg(){
		return bfSupportLogo;
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
	
	public BufferedImage getBtnSaveImg2(){
		return bfBtnSave2;
	}
	
	public BufferedImage getBtnSaveImg(){
		return bfBtnSave;
	}
	
	public BufferedImage getBtnCancelImg2(){
		return bfBtnCancel2;
	}
	
	public BufferedImage getBtnCancelImg(){
		return bfBtnCancel;
	}
}
