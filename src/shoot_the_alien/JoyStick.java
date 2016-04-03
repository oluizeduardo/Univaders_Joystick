package shoot_the_alien;


import java.util.ArrayList;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Component.Identifier;


/**
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 03/04/2016
 */
public class JoyStick implements Runnable {

	
	/**
	 * The cod of button, verified if it was pressed or not.
	 */
	public static int VERIFIED_BUTTON = 9;// start=9, shoot=5.
	
	public static boolean isButtonPressed = false;
	
	
	
	
	/**
	 * ArrayList with all the instances of controllers have found.
	 */
	private ArrayList<Controller> foundControllers = null;
	
	private PlayWAVFile pf;
	
	
	
	
	public JoyStick() {  
		
		this.foundControllers = new ArrayList<Controller>();
		searchForControllers();
	}
	
	
	
	
	
	@Override
	public void run() {
		
        while (true) {
            
        	update();
			isButtonPressed = checkButtonPressed(VERIFIED_BUTTON);
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
            if (controller.getType() == Controller.Type.STICK )
            {
                // Add new controller to the list of all controllers.
                foundControllers.add(controller);
               
            }
        }
    }
    
    
    
    
    
    
    private void update() {
        for (int i = 0; i < foundControllers.size(); i++) {
            foundControllers.get( i ).poll();
        }
    }



    
    /**
     * Check if the button was pressed.
     * 
     * @param button
     * @return true or false.
     */
    private boolean checkButtonPressed(int button) {

        if(!foundControllers.isEmpty()){
        	
        	for (int i = 0; i < foundControllers.size(); i++) {

                Component[] components = foundControllers.get( i ).getComponents();
                
                for (int c = 0; c < components.length; c++) {

                	Component cmp = components[ c ];
                    Identifier compIdentifier = cmp.getIdentifier();
                
                    // Verifica se é um botão que tem número no nome.
                    if(compIdentifier.getName().matches(""+button)){
                    	if(cmp.getPollData() != 0.0f)
                    		return true;
                    }
                }
            }
        }
        return false;
    }
    
    
    
    
    /**
     * Verify if there are any controller on the list.
     * @return true or false.
     */
    public boolean thereAreControllers(){
    	return (foundControllers.size() > 0);
    }

    
	
}
