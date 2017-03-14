package rahulShah.minMath.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gameLogic.Level;

public class FiveBasicOperations {
	
	private static char[] superScriptArray = {'\u2070', '\u00b9', '\u00b2', '\u00b3', '\u2074', '\u2075', '\u2076', '\u2077', '\u2078', '\u2079'}; // Super Scripts used for displaying exponents
	
	/**
	 * Generates the questions for the four basic operations
	 * @param level level gives the current level to determine which operation to generate a question for
	 * @return question string
	 */
	public static String fourBasicOperationGenerator(Level level) {
		
		char operation;														// operation that will be used to construct the question
		int num1, num2;														// the two numbers that will be generated for the question
		int numDigit = (int)(Math.random() * level.getLevelNum() + 1);		// max. number of digits allowed in each number

		// generates the first number
		num1 = (int)(Math.random() * Math.pow(10, level.getLevelNum()));

		// generates the second number, and error checking for division
		do{
			num2 = (int) (Math.random() * Math.pow(10, numDigit));
		}while (num2 == 0 && level.getLevelName().equals("Division"));

		// decides which operation to use
		switch (level.getLevelName()) {
		case "Addition":
			operation = '+';
			break;
		case "Subtraction":
			operation = '-';
			break;
		case "Multiplication":
			operation = '*';
			break;
		case "Division":
			operation = '/';
			break;
		default:
			operation = ' ';
			break;
		}
		
		// builds question to display
		return "" + num1 + " " + operation + " " + num2 + " =";
	}
	
	/**
	 * Generates the questions for the exponents
	 * @param level level gives the current level number to generate appropriate questions
	 * @return question string
	 */
	public static String genExponentQuestion(Level level) {


		int indexExponent = (int) (Math.random() * level.getLevelNum() * 1.6 + 2);	// determines what the exponent will be

		int rangeOfBases = (int) (Math.random() * 10);								// determines what the base will be 

		// builds the question
		return "" + rangeOfBases + superScriptArray[indexExponent];
	}

	/**
	 * converts superscript to integer
	 * @param superScriptChar the character to be converted
	 * @return integer conversion
	 */
	private static int superScriptCharToInt(char superScriptChar) {
		int i;

		// determines the integer that the superscript represents
		for (i = 0; i < superScriptArray.length; i++) {
			if(superScriptChar == superScriptArray[i]){
				break;
			}
		}
		
		return i;
	}
	
	/**
	 * Checks answers that are easy to check, things that only need one basic operation to get an answer
	 * @param currentLevel gives the current level to determine which operation to do
	 * @param answerToParse the answer that need to be confirmed
	 * @param questionParts the parts of a question that need to be treated separately
	 * @param question the question given to the user
	 * @return if the answer was correct or not
	 */
	
	public static boolean checkFiveBasicAnswer(Level currentLevel, String answerToParse, String[] questionParts, String question) {
		
		int answer;					// the actual answer to the question
		BigDecimal questionsAnswer;	// the answer converted to a decimal

		// decides which operation to do, executes the correct operation, then converts it to decimal
		if(currentLevel.getLevelName().equals("Addition")){

			answer = Integer.parseInt(questionParts[0]) + Integer.parseInt(questionParts[2]);
			questionsAnswer = BigDecimal.valueOf(answer);

		}else if (currentLevel.getLevelName().equals("Subtraction")) {

			answer = Integer.parseInt(questionParts[0]) - Integer.parseInt(questionParts[2]);
			questionsAnswer = BigDecimal.valueOf(answer);

		}else if (currentLevel.getLevelName().equals("Multiplication")) {

			answer = Integer.parseInt(questionParts[0]) * Integer.parseInt(questionParts[2]);
			questionsAnswer = BigDecimal.valueOf(answer);

		}else if (currentLevel.getLevelName().equals("Division")) {

			questionsAnswer = (BigDecimal.valueOf(Double.parseDouble(questionParts[0])).divide(BigDecimal.valueOf(Double.parseDouble(questionParts[2])), 3, RoundingMode.HALF_UP));

		}else if (currentLevel.getLevelName().equals("Exponents")) {

			questionsAnswer = BigDecimal.valueOf(Math.pow(Character.digit(question.charAt(0), 10), superScriptCharToInt(question.charAt(1))));

		}else{
			questionsAnswer = BigDecimal.valueOf(-1);
		}

		// compares both the actual answer and the users answer to determine if they are correct
		return (BigDecimal.valueOf(Double.parseDouble(answerToParse)).compareTo(questionsAnswer) == 0);
	}

}
