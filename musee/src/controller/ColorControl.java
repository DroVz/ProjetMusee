package controller;
import java.util.Random;

public class ColorControl {
	
	private int lengthOfHexCode = 6;
	private String[] character = {"a","b","c","d","e","f","1","2","3","4","5","6","7","8","9","0"};

	private static ColorControl instance = null;
	
	public static ColorControl getInstance() {
		if (instance == null) {
			instance = new ColorControl();
		}
		return instance;
	}
	
	private ColorControl() {	
	}
	
	public String getHexColor() {
		String hexCode = "#";
		while( hexCode.length() <= this.lengthOfHexCode  ) {
			hexCode += this.getCharacter();
		}
		return hexCode;
	}
	
	private String getCharacter() {
		return this.character[new Random().nextInt(this.character.length)];
	}
	

}
