package rahulShah.minMath.calculations;

import java.math.BigDecimal;

public class Calculator {
	
	private static String expression;
	
	private static int parenIndexes[];
	
	private static String parenString = "";
	
	private static String regex = "[\\+\\−×/\\^√]";// Uses − instead of - (−/-) and × instead of x (×/x)
	
	public static boolean isitRadians = false;
	
	private final static String FUNCTIONS[] = {"sin","cos","tan", "csc", "sec", "cot"};
	
	private final static int SCALE_SET = 16;
	
	private final static double PI = 3.1415926535897932384626433832795028841971693993751;
	
	
	public static String solve(String toSolve) {
		
		if(toSolve.equals("undef"))
			return toSolve.trim();
		
		expression = toSolve.trim();
		
		paren();
		
		System.gc();
		if(expression.contains("[0-9]"))
			expression = numberToString((new BigDecimal(expression).setScale(SCALE_SET-1, BigDecimal.ROUND_HALF_UP)));
		//System.out.println(expression);
		return expression;
		
	}
	
private static void paren() {
		
		expression = expression.replaceAll(" ", "");
		
		while (expression.contains("(")){
		int numParen = expression.length() - expression.replaceAll("[()]", "").length();
		
		parenIndexes = new int[numParen];
		int j = 0;
		for(int i = 0; i < expression.length(); i++){
			if(expression.charAt(i) == '(' || expression.charAt(i) == ')'){
				parenIndexes[j] = i;
				j++;
			}
		}
		
		parenString = expression.replaceAll("[^()]", "");
		
			int parenDex = parenString.indexOf("()");
			
			expression = eval(expression.substring(parenIndexes[parenDex] + 1, parenIndexes[parenDex + 1]));
			
			parenString = parenString.replace("()", "");
		}
	}
	
	public static String eval(String equation) {
		
		equation = equation.replaceAll("", "");
		String parts[] = createArrayParts(equation);
		String origEquation = new String(equation);
		
		for(String func : FUNCTIONS){
			
			while(equation.indexOf(func) != -1){
				int funcIndex = findIndexOf(parts, func);
				
				String part = parts[funcIndex];
				
				BigDecimal number1 = new BigDecimal(part.substring(func.length()));
				
				BigDecimal numberAns = functionEval(number1, func);
				
				equation = equation.replace(func + numberToString(number1), numberToString(numberAns));
				
				parts = createArrayParts(equation);
	
			}
			
		}
		
		while (equation.indexOf('^') != -1 || equation.indexOf('√') != -1) {
			int expIndex = findIndexOf(parts, "^");
			int radIndex = findIndexOf(parts, "√");
			BigDecimal number3;
			
			if (radIndex < 0 ||(expIndex > 0 && expIndex < radIndex)) {
				BigDecimal number1 = BigDecimal.valueOf(Double.parseDouble(parts[expIndex - 1]));
				int number2 = Integer.parseInt(parts[expIndex + 1]);
				
				number3 = number1.pow(number2);
				
				equation = equation.replace(numberToString(number1) +  "^"  + number2, numberToString(number3));
			}else {
				
				BigDecimal number1 = BigDecimal.valueOf(Double.parseDouble(parts[radIndex + 1]));
				
				number3 = sqrt(number1);
				
				equation = equation.replace("√" + numberToString(number1), numberToString(number3));
				
			}
			parts = createArrayParts(equation);
		}
		
		while (equation.indexOf('/') != -1 || equation.indexOf('×') != -1) {

			int divIndex = findIndexOf(parts, "/");
			int multIndex = findIndexOf(parts, "×");
			BigDecimal number3 = BigDecimal.ZERO;
			if(multIndex < 0 ||(divIndex > 0 && divIndex < multIndex)){
				BigDecimal number1 = BigDecimal.valueOf(Double.parseDouble(parts[divIndex - 1]));
				BigDecimal number2 = BigDecimal.valueOf(Double.parseDouble(parts[divIndex + 1]));

				if(number2.compareTo(BigDecimal.ZERO) == 0){
					equation = "undef";
					expression = "undef";
				}else{
					number3 = BigDecimal.valueOf(number1.doubleValue() / number2.doubleValue());
				}
				equation = equation.replace(numberToString(number1) + "/" + numberToString(number2), numberToString(number3));

			}else{
				BigDecimal number1 = BigDecimal.valueOf(Double.parseDouble(parts[multIndex - 1]));
				BigDecimal number2 = BigDecimal.valueOf(Double.parseDouble(parts[multIndex + 1]));
				number3 = number1.multiply(number2);

				equation = equation.replace(numberToString(number1) + "×" + numberToString(number2), numberToString(number3));
			}

			parts = createArrayParts(equation);
		}
		
		while (equation.indexOf('+') != -1 || equation.indexOf('−') != -1) {
			int addIndex = findIndexOf(parts, "+");
			int subIndex = findIndexOf(parts, "−");
			BigDecimal number3;
			if(subIndex < 0 || (addIndex > 0 && addIndex < subIndex)){;
				BigDecimal number1 = BigDecimal.valueOf(Double.parseDouble(parts[addIndex - 1]));
				BigDecimal number2 = BigDecimal.valueOf(Double.parseDouble(parts[addIndex + 1]));
				number3 = number1.add(number2);
				equation = equation.replace(numberToString(number1) + "+" + numberToString(number2), numberToString(number3));

			}else{
				BigDecimal number1 = BigDecimal.valueOf(Double.parseDouble(parts[subIndex - 1]));
				BigDecimal number2 = BigDecimal.valueOf(Double.parseDouble(parts[subIndex + 1]));
				number3 = number1.subtract(number2);
				
				equation = equation.replace(numberToString(number1) + "−" + numberToString(number2), numberToString(number3));
				
			}	
			parts = createArrayParts(equation);

		}
		return expression.replace("("+ origEquation + ")", "" + equation);
	}

