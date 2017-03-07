package rahulShah.minMath.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeScreenController {
	
	// Declare objects for various elements in the view
	@FXML
	public Button rulesButton;
	@FXML
	public Button playButton;
	
	/**
	 * switches screens to Rules Screen
	 */
	@FXML
	public void changeToRulesScreen() {
		setupScreen("RulesScreen.fxml");
	}
	
	/**
	 * Switches screen to start the actual game setup
	 */
	@FXML
	public void gameStart() {
		setupScreen("LevelSelector.fxml");
	}
	
	/**
	 * Does the actual game switching
	 * @param nameFileGUI name of the GUI file
	 */
	private void setupScreen(String nameFileGUI) {
		try {
			FXMLLoader root = new FXMLLoader(getClass().getResource(nameFileGUI));
	        Parent parent = root.load();
	        Stage stage = (Stage) playButton.getScene().getWindow();
	        stage.setOpacity(1);
	        stage.setScene(new Scene(parent, 1000, 800));
	        stage.setResizable(false);
	        stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
