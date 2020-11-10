

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Class representing our leaderboard
 * 
 * @author Cody Claxton
 *
 */
public class Leaderboards {
	private static Leaderboards leaderBoard;
	private String filePath;
	private String highScores;

	private ArrayList<Integer> topScores;
	private ArrayList<Integer> topTiles;

	/**
	 * Initializing our top scores and tiles and setting up the scores file
	 */
	private Leaderboards() {
		filePath = new File("").getAbsolutePath();
		highScores = "Scores";

		topScores = new ArrayList<Integer>();
		topTiles = new ArrayList<Integer>();

	}

	/**
	 * Getting our leaderboard and creating one if it hasnt been created already
	 * 
	 * @return the leaderboard
	 */
	public static Leaderboards getInstance() {
		if (leaderBoard == null) {
			leaderBoard = new Leaderboards();
		}

		return leaderBoard;
	}

	/**
	 * Adding the top score to leaderboard
	 * 
	 * @param score
	 *            - the score to add
	 */
	public void addScores(int score) {
		for (int i = 0; i < topScores.size(); i++) {
			if (score >= topScores.get(i)) {
				topScores.add(score);
				topScores.remove(topScores.size() - 1);
				return;
			}
		}
	}

	/**
	 * Adding the top tile achieved to our leaderboard
	 * 
	 * @param tileValue
	 *            - the tile value to add
	 */
	public void addTiles(int tileValue) {
		for (int i = 0; i < topTiles.size(); i++) {
			if (tileValue >= topTiles.get(i)) {
				topTiles.add(i, tileValue);
				topTiles.remove(topTiles.size() - 1);
				return;
			}
		}
	}

	/**
	 * Loading the scores from our file And clearing previous scores
	 */
	public void loadScores() {
		try {
			File f = new File(filePath, highScores);
			if (!f.isFile()) {//if its not already a file
				createSaveData();
			}

			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

			topScores.clear();
			topTiles.clear();

			String[] scores = reader.readLine().split("-");
			String[] tiles = reader.readLine().split("-");

			for (int i = 0; i < scores.length; i++) {
				topScores.add(Integer.parseInt(scores[i]));
			}

			for (int i = 0; i < tiles.length; i++) {
				topTiles.add(Integer.parseInt(tiles[i]));
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saving the top 5 tiles and scores to our file
	 */
	public void saveScores(){
		FileWriter output = null;
		
		try{
			File f = new File(filePath, highScores);
			output = new FileWriter(f);//only saved at end of game
			BufferedWriter writer = new BufferedWriter(output);
			
			writer.write(topScores.get(0) + "-" + topScores.get(1) + "-" +  topScores.get(2) + "-" +  topScores.get(3)
					+ "-" +  topScores.get(4));
			writer.newLine();
			writer.write(topTiles.get(0) + "-" + topTiles.get(1) + "-" +  topTiles.get(2) + "-" +  topTiles.get(3)
			+ "-" +  topTiles.get(4));
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Creating our top tiles and scores with 0s
	 */
	private void createSaveData() {
		FileWriter output = null;
		
		try{
			File f = new File(filePath, highScores);
			output = new FileWriter(f);//only saved at end of game
			BufferedWriter writer = new BufferedWriter(output);
			
			writer.write("0-0-0-0-0");//line for our top scores
			writer.newLine();
			writer.write("0-0-0-0-0");//line for our top tiles
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Getting the top high score in our file
	 * @return the top score
	 */
	public int getHighScore(){
		return topScores.get(0);
	}
	
	/**
	 * Getting the top tile score in our file
	 * @return top tile score
	 */
	public int getTopTile(){
		return topTiles.get(0);
	}
	
	/**
	 * Getting all of the top scores in our file
	 * @return all 5 top scores
	 */
	public ArrayList<Integer> getTopScores(){
		return topScores;
	}
	
	/**
	 * Getting the top tile scores in our file
	 * @return top 5 tiles scores
	 */
	public ArrayList<Integer> getTopTiles(){
		return topTiles;
	}
	
}
