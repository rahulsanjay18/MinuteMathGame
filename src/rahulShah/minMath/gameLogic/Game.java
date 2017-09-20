package rahulShah.minMath.gameLogic;

import rahulShah.minMath.view.EndGameView;
import rahulShah.minMath.view.GameScreenView;

public class Game {
	private GameScreenView gameScreenView;
	private Level currentLevel;
	private EndGameView endGameView;
	private int score;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void incrementScore() {
		this.score = this.score + 1;
	}

	public EndGameView getEndGameView() {
		return endGameView;
	}

	public void setEndGameView(EndGameView endGameView) {
		this.endGameView = endGameView;
	}

	public GameScreenView getGameScreenView() {
		return gameScreenView;
	}

	public void setGameScreenView(GameScreenView gameScreenView) {
		this.gameScreenView = gameScreenView;
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	public Game(GameScreenView g, Level level){
		this.gameScreenView = g;
		
		this.currentLevel = level;
		
		this.score = 0;
	}
	
	public Game (GameScreenView g){
		this.gameScreenView = g;
		
		this.currentLevel = new Level(0, -1);
	}
	
}
