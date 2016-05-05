package shoot_the_alien;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import shoot_the_alien.Stopwatch;



/**
 * Create a JPanel on which we draw the images and listen to the mouse events.
 * 
 * @author www.gametutorial.net
 * @author Luiz Eduardo da Costa
 * @category Game
 * @version 2.0
 */
public abstract class Canvas extends JPanel {
    
    
	
  	private static final long serialVersionUID = 1L;
    
  	/**
     * The stopwatch of the game.
     */
    private Stopwatch stopWatch = null;
    /**
     * An object Thread to start the chronometer of the game.
     */
    public static Thread th_stopwatch = null;

//    /**
//     * The fields to colect information about the winner.
//     */
//    private JTextField tfName, tfIdentification, tfFinalScore;
    
    
  	
    
    
    /**
     * Setup and create a new panel.
     */
    public Canvas()
    {
        // We use double buffer to draw on the screen.
        super.setDoubleBuffered(true);
        super.setFocusable(true);
        super.setBackground(Color.black);
        super.setLayout(null);
        
        
        BufferedImage blankCursorImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
        super.setCursor(blankCursor);

        this.stopWatch = new Stopwatch();
        this.stopWatch.setBounds((Window.frameWidth-220)/2, 10, 220, 50);
        super.add(stopWatch);
        th_stopwatch = new Thread(stopWatch);
        // The object th_stopwatch must to be started when the game state is GAME_CONTENT_LOADING.
        
        // Adds the keyboard listener to JPanel to receive key events from this component.
        this.addKeyListener(getKeyListener());
    }
    
    
    // This method is overridden in the class Framework.java and is used for drawing on the screen.
    public abstract void Draw(Graphics2D g2d);
    
    
    
    
    
    
    /**
     * The JPanel object is an invisible component until setup the method paintComponent(...). 
     * Override this method means that you want to use the graphic properties of the panel 
     * to draw something over it.
     * <p>
     * This is a native method of the JPanel class and it's useful to draw something over it.
     * 
     * How does paintComponent work? check it up:
     * http://stackoverflow.com/questions/5446396/concerns-about-the-function-of-jpanel-paintcomponent
     */
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;        
        super.paintComponent(g2d); // Prepare the component to receive new paint.
        Draw(g2d);
    }
       
    
    
    
    
//    public JPanel getPanelFields(){
//    	
//    	GridLayout gridLayout = new GridLayout(3, 1);
//    	gridLayout.setVgap(30);
//        
//        pnBaseFields = new JPanel(null);
//        pnBaseFields.setBackground(new Color(0,0,0, 150));
//        pnBaseFields.setSize(880, 400);
//        int x = (Window.frameWidth / 2) - (pnBaseFields.getWidth() / 2);
//        int y = (Window.frameHeight / 2) - (pnBaseFields.getHeight() / 2); 
//        pnBaseFields.setLocation(x, y-50);
//        
//        JPanel pnFields = new JPanel(gridLayout);
//        pnFields.setSize(500, 300);
//        pnFields.setBackground(new Color(0,0,0,0));
//        pnFields.setLocation(330, 50);
//        
//        JPanel pnLabels = new JPanel(gridLayout);
//        pnLabels.setSize(300, 300);
//        pnLabels.setBackground(new Color(0,0,0,0));
//        pnLabels.setLocation(0, 50);
//        
//        Font fontField = new Font("Arial", Font.BOLD, 40);
//        Font fontLabel = new Font("Arial", Font.ITALIC + Font.BOLD, 50);
//        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,null,new Color(0, 0, 0));
//        
//        tfName = new JTextField(); 
//        tfName.setFont(fontField);
//        tfName.setHorizontalAlignment(JTextField.CENTER);
//        tfName.setBorder(border);
//        
//        tfIdentification = new JTextField();
//        tfIdentification.setFont(fontField);
//        tfIdentification.setHorizontalAlignment(JTextField.CENTER);
//        tfIdentification.setBorder(border);
//        
//        tfFinalScore = new JTextField("5550");
//        tfFinalScore.setFont(fontField);
//        tfFinalScore.setHorizontalAlignment(JTextField.CENTER);
//        tfFinalScore.setBorder(border);
//        tfFinalScore.setEditable(false);
//        
//        
//        JLabel lbName  = new JLabel("Nome", JLabel.CENTER);
//        lbName.setFont(fontLabel);
//        lbName.setForeground(Color.WHITE);
//        
//        JLabel lbId = new JLabel("Escola", JLabel.CENTER);
//        lbId.setFont(fontLabel);
//        lbId.setForeground(Color.WHITE);
//        
//        JLabel lbScore = new JLabel("Pontuação", JLabel.CENTER);
//        lbScore.setFont(fontLabel);
//        lbScore.setForeground(Color.WHITE);
//        
//        pnLabels.add(lbName);
//        pnLabels.add(lbId);
//        pnLabels.add(lbScore);
//        
//        pnFields.add(tfName);
//        pnFields.add(tfIdentification);
//        pnFields.add(tfFinalScore);
//        pnBaseFields.add(pnFields);
//        pnBaseFields.add(pnLabels);
//        
//        return pnBaseFields;
//    }
    
    
    
    
    
    
    /**
     * Sets up a listener of the keyboard.
     * 
     * @return A configured object KeyListener.
     */
    private KeyListener getKeyListener(){
    	return new KeyAdapter() {
    		
    		@Override
    	    public void keyPressed(KeyEvent e) 
    	    {
    	    	
    	        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
    	    		// ESC - Exit the game.
    	        	System.exit(0);
    	    		
    	    	}else if(e.getKeyCode() == KeyEvent.VK_SPACE ||
    	    			e.getKeyCode() == KeyEvent.VK_ENTER){
    	    		// SPACE or ENTER - Stop the game and return to main menu.
    	    		Framework.gameState = Framework.GameState.MAIN_MENU;
    	    	
    	    	}else if(e.getKeyCode() == KeyEvent.VK_R){
    	    		// R - Restart the game
    	    		Framework.gameState = Framework.GameState.RESTART;
    	    	}
    	    }
		};
    }
    
    
    
}
