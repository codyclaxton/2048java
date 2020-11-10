

import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
/**
 * Class representing the events of the buttons on our main menu 
 * @author CodyMacxton
 *
 */
public class GuiButton {

	private State currentState = State.RELEASED;
	private Rectangle clickBox;
	private ArrayList<ActionListener> actionListeners;
	private String text = "";

	private Color released;
	private Color hover;
	private Color pressed;
	private Font font = Game.main.deriveFont(22f);
	/**
	 * Initialazing our clickbox and action listeners
	 * And setting the colors for our specific states
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GuiButton(int x, int y, int width, int height) {
		clickBox = new Rectangle(x, y, width, height);
		actionListeners = new ArrayList<ActionListener>();
		released = new Color(173, 175, 178);
		hover = new Color(140, 150, 155);
		pressed = new Color(110, 115, 116);

	}

	public void update() {
		// TODO Auto-generated method stub

	}
	/**
	 * Setting the colors of our states and drawing the clickbox
	 * @param g
	 */
	public void render(Graphics2D g) {
		if (currentState == State.RELEASED) {
			g.setColor(released);
			g.fill(clickBox);
		} else if (currentState == State.HOVER) {
			g.setColor(hover);
			g.fill(clickBox);
		} else {
			g.setColor(pressed);
			g.fill(clickBox);
		}

		g.setColor(Color.white);
		g.setFont(font);
		g.drawString(text, clickBox.x + clickBox.width / 2 - DrawUtils.getMessageWidth(text, font, g) / 2,
				clickBox.y + clickBox.height / 2 + DrawUtils.getMessageHeight(text, font, g) / 2);
	}

	/**
	 * Action for when our button is clicked
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener) {
		actionListeners.add(listener);
	}
	/**
	 * If the mouse is pressed on our clickbox we are setting the state to pressed
	 * @param e - mouseevent 
	 */
	public void mousePressed(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) {
			currentState = State.PRESSED;
		}

	}
	/**
	 * If the mouse is released from our clickbox we are setting the state to released
	 * @param e - the mouseevent e
	 */
	public void mouseReleased(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) {
			for (ActionListener a : actionListeners) {
				a.actionPerformed(null);
			}
		}
		currentState = State.RELEASED;
	}

	/**
	 * If the the mouse is dragged from our click box we are determing its state
	 * @param e - mouseevent
	 */
	public void mouseDragged(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) {
			currentState = State.PRESSED;
		} else {
			currentState = State.RELEASED;
		}

	}

	/**
	 * If the mouse is moved from our clickbox we are determining its state
	 * 
	 * @param e
	 *            - the event of our mouse
	 */
	public void mouseMoved(MouseEvent e) {
		if (clickBox.contains(e.getPoint())) {
			currentState = State.HOVER;
		} else {
			currentState = State.RELEASED;
		}

	}

	/**
	 * Getting the x of our click box
	 * 
	 * @return x
	 */
	public int getX() {
		return clickBox.x;
	}

	/**
	 * Getting the y our clickbox
	 * 
	 * @return y
	 */
	public int getY() {
		return clickBox.y;
	}

	/**
	 * Getting the width of our clickbox
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return clickBox.width;
	}

	/**
	 * Getting the height of our clickbox
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return clickBox.height;
	}

	/**
	 * Setting the text for our clickbox
	 * 
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	private enum State {
		RELEASED, HOVER, PRESSED
	}

}
