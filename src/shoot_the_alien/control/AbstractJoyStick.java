package shoot_the_alien.control;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import shoot_the_alien.Framework;
import shoot_the_alien.GameButtons;
import shoot_the_alien.view.screens.ConfirmButtonListener;
import shoot_the_alien.view.screens.InitialScreenListener;
import shoot_the_alien.view.screens.WinnerListener;
import shoot_the_alien.view.screens.frame.Window;

/**
 * Control the actions of the joystick.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 03/04/2016
 */
public abstract class AbstractJoyStick implements Runnable {

	public static final float NO_VALUE_PRESSED = -1f;
	
	/** The code of the button to get the ammunition kit (3). */
	public static final Identifier BTN_GET_KIT = Identifier.Button._9;
	public static final Identifier BTN_GET_KIT_2 = Identifier.Button.THUMB2;
	/**
	 * The code of the button to confirm Save or Cancel the winner registration.
	 */
	public static final Identifier BTN_CONFIRM = Identifier.Button._9;

	/** The code of the button shoot (R1). */
	public static final Identifier BTN_SHOOT = Identifier.Button._5;
	public static final Identifier BTN_SHOOT_2 = Identifier.Button.PINKIE;
	/** The code of the button SELECT. */
	public static final Identifier BTN_SELECT = Identifier.Button._8;
	/** The code of the button start. */
	public static final Identifier BTN_START = Identifier.Button._9;
	public static final Identifier BTN_START_2 = Identifier.Button.BASE4;
	/** The float value of the button UP. */
	public static final float BTN_UP = Component.POV.UP;
	/** The float value of the button DOWN. */
	public static final float BTN_DOWN = Component.POV.DOWN;
	/** The float value of the button LEFT. */
	public static final float BTN_LEFT = Component.POV.LEFT;
	/** The float value of the button RIGHT. */
	public static final float BTN_RIGHT = Component.POV.RIGHT;

	/**
	 * Instance of the class, used in the Singleton statement.
	 */
	private static AbstractJoyStick joystick = null;
	/**
	 * Buffer of the shotgun sight image.
	 */
	protected BufferedImage sightImg;
	/**
	 * The point location of the Soystick sight.
	 */
	private Point location = new Point();

	private GameButtons gameListener;
	
	private InitialScreenListener initialListener;
	
	private List<ConfirmButtonListener> confirmListeners = new ArrayList<ConfirmButtonListener>();
	
	private WinnerListener winnerListener;
	
	public void setGameListener(GameButtons gameListener) {
		this.gameListener = gameListener;
	}

	public void setInitialListener(InitialScreenListener initialListener) {
		this.initialListener = initialListener;
	}

	public void setWinnerListener(WinnerListener winnerListener) {
		this.winnerListener = winnerListener;
	}
	
	public void addConfirmListener(ConfirmButtonListener listener) {
		confirmListeners.add(listener);
	}

	public abstract Point getJoystickPosition();

	public abstract void update();

	public abstract boolean initialize();

	public abstract boolean thereAreControllers();

	public abstract void configure(Framework framework);

	/**
	 * Get the instance of the class. Singleton query. It is necessary to exist
	 * just one instance of the class in all parts of the program.
	 * 
	 * @return An instance of joystick class.
	 */
	public static AbstractJoyStick getInstance() {
		if (joystick == null) {
			joystick = new JoyStick();
			boolean success = joystick.initialize();
			if (!success) {
				System.out.println("Erro inicializando Joystick. Iniciando teclado...");
				joystick = new Keyboard();
				joystick.initialize();
			}
		}
		return joystick;
	}
	
