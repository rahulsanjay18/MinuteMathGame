package rahulShah.minMath.view;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import rahulShah.minMath.calculations.Calculations;
import rahulShah.minMath.gameLogic.Game;

public class GameScreenController {

	private static String currentAnswerField = "";
	
	private static Game currentGame;
	
	private static Timer timer;					// Timer used to run the game

	private static TimerTask timerTask;			// method/function that operates the timer
	
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
						this.cancel();
						currentGame.getGameScreenView().endGame(currentGame);
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

	public static void processInput(String buttonName) {
		if(buttonName.matches("[0-9.-^()-]")){
			currentAnswerField += buttonName;
		}else if(buttonName.matches("[\\+\\−×/]")){
			currentAnswerField += " " + buttonName + " ";
		}else if (buttonName.matches("√")) {
			currentAnswerField += buttonName + "(";
		}else if (buttonName.equals("undef")) {
			currentAnswerField += "undef";
		
		}
		currentGame.getGameScreenView().updateAnswer(currentAnswerField);
	}

	/**
	 * sends answer to be corrected
	 */
	public static void submitAnswer(){
		String question = currentGame.getGameScreenView().getQuestion();	// question portion of the problem
		String finalAnswer = currentAnswerField;	// answer portion of the problem
		
		// updates score if answer is correct
		if(Calculations.checkAnswer(currentGame.getCurrentLevel(), finalAnswer, question)){
			currentGame.incrementScore();
			currentGame.getGameScreenView().updateScore(currentGame.getScore());
			currentGame.getGameScreenView().clearAnswerField();
			currentGame.getGameScreenView().updateStatBar("Correct!");
			currentGame.getGameScreenView().displayQuestion(Calculations.genQuestion(currentGame.getCurrentLevel()));
		}else{
			currentGame.getGameScreenView().clearAnswerField();
			currentGame.getGameScreenView().updateStatBar("Wrong. Try Again");
		}
	}


	public static void clearAnswer(){
		currentAnswerField = "";
	}

	
}
