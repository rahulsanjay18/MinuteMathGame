package rahulShah.minMath.view;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import rahulShah.minMath.calculations.Calculations;
import rahulShah.minMath.calculations.Level;

public class GameScreenController {

	/*---------------------------View Instance Variables--------------------------*/
	// Declare objects for various elements in the view
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
	
	/*----------------------------------------------------------------------------*/

	private static Timer timer;					// Timer used to run the game

	private static TimerTask timerTask;			// method/function that operates the timer

	private static int negPowerOfTen = 0;		// used to calculate decimal values

	private static boolean isItDecimal = false;	// determines if it is in decimal mode

	private static boolean isNewNumber = true;	// determines if a new number is being entered

	private static Level levelOfGame;			// level of game being played

	private static double existingNum = 0;		// the number that exists in the system

	private static String holdFrac;				// holds the first half of the fraction
	
	private static boolean hasBeenFrac = false;	// determines if the number has been a fraction
	
	private static int score = 0;				// holds the score

	// formats the decimals
	private static DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

	/**
	 * Begins the game, including starting timer and getting the level from the Level Selector class
	 * @param level determines the difficulty and the type of problems in this game
	 */
	public void initialize(Level level) {
		levelOfGame = level;
		playAgainButton.setVisible(false);
		playAgainButton.setDisable(true);
		
		// sets a timer task for 60 seconds, updating the label every second
		timerTask  = new TimerTask() {
			int seconds = 0;
			int maxTimes = 60;

			@Override
			public void run() {
				Platform.runLater(() -> {
					seconds++;
					timerLabel.setText("" + (60 - seconds));
					if (seconds >= maxTimes) {
						System.out.println("ended");
						this.cancel();
						endGame();
					}
				});
			}
		};
		df.setMaximumFractionDigits(340);
		timer = new Timer(true);
		
		// exectues the game
		questionField.setText(Calculations.genQuestion(levelOfGame));
		timer.schedule(timerTask, 0, 1000);
	}

	@FXML
	public void numberButtonPressed(ActionEvent event){
		Button buttonSelected = (Button)event.getSource();	// determines which button was selected
		String output;										// holds the next state of the answer field


		// determines the type of output needed, processes input accordingly, and formats it to fit with the level name
		if(buttonSelected.getText().equals("/")){

			isNewNumber = false;
			hasBeenFrac = true;
			output = answerField.getText() + "/";

		}else if(isNewNumber && hasBeenFrac){
			output = holdFrac + inputString(event, buttonSelected.getText());

		}else if (isNewNumber) {
			
			output = inputString(event, buttonSelected.getText());
			
		}else{
			holdFrac = answerField.getText();
			output = holdFrac + inputString(event, buttonSelected.getText());
			isNewNumber = true;
		}

		// sets the answer test with the right input
		answerField.setText(output);
	}
	
	/**
	 * processes the input from the string given
	 * @param event the object that describes the type of action
	 * @param buttonName the name of the button needed
	 * @return the string of that part of the answer field
	 */
	private String inputString(ActionEvent event, String buttonName) {
		
		String answerString = (answerField.getText().isEmpty()) ? "0" : answerField.getText();
		
		// checks if the answer is in the format of the denominator of fraction, or if it is a normal integer, and updates the existing number
		if(answerString.contains("/") && answerString.substring(answerString.indexOf("/") + 1).isEmpty()){
			
			existingNum = 0;
			
		}else if(answerString.contains("/")){
			
			existingNum = Double.parseDouble(answerString.substring(answerString.indexOf("/") + 1));
			
		}else{
			
			existingNum = Double.parseDouble(answerString);
			
		}
		
		// handles input for other buttons, further manipulating the existing number
		if (buttonName.equals("+/-")) {
			
			return df.format(posNegSwitch(existingNum));

		}else if(isItDecimal){
			
			int numberInput = Integer.parseInt(buttonName);
			return df.format(decimalPoint(numberInput, existingNum));

		}else{
			
			int numberInput = Integer.parseInt(buttonName);
			return df.format(getActualInputNumber(numberInput, existingNum));

		}

	}

	/**
	 * Switches the state of isItDecimal
	 */
	@FXML
	public void changeDecPoint(){
		isItDecimal = (isItDecimal) ? false : true;
	}

	/**
	 * sends answer to be corrected
	 */
	@FXML
	public void submitAnswerForCorrection(){
		String question = questionField.getText();	// question portion of the problem
		String finalAnswer = answerField.getText();	// answer portion of the problem
		
		// updates score if answer is correct
		if(Calculations.checkAnswer(levelOfGame, finalAnswer, question)){
			score++;
			updateScore(score);
			clearAnswerField();
			questionField.setText(Calculations.genQuestion(levelOfGame));
		}
	}

	/**
	 * Resets the static variables and the answer field to ready for the next problem
	 */
	@FXML
	public void clearAnswerField(){
		answerField.clear();
		isItDecimal = false;
		negPowerOfTen = 0;
		isNewNumber = true;
		hasBeenFrac = false;
	}

	/**
	 * updates score
	 * @param score the new score to be updated
	 */
	private void updateScore(int score) {
		scoreLabel.setText("" + score);
	}

	/**
	 * adds the number entered to the end of the existing number
	 * @param numberInput the number of the button pushed
	 * @param existingNum the number that exists as the current answer
	 * @return the transformed number with the input added to the end
	 */
	private static double getActualInputNumber(int numberInput, double existingNum) {
		double newNumber;
		
		existingNum = (isNewNumber) ? existingNum : 0;	// new numbers are automatically set to 0
		
		newNumber = numberInput + existingNum * 10;
		return newNumber;
	}

	/**
	 * adds the number entered to the end of the existing decimal number
	 * @param numberInput the number of the button pushed
	 * @param existingNum the number that exists as the current answer
	 * @return the transformed number with the input added to the end
	 */
	private BigDecimal decimalPoint(int numberInput, double existingNum) {
		negPowerOfTen--;
		
		BigDecimal existBigDec = BigDecimal.valueOf(existingNum);				// BigDecimal version of the existing number
		
		BigDecimal powTen = BigDecimal.valueOf(Math.pow(10, negPowerOfTen));	// BigDecimal version of the power of ten
		
		BigDecimal inNum = BigDecimal.valueOf(numberInput);						// BigDecimal version of the number inputed
		
		BigDecimal decimal = existBigDec.add(inNum.multiply(powTen));			// the operation that combines all components to get final answer
		
		decimal.setScale(3, RoundingMode.HALF_UP);								// rounds decimal to 3 places
		return decimal;
	}

	/**
	 * Swtiches the sign of the existing number
	 * @param existingNum the number that exists as the current answer
	 * @return the opposite of what was the existing number
	 */
	private double posNegSwitch(double existingNum) {
		return -existingNum;
	}

	/**
	 * Ends the game, and adjusts view for an end game screen
	 */
	private void endGame() {
		questionField.setText("Score: " + score);

		submitAns.setDisable(true);
		submitAns.setVisible(false);

		playAgainButton.setVisible(true);
		playAgainButton.setDisable(false);
		score = 0;
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
}
