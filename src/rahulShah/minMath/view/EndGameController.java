package rahulShah.minMath.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import rahulShah.minMath.gameLogic.Game;
import rahulShah.minMath.gameLogic.Level;

public class EndGameController {

	int highScoresArr[][] = new int[Level.numLevelNames()][Level.NUM_OF_LEVELS];
	File highScores;
	
	public void initialize(Game currentGame) {
		highScores = new File("highScores.txt");
		int levelName = currentGame.getCurrentLevel().getLevelNameNumber();
		int levelNum = currentGame.getCurrentLevel().getLevelNum() - 1;
		try {

			if(!highScores.exists()) {
				highScores.createNewFile();
				writeToFile();
			}else {
				readToFile();
			}

			if(currentGame.getScore() > highScoresArr[levelName][levelNum]) {
				highScoresArr[levelName][levelNum] = currentGame.getScore();
				
				writeToFile();
			}
			
			currentGame.getEndGameView().highScoreUpdate(highScoresArr[levelName][levelNum]);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile() {
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(highScores));

			for (int[] arr : highScoresArr) {
				int i = 0;
				for(int j : arr) {
					w.write(j + " ");
				}
				
				if(i < highScoresArr.length - 1)
					w.newLine();
				
				i++;
			}
			
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void readToFile() {
		try {
			BufferedReader r = new BufferedReader(new FileReader(highScores));
			
			int scoresArr[][] = new int[Level.numLevelNames()][Level.NUM_OF_LEVELS];
			
			for(int i = 0; i < scoresArr.length; i++) {
				String line = r.readLine();
				
				String parts[] = line.split(" ");
				
				int j = 0;
				for(String c : parts) {
					scoresArr[i][j] = Integer.parseInt(c);
					j++;
				}
				
			}
			
			highScoresArr = scoresArr;
			r.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
