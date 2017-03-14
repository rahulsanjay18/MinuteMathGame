package rahulShah.minMath.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import gameLogic.Level;

public class Calculations {
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

			isItCorrect = FiveBasicOperations.checkFiveBasicAnswer(currentLevel, answerToParse, questionParts, question);

		}else if(currentLevel.getLevelName().equals("Add Fraction") || currentLevel.getLevelName().equals("Subtract Fraction")){

			isItCorrect = checkFractionAnswer(currentLevel, answerToParse, questionParts, question);

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
			return FiveBasicOperations.fourBasicOperationGenerator(level);
		}else if (lvlName.equals("Exponents")) {
			return FiveBasicOperations.genExponentQuestion(level);
		}else if (lvlName.equals("Add Fraction") || lvlName.equals("Subtract Fraction")) {
			return Fraction.genFracQuestion(level);
		}else{
			return "";
		}
	}
}