	public static int findIndexOf(String  parts[], String part) {
		if (part.length() == 1) {
			for (int i = 0; i < parts.length; i++) {
				if (parts[i].equals(part)) {
					return i;
				}
			} 
		}else{
			for (int i = 0; i < parts.length; i++) {
				if (parts[i].contains(part)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static String[] createArrayParts(String equation) {
		String part = "";
		String equationParts[] = new String[equation.replaceAll(" ", "").replaceAll(regex, "` `").split("`").length];

		int j = 0;
		for (int i = 0; i < equation.length(); i++){
			String singleChar = "" + equation.charAt(i);
			
			if(singleChar.matches(regex)){
				equationParts[j] = part;
				j++;
				equationParts[j] = singleChar;
				j++;
				part = "";
			}else{
				part += singleChar;
			}
		}
		equationParts[j] = part;
		return equationParts;
	}
	
	private static BigDecimal functionEval(BigDecimal number, String func) {
		double answer = 0;
		double angle = number.doubleValue();
		
		if(!isitRadians)
			angle = angle * PI / 180;
		
		
		try {
			switch (func) {
			case "sin":
				answer = Math.sin(angle);
				break;
			case "cos":
				answer = Math.cos(angle);
				break;
			case "tan":
				answer = Math.tan(angle);
				break;
			case "csc":
				answer = 1 / Math.sin(angle);
				break;
			case "sec":
				answer = 1 / Math.cos(angle);
				break;
			case "cot":
				answer = 1 / Math.tan(angle);
				break;
			default:
				break;
			}
			return BigDecimal.valueOf(answer).setScale(SCALE_SET, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			expression = "undef";
		}
		return BigDecimal.ZERO;
	}
	
	public static BigDecimal sqrt(BigDecimal num) {
		return BigDecimal.valueOf(Math.sqrt(num.doubleValue()));
	}
	
	public static String numberToString(BigDecimal number) {
		return number.setScale(SCALE_SET, BigDecimal.ROUND_FLOOR).stripTrailingZeros().toPlainString().trim();
	}
}