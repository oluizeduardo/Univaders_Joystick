package shoot_the_alien.model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Component.Identifier;
import shoot_the_alien.view.screens.frame.Window;


/**
 * Control the actions of the joystick.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 03/04/2016
 */
public class JoyStick implements Runnable {

	
	/** The code of the button to get the ammunition kit (3).*/
	public static final Identifier BTN_GET_KIT = Identifier.Button._2;
	/** The code of the button to confirm Save or Cancel the winner registration.*/
	public static final Identifier BTN_CONFIRM = Identifier.Button._2;
	
	/** The code of the button shoot (R1).*/
	public static final Identifier BTN_SHOOT = Identifier.Button._5;
	/** The code of the button SELECT.*/
	public static final Identifier BTN_SELECT = Identifier.Button._8;
	/** The code of the button start.*/
	public static final Identifier BTN_START = Identifier.Button._9;
	/** The float value of the button UP.*/
	public static final float BTN_UP = Component.POV.UP;
	/** The float value of the button DOWN.*/
	public static final float BTN_DOWN = Component.POV.DOWN;
	/** The float value of the button LEFT.*/
	public static final float BTN_LEFT = Component.POV.LEFT;
	/** The float value of the button RIGHT.*/
	public static final float BTN_RIGHT = Component.POV.RIGHT;
	
	
	
	
	/**
     * Instance of the class, used in the Singleton statement.
     */
    private static JoyStick joystick = null;	
	/**
	 * ArrayList with the controllers used to play the game.
	 */
	private ArrayList<Controller> foundControllers = null;
	/**
	 * The instance of the first controller found.
	 */
	private Controller controller;
	/**
	 * The controller's components.
	 */
	private Component[] components = null;
    /**
     * Buffer of the shotgun sight image.
     */
    private BufferedImage sightImg;
    /**
     * The point location of the Soystick sight.
     */
    private Point location = new Point();
    
    
    
    
    
    
	
    /**
     * The private constructor of the class.
     */
    private JoyStick(){
    	initialize();
    }
    
    
    
    /**
     * Get the instance of the class. Singleton query.
     * It is necessary to exist just one instance of the class in all parts of the program.
     * 
     * @return An instance of joystick class.
     */
	public static JoyStick getInstance() {  		
		if(joystick == null){
			joystick = new JoyStick();
		}
		return joystick;
	}
	
	
	
	
	
	/**
	 * Run the update of the controller's values and the joystick position.
	 */
	@Override
	public void run() {
		
		// This loop must to be infite to update the controler values.
        while (true) {
        	update();
        	location = getJoystickPosition();
        }
	}
	
	
	
	
	/**
	 * Initialize the objects.
	 */
	private void initialize(){		
		foundControllers = new ArrayList<Controller>();
		sightImg = new Image().getSightImg();
		
		searchForControllers();
		
		if(foundControllers.size() > 0){
			controller = foundControllers.get( 0 );
		}
	}
	
	
	
	
    /**
     * Search for all controllers connected.
     */
    private void searchForControllers() {
        Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        for(int i = 0; i < controllers.length; i++){
            Controller controller = controllers[i];
            
            // Verify if it is a stick controller.
            if (controller.getType() == Controller.Type.STICK)
            {
                // Add new controller to the list of all controllers.
                foundControllers.add(controller);
            }
        }
    }
	
	
	
	/**
	 * Draw the sight on the screen.
	 * @param g2d
	 */
	public void drawSight(Graphics2D g2d){
		
		int x = (int) location.getX();
		int y = (int) location.getY();
		
		if(x >= Window.frameWidth){
			x = (Window.frameWidth - sightImg.getWidth());
		}
		
		if(y >= Window.frameHeight){
			y = Window.frameHeight - sightImg.getHeight();
		}
		
        g2d.drawImage(sightImg, x , y , null);
	}
	
	
	
	
	
	
	/**
     * Verify if there are any controller on the list.
     * @return true or false.
     */
    public boolean thereAreControllers(){
    	return (foundControllers.size() > 0);
    }
	
	
    
    
    
