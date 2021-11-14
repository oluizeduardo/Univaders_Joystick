package shoot_the_alien.view.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import shoot_the_alien.Framework;
import shoot_the_alien.Framework.GameState;
import shoot_the_alien.model.AbstractJoyStick;
import shoot_the_alien.model.Image;
import shoot_the_alien.model.PlayWAVFile;
import shoot_the_alien.model.Winner;
import shoot_the_alien.model.dao.WinnerDAO;
import shoot_the_alien.view.screens.frame.Window;

/**
 * Build the winner screen.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 05/05/2016
 */
public class WinnerScreen implements ConfirmButtonListener, WinnerListener {

	/**
	 * Used to load the buffer of an image.
	 */
	private BufferedImage background, 
						  imgBtnSave, imgBtnSave2,
						  imgBtnCancel, imgBtnCancel2;
	/**
	 * Used to open an image.
	 */
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
    private int indexBtnSelected = 0;
    /**
     * Play a file sound.
     */
    private PlayWAVFile soundFile;
    
    
    
    
    
	/**
	 * The class constructor.
	 * Load all the contents in its initialization.
	 */
	public WinnerScreen() {
		AbstractJoyStick.getInstance().addConfirmListener(this);
		AbstractJoyStick.getInstance().setWinnerListener(this);
		loadContent();
	}
	
	
	
	
	
	/**
	 * Load all contents of the class, image, sounds, etc.
	 */
	private void loadContent(){
		objImage = new Image();		
		background  = objImage.getBackgroundWinnerImg();
		imgBtnSave = objImage.getBtnSaveImg();
    	imgBtnSave2 = objImage.getBtnSaveImg2();
    	imgBtnCancel = objImage.getBtnCancelImg();
    	imgBtnCancel2 = objImage.getBtnCancelImg2();
    	
    	soundFile = new PlayWAVFile(PlayWAVFile.CLICK, 1);
	}
	
	
	
	
	/**
	 * Build the base panel of the formulary which will get information about the game winner.
	 * 
	 * @param finalScore The gamer's final score.
	 * @return An instance setuped of a JPanel object.
	 */
	public JPanel getPanelFields(int finalScore){
		
    	GridLayout gridLayout = new GridLayout(3, 1);
    	gridLayout.setVgap(30);
        
        pnBaseFields = new JPanel(null);
        pnBaseFields.setBackground(new Color(0,0,0, 150));
        pnBaseFields.setSize(880, 400);
        int x = (Window.frameWidth / 2) - (pnBaseFields.getWidth() / 2);
        int y = (Window.frameHeight / 2) - (pnBaseFields.getHeight() / 2); 
        pnBaseFields.setLocation(x, y-50);
        pnBaseFields.setVisible(false);// Set the initial visible as false.
        
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
        Font fontLabel2 = new Font("Arial", Font.ITALIC + Font.BOLD, 43);
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,null,new Color(0, 0, 0));
        
        tfName = new JTextField(); 
        tfName.setFont(fontField);
        tfName.setHorizontalAlignment(JTextField.CENTER);
        tfName.setBorder(border);
        
        tfIdentification = new JTextField();
        tfIdentification.setFont(fontField);
        tfIdentification.setHorizontalAlignment(JTextField.CENTER);
        tfIdentification.setBorder(border);
        
        tfFinalScore = new JTextField(""+finalScore);
        tfFinalScore.setFont(fontField);
        tfFinalScore.setHorizontalAlignment(JTextField.CENTER);
        tfFinalScore.setBorder(border);
        tfFinalScore.setEditable(false);
        tfFinalScore.setForeground(Color.RED);
        
        
        JLabel lbName  = new JLabel("Nome", JLabel.CENTER);
        lbName.setFont(fontLabel);
        lbName.setForeground(Color.WHITE);
        
        JLabel lbId = new JLabel("Curso/Cidade", JLabel.CENTER);
        lbId.setFont(fontLabel2);
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
		
