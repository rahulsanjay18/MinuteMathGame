package rahulShah.minMath.view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rahulShah.minMath.gameLogic.Game;

public class GameScreenView {
	@FXML
	private Button buttonNumber1;			
	@FXML
	private Button buttonNumber2;			
	@FXML
	private Button buttonNumber3;			
	@FXML
	private Button buttonNumber4;			
	@FXML
	private Button buttonNumber5;			
	@FXML
	private Button buttonNumber6;			
	@FXML
	private Button buttonNumber7;			
	@FXML
	private Button buttonNumber8;			
	@FXML
	private Button buttonNumber9;			
	@FXML
	private Button buttonNumber0;			
	@FXML
	private Button buttonDecimal;			
	@FXML
	private Button buttonPosNeg;			
	@FXML
	private Button buttonForwardSlash;		
	@FXML
	private Button submitAns;				
	@FXML
	private TextField answerField;			
	@FXML
	private Label questionField;
	@FXML
	private Label scoreLabel;
	@FXML
	private Label timerLabel;
	@FXML
	private Button clearButton;
	@FXML
	private Label statusBar;
	@FXML
	private Button plusSignButton;
	@FXML
	private Button minusSignButton;
	@FXML
	private Button radicalButton;
	@FXML
	private Button undefinedButton;
	@FXML
	private Button openParenthetical;
	@FXML
	private Button closeParenthetical;
	
	@FXML
	public void numberButtonPressed(ActionEvent event){
		Button buttonSelected = (Button)event.getSource();
		GameScreenController.processInput(buttonSelected.getText());
	}
	
	public void initialize(Game currentGame) {
		statusBar.setText("");
		GameScreenController controller = new GameScreenController();
		disableControls(currentGame);
		controller.initialize(currentGame);
	}
	
	public void updateTimer(int time) {
		timerLabel.setText("" + time);
	}
	
	public void displayQuestion(String question) {
		questionField.setText(question);
	}
	
	public String getQuestion(){
		return questionField.getText();
	}
	
	public void updateAnswer(String answer){
		answerField.setText(answer);
	}
	
	public void clearAnswerField(){
		answerField.clear();
		GameScreenController.clearAnswer();
	}
	
	public void updateScore(int score){
		scoreLabel.setText("" + score);
	}
	
	public void endGameView(){
		submitAns.setDisable(true);
		submitAns.setVisible(false);
	}
	
	public void updateStatBar(String msg) {
		statusBar.setText(msg);
	}
	
	
	
	public void disableControls(Game currentGame) {
		String levelName = currentGame.getCurrentLevel().getLevelName();
		
		if(levelName.equals("Addition") || levelName.equals("Subtraction") || 
		   levelName.equals("Multiplication") || levelName.equals("Division") ||
		   levelName.equals("Exponents")) {
			
			plusSignButton.setDisable(true);
			minusSignButton.setDisable(true);
			buttonForwardSlash.setDisable(true);
			radicalButton.setDisable(true);
			undefinedButton.setDisable(true);
			openParenthetical.setDisable(true);
			closeParenthetical.setDisable(true);
			
		}else if (levelName.contains("Fraction")) {
			plusSignButton.setDisable(true);
			minusSignButton.setDisable(true);
			radicalButton.setDisable(true);
			undefinedButton.setDisable(true);
			openParenthetical.setDisable(true);
			closeParenthetical.setDisable(true);
		}
		
	}
	
	/**
	 * sends answer to be corrected
	 */
	@FXML
	public void submitAnswerForCorrection(){
		GameScreenController.submitAnswer();
	}
	
	/**
	 * Reruns the game by going back to the level selector
	 */
	public void endGame(Game currentGame) {
		FXMLLoader root = new FXMLLoader(getClass().getResource("EndGameScreen.fxml"));
		Parent parent;
		try {
			parent = root.load();
			Stage stage = (Stage) statusBar.getScene().getWindow();
			stage.setOpacity(1);
			stage.setScene(new Scene(parent, 1000, 800));
			EndGameView controller = root.<EndGameView>getController();
			currentGame.setEndGameView(controller);
			currentGame.getEndGameView().initialize(currentGame);	// initializes controller by sending the Game object to the GameScreenController
			
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
