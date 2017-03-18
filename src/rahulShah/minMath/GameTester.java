package rahulShah.minMath;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import rahulShah.minMath.gameLogic.Game;
import rahulShah.minMath.view.GameScreenView;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class GameTester extends Application {
	private AnchorPane rootLayout = new AnchorPane();
	
	/**
	 *  Sets up the initial frame
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader root = new FXMLLoader();
			root.setLocation(GameTester.class.getResource("view/GameScreen.fxml"));
			rootLayout = (AnchorPane) root.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			GameScreenView controller = root.<GameScreenView>getController();
			Game currentGame = new Game(controller);
			
			currentGame.getGameScreenView().initialize(currentGame);	// initializes controller by sending the Game object to the GameScreenController
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
