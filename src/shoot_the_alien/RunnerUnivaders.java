package shoot_the_alien;

import javax.swing.SwingUtilities;

import shoot_the_alien.view.screens.frame.Window;

/**
 * Creates frame and set its properties.
 * 
 * @author www.gametutorial.net
 * @author Luiz Eduardo da Costa
 * @category Game
 * @version 2.0
 */
public class RunnerUnivaders {
	
    /**
     * Main method loads the start of the game.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window();
            }
        });
    }
}
