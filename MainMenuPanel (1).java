

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Class representing our main menu panel
 * @author Cody Claxton
 *
 */
public class MainMenuPanel extends GuiPanel {

	private Font titleFont = Game.main.deriveFont(100f);
	private Font author = Game.main.deriveFont(24f);
	private String title = "2048";
	private String creator = "Made By: Cody Claxton";
	private int buttonWidth = 220;
	private int buttonHeight = 60;
	private int spacing = 90;
	
	/**
	 * Initializing our main menu screen with 3 buttons
	 * And adding action listeners to decides what happens when the button is pressed
	 */
	public MainMenuPanel(){
		super();//getting the constructor from gui panel
		GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2,220, buttonWidth, buttonHeight);
		GuiButton leaderBoardButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2,playButton.getY() + spacing, buttonWidth, buttonHeight);
		GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2,leaderBoardButton.getY() + spacing, buttonWidth, buttonHeight);
		
		playButton.setText("Play");
		leaderBoardButton.setText("Leaderboards");
		quitButton.setText("Quit");
		
		/**
		 * Setting the current panel to our play screen
		 */
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GuiScreen.getInstance().setCurrentPanel("Play");
				
			}
			
		});
		
		/**
		 * Getting leaderboards when leaderboards button is pressed
		 */
		leaderBoardButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiScreen.getInstance().setCurrentPanel("Leaderboards");
			}
			
		});
		
		/**
		 * Exiting the game when the quit button is clicked
		 */
		quitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
			
		});
		
		add(playButton);
		add(leaderBoardButton);
		add(quitButton);
	}
	
	/**
	 * Rendering all of our buttons
	 */
	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setFont(titleFont);
		g.setColor(Color.red);
		g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
		g.setFont(author);
		g.drawString(creator, 20, Game.HEIGHT - 10);
	}
	
}
