/**
 * Climax.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
package Card;
import java.awt.image.BufferedImage;



/**
 * Climax class for climax cards.
 */
public class Climax extends Card {

	/**
	 * @param name Name
	 * @param jpnName jpnName
	 * @param number Number
	 * @param rarity Rarity
	 * @param trigger Trigger
	 * @param color Color
	 * @param flavor Flavor
	 * @param text Text
	 * @param pack Pack
	 * @param image Image
	 */ 
	public Climax(String name, String jpnName, String number, String rarity, Trigger trigger, CColor color,
			String flavor, String[] text, String pack, BufferedImage image) {
		super(name, jpnName, number, rarity, trigger, color, flavor, text, pack, image);
	}
	
	/**
	 * no-arg Constructor for Climax class.
	 */
	public Climax() {
		super();
	}

	/**
	 * produces a description of the card.
	 * @return description of the card.
	 */
	public String getDescription() {
		String str = "Card Type: Climax\n"
				     + super.getDescription();
		return str;
	}

}
