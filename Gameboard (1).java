

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * Class representing the manipulation of the tiles
 * 
 * @author Cody Claxton
 */
public class Gameboard {
	public static final int ROWS = 4;
	public static final int COLS = 4;

	private final int startingTiles = 2;
	private Tile[][] board;
	private boolean started;
	private boolean over;
	private boolean won;
	private BufferedImage gameBoard;// Background of game board
	private BufferedImage finalBoard;
	private int x;
	private int y;
	private int saveCount;

	private static int SPACING = 10;
	public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
	public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

	private String saveData;
	private String fileName = "SaveData";
	private ScoreManager scores;
	private Leaderboards leaderBoards;

	/**
	 * Initializing our gameboard and top scores
	 * 
	 * @param x
	 * @param y
	 */
	public Gameboard(int x, int y) {

		this.x = x;
		this.y = y;
		board = new Tile[ROWS][COLS];
		gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

		boardImage();

		leaderBoards = Leaderboards.getInstance();
		leaderBoards.loadScores();
		scores = new ScoreManager(this);
		scores.loadGame();
		scores.setCurrentTopScore(leaderBoards.getHighScore());

		if (scores.newGame()) {
			start();
			scores.saveGame();
		} else {
			for (int i = 0; i < scores.getBoard().length; i++) {
				if (scores.getBoard()[i] == 0)
					continue;
				spawn(i / ROWS, i % COLS, scores.getBoard()[i]);
			}

			over = checkDead();
			won = checkWon();
		}

	}

