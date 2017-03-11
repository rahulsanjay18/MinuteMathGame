package rahulShah.minMath.view;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import rahulShah.minMath.calculations.Calculations;

public class GameScreenController {

	private static String currentAnswerField = "";
	
	private static Game currentGame;
	
	private static Timer timer;					// Timer used to run the game

	private static TimerTask timerTask;			// method/function that operates the timer

	private static int negPowerOfTen = 0;		// used to calculate decimal values

	private static boolean isItDecimal = false;	// determines if it is in decimal mode

	private static boolean isNewNumber = true;	// determines if a new number is being entered

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
	public void initialize(Game game) {
		
		currentGame = game;
		
		// sets a timer task for 60 seconds, updating the label every second
		timerTask  = new TimerTask() {
			int seconds = 0;
			int maxTimes = 60;

			@Override
			public void run() {
				Platform.runLater(() -> {
					seconds++;
					game.getGameScreenView().updateTimer(60 - seconds);
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
		
		// Executes the game
		currentGame.getGameScreenView().displayQuestion(Calculations.genQuestion(currentGame.getCurrentLevel()));
		timer.schedule(timerTask, 0, 1000);
	}

	public static void processInput(String buttonName){
		
		// determines the type of output needed, processes input accordingly, and formats it to fit with the level name
		if(buttonName.equals("/")){

			isNewNumber = false;
			hasBeenFrac = true;
			currentAnswerField += "/";

		}else if(isNewNumber && hasBeenFrac){
			currentAnswerField = holdFrac + inputString(buttonName);

		}else if (isNewNumber) {
			
			currentAnswerField = inputString(buttonName);
			
		}else{
			holdFrac = currentAnswerField;
			currentAnswerField += inputString(buttonName);
			isNewNumber = true;
		}

		// sets the answer test with the right input
		currentGame.getGameScreenView().updateAnswer(currentAnswerField);
	}
	
	/**
	 * processes the input from the string given
	 * @param event the object that describes the type of action
	 * @param buttonName the name of the button needed
	 * @return the string of that part of the answer field
	 */
	private static String inputString(String buttonName) {
		
		String answerString = (currentAnswerField.isEmpty()) ? "0" : currentAnswerField;
		
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
	public static void flipDecBool(){
		isItDecimal = !isItDecimal;
	}

	/**
	 * sends answer to be corrected
	 */
	@FXML
	public static void submitAnswer(){
		String question = currentGame.getGameScreenView().getQuestion();	// question portion of the problem
		String finalAnswer = currentAnswerField;	// answer portion of the problem
		
		// updates score if answer is correct
		if(Calculations.checkAnswer(currentGame.getCurrentLevel(), finalAnswer, question)){
			score++;
			currentGame.getGameScreenView().updateScore(score);
			currentGame.getGameScreenView().clearAnswerField();
			currentGame.getGameScreenView().displayQuestion(Calculations.genQuestion(currentGame.getCurrentLevel()));
		}
	}


	public static void clearAnswer(){
		isItDecimal = false;
		negPowerOfTen = 0;
		currentAnswerField = "";
		isNewNumber = true;
		hasBeenFrac = false;
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
	private static BigDecimal decimalPoint(int numberInput, double existingNum) {
		negPowerOfTen--;
		
		BigDecimal existBigDec = BigDecimal.valueOf(existingNum);				// BigDecimal version of the existing number
		
		BigDecimal powTen = BigDecimal.valueOf(Math.pow(10, negPowerOfTen));	// BigDecimal version of the power of ten
		
		BigDecimal inNum = BigDecimal.valueOf(numberInput);						// BigDecimal version of the number inputed
		
		BigDecimal decimal = existBigDec.add(inNum.multiply(powTen));			// the operation that combines all components to get final answer
		
		decimal.setScale(3, RoundingMode.HALF_UP);								// rounds decimal to 3 places
		return decimal;
	}

	/**
	 * Switches the sign of the existing number
	 * @param existingNum the number that exists as the current answer
	 * @return the opposite of what was the existing number
	 */
	private static double posNegSwitch(double existingNum) {
		return -existingNum;
	}

	/**
	 * Ends the game, and adjusts view for an end game screen
	 */
	private void endGame() {
		currentGame.getGameScreenView().displayQuestion(Calculations.genQuestion(currentGame.getCurrentLevel()));

		currentGame.getGameScreenView().endGameView();
		
		score = 0;
	}

	
}
