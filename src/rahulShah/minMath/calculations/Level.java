package rahulShah.minMath.calculations;

public class Level {
	
	private int levelNum;		// difficulty of the level
	private String levelName;	// the category played at
	
	public Level(int num, String name){
		this.setLevelName(name);
		this.setLevelNum(num);
	}
	
	/**
	 * @return the levelNum
	 */
	public int getLevelNum() {
		return levelNum;
	}
	/**
	 * @param levelNum the levelNum to set
	 */
	public void setLevelNum(int levelNum) {
		this.levelNum = levelNum;
	}
	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}
	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}
