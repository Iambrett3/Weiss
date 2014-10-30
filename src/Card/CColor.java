/**
 * Color.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */

package Card;

import java.awt.Color;

/**
 * Color class.
 *
 */
public class CColor {
	
	/**
	 * Enumerated types for color.
	 */
	public enum TypeC { YELLOW, GREEN, RED, BLUE }
	
	private TypeC type;
	
	/**
	 * Constructor for Color class.
	 * @param type Type
	 */
	public CColor(TypeC type) {
		this.type = type;
	}
	
	/**
	 * Constructor for Color class. String as parameter.
	 * @param color Color
	 */
	public CColor(String color) {
		color = color.toLowerCase();
		if (color.equals("yellow")) {
			setType(TypeC.YELLOW);
		}
		else if (color.equals("green")) {
			setType(TypeC.GREEN);
		}
		else if (color.equals("red")) {
			setType(TypeC.RED);
		}
		else {
			setType(TypeC.BLUE);
		}
	}
	
	/**
	 * Setter method for type field.
	 * @param type Type
	 */
	public void setType(TypeC type) {
		this.type = type;
	}
	
	/**
	 * Getter method for type field.
	 * @return type
	 */
	public TypeC getType() {
		return type;
	}

	/**
	 * toString method for Color class.
	 */
	public String toString() {
		String str = "";
		if (this.type == TypeC.YELLOW)
			str += "Yellow";
		else if (this.type == TypeC.GREEN)
			str += "Green";
		else if (this.type == TypeC.RED)
			str += "Red";
		else 
			str += "Blue";
			
		return str;
	}
}

