

import java.io.*;

/**
 * Class representing all the storage of the scores
 * @author Cody claxton
 *
 */
public class ScoreManager {

	private int currentScore;
	private int currentTopScore;
	private int[] board = new int[Gameboard.ROWS * Gameboard.COLS];
	
	private String filePath;
	private String temp = "Temp";
	private Gameboard gameBoard;
	
	private boolean newGame;
	
	/**
	 * Allowing our score access to our gameboard
	 * And initializing the path to our file we are saving too
	 * @param board
	 */
	public ScoreManager(Gameboard board){
		this.gameBoard = board;
		filePath = new File("").getAbsolutePath();
	}
	
	/**
	 * Resetting our f
	 */
	public void reset(){
		File f = new File(filePath, temp);
		if(f.isFile()){
			f.delete();
		}
		
		newGame = true;
		currentScore = 0;
	}
	
	/**
	 * Creating the file to save to if it doesnt exist already
	 */
	private void createFile(){
		FileWriter output = null;
		newGame = true;
		
		try{
			File f = new File(filePath, temp);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("" + 0);
			writer.newLine();
			writer.write(""+ 0);
			writer.newLine();
			
			for(int row = 0; row < Gameboard.ROWS; row++){
				for (int col = 0; col < Gameboard.COLS; col++){
					if(row == Gameboard.ROWS - 1 && col == Gameboard.COLS - 1){
						writer.write("" + 0);
					}
					else{
						writer.write(0 + "-");
					}
				}
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Saving the data from our game.  Ie High score
	 */
	public void saveGame(){
		FileWriter output = null;
		if(newGame){
			newGame = false;
		}
		 
		try{
			File f = new File(filePath, temp);
			output = new FileWriter(f);
			BufferedWriter writer = new BufferedWriter(output);
			writer.write("" + currentScore);
			writer.newLine();
			writer.write(""+ currentTopScore);
			writer.newLine();
			
			for(int row = 0; row < Gameboard.ROWS; row++){
				for (int col = 0; col < Gameboard.COLS; col++){
					int location = row * Gameboard.COLS + col;
					Tile tile = gameBoard.getBoard()[row][col]; 
					this.board[location] = tile != null ? tile.getValue() : 0;
					//Replacing null values with 0 for when we save
					
					if(row == Gameboard.ROWS - 1 && col == Gameboard.COLS - 1){
						writer.write("" + board[location]);
					}
					else{
						writer.write(board[location] + "-");
					}
				}
			}
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * Loads our game if the user closes the game
	 */
	public void loadGame(){
		try{
			File f = new File(filePath,temp);
			
			if(!f.isFile()){
				createFile();
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			currentScore = Integer.parseInt(reader.readLine());
			currentTopScore = Integer.parseInt(reader.readLine());
			
			String[] board = reader.readLine().split(".");
			for(int i = 0; i < board.length; i++){
				this.board[i] = Integer.parseInt(board[i]);
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Getting the current score
	 * @return the current score
	 */
	public int getCurrentScore() {
		return currentScore;
	}
	
	/**
	 * Setting the current score
	 * @param currentScore
	 */
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}

	/**
	 * Getting the current top score
	 * @return the current top score
	 */
	public int getCurrentTopScore() {
		return currentTopScore;
	}
	
	/**
	 * Setting the current topscore
	 * @param currentTopScore
	 */
	public void setCurrentTopScore(int currentTopScore) {
		this.currentTopScore = currentTopScore;
	}

	/**
	 * Getting our board as a 1d array
	 * @return board
	 */
	public int[] getBoard() {
		return board;
	}

	/**
	 * Getting if the user has selected a anew game
	 * @return boolean new game
	 */
	public boolean newGame() {
		return newGame;
	}

}
