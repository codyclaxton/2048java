

import java.awt.*;

import java.awt.image.BufferedImage;

/**
 * @author Cody Claxton Represents a single game tile on our board
 */
public class Tile {
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;
	public static final int SLIDE_SPEED = 10;
	public static final int ARC_WIDTH = 15;
	public static final int ARC_HEIGHT = 15;

	private int value;
	private BufferedImage tileImage;
	private Color background;
	private Color text;
	private Font font;
	private Point slideTo;// our point class. not javas
	private int x;
	private int y;

	private boolean beginningAnimation = true;

	private boolean combineAnimation = false;
	private double scaleCombine = 1.2;
	private BufferedImage combineImage;

	private boolean canCombine = true;

	/**
	 * Setting the size of our tile
	 * 
	 * @param value
	 *            - the facevalue of the tile
	 * @param x-position
	 * @param y-position
	 */
	public Tile(int value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
		slideTo = new Point(x, y);
		tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		combineImage = new BufferedImage(WIDTH * 2, HEIGHT * 2, BufferedImage.TYPE_INT_ARGB);
		drawImage();
	}

	/**
	 * Setting up the colors of each square depending on the value Also drawing
	 * the tile onto our screen
	 * -Got color codes from Photoshop
	 */
	private void drawImage() {
		Graphics2D g = (Graphics2D) tileImage.getGraphics();
		if (value == 2) {
			background = new Color(0xe9e9e9);
			text = new Color(0x000000);
		} else if (value == 4) {
			background = new Color(0xe6daab);
			text = new Color(0x000000);
		} else if (value == 8) {
			background = new Color(0xf79d3d);
			text = new Color(0xffffff);
		} else if (value == 16) {
			background = new Color(0xf28007);
			text = new Color(0xffffff);
		} else if (value == 32) {
			background = new Color(0xf55e3b);
			text = new Color(0xffffff);
		} else if (value == 64) {
			background = new Color(0xff0000);
			text = new Color(0xffffff);
		} else if (value == 128) {
			background = new Color(0xe9de84);
			text = new Color(0xffffff);
		} else if (value == 256) {
			background = new Color(0xf6e873);
			text = new Color(0xffffff);
		} else if (value == 512) {
			background = new Color(0xf5e455);
			text = new Color(0xffffff);
		} else if (value == 1024) {
			background = new Color(0xf7e12c);
			text = new Color(0xffffff);
		} else if (value == 2048) {
			background = new Color(0xffe400);
			text = new Color(0xffffff);
		} else {
			background = Color.black;
			text = Color.white;
		}

		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(background);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);

		g.setColor(text);

		if (value <= 64) {
			font = Game.main.deriveFont(36f);
		} else {
			font = Game.main;
		}
		g.setFont(font);

		int drawX = WIDTH / 2 - DrawUtils.getMessageWidth("" + value, font, g) / 2;
		int drawY = HEIGHT / 2 - DrawUtils.getMessageHeight("" + value, font, g) / 6;
		g.drawString("" + value, drawX, drawY);
		g.dispose();

	}



	/**
	 * Rendering our tiles and makeing use of our animations
	 * @param g - Graphics
	 */
	public void render(Graphics2D g) {
		if(beginningAnimation){
			g.drawImage(tileImage, x, y, null);
		}
		else if(combineAnimation){
			g.drawImage(combineImage,(int)(x+WIDTH/2 - scaleCombine * WIDTH / 2),
									 (int)(y + HEIGHT / 2 - scaleCombine * HEIGHT / 2),null);
		}
		else{
			g.drawImage(tileImage, x, y, null);
		}
	}

	public void setValue(int value) {
		this.value = value;
		drawImage();
	}

	/**
	 * @return getter to see if we can combine the tiles
	 */
	public boolean canCombine() {
		return canCombine;
	}

	/**
	 * @param setting
	 *            the value from canCombine
	 */
	public void setCanCombine(boolean canCombine) {
		this.canCombine = canCombine;
	}

	/**
	 * @return getting the tile we are sliding too
	 */
	public Point getSlideTo() {
		return slideTo;
	}

	/**
	 * @param slideTo
	 *            the tile to slide
	 */
	public void setSlideTo(Point slideTo) {
		this.slideTo = slideTo;
	}

	/**
	 * Getting the value of our tile
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Getting the x value
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setting our x value
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getting our y value
	 * @return
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setting our y
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Getting the combine animation
	 * @return combining animation
	 */
	public boolean getCombineAnimation(){
		return combineAnimation;
	}
	
	/**
	 * Setting the combine animation
	 * @param combineAnimation
	 */
	public void setCombineAnimation(boolean combineAnimation){
		this.combineAnimation = combineAnimation;
	}
}
