

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/**
 * Helper methods for help centering our tiles
 * Static because we need to access the width and heights throughout game
 * @author Cody Claxton
 *
 */
public class DrawUtils {
	private DrawUtils() { }
	
	/**
	 * Getting the width for centering the text of the number on the tile
	 * @param number -  Number as a string to be centered 
	 * @param font - font to be used
	 * @param g - graphics
	 * @return the width
	 */
	public static int getMessageWidth(String number, Font font, Graphics2D g){
		g.setFont(font);
		Rectangle2D bounds = g.getFontMetrics().getStringBounds(number,  g);
		return (int)bounds.getWidth();
	}
	/**
	 * Getting the height for center the text of the number on the tile
	 * @param number - Number as a string to be centered
	 * @param font - font to be set
	 * @param g graphics
	 * @return the height
	 */
	public static int getMessageHeight(String number, Font font, Graphics2D g){
		g.setFont(font);
		if(number.length()== 0) return 0;
		TextLayout tl = new TextLayout(number, font, g.getFontRenderContext());
		return (int)(tl.getBounds().getHeight());
	}
}
