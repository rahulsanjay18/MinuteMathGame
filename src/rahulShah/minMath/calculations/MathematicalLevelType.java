/**
 * 
 */
package rahulShah.minMath.calculations;

import gameLogic.Level;

/**
 * @author rahul
 *
 */
public interface MathematicalLevelType {
	public String generate();
	
	public boolean checkAnswer(Level currentLevel, String answerToParse, String[] questionParts, String question);
}
