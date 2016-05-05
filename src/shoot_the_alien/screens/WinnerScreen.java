package shoot_the_alien.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import shoot_the_alien.Image;
import shoot_the_alien.Window;

/**
 * 
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 05/05/2016
 *
 */
public class WinnerScreen {

	private BufferedImage background, univas_logo, 
						  imgBtnSave, imgBtnSave2,
						  imgBtnCancel, imgBtnCancel2;
	
	private Image objImage;
	/**
     * The panel where will add the other components.
     */
	public JPanel pnBaseFields;
	/**
     * The fields to colect information about the winner.
     */
    private JTextField tfName, tfIdentification, tfFinalScore;
    /**
     * Possible button selected.
     */
    private static enum ButtonSelected {SAVE, CANCEL};   
    /**
     * An array of ButtonSelected.
     */
    private ButtonSelected [] mainButtons = ButtonSelected.values();
    /**
     * The initial currently selected button.
     */
    private static ButtonSelected currentlyButtonSelected = ButtonSelected.SAVE;
    /**
     * The index of the currently selected button.
     */
    private int btnSelectedIndex = 0;
    
    
    
    
    
    
	/**
	 * The class constructor.
	 * Load all the contents in its initialization.
	 */
	public WinnerScreen() {
		loadContent();
	}
	
	
	
	
	
	/**
	 * Load all contents of the class, image, sounds, etc.
	 */
	private void loadContent(){
		objImage = new Image();		
		background  = objImage.getBackgroundImg();
		univas_logo = objImage.getUnivasLogoImg();
		imgBtnSave = objImage.getBtnSaveImg();
    	imgBtnSave2 = objImage.getBtnSaveImg2();
    	imgBtnCancel = objImage.getBtnCancelImg();
    	imgBtnCancel2 = objImage.getBtnCancelImg2();
	}
	
	
	
	
	/**
	 * Build the base panel of the formulary which will get information about the game winner.
	 * @return An instance setuped of a JPanel object.
	 */
	public JPanel getPanelFields(){
    	
    	GridLayout gridLayout = new GridLayout(3, 1);
    	gridLayout.setVgap(30);
        
        pnBaseFields = new JPanel(null);
        pnBaseFields.setBackground(new Color(0,0,0, 150));
        pnBaseFields.setSize(880, 400);
        int x = (Window.frameWidth / 2) - (pnBaseFields.getWidth() / 2);
        int y = (Window.frameHeight / 2) - (pnBaseFields.getHeight() / 2); 
        pnBaseFields.setLocation(x, y-50);
        
        JPanel pnFields = new JPanel(gridLayout);
        pnFields.setSize(500, 300);
        pnFields.setBackground(new Color(0,0,0,0));
        pnFields.setLocation(330, 50);
        
        JPanel pnLabels = new JPanel(gridLayout);
        pnLabels.setSize(300, 300);
        pnLabels.setBackground(new Color(0,0,0,0));
        pnLabels.setLocation(0, 50);
        
        Font fontField = new Font("Arial", Font.BOLD, 40);
        Font fontLabel = new Font("Arial", Font.ITALIC + Font.BOLD, 50);
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,null,new Color(0, 0, 0));
        
        tfName = new JTextField(); 
        tfName.setFont(fontField);
        tfName.setHorizontalAlignment(JTextField.CENTER);
        tfName.setBorder(border);
        
        tfIdentification = new JTextField();
        tfIdentification.setFont(fontField);
        tfIdentification.setHorizontalAlignment(JTextField.CENTER);
        tfIdentification.setBorder(border);
        
        tfFinalScore = new JTextField("5550");
        tfFinalScore.setFont(fontField);
        tfFinalScore.setHorizontalAlignment(JTextField.CENTER);
        tfFinalScore.setBorder(border);
        tfFinalScore.setEditable(false);
        
        
        JLabel lbName  = new JLabel("Nome", JLabel.CENTER);
        lbName.setFont(fontLabel);
        lbName.setForeground(Color.WHITE);
        
        JLabel lbId = new JLabel("Escola", JLabel.CENTER);
        lbId.setFont(fontLabel);
        lbId.setForeground(Color.WHITE);
        
        JLabel lbScore = new JLabel("Pontuação", JLabel.CENTER);
        lbScore.setFont(fontLabel);
        lbScore.setForeground(Color.WHITE);
        
        pnLabels.add(lbName);
        pnLabels.add(lbId);
        pnLabels.add(lbScore);
        
        pnFields.add(tfName);
        pnFields.add(tfIdentification);
        pnFields.add(tfFinalScore);
        pnBaseFields.add(pnFields);
        pnBaseFields.add(pnLabels);
        
        return pnBaseFields;
    }
	
	
	
	
	
	/**
	 * Draw the winner screen: background image, selected buttons and string message.
	 * All this are drawing in the game state WINNER.
     * @param g2d
     */
	public void drawWinnerScreen(Graphics2D g2d){
		String msg = "Use TAB e Shift+TAB para navergar entre os campos";         	
    	g2d.setColor(Color.WHITE);
    	g2d.setFont(new Font("Lucida Sans", Font.BOLD, 16));
    	g2d.drawImage(background, 0, 0, Window.frameWidth, Window.frameHeight, null);                
        g2d.drawImage(univas_logo, 0, Window.frameHeight - (univas_logo.getHeight() + 10), 250, 70, null);
    	
        if(pnBaseFields != null){
        	int btn_x = pnBaseFields.getX() + 110;
        	int btn_y = pnBaseFields.getY() + pnBaseFields.getHeight() + 40;
            g2d.drawImage(imgBtnSave2, btn_x, btn_y, imgBtnSave2.getWidth(), imgBtnSave2.getHeight(), null);
            g2d.drawImage(imgBtnCancel, btn_x+360, btn_y, imgBtnCancel.getWidth(), imgBtnCancel.getHeight(), null);
        }

        g2d.drawString(msg, Window.frameWidth/2-200, Window.frameHeight - 8);
	}
	
	
	
	
	
	
    /**
     * Draw the image of the currently button selected on the screen.
     * 
     * @param g2d Necessary to show the images on the screen.
     */
    private void drawSelectedButton(Graphics2D g2d){
    	
        BufferedImage imgSave = null;
    	BufferedImage imgCancel = null;
    	
    	switch (currentlyButtonSelected) {
			case SAVE:
				imgSave = imgBtnSave2;
				imgCancel = imgBtnCancel;
				break;
	
			case CANCEL:
				imgSave = imgBtnSave;
				imgCancel = imgBtnCancel2;
				break;
			default:
				currentlyButtonSelected = ButtonSelected.SAVE;
				break;
		}
    	int btn_x = (Window.frameWidth / 2) - (imgBtnSave.getWidth() / 2);
    	int btn_y = (Window.frameHeight / 2) ;
    	
    	g2d.drawImage(imgSave, btn_x, btn_y, imgSave.getWidth(), imgSave.getHeight(), null); 
    	g2d.drawImage(imgCancel, btn_x, (int) (btn_y*1.25), imgCancel.getWidth(), imgCancel.getHeight(), null); 
    }
	
	
	
	
	
	
	
	
}