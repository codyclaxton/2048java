

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class GuiScreen {

	private static GuiScreen screen;
	private HashMap<String, GuiPanel> panels;
	private String currentPanel = "";

	/**
	 * Initializing our panels
	 */
	private GuiScreen() {
		panels = new HashMap<String, GuiPanel>();
	}

	/**
	 * Creating a screen
	 * @return a screen
	 */
	public static GuiScreen getInstance() {
		if (screen == null) {
			screen = new GuiScreen();
		}
		return screen;
	}

	/**
	 * Updating our panels
	 */
	public void update() {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).update();
		}
	}

	/**
	 * Rendering our panels
	 * @param g - graphics
	 */
	public void render(Graphics2D g) {
		if (panels.get(currentPanel) != null) {
			panels.get(currentPanel).render(g);
		}
	}

	/**
	 * Allows other classes to add panels in
	 * 
	 * @param panelName
	 *            - the name of the panel
	 * @param panel
	 *            - the panel
	 */
	public void add(String panelName, GuiPanel panel) {
		panels.put(panelName, panel);
	}

	/**
	 * Sets the panel to be focused
	 * 
	 * @param panelName
	 *            - the name of the panel
	 */
	public void setCurrentPanel(String panelName) {
		currentPanel = panelName;
	}

	/**
	 * Determining if our mouse was pressed
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		if(panels.get(currentPanel) != null){
			panels.get(currentPanel).mousePressed(e);
		}
	}

	/**
	 * Determining if our mouse was released
	 * @param e
	 */
	public void mouseReleased(MouseEvent e) {
		if(panels.get(currentPanel) != null){
			panels.get(currentPanel).mouseReleased(e);
		}
	}

	/**
	 * 
	 * @param e
	 */
	public void mouseDragged(MouseEvent e) {
		if(panels.get(currentPanel) != null){
			panels.get(currentPanel).mouseDragged(e);
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(panels.get(currentPanel) != null){
			panels.get(currentPanel).mouseMoved(e);
		}
	}
}
