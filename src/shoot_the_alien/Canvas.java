package shoot_the_alien;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;



/**
 * Create a JPanel on which we draw the images and listen to the mouse events.
 * 
 * @author www.gametutorial.net
 * @author Luiz Eduardo da Costa
 * @category Game
 * @version 2.0
 */
public abstract class Canvas extends JPanel implements MouseListener {
    
    
	
  	private static final long serialVersionUID = 1L;

	
	/** Mouse states - Here are stored states for mouse keys - is it down or not.  */
    private static boolean[] mouseState = new boolean[3];
        
        
    
    
    
    /**
     * Setup and create a new panel.
     */
    public Canvas()
    {
        // We use double buffer to draw on the screen.
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.black);

        BufferedImage blankCursorImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImg, new Point(0, 0), null);
        this.setCursor(blankCursor);

        
        // Adds the keyboard listener to JPanel to receive key events from this component.
        this.addKeyListener(getKeyListener());
        // Adds the mouse listener to JPanel to receive mouse events from this component.
        this.addMouseListener(this);

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
    
    
    
    
    /**
     * Is the mouse button "button" down?
     * Parameter "button" can be "MouseEvent.BUTTON1" - Indicates mouse button #1
     * or "MouseEvent.BUTTON2" - Indicates mouse button #2 
     * or "MouseEvent.BUTTON3" - Indicates mouse button #3.
     * 
     * @param button Number of mouse button for which you want to check the state.
     * @return true if the button is down, false if the button is not down.
     */
    public static boolean mouseButtonState(int button)
    {
        return mouseState[button - 1];
    }
    
    
    
    
    
    /**
     * Sets mouse key status array.
     * 
     * @param e MouseEvent object.
     * @param status true of false
     */
    private void mouseKeyStatus(MouseEvent e, boolean status)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
            mouseState[0] = status;
        else if(e.getButton() == MouseEvent.BUTTON2)
            mouseState[1] = status;
        else if(e.getButton() == MouseEvent.BUTTON3)
            mouseState[2] = status;
    }
    
    
    
    
    /**
     * Method override from MouseListener.
     * It is call when the mouse button is pressed.
     */
    @Override
    public void mousePressed(MouseEvent e)
    {
        mouseKeyStatus(e, true);
    }
    
    
    /**
     * Method override from MouseListener.
     * It is call when the mouse button is released.
     */
    @Override
    public void mouseReleased(MouseEvent e)
    {
        mouseKeyStatus(e, false);
    }
    
    
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) { }
    
    @Override
    public void mouseEntered(MouseEvent e) { }
    
    @Override
    public void mouseExited(MouseEvent e) { }
    
}
