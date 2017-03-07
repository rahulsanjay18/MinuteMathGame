package rahulShah.minMath.calculations;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Fraction {
	private int numerator;		// numerator of fraction
	private int denominator;	// denominator of fraction

	/**
	 * Fraction class constructor
	 * @param num numerator of fraction
	 * @param den denominator of fraction
	 */
	public Fraction(int num, int den) {
		numerator = num;
		denominator = den;

		if(numerator < 0 && denominator < 0){
			numerator = -numerator;
			denominator = -denominator;
		}
	}

	/**
	 * Converts the fraction to a string
	 * @return string version of fraction
	 */
	public String formatToString() {
		String numString = "" + this.getNumerator();
		String denString = "" + this.getDenominator();
		
		return numString + "/" + denString;
	}

	/**
	 * converts string to a fraction
	 * @param fraction string of the fraction
	 * @return Fraction object 
	 */
	public static Fraction parseFraction(String fraction) {
		String[] fracParts = fraction.split("/");	// splits the fraction into relevant parts
		fracParts[0] = fracParts[0].trim();			// cleans up the numerator string
		fracParts[1] = fracParts[1].trim();			// cleans up the denominator string
		
		return new Fraction(Integer.parseInt(fracParts[0]), Integer.parseInt(fracParts[1]));
	}
	
	/**
	 * converts fraction object to decimal
	 * @return BigDecimal object of fraction
	 */
	public BigDecimal evaluate() {
		return BigDecimal.valueOf(this.getNumerator()).divide(BigDecimal.valueOf(this.getDenominator()), 3, RoundingMode.HALF_UP);
	}
	
	/**
	 * Adds two fractions
	 * @param otherFrac the second fraction
	 * @return a fraction that is the sum of both
	 */
	public Fraction addFraction(Fraction otherFrac) {
		
		int lcm = Calculations.leastCommonMultiple(this.getDenominator(), otherFrac.getDenominator());
		
		int newNumerator = this.getNumerator() * lcm / this.getDenominator() + otherFrac.getNumerator() * lcm / otherFrac.getDenominator();
		
		return new Fraction(newNumerator, lcm);
	}
	
	/**
	 * Subtracts two fractions
	 * @param otherFrac the second fraction
	 * @return a fraction that is the difference of both
	 */
	public Fraction subtractFraction(Fraction otherFrac) {
		int lcm = Calculations.leastCommonMultiple(this.getDenominator(), otherFrac.getDenominator());
		
		int newNumerator = this.getNumerator() * lcm / this.getDenominator() - otherFrac.getNumerator() * lcm / otherFrac.getDenominator();		
		
		return new Fraction(newNumerator, lcm);
	}
	
	/**
	 * Generates the fraction question
	 * @param level the level of the question
	 * @return a string that is the question
	 */
	public static String genFracQuestion(Level level) {
		
		char operationChar = (level.getLevelName().substring(0, 3).equals("Add")) ? '+' : '-';	// determines the operation character
		int num1, num2, num3, num4;																// all of the relevant numbers in generating the fraction
		int numDigit = (int)(Math.random() * level.getLevelNum() + 1);							// determines number of digits in each fraction

		// generates numerator of first fraction
		num1 = (int)(Math.random() * Math.pow(10, level.getLevelNum()));

		// generates denominator of first fraction, with error check
		do{
			num2 = (int) (Math.random() * Math.pow(10, numDigit));
		}while (num2 == 0);

		// generates numerator of second fraction
		num3 = (int)(Math.random() * Math.pow(10, level.getLevelNum()));

		// generates denominator of second fraction, with error check
		do{
			num4 = (int) (Math.random() * Math.pow(10, numDigit));
		}while (num4 == 0);
		
		// constructs and returns question string
		return new Fraction(num1, num2).formatToString() + " " + operationChar + " " + new Fraction(num3, num4).formatToString();
	}
	
	/**
	 * @return the numerator
	 */
	public int getNumerator() {
		return numerator;
	}

	/**
	 * @param numerator the numerator to set
	 */
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}

	/**
	 * @return the denominator
	 */
	public int getDenominator() {
		return denominator;
	}

	/**
	 * @param denominator the denominator to set
	 */
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
}
