package rahulShah.minMath.view;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import rahulShah.minMath.gameLogic.Game;

public class EndGameView {
	@FXML
	private Label yourScore;
	@FXML
	private Label highScore;
	@FXML
	private Button playAgain;
	
	private static Game game;
	
	public void initialize(Game cGame) {
		game = cGame;
		scoreUpdate();
		EndGameController controller = new EndGameController();
		controller.initialize(game);
	}
	
	
	public void highScoreUpdate(int score) {
		highScore.setText("" + score);
	}
	
	
	public void scoreUpdate() {
		yourScore.setText("" + game.getScore());
	}
	
	/**
	 * Reruns the game by going back to the level selector
	 */
	@FXML
	public void endGame() {
		FXMLLoader root = new FXMLLoader(getClass().getResource("LevelSelector.fxml"));
		Parent parent;
		try {
			parent = root.load();
			Stage stage = (Stage) playAgain.getScene().getWindow();
			stage.setOpacity(1);
			stage.setScene(new Scene(parent, 1000, 800));
			stage.setResizable(false);
			stage.show();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
