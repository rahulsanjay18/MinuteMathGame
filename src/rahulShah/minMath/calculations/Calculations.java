package rahulShah.minMath.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import rahulShah.minMath.gameLogic.Level;

public class Calculations {

	//private static char[] superScriptArray = {'\u2070', '\u00b9', '\u00b2', '\u00b3', '\u2074', '\u2075', '\u2076', '\u2077', '\u2078', '\u2079'}; // Super Scripts used for displaying exponents

	//private static String superScripts = "[⁰¹²³⁴⁵⁶⁷⁸⁹]";

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
		question = question.substring(0, question.length() - 1);

		if(answerToParse.isEmpty()) {
			isItCorrect = false;
		}else if(Calculator.solve(question).equals("undef") || Calculator.solve(answerToParse).equals("undef"))
			isItCorrect = Calculator.solve(question).equals(answerToParse);
		else{
			isItCorrect = new BigDecimal(Calculator.solve("(" + question + ")")).subtract(new BigDecimal(Calculator.solve("("+ answerToParse + ")"))).abs().compareTo(BigDecimal.valueOf(.000000001)) == -1;
		}


		return isItCorrect;
	}

	/**
	 * Checks if a fraction problem has the right answer or not
	 * @param currentLevel gives the current level to determine which operation to do
	 * @param answerToParse the answer that need to be confirmed
	 * @param questionParts the parts of a question that need to be treated separately
	 * @param question question the question given to the user
	 * @return if the answer was correct or not
	 */

	public static boolean checkFractionAnswer(Level currentLevel, String answerToParse, String[] questionParts, String question) {

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
			return fourBasicGen(level);
		}else if (lvlName.equals("Exponents")) {
			return expGen(level);
		}else if (lvlName.equals("Add Fraction") || lvlName.equals("Subtract Fraction")) {
			return Fraction.genFracQuestion(level);
		}else if (lvlName.equals("Trigonometry")) {
			return trigGen(level.getLevelNum());
		}

		return "";

	}

	private static String fourBasicGen(Level level) {
		char operation;														// operation that will be used to construct the question
		int num1, num2;														// the two numbers that will be generated for the question
		Random rand = new Random();
		
		int sign = (rand.nextInt(2) == 0) ? 1 : -1;
		int sign2 = (rand.nextInt(2) == 0) ? 1 : -1;

		// generates the first number
		do {
			num1 = (int)(rand.nextInt((int)Math.pow(10, level.getLevelNum())));
		}while (num1 == 0);
		
		// generates the second number, and error checking for division
		do{
			num2 = (int) (rand.nextInt((int)Math.pow(10, (level.getLevelNum()))));
		}while (num2 == 0);

		// decides which operation to use
		switch (level.getLevelName()) {
		case "Addition":
			operation = '+';
			break;
		case "Subtraction":
			operation = '−';
			break;
		case "Multiplication":
			operation = '×';
			break;
		case "Division":
			operation = '/';
			break;
		default:
			operation = ' ';
			break;
		}

		// builds question to display
		return "" + num1 * sign + " " + operation + " " + num2 * sign2 + " =";
	}

	private static String expGen(Level level) {
		int indexExponent = (int) (Math.random() * level.getLevelNum() * 1.6 + 2);	// determines what the exponent will be

		int rangeOfBases = (int) (Math.random() * 10);								// determines what the base will be 

		// builds the question
		return "" + rangeOfBases + "^" + indexExponent + " =" ;
	}

	private static String trigGen(int levelNum) {

		String question = "";

		final int MAX_RANDOM = 72;

		int angles[] = {0, 30, 45, 60, 
				90, 120, 135, 150, 
				180, 210, 225, 240, 
				270, 300, 315, 330};

		final String FUNCTIONS_1[] = {"sin","cos","tan", "csc", "sec", "cot"};

		final String FUNCTIONS_2[] = {"(sin(θ))^2 + (cos(θ))^2", "1 + (tan(θ))^2", "(cot(θ))^2 + 1",
				"(sin(θ))^2", "(cos(θ))^2", "(tan(θ))^2", "(csc(θ))^2", 
				"(sec(θ))^2", "(cot(θ))^2"};

		final String FUNCTIONS_4[] = {"2 × sin(θ) × cos(θ)", "(cos(θ))^2 − (sin(θ))^2", "(2 × tan(θ))/(1 − (tan(θ))^2)", 
				"csc(θ) − cot(θ)", "csc(θ) + cot(θ)", "√((1 − cos(θ))/ 2)", "√((1 + cos(θ))/ 2)", 
				"√(2 / (1 − cos(θ))", "√(2 / (1 + cos(θ))"};

		int rand = new Random().nextInt(MAX_RANDOM);

		int angleIndex = new Random().nextInt(angles.length);

		String sign = (new Random().nextInt(2) == 0 || angles[angleIndex] == 0) ? "" : "-";

		if(levelNum == 1){

			question = FUNCTIONS_1[rand % FUNCTIONS_1.length] + "(" + sign + angles[angleIndex] + ")";

		}else if(rand > MAX_RANDOM * 3 / 4){
			return trigGen(levelNum - 1);

		}else if (levelNum == 2) {

			question = FUNCTIONS_2[rand % FUNCTIONS_2.length].replaceAll("θ", sign + angles[angleIndex]);

		}else if (levelNum == 3) {
			BigDecimal angle = BigDecimal.valueOf((new Random().nextInt(2) == 0) ? angles[angleIndex] : angles[angleIndex] + 360);

			angle = angle.divide(BigDecimal.valueOf(2), 3, BigDecimal.ROUND_UNNECESSARY);

			question = FUNCTIONS_1[rand % FUNCTIONS_1.length] + "(" + sign + angle.stripTrailingZeros().toPlainString().trim() + ")";

		}else if (levelNum == 4) {

			question = FUNCTIONS_4[rand % FUNCTIONS_4.length].replaceAll("θ", sign + angles[angleIndex]);

		}else if (levelNum == 5) {
			BigDecimal angle = BigDecimal.valueOf((new Random().nextInt(2) == 0) ? angles[angleIndex] : angles[angleIndex] + 360);

			angle = angle.divide(BigDecimal.valueOf(4), 3, BigDecimal.ROUND_UNNECESSARY);

			question = FUNCTIONS_1[rand % FUNCTIONS_1.length] + "(" + sign + angle.stripTrailingZeros().toPlainString().trim() + ")";

		}
		return question + "=";

	}	

}
