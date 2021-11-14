package shoot_the_alien.model;

import java.awt.Point;
import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import shoot_the_alien.Framework;
import shoot_the_alien.view.screens.frame.Window;


/**
 * Control the actions of the joystick.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 03/04/2016
 */
public class JoyStick extends AbstractJoyStick {
	
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
	
	private Point point = new Point(Window.frameWidth / 2, Window.frameHeight / 2);
	
    /**
     * The private constructor of the class.
     */
    public JoyStick(){
    	super();
    	initialize();
    }

    @Override
    public void configure(Framework framework) {
    	// não faz nada
    	// a classe do mouse é que adiciona o mouse listener
    	
    }
    
	/**
	 * Initialize the objects.
	 */
	public void initialize(){		
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
                System.out.println("Controller found: " + controller);
            }
        }
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
    public void update() {
    	// get the first controller of the list.
    	if(!controller.poll()){
        	showControllerDisconected();
        }
    }
    
    /** 
     * @return The position of the joystick.
     */
    public Point getJoystickPosition(){    	

    	 // X axis and Y axis
        int xAxisPercentage = 0;
        int yAxisPercentage = 0;
        int axisValueInPercentage = -1;
    	
    	components = controller.getComponents();

    	for(int i=0; i < components.length; i++){
    		if(components[ i ].isAnalog()){
    			
    			Component compStick = components[ i ];
    			
    			float axisValue = compStick.getPollData();
    			Identifier compIdentifier = compStick.getIdentifier();
//    	        int axisValueInPercentage = getAxisValueInPercentage(axisValue);
//    			System.out.println(axisValue);
    	        axisValueInPercentage = (int)(axisValue*40);
    	            	        
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
    	
//    	System.out.println("axisValueInPercentage: " + axisValueInPercentage + " point: " + point);
    	int newPx = point.x + xAxisPercentage;
    	int newPy = point.y + yAxisPercentage;
    	
    	if(newPx > Window.frameWidth - 10) {
    		newPx = Window.frameWidth - 10;
    	}
    	if(newPy > Window.frameHeight - 10) {
    		newPy = Window.frameHeight - 10;
    	}

    	if(newPx < 10) {
    		newPx = 10;
    	}
    	if(newPy < 10) {
    		newPy = 10;
    	}
    	
    	point =  new Point(newPx, newPy);
    	
    	return point;
    }
	
    @Override
    protected float getValuePressed() {
    	// Get the components of the first controller found.
        this.components = this.controller.getComponents();
        
        for (int c = 0; c < components.length; c++) {
        	Component comp = components[ c ];
            Identifier id = comp.getIdentifier();
        
            if(id == Component.Identifier.Axis.POV){
        		
            	float valuePressed = comp.getPollData();
            	return valuePressed;
            }
        }
        return NO_VALUE_PRESSED;
    }
    
    @Override
    protected Identifier[] readButtonsPressed() {
    	
    	ArrayList<Identifier> list = new ArrayList<Identifier>();
    	// Get the components of the first controller found.
        this.components = this.controller.getComponents();
        
        for (int c = 0; c < components.length; c++) {

        	Component comp = components[ c ];
            Identifier id = comp.getIdentifier();
            if(comp.getPollData() != 0.0f) {
            	list.add(id);
            }
        }
        return list.toArray(new Identifier[list.size()]);
    }
}
