package shoot_the_alien.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import shoot_the_alien.Framework;
import shoot_the_alien.model.Stopwatch;
import shoot_the_alien.view.screens.frame.Window;



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
     * This object will be started when the game state is GAME_CONTENT_LOADING.
     */
    public Stopwatch stopWatch = null;
    
    
  	
    
    
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
    	    	
    	    	}else if(e.getKeyCode() == KeyEvent.VK_R){
    	    		// R - Restart the game
    	    		Framework.gameState = Framework.GameState.RESTART;
    	    	}
    	    }
		};
    }
    
    
    
}
