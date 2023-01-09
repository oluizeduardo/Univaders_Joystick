package shoot_the_alien.control;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import shoot_the_alien.Framework;
import shoot_the_alien.model.Image;

/**
 * Control the actions of the keyboard.
 * 
 * @author Roberto Rocha
 * @version 1.0, 16/09/2016
 */
public class Keyboard extends AbstractJoyStick 
	implements MouseListener, MouseMotionListener, KeyListener {

	private float valuePressed;

	@Override
	public void configure(Framework framework) {
		framework.addMouseListener(this);
		framework.addMouseMotionListener(this);
		framework.addKeyListener(this);
	}

	/**
	 * Initialize the objects.
	 */
	public boolean initialize() {
		sightImg = new Image().getSightImg();
		return thereAreControllers();
	}

	/**
	 * Verify if there are any controller on the list.
	 * 
	 * @return true or false.
	 */
	public boolean thereAreControllers() {
		return true; // the keyboard is always available.
	}

	/**
	 * Use the method pool() to make an update in the state of the component.
	 * This action makes every round of the game loop the updated data controls
	 * are available to be read.
	 * 
	 * Also it helps to verify if the controller is still connected. If the
	 * method pool() returns false, it's mean there is no controller connected.
	 * 
	 * If there's no controller connected, show an error message.
	 */
	public void update() {
		// nothing needed here
	}

	//private boolean POVPressed = false;
	private int xAxis = 0;
	private int yAxis = 0;
	private Identifier[] buttonPressed = null;

	/**
	 * @return The position of the joystick.
	 */
	public Point getJoystickPosition() {
		Point p = new Point(xAxis, yAxis);
		// TODO: se comentar esta linha o mouse para de funcionar
		// precisa verificar
		// System.out.println("Point: " + p);
		return p;
	}

	@Override
	protected float getValuePressed() {
		return valuePressed;
	}

	@Override
	protected Identifier[] readButtonsPressed() {
		Identifier[] buttonPressedCopy = buttonPressed;
		buttonPressed = null;
		return buttonPressedCopy;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		buttonPressed = new Identifier[3];
		if (e.getButton() == 1) {
			buttonPressed[0] = BTN_SHOOT;
		} else if (e.getButton() == 2) {
			buttonPressed[1] = BTN_SELECT;
		} else {
			buttonPressed[2] = BTN_START;
		}
		System.out.println("New button pressed: " + e.getButton());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		xAxis = e.getX();
		yAxis = e.getY();
		// System.out.println("Moveu: " + xAxis + "-" + yAxis);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_I) {
			valuePressed = AbstractJoyStick.BTN_UP;
		} else if (e.getKeyChar() == KeyEvent.VK_J) {
			valuePressed = AbstractJoyStick.BTN_DOWN;
		} else if (e.getKeyChar() == KeyEvent.VK_K) {
			valuePressed = AbstractJoyStick.BTN_LEFT;
		} else if (e.getKeyChar() == KeyEvent.VK_L) {
			valuePressed = AbstractJoyStick.BTN_RIGHT;	
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			buttonPressed = new Identifier[1];
			buttonPressed[0] = BTN_START;
			return;
		}
		buttonPressed = new Identifier[1];
		buttonPressed[0] = Component.Identifier.Axis.POV;
		System.out.println("Key typed: " + e.getKeyCode() + " - " + e.getKeyChar() + " value: " + valuePressed);
	}

	//other methods not used - just to comply with the interfaces
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_I || e.getKeyCode() == KeyEvent.VK_UP) {
			valuePressed = AbstractJoyStick.BTN_UP;
		} else if (e.getKeyChar() == KeyEvent.VK_J || e.getKeyCode() == KeyEvent.VK_DOWN) {
			valuePressed = AbstractJoyStick.BTN_DOWN;
		} else if (e.getKeyChar() == KeyEvent.VK_K || e.getKeyCode() == KeyEvent.VK_LEFT) {
			valuePressed = AbstractJoyStick.BTN_LEFT;
		} else if (e.getKeyChar() == KeyEvent.VK_L || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			valuePressed = AbstractJoyStick.BTN_RIGHT;	
		}
		buttonPressed = new Identifier[1];
		buttonPressed[0] = Component.Identifier.Axis.POV;
		System.out.println("Key pressed: " + e.getKeyCode() + " - " + e.getKeyChar() + " value: " + valuePressed);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}
