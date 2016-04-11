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
	
	/**Buffer of the Red border image.*/
	private BufferedImage bfRedBorder;
	
	/**Buffer of the sight image.*/
	private BufferedImage bfSight;
	
	
	
	
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
        	
        	URL backgroundImgUrl = this.getClass().getResource(url+"backgrounds/background.jpg");
            bfbackground = ImageIO.read(backgroundImgUrl);
            
            URL univas_rodape_ImgUrl = this.getClass().getResource(url+"backgrounds/logo_base.png");
            bfUnivasLogo = ImageIO.read(univas_rodape_ImgUrl);
            
            URL alienImgUrl_1 = this.getClass().getResource(url+"aliens/alien1.png");
            bfAlien_1 = ImageIO.read(alienImgUrl_1);
            
            URL alienImgUrl_2 = this.getClass().getResource(url+"aliens/alien2.png");
            bfAlien_2 = ImageIO.read(alienImgUrl_2);
            
            URL red_borderImgUrl = this.getClass().getResource(url+"backgrounds/red_border.png");
            bfRedBorder = ImageIO.read(red_borderImgUrl);
        	
        	
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
	
	
	
	
	
	
	
}
