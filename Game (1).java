

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


/**
 * Class setting up our game Also the game loop
 * 
 * @author Cody Claxton
 *
 */
public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener, Runnable {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 630;
	public static final Font main = new Font("Cooper Std", Font.PLAIN, 28);
	private Thread game;
	private boolean running;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private GuiScreen screen;

	/**
	 * Allowing keyboard input & setting up our main screen
	 */
	public Game() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		screen = GuiScreen.getInstance();
		screen.add("Menu", new MainMenuPanel());
		screen.add("Play", new PlayPanel());
		screen.setCurrentPanel("Menu");
	}

	/**
	 * Updating our screen and keyboard
	 */
	private void update() {
		screen.update();
		Keyboard.update();
	}

	/**
	 * Drawing everything onto our screen
	 */
	private void render() {

		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		screen.render(g);
		g.dispose(); // Getting rid of the graphic

		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
	}

	/**
	 * Is going to run whenever we start our thread
	 * Determining how many updates we have and need to have
	 * CITED fatalCubez
	 */
	@Override
	public void run() {
		int fps = 0;
		int update = 0;
		long fpsTimer = System.currentTimeMillis();
		double nsPerUpdate = 1000000000.0 / 60;

		// last update time in nanoseconds
		double then = System.nanoTime();
		double unprocessed = 0;// how many updates we need just in case
								// rendering if failing

		while (running) {
			boolean shouldRender = false;
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;

			// update queue
			while (unprocessed >= 1) {
				update++;
				update();
				unprocessed--;
				shouldRender = true;
			}

			// render
			if (shouldRender) {
				fps++;
				render();
				shouldRender = false;
			} else
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (System.currentTimeMillis() - fpsTimer > 1000) {
				System.out.printf("%d fps %d updates", fps, update);
				System.out.println();
				fps = 0;
				update = 0;
				fpsTimer += 1000;
			}
		}
	}

	/**
	 * Starting our thread making it able to go through loop
	 */
	public synchronized void start() {
		if (running)
			return;
		running = true;
		game = new Thread(this, "game");
		game.start();
	}

	/**
	 * Stopping our thread if the game is not running
	 */
	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		System.exit(0);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	/**
	 * Getting the key that pressed
	 * @param - Key event
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		Keyboard.keyPressed(e);
	}

	/**
	 * Getting when the key was released
	 * @param key event
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		Keyboard.keyReleased(e);
	}

	/**
	 * When the users mouse is dragged
	 * @param Mouse event
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);

	}

	/**
	 * Setting that our mouse has moved
	 * @param - mouse event
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Setting when our mouse was pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		screen.mousePressed(e);

	}

	/**
	 * Setting when our mouse was released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
