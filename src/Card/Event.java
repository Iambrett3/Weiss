/**
 * Even.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
package Card;

import java.awt.image.BufferedImage;

/**
 * Event class for event cards.
 */
public class Event extends LevelCard {
	
	/**
	 * Constructor for Event class.
	 * 
	 * @param name Name
	 * @param jpnName jpnName
	 * @param number Number
	 * @param rarity Rarity
	 * @param trigger Trigger
	 * @param color Color
	 * @param flavor Flavor 
	 * @param text Text
	 * @param pack Pack
	 * @param level Level
	 * @param cost Cost
	 */
	public Event(String name, String jpnName, String number, String rarity, Trigger trigger, CColor color,
			String flavor, String[] text, String pack, int level, int cost, BufferedImage image) {
		super(name, jpnName, number, rarity, trigger, color, flavor, text, pack, level, cost, image);
	}

	public Event() { 
		super(); 
		}
	
	/**
	 * produces a description of the card.
	 * @return description of the card.
	 */
	public String getDescription() {
		String str = "Card Type: Event\n"
				     + super.getDescription();
		return str;
	}
}
