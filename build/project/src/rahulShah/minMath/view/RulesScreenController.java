package rahulShah.minMath.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RulesScreenController {
	@FXML
	private Button backButton;	// goes back to the home screen
	
	/**
	 * Moves the screen back to the Home Screen
	 */
	@FXML
	public void goBack(){
		FXMLLoader root = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
		Parent parent;
		try {
			parent = root.load();
			Stage stage = (Stage) backButton.getScene().getWindow();
			stage.setOpacity(1);
			stage.setScene(new Scene(parent, 1000, 800));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
