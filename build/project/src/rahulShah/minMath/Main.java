package rahulShah.minMath;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	private AnchorPane rootLayout = new AnchorPane();;
	
	/**
	 *  Sets up the initial frame
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader root = new FXMLLoader();
			root.setLocation(Main.class.getResource("view/HomeScreen.fxml"));
			rootLayout = (AnchorPane) root.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
