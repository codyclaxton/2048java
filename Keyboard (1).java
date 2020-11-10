

import java.awt.event.KeyEvent;

/**
 * Class representing the keyboard input
 * @author Cody Claxton
 *
 */
public class Keyboard {

	public static boolean[] pressed = new boolean[256];
	public static boolean[] prev = new boolean[256];

	private Keyboard() {
	}
	
	/**
	 * Updating the key that is pressed to determine 
	 * direction of tiles
	 */
	public static void update() {
		for (int i = 0; i < 4; i++) {
			if (i == 0)
				prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
			if (i == 1)
				prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
			if (i == 2)
				prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
			if (i == 3)
				prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
		}
	}
	
	/**
	 * Sets whatever key pressed to true
	 * @param e-event pressed
	 */
	public static void keyPressed(KeyEvent e) {
		pressed[e.getKeyCode()] = true;
	}
	
	/**
	 * Sets the key that was pressed to false once released
	 * @param e - keyevent
	 */
	public static void keyReleased(KeyEvent e) {
		pressed[e.getKeyCode()] = false;
	}
	
	/**
	 * If you release the key the pressed event becomes false
	 * @param keyEvent
	 * @return boolean if the key is released
	 */
	public static boolean typed(int keyEvent){
		return !pressed[keyEvent] && prev[keyEvent];
	}
}
