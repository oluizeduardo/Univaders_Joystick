package shoot_the_alien.view.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import shoot_the_alien.Framework;
import shoot_the_alien.Framework.GameState;
import shoot_the_alien.model.Image;
import shoot_the_alien.model.JoyStick;
import shoot_the_alien.model.Winner;
import shoot_the_alien.model.dao.WinnerDAO;
import shoot_the_alien.model.table.TableWinners;
import shoot_the_alien.view.screens.frame.Window;

/**
 * It contains all about building Ranking screen.
 * <p>
 * There is the table to list the best winners.
 * 
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 08/05/16
 */
public class RankingScreen {

	
	/**
	 * Used to open an image.
	 */
	private Image objImage;
	/**
	 * Used to load the buffer of an image.
	 */
	private BufferedImage img_background, img_btnCancelar;
	/**
     * The panel where will add the other components.
     */
	public JPanel pnBaseTable;
	/**
	 * The table where will appear the best winners of the game.
	 */
	private TableWinners tableWinners;
	/**
	 * Scroll panel to put the table over it.
	 */
	private JScrollPane pnScroll;
	/**
	 * The list with the best winners.
	 */
	private ArrayList<Winner> listWinners;
	
	
	
	
	/**
	 * The contructor of the class.
	 */
	public RankingScreen() {
		
		loadContent();
		buildTableBase();
	}
	
	
	
	
	/**
	 * Load all contents of the class, image, sounds, etc.
	 */
	private void loadContent(){
		objImage = new Image();		
		img_background  = objImage.getBackgroundRankingImg();
		img_btnCancelar = objImage.getBtnCancelImg2();
	}
	
	
	
	
	
	/**
	 * Build the panel for the table base.
	 */
	private void buildTableBase(){
		pnBaseTable = new JPanel(new GridLayout(1, 1));
		pnBaseTable.setSize(900, 450);
        int x = (Window.frameWidth / 2) - (pnBaseTable.getWidth() / 2);
        int y = (Window.frameHeight / 2) - (pnBaseTable.getHeight() / 2); 
        pnBaseTable.setLocation(x, y-20);
        pnBaseTable.setBackground(new Color(0,0,0,100));
        
        tableWinners = new TableWinners(pnBaseTable);	        
        pnScroll = new JScrollPane(tableWinners.getTable());
        pnBaseTable.add(pnScroll);
        pnBaseTable.setVisible(false);
	}
	
	
	
	
	/**
	 * It list the table with a list of the best winners.
	 */
	public void listTableWithBestWinners(){
		this.listWinners = WinnerDAO.getInstance().getAllWinners();
		tableWinners.getTableModel().setList(listWinners);
	}
	
	
	
	
	
	/**
	 * It returns the base painel built and configured.
	 */
	public JPanel getPnTableBase(){
		return pnBaseTable;	        
	}
	
	
	
	
	
	
	/**
	 * Draw the ranking screen: background image and selected buttom.
	 * All this are drawing in the game state RANKING.
     * @param g2d
     */
	public void drawRankingScreen(Graphics2D g2d){

		int btn_x = (pnBaseTable.getX() + ((pnBaseTable.getWidth() / 2) - (img_btnCancelar.getWidth() / 2)));
    	int btn_y = pnBaseTable.getY() + pnBaseTable.getHeight() + 30;
		
		g2d.drawImage(img_background, 0, 0, Window.frameWidth, Window.frameHeight, null);                
        g2d.drawImage(img_btnCancelar, btn_x, btn_y, img_btnCancelar.getWidth(), img_btnCancelar.getHeight(), null);
	
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Lucida Sans", Font.BOLD, 16));
        g2d.drawString("Pressione BOTÃO 3", Window.frameWidth - 200, Window.frameHeight - 10);

	}
	
	
	
	
	/**
     * Check which button was pressed and do any action.
     */
    public void checkButtonPressed(){   
    	boolean isConfirmPressed = JoyStick.getInstance().checkButtonPressed(JoyStick.BTN_CONFIRM);
        
        // Check if cancel button was pressed and return to main menu.
        if(isConfirmPressed){
        	
        	pnBaseTable.setVisible(false);
        	Framework.gameState = GameState.MAIN_MENU;
        }
    }
    
    
    
}