	/**
	 * Drawing the background of our board
	 */
	private void boardImage() {
		Graphics2D g = (Graphics2D) gameBoard.getGraphics();
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		g.setColor(Color.lightGray);

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				int x = SPACING + SPACING * col + Tile.WIDTH * col;
				int y = SPACING + SPACING * row + Tile.HEIGHT * row;
				g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);

			}
		}
	}

	/**
	 * Spawning in the first 2 tiles
	 */
	private void start() {
		for (int i = 0; i < startingTiles; i++) {
			spawnRandom();
		}
	}

	/**
	 * Spawing random tiles either 2 or 4 after tiles are slid
	 */
	private void spawnRandom() {
		Random random = new Random();
		boolean notValid = true;

		while (notValid) {
			int location = random.nextInt(ROWS * COLS);
			int row = location / ROWS;
			int col = location % COLS;
			Tile current = board[row][col];
			if (current == null) {
				int value = random.nextInt(10) < 9 ? 2 : 4;// if less than 9
															// spawn 2 else
															// spawn 4
				Tile tile = new Tile(value, getTileX(col), getTileY(row));
				board[row][col] = tile;
				notValid = false;
			}
		}
	}

	/**
	 * Getting the x of our tile
	 * 
	 * @param column
	 * @return where the X positions should be
	 */
	public int getTileX(int col) {
		return SPACING + col * Tile.WIDTH + col * SPACING;
	}

	/**
	 * Getting the y of our tile
	 * 
	 * @param row
	 * @return where the y position should be
	 */
	public int getTileY(int row) {
		return SPACING + row * Tile.HEIGHT + row * SPACING;
	}

	/**
	 * Drawing the tiles on our board and will render continuously for when
	 * tiles are moved
	 * 
	 * @param Graphic
	 */
	public void render(Graphics2D g) {
		Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
		g2d.drawImage(gameBoard, 0, 0, null);

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null)
					continue;
				current.render(g2d);
			}
		}

		g.drawImage(finalBoard, x, y, null);// Drawing to the screen
		g2d.dispose();
	}

	/**
	 * Updating for when a key is pressed checking if a tile equals 2048 Saveing
	 * our game every couple seconds
	 */
	public void update() {
		saveCount++;
		checkKeys();

		if (saveCount >= 120) {
			saveCount = 0;
			scores.saveGame();
		}

		if (scores.getCurrentScore() > scores.getCurrentTopScore()) {
			scores.setCurrentTopScore(scores.getCurrentScore());
		}

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				Tile current = board[row][col];
				if (current == null)
					continue;
				resetPosition(current, row, col);
				if (current.getValue() == 2048) {
					setOver(true);
				}
			}
		}
	}

	/**
	 * Allowing our tiles to be slid without combining continuously.
	 * 
	 * @param current
	 * @param row
	 * @param col
	 */
	private void resetPosition(Tile current, int row, int col) {
		if (current == null)
			return;
		int x = getTileX(col);
		int y = getTileY(row);

		int distanceX = current.getX() - x;
		int distanceY = current.getY() - y;

		if (Math.abs(distanceX) < Tile.SLIDE_SPEED) {
			current.setX(current.getX() - distanceX);
		}
		if (Math.abs(distanceY) < Tile.SLIDE_SPEED) {
			current.setY(current.getY() - distanceY);
		}

		if (distanceX < 0) {
			current.setX(current.getX() + Tile.SLIDE_SPEED);
		}
		if (distanceY < 0) {
			current.setY(current.getY() + Tile.SLIDE_SPEED);
		}
		if (distanceX > 0) {
			current.setX(current.getX() - Tile.SLIDE_SPEED);
		}
		if (distanceY > 0) {
			current.setY(current.getY() - Tile.SLIDE_SPEED);
		}
	}

	/**
	 * Determaning if we can move to positions on our board 
	 * 
	 * @param row-row
	 *            we are moving too
	 * @param col-column
	 *            we are moving too
	 * @param horizontalDirection
	 * @param verticalDirection
	 * @param d=Direction
	 * @return boolean if we can move the tile
	 */
	private boolean move(int row, int col, int horizontalDirection, int verticalDirection, Direction d) {
		boolean canMove = false;

		Tile current = board[row][col];
		if (current == null)
			return false;
		boolean moveable = true;
		int newCol = col;
		int newRow = row;
		while (moveable) {// while can combine,go to empty tile, or hasnt
							// reached wall
			newCol += horizontalDirection;
			newRow += verticalDirection;
			if (checkOutOfBounds(d, newRow, newCol))
				break;//if true we cant move anymore
			if (board[newRow][newCol] == null) {
				board[newRow][newCol] = current;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].setSlideTo(new Point(newRow, newCol));// Set
																			// where
																			// the
																			// tile
																			// will
																			// end
																			// up
				canMove = true;
			} else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()) {
				board[newRow][newCol].setCanCombine(false);
				board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
				canMove = true;
				board[newRow - verticalDirection][newCol - horizontalDirection] = null;
				board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
				board[newRow][newCol].setCombineAnimation(true);
				scores.setCurrentScore(scores.getCurrentScore() + board[newRow][newCol].getValue());// resets
																									// value
																									// after
																									// combining
																									// tiles
			} else {
				moveable = false;
			}
		}
		return canMove;
	}

	/**
	 * Checking to see if certain directions will cause out of bounds
	 * 
	 * @param d-Direction
	 * @param row
	 * @param column
	 * @return if we are out of bounds
	 */
	private boolean checkOutOfBounds(Direction d, int row, int col) {
		if (d == Direction.LEFT) {
			return col < 0;
		} else if (d == Direction.RIGHT) {
			return col > COLS - 1;
		} else if (d == Direction.UP) {
			return row < 0;
		} else if (d == Direction.DOWN) {
			return row > ROWS - 1;
		}
		return false;
	}

	/**
	 * Moving our tiles in the specified direction paramater: The direction the
	 * tiles are going to check to see if we can move CITED- Got logic to move
	 * things in a 2d array from stack overflow
	 */
	private void moveTiles(Direction d) {
		boolean canMove = false;
		int horizontalDirection = 0;
		int verticalDirection = 0;

		// Logic for moving each tile indivually
		if (d == Direction.LEFT) {
			horizontalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = COLS - 1; col >= 0; col--) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, d);
					} else
						move(row, col, horizontalDirection, verticalDirection, d);
				}
			}
		}

		else if (d == Direction.RIGHT) {
			horizontalDirection = 1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = COLS - 1; col >= 0; col--) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, d);
					} else
						move(row, col, horizontalDirection, verticalDirection, d);
				}
			}
		}

		else if (d == Direction.DOWN) {
			verticalDirection = 1;
			for (int row = ROWS - 1; row >= 0; row--) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, d);
					} else
						move(row, col, horizontalDirection, verticalDirection, d);
				}
			}
		}

		else if (d == Direction.UP) {
			verticalDirection = -1;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < COLS; col++) {
					if (!canMove) {
						canMove = move(row, col, horizontalDirection, verticalDirection, d);
					} else
						move(row, col, horizontalDirection, verticalDirection, d);
				}
			}
		}

		else {
			System.out.println(d + " is not a valid direction");
		}
		for (int row = 0; row < ROWS; row++) {
			for (int cols = 0; cols < COLS; cols++) {
				Tile current = board[row][cols];
				if (current == null)
					continue;
				current.setCanCombine(true);

			}
		}

		if (canMove) {
			spawnRandom();
			setOver(checkDead());
		}
	}

	/**
	 * Checking to see if we have exhausted all of our moves
	 */
	private boolean checkDead() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null)
					return false;
				boolean canCombine = checkTilesAround(row, col, board[row][col]);
				if (canCombine) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Spawning in a tile at the specificied position with the value param
	 * 
	 * @param row
	 *            - row to spawn
	 * @param col
	 *            - col to spawn
	 * @param value
	 *            - value to spawn
	 */
	private void spawn(int row, int col, int value) {
		board[row][col] = new Tile(value, getTileX(col), getTileY(row));
	}

	/**
	 * Checking the surrounding tiles to see if we can combine tiles
	 * 
	 * @param row
	 * @param col
	 * @param current
	 *            - the current tile
	 * @return boolean if there are surrounding tiles available to move to
	 */
	private boolean checkTilesAround(int row, int col, Tile current) {
			if (row > 0) {
				Tile check = board[row - 1][col];
				if (check == null)
					return true;
				if (current.getValue() == check.getValue())
					return true;
			} else if (row < ROWS - 1) {
				Tile check = board[row + 1][col];
				if (check == null)
					return true;
				if (current.getValue() == check.getValue())
					return true;
			}

			else if (col > 0) {
				Tile check = board[row][col - 1];
				if (check == null)
					return true;
				if (current.getValue() == check.getValue())
					return true;
			} else if (col < COLS - 1) {
				Tile check = board[row][col + 1];
				if (check == null)
					return true;
				if (current.getValue() == check.getValue())
					return true;
			}
		return false;
	}

	/**
	 * Checking to see if any tile on our board equals 2048
	 * 
	 * @return boolean if 2048 has been reached
	 */
	private boolean checkWon() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null)
					continue;
				if (board[row][col].getValue() >= 2048)
					return true;
			}
		}
		return false;
	}

	/**
	 * Checking to see when a key is pressed So we can start our game
	 */
	private void checkKeys() {
		if (Keyboard.typed(KeyEvent.VK_LEFT)) {
			moveTiles(Direction.LEFT);
			if (!started)
				started = !over;
		}
		if (Keyboard.typed(KeyEvent.VK_RIGHT)) {
			moveTiles(Direction.RIGHT);
			if (!started)
				started = !over;
		}
		if (Keyboard.typed(KeyEvent.VK_DOWN)) {
			moveTiles(Direction.DOWN);
			if (!started)
				started = !over;
		}
		if (Keyboard.typed(KeyEvent.VK_UP)) {
			moveTiles(Direction.UP);
			if (!started)
				started = !over;
		}
	}

	/**
	 * Going through our tiles to see what is the highest tile on our board
	 * 
	 * @return the hight tile on our board
	 */
	public int getHighestTileValue() {
		int value = 2;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == null)
					continue;
				if (board[row][col].getValue() > value) {
					value = board[row][col].getValue();
				}
			}
		}
		return value;
	}

	/**
	 * Determing if our game is over
	 * 
	 * @return boolean if our game is over
	 */
	public boolean isOver() {
		return over;
	}

	/**
	 * Setting whether the game is over
	 * 
	 * @param dead
	 *            - boolean if our game is over
	 */
	public void setOver(boolean dead) {
		if (!this.over && dead) {
			leaderBoards.addTiles(getHighestTileValue());
			leaderBoards.addScores(scores.getCurrentScore());
			leaderBoards.saveScores();
		}
		this.over = dead;
	}

	/**
	 * Getting if we have won
	 * 
	 * @return boolean if we have won
	 */
	public boolean hasWon() {
		return won;
	}

	/**
	 * Saving our scores if the player has won
	 * 
	 * @param won
	 */
	public void setWon(boolean won) {
		if (!this.won && won) {
			leaderBoards.saveScores();
		}
		this.won = won;
	}

	/**
	 * Getting our scores;
	 * 
	 * @return the scores
	 */
	public ScoreManager getScores() {
		return scores;
	}

	/**
	 * Resetting all of properties of our game to signal a new game
	 */
	public void reset() {
		board = new Tile[ROWS][COLS];
		start();
		scores.saveGame();
		over = false;
		won = false;
		started = false;
		saveCount = 0;
	}

	/**
	 * Getting our gameboard
	 * 
	 * @return our board
	 */
	public Tile[][] getBoard() {
		return board;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
