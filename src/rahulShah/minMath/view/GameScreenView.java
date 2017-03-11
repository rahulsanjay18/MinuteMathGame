package rahulShah.minMath.view;

import gameLogic.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	private Button playAgainButton;
	
	@FXML
	public void numberButtonPressed(ActionEvent event){
		Button buttonSelected = (Button)event.getSource();
		GameScreenController.processInput(buttonSelected.getText());
	}
	
	public void initialize(Game currentGame) {
		playAgainButton.setVisible(false);
		playAgainButton.setDisable(true);
		GameScreenController controller = new GameScreenController();
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

		playAgainButton.setVisible(true);
		playAgainButton.setDisable(false);
	}
	
	/**
	 * Reruns the game by going back to the level selector
	 */
	@FXML
	public void playGameAgain() {
		setupScreen("LevelSelector.fxml");
	}

	/**
	 * Sets up view to go back to the level selector
	 * @param nameFileGUI name of the GUI file
	 */
	private void setupScreen(String nameFileGUI) {
		FXMLLoader root = new FXMLLoader(getClass().getResource(nameFileGUI));
		Parent parent;
		try {
			parent = root.load();
			Stage stage = (Stage) questionField.getScene().getWindow();
			stage.setOpacity(1);
			stage.setScene(new Scene(parent, 1000, 800));
			stage.setResizable(false);
			stage.show();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void changeDecPoint(){
		GameScreenController.flipDecBool();
	}
	
	/**
	 * sends answer to be corrected
	 */
	@FXML
	public void submitAnswerForCorrection(){
		GameScreenController.submitAnswer();
	}
}
