package rahulShah.minMath.gameLogic;

public class Level {
	
	private int levelNum;		// difficulty of the level
	private int levelName;		// the category played at
	private static final String[] LEVEL_NAMES = {"Addition", "Subtraction", "Multiplication", "Division", "Exponents", "Add Fraction", "Subtract Fraction", "Trigonometry"};
	public static final int NUM_OF_LEVELS = 5;
	
	public Level(int num, int name){
		this.levelName = name;
		this.levelNum = num;
	}
	
	public Level(int n, String name) {
		this.levelName = findIndexOf(name);
		this.levelNum = n;
	}
	
	public static int numLevelNames() {
		return LEVEL_NAMES.length;
	}

	/**
	 * @return the levelNum
	 */
	public int getLevelNum() {
		return levelNum;
	}
	
	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return LEVEL_NAMES[levelName];
	}
	
	public int getLevelNameNumber() {
		return levelName;
	}
	
	private int findIndexOf(String name) {
		for(int i = 0; i < LEVEL_NAMES.length; i++)
			if(LEVEL_NAMES[i].equals(name))
				return i;
		return -1;
	}
}
