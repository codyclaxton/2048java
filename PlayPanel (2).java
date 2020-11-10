

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 * Class representing drawing our play scren
 * @author Cody Claxton
 */
public class PlayPanel extends GuiPanel {
	private Gameboard board;
	private BufferedImage info;
	private ScoreManager scores;
	private Font scoreFont;

	private GuiButton playAgain;
	private GuiButton mainMenu;
	private int buttonWidth = 160;
	private int spacing = 20;
	private int otherWidth = buttonWidth * 2 + spacing;
	private int buttonHeight = 50;
	private boolean added;
	private int alpha;
	private Font gameOverFont;
	
	/**
	 * Drawing our menu
	 */
	public PlayPanel(){
		scoreFont = Game.main.deriveFont(24f);
		gameOverFont = Game.main.deriveFont(68f);
		board = new Gameboard(Game.WIDTH / 2 - Gameboard.BOARD_WIDTH / 2, Game.HEIGHT - Gameboard.BOARD_HEIGHT - 20);
		scores = board.getScores();
		info = new BufferedImage(Game.WIDTH,200,BufferedImage.TYPE_INT_RGB);
		
		mainMenu = new GuiButton(Game.WIDTH / 2 - otherWidth / 2,450,otherWidth,buttonHeight);
		playAgain = new GuiButton(mainMenu.getX(),mainMenu.getY() - spacing - buttonHeight,otherWidth,buttonHeight);
		
		mainMenu.setText("Main Menu");
		playAgain.setText("Play Again");
		
		playAgain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				board.getScores().reset();
				board.reset();
				alpha = 0;
				
				remove(playAgain);
				remove(mainMenu);
				
				added = false;
			}	
		});
		
		mainMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				GuiScreen.getInstance().setCurrentPanel("Menu");
			}
		});
	}
	
	/**
	 * Drawing our scores onto the gui
	 * @param g - graphics
	 */
	private void drawGui(Graphics2D g){
		Graphics2D g2d = (Graphics2D)info.getGraphics();
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, info.getWidth(), info.getHeight());
		g2d.setColor(Color.lightGray);
		g2d.setFont(scoreFont);
		g2d.drawString("" + scores.getCurrentScore(), 30, 40);
		g2d.setColor(Color.red);
		g2d.drawString("Best: " + scores.getCurrentTopScore(), 
					Game.WIDTH-DrawUtils.getMessageWidth("Best: " + scores.getCurrentTopScore(),scoreFont, g2d)-20,40);
		//Aligning on the right side of the screen
		g2d.dispose();
		g.drawImage(info,0,0,null);	
	}
	
	/**
	 * Drawing our game over screen
	 * @param g - graphics
	 */
	public void drawGameOver(Graphics2D g){
		g.setColor(new Color(150,150,150,alpha));
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.red);
		g.setFont(gameOverFont);
		g.drawString("Game Over!", Game.WIDTH / 2 - DrawUtils.getMessageWidth("Game Over", gameOverFont, g) / 2, 250);		
	}
	
	/**
	 * Checking to see if our board is dead so we can darken our screen
	 */
	@Override //overriding update function in gui class
	public void update(){
		board.update();
		if(board.isOver()){
			alpha++;
			if(alpha>170){
				alpha = 170;
			}
		}
	}
	
	/**
	 * Rendering our board and puts buttons on top of board
	 */
	@Override //overriding render function in gui class
	public void render(Graphics2D g){
		drawGui(g);
		board.render(g);
		
		if(board.isOver()){
			if(!added){
				added = true;
				add(mainMenu);
				add(playAgain);
			}
			drawGameOver(g);
		}
		super.render(g);//calling render method of guipanel
	}
}
