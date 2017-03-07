package rahulShah.minMath.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculations {
	private static char[] superScriptArray = {'\u2070', '\u00b9', '\u00b2', '\u00b3', '\u2074', '\u2075', '\u2076', '\u2077', '\u2078', '\u2079'}; // Super Scripts used for displaying exponents

	/**
	 * checks the answer that the user inputs
	 * @param currentLevel the level the user is playing in
	 * @param answerToParse the user's answer that will be manipulated
	 * @param question the question that the computer generated for the user
	 * @return if the answer was correct or not
	 */
	public static boolean checkAnswer(Level currentLevel, String answerToParse, String question) {
		answerToParse = answerToParse.trim(); 			// cleans up the answer string
		boolean isItCorrect = false; 					// used to confirm if the calculation is correct
		String[] questionParts = question.split(" "); 	// splits the question so it can be worked with
		
		// decides which method to use to determine if the answer is correct
		if(currentLevel.getLevelName().equals("Addition") || currentLevel.getLevelName().equals("Subtraction") ||
				currentLevel.getLevelName().equals("Multiplication") || currentLevel.getLevelName().equals("Division")){

			isItCorrect = checkFiveBasicAnswer(currentLevel, answerToParse, questionParts, question);

		}else if(currentLevel.getLevelName().equals("Add Fraction") || currentLevel.getLevelName().equals("Subtract Fraction")){

			isItCorrect = checkFractionAnswer(currentLevel, answerToParse, questionParts, question);

		}

		return isItCorrect;
	}

	/**
	 * Checks answers that are easy to check, things that only need one basic operation to get an answer
	 * @param currentLevel gives the current level to determine which operation to do
	 * @param answerToParse the answer that need to be confirmed
	 * @param questionParts the parts of a question that need to be treated separately
	 * @param question the question given to the user
	 * @return if the answer was correct or not
	 */
	
	private static boolean checkFiveBasicAnswer(Level currentLevel, String answerToParse, String[] questionParts, String question) {
		
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
	
	/**
	 * Checks if a fraction problem has the right answer or not
	 * @param currentLevel gives the current level to determine which operation to do
	 * @param answerToParse the answer that need to be confirmed
	 * @param questionParts the parts of a question that need to be treated separately
	 * @param question question the question given to the user
	 * @return if the answer was correct or not
	 */

	private static boolean checkFractionAnswer(Level currentLevel, String answerToParse, String[] questionParts, String question) {
		
		BigDecimal questionsAnswer;															// the answer converted to a decimal		
		String[] fracPart = answerToParse.split("/");										// splits the fraction so it can be worked with
		BigDecimal numeratorAns = BigDecimal.valueOf(Double.parseDouble(fracPart[0]));		// the numerator of the fraction
		BigDecimal denominatorAns = BigDecimal.valueOf(Double.parseDouble(fracPart[1]));	// the denominator of the fraction
		
		// determines which operation to do, and does the operation
		if(currentLevel.getLevelName().equals("Add Fraction")){
			
			questionsAnswer = Fraction.parseFraction(questionParts[0]).addFraction(Fraction.parseFraction(questionParts[2])).evaluate();
			
		}else if (currentLevel.getLevelName().equals("Subtract Fraction")) {
			
			questionsAnswer = Fraction.parseFraction(questionParts[0]).subtractFraction(Fraction.parseFraction(questionParts[2])).evaluate();
			
		}else{
			questionsAnswer = BigDecimal.valueOf(-1);
		}
		
		// compares both the actual answer and the users answer to determine if they are correct
		return (numeratorAns.divide(denominatorAns, 3, RoundingMode.HALF_UP)).compareTo(questionsAnswer) == 0;
	}

	/**
	 * Generates the questions for all operations
	 * @param level gives the current level to determine which operation to generate a question for
	 * @return question string
	 */
	
	public static String genQuestion(Level level) {

		String lvlName = level.getLevelName(); // used to determine what kind of question to generate

		// determines the kind of question needed to be generated, and executes the appropriate method
		if (lvlName.equals("Addition") || lvlName.equals("Subtraction") || lvlName.equals("Multiplication") || lvlName.equals("Division")) {
			return fourBasicOperationGenerator(level);
		}else if (lvlName.equals("Exponents")) {
			return genExponentQuestion(level);
		}else if (lvlName.equals("Add Fraction") || lvlName.equals("Subtract Fraction")) {
			return Fraction.genFracQuestion(level);
		}else{
			return "";
		}
	}

	/**
	 * Generates the questions for the four basic operations
	 * @param level level gives the current level to determine which operation to generate a question for
	 * @return question string
	 */
	private static String fourBasicOperationGenerator(Level level) {
		
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
	private static String genExponentQuestion(Level level) {


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
	 * Calculates the GCD
	 * @param first first number to be calculated
	 * @param second second number to be calculated
	 * @return the GCD of the two numbers
	 */
	public static int greatestCommonDivisor(int first, int second) {
		int large = (first > second) ? first : second;	// determine what is larger
		int small = (first < second) ? first : second;	// determine what is smaller
		int remainder = large % small; 					// gets the remainder

		// loops until remainder is 0, and then returns smallest
		if(remainder == 0){
			return small;
		}else{
			return greatestCommonDivisor(small, remainder);
		}
	}

	/**
	 * Calculates the LCM
	 * @param first first number to be calculated
	 * @param second second number to be calculated
	 * @return the LCM of the two numbers
	 */
	public static int leastCommonMultiple(int first, int second) {
		return (first/greatestCommonDivisor(first, second)) * second;
	}
}
