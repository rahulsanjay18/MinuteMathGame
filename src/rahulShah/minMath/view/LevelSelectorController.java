package rahulShah.minMath.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import rahulShah.minMath.gameLogic.Game;
import rahulShah.minMath.gameLogic.Level;

public class LevelSelectorController {

	// Declare objects for various elements in the view
	@FXML
	public Button selectAddition;
	@FXML
	public Button selectSubtraction;
	@FXML
	public Button selectMultiplication;
	@FXML
	public Button selectDivision;
	@FXML
	public Button selectExponent;
	@FXML
	public Button selectAddFraction;
	@FXML
	public Button selectSubtractFraction;
	@FXML
	public Button selectTrig;
	@FXML
	public Button backButton;
	@FXML
	public Button playGame;
	@FXML
	public Slider levelSelector;
	@FXML
	public Label chooseLevelLabel;

	private static String nameButtonSelected;	// name of the button to be used to start the game

	/**
	 * Gets the name of the button selected
	 * @param event the Button clicked
	 */
	@FXML
	public void categoryButtonSelected(ActionEvent event) {
		Button buttonSelected = (Button)event.getSource();

		nameButtonSelected = buttonSelected.getText();

		chooseLevelLabel.setText("You chose: " + nameButtonSelected);
		 playGame.setDisable(false);
	}
	
	/**
	 * Moves the screen back to the Home Screen
	 */
	@FXML
	public void goBack(){
		FXMLLoader root = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
		Parent parent;
		try {
			parent = root.load();
			Stage stage = (Stage) playGame.getScene().getWindow();
			stage.setOpacity(1);
			stage.setScene(new Scene(parent, 1000, 800));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Begins the game by setting up the view
	 */
	@FXML
	public void startTheGame() {
		try {
			FXMLLoader root = new FXMLLoader(getClass().getResource("GameScreen.fxml"));

			Parent parent;
			parent = root.load();
			Stage stage = (Stage) playGame.getScene().getWindow();
			stage.setOpacity(1);
			stage.setScene(new Scene(parent, 1000, 800));
			GameScreenView controller = root.<GameScreenView>getController();
			
			Game currentGame = new Game(controller, new Level((int)levelSelector.getValue(), nameButtonSelected));
			
			currentGame.getGameScreenView().initialize(currentGame);	// initializes controller by sending the Game object to the GameScreenController
			
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