    /**
     * Use the method pool() to make an update in the state of the component.
     * This action makes every round of the game loop the updated data controls 
     * are available to be read. 
     * 
     * Also it helps to verify if the controller is still connected.
     * If the method pool() returns false, it's mean there is no controller connected.
     * 
     * If there's no controller connected, show an error message.
     */
    private void update() {
    	// get the first controller of the list.
    	if(!controller.poll()){
        	showControllerDisconected();
        }
    }



    
    /**
     * Check if the button was pressed.
     * 
     * @param button The code of the button. (0-12)
     * @return true or false.
     */
    public boolean checkButtonPressed(Identifier idBtn) {

    	// Get the components of the first controller found.
        this.components = this.controller.getComponents();
        
        for (int c = 0; c < components.length; c++) {

        	Component comp = components[ c ];
            Identifier id = comp.getIdentifier();
        
            if(id.equals(idBtn)){
            	if(comp.getPollData() != 0.0f)
            		return true;
            }
        }
        return false;
    }
    
    
    
    

    /**
     * Check if some POV button was pressed.
     * 
     * @param componentPOV The float value of the button.
     * @return true or false.
     */
    public boolean checkPOVPressed(float componentPOV){
    	// Get the components of the first controller found.
        this.components = this.controller.getComponents();
        
        for (int c = 0; c < components.length; c++) {
        	Component comp = components[ c ];
            Identifier id = comp.getIdentifier();
        
            if(id == Component.Identifier.Axis.POV){
        		
            	float valuePressed = comp.getPollData();

        		if(Float.compare(valuePressed, componentPOV) == 0)
                    return true;
            }
        }
        return false;
    }
    
   
    
    
    
    /** 
     * @return The position of the joystick.
     */
    public Point getJoystickPosition(){    	

    	 // X axis and Y axis
        int xAxisPercentage = 0;
        int yAxisPercentage = 0;
    	
    	components = controller.getComponents();

    	for(int i=0; i < components.length; i++){
    		if(components[ i ].isAnalog()){
    			
    			Component compStick = components[ i ];
    			
    			float axisValue = compStick.getPollData();
    			Identifier compIdentifier = compStick.getIdentifier();
    	        int axisValueInPercentage = getAxisValueInPercentage(axisValue);
    	            	        
    	        // X axis
    	        if(compIdentifier == Component.Identifier.Axis.X){
    	            xAxisPercentage = axisValueInPercentage;
    	        }
    	        // Y axis
    	        if(compIdentifier == Component.Identifier.Axis.Y){
    	            yAxisPercentage = axisValueInPercentage;
    	        }
    		}
    	}    	
    	return new Point(xAxisPercentage, yAxisPercentage);
    }
    
    
    
    
    
    /**
     * Given value of axis in percentage.
     * Percentages increases from left/top to right/bottom.
     * 
     * @return value of axis in percentage.
     */
    private int getAxisValueInPercentage(float axisValue)
    {    	
        return (int)(((2 - (1 - axisValue)) * Window.frameWidth) / 2);
    }
    
    
    
    
    
    
    /**
     * Show the message of none controller found.
     */
    public void showControllerNotFound(){
    	String msg = "Nenhum controle encontrado!!\n\n"
    			+ "Conecte um controlador USB e reinicie o jogo.";
    	JOptionPane.showMessageDialog(null, msg, "Univaders", JOptionPane.ERROR_MESSAGE);
		System.exit( 0 );
    }
   

    /**
     * Show the message of controller disconected.
     */
    public void showControllerDisconected(){
    	String msg = "Controle Desconectado!!\n\n"
    			+ "Reconecte o controle e reinicie o jogo.";
    	JOptionPane.showMessageDialog(null, msg, "Univaders", JOptionPane.ERROR_MESSAGE);
		System.exit( 0 );
    }
    
	
}