	/**
	 * Run the update of the controller's values and the joystick position.
	 */
	@Override
	public void run() {

		// This loop must to be infinite to update the controller values.
		while (true) {
			// pega a posição
			location = getJoystickPosition();

			update();

			// verifica os botões
			checkButtons();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected abstract Identifier[] readButtonsPressed();

	protected abstract float getValuePressed();

	/**
	 * Verifies if any of the mains buttons of the controller were pressed. This
	 * method must to be used while the game is running.
	 */
	private void checkButtons() {

		Identifier buttonPressed[] = readButtonsPressed();
		if(buttonPressed == null) {
			//System.out.println("checkButtons return 1");
			return;
		}
		boolean flag = false;
		for (Identifier id : buttonPressed) {
			if(id!= null) {
//				System.out.println("Check Buttons: " + id.getName());
				flag = true;
			}
		}
		if(flag) {
//			System.out.println();
		} else {
			System.out.println("checkButtons return 2");
			return;
		}
		for (Identifier id : buttonPressed) {
			if(id == null) {
				System.out.println("checkButtons continue");
				continue;
			}

			if (id.equals(BTN_GET_KIT) || id.equals(BTN_GET_KIT_2) || id.equals(BTN_START) || id.equals(BTN_START_2) || id.equals(BTN_CONFIRM)) {
				
				for (ConfirmButtonListener listener : confirmListeners) {
					System.out.println("BTN_CONFIRM");
					listener.confirmPressed();
				}

				System.out.println("BTN_START");
				initialListener.confirmed();

				if(gameListener != null) { //botão direito
					System.out.println("BTN_GET_KIT");
					gameListener.gotKit();
				}
//			} else if (id.equals(BTN_CONFIRM)) {
//				
//				for (ConfirmButtonListener listener : confirmListeners) {
//					System.out.println("BTN_CONFIRM");
//					listener.confirmPressed();
//				}
				
			} else if (id.equals(BTN_SHOOT) || id.equals(BTN_SHOOT_2)) {
				if(gameListener != null) { //botão esquerdo
					System.out.println("BTN_SHOOT");
					gameListener.shoot();
				}
			} else if (id.equals(BTN_SELECT)) {
				if(gameListener != null) { //botão do meio
					System.out.println("BTN_SELECT");
					gameListener.selected();
				}
//			} else if (id.equals(BTN_START)) {
//				System.out.println("BTN_START");
//				initialListener.confirmed();
			} else if (id == Component.Identifier.Axis.POV) {

				float valuePressed = getValuePressed();

				if (Float.compare(valuePressed, BTN_UP) == 0) {
					System.out.println("BTN_UP");
					initialListener.upPressed();
				} else if (Float.compare(valuePressed, BTN_DOWN) == 0) {
					System.out.println("BTN_DOWN");
					initialListener.downPressed();
				} else if (Float.compare(valuePressed, BTN_LEFT) == 0) {
					System.out.println("BTN_LEFT");
					winnerListener.leftPressed();
				} else if (Float.compare(valuePressed, BTN_RIGHT) == 0) {
					System.out.println("BTN_RIGHT");
					winnerListener.rightPressed();
				} else {
					System.out.println("Nenhum botão mapeado para: " + valuePressed);
					//System.out.println("else: " + valuePressed);
				}
			}
		}
	}

	/**
	 * Draw the sight on the screen.
	 * 
	 * @param g2d
	 */
	public void drawSight(Graphics2D g2d) {

		int x = (int) location.getX();
		int y = (int) location.getY();

		if (x >= Window.frameWidth) {
			x = (Window.frameWidth - sightImg.getWidth());
		}

		if (y >= Window.frameHeight) {
			y = Window.frameHeight - sightImg.getHeight();
		}

		g2d.drawImage(sightImg, x, y, null);
	}

	/**
	 * Given value of axis in percentage. Percentages increases from left/top to
	 * right/bottom.
	 * 
	 * @return value of axis in percentage.
	 */
	protected int getAxisValueInPercentage(float axisValue) {
		return (int) (((2 - (1 - axisValue)) * Window.frameWidth) / 2);
	}

	/**
	 * Show the message of none controller found.
	 */
	public void showControllerNotFound() {
		String msg = "Nenhum controle encontrado!!\n\n" + "Conecte um controlador USB e reinicie o jogo.";
		JOptionPane.showMessageDialog(null, msg, "Univaders", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	/**
	 * Show the message of controller disconected.
	 */
	public void showControllerDisconected() {
		String msg = "Controle Desconectado!!\n\n" + "Reconecte o controle e reinicie o jogo.";
		JOptionPane.showMessageDialog(null, msg, "Univaders", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
}
