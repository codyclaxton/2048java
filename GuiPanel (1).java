

import java.awt.Graphics2D;
import java.awt.event.*;
import java.util.ArrayList;

public class GuiPanel {
	private ArrayList<GuiButton> buttons;
	
	/**
	 * Initializing our gui buttons
	 */
	public GuiPanel(){
		buttons = new ArrayList<GuiButton>();
	}
	
	/**
	 * Updating our buttons
	 */
	public void update() {
			for(GuiButton b : buttons){
				b.update();
			}
		}

	/**
	 * Rendering our buttons
	 * @param g
	 */
	public void render(Graphics2D g) {
			for(GuiButton b : buttons){
				b.render(g);
			}
		}

	/**
	 * Adding our buttons to screen
	 * @param button
	 */
	public void add(GuiButton button){
		buttons.add(button);
	}
	
	/**
	 * Removing our buttons from screen
	 * @param button
	 */
	public void remove(GuiButton button){
		buttons.remove(button);
	}
	
	/**
	 * Determine when a button is pressed
	 * @param e
	 */
	public void mousePressed(MouseEvent e) {
		for(GuiButton b : buttons){
			b.mousePressed(e);
		}		
	}

	/**
	 * Determining when our mouse was released
	 * @param e mouse event
	 */
	public void mouseReleased(MouseEvent e) {
		for(GuiButton b : buttons){
			b.mouseReleased(e);
		}
	}

	/**
	 * Determingin if our mouse was draggeed
	 * @param e mouse event
	 */
	public void mouseDragged(MouseEvent e) {
		for(GuiButton b : buttons){
			b.mouseDragged(e);
		}
	}

	/**
	 * Determining if our mouse moved
	 * @param e - mousevent 
	 */
	public void mouseMoved(MouseEvent e) {
		for(GuiButton b : buttons){
			b.mouseMoved(e);
		}
		
	}

}
