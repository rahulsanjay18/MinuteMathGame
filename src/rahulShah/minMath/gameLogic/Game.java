package rahulShah.minMath.gameLogic;

import rahulShah.minMath.view.GameScreenView;

public class Game {
	private GameScreenView gameScreenView;
	
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

	private Level currentLevel;
	
	public Game(GameScreenView g, Level level){
		this.gameScreenView = g;
		
		this.currentLevel = level;
	}
	
	public Game (GameScreenView g){
		this.gameScreenView = g;
		
		this.currentLevel = new Level(0, "Tester");
	}
	
}