		if(pnBaseFields != null){
			
			// Plays the sound if the panel is still invisible.
			if(!pnBaseFields.isVisible()){
				PlayWAVFile winnerSound = new PlayWAVFile(PlayWAVFile.WINNER, 1);
				Thread th_sound = new Thread(winnerSound);
				th_sound.start();
			}
			
			pnBaseFields.setVisible(true);	
		}
			
		
		String msg = "Use TAB e Shift+TAB para navergar entre os campos, 3 para confirmar";         	
    	g2d.setColor(Color.RED);
    	g2d.setFont(new Font("Lucida Sans", Font.BOLD, 16));
    	g2d.drawImage(background, 0, 0, Window.frameWidth, Window.frameHeight, null);                
        g2d.drawString(msg, Window.frameWidth/2-250, Window.frameHeight - 10);
        
        
        drawSelectedButton(g2d);
	}
	
	
	
	
	
	
    /**
     * Draw the image of the currently button selected on the screen.
     * <p>
     * To do this, the method uses the flag 'currentlyButtonSelected'
     * to know which button was pressed and which image print on the screen.
     * 
     * @param g2d Necessary to show the images on the screen.
     */
    private void drawSelectedButton(Graphics2D g2d){
    	
        BufferedImage imgSave = null;
    	BufferedImage imgCancel = null;
    	
    	// Change the button image depending on the state.
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
    	
    	
    	if(pnBaseFields != null){
        	int btn_x = pnBaseFields.getX() + 110;
        	int btn_y = pnBaseFields.getY() + pnBaseFields.getHeight() + 40;
            g2d.drawImage(imgSave, btn_x, btn_y, imgBtnSave2.getWidth(), imgBtnSave2.getHeight(), null);
            g2d.drawImage(imgCancel, btn_x+360, btn_y, imgBtnCancel.getWidth(), imgBtnCancel.getHeight(), null);
        }   
    }


	@Override
	public void confirmPressed() {
		if(!Framework.gameState.equals(Framework.GameState.WINNER)) {
			return;
		}

        if(currentlyButtonSelected.equals(ButtonSelected.SAVE)){

			if(tfName.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Informe um nome!");
				tfName.grabFocus();
			}else{
				
				if(tfIdentification.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Informe uma identificação sobre o vencedor.");
					tfIdentification.grabFocus();
				}else{
					String name = tfName.getText().toUpperCase();
			    	String id = tfIdentification.getText().toUpperCase();
			    	int score = Integer.parseInt(tfFinalScore.getText());
	
			    	Winner newWinner = new Winner(name, id, score);
			    	WinnerDAO wdao = new WinnerDAO();
					
					if(wdao.insertNew(newWinner)){
						JOptionPane.showMessageDialog(null, "VENCEDOR REGISTRADO COM SUCESSO!");
						pnBaseFields.setVisible(false);
						pnBaseFields = null;
			        	Framework.gameState = GameState.SUPPORT;
					} 
				}
			}
        }

        if(currentlyButtonSelected.equals(ButtonSelected.CANCEL)){
        	pnBaseFields.setVisible(false);
        	pnBaseFields = null;
        	Framework.gameState = GameState.MAIN_MENU;
        }
	}

	@Override
	public void rightPressed() {
		indexBtnSelected = indexBtnSelected == 1 ? 0 : 1;
    	playSound();
        // Sets the currently button selected.
        currentlyButtonSelected = mainButtons[ indexBtnSelected ];
	}

	@Override
	public void leftPressed() {
		indexBtnSelected = indexBtnSelected == 0 ? 1 : 0;
    	playSound();
        // Sets the currently button selected.
        currentlyButtonSelected = mainButtons[ indexBtnSelected ];
	}

	private void playSound() {
		// Play the click sound.
		Thread th_click = new Thread(soundFile);
		th_click.start();
		
		// Wait a little time to change the button again.
		try {
			Thread.sleep(180);
		} catch (InterruptedException e1) { }
	}
}
