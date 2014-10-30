package Card;
import java.awt.image.BufferedImage;


/**
 * LevelCard class.
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
public abstract class LevelCard extends Card {
	private int level;
	private int cost;
	
	/**
	 * LevelCard Constructor
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
	 * @param image Image
	 */
	public LevelCard(String name, String jpnName, String number, String rarity, Trigger trigger, CColor color,
			String flavor, String[] text, String pack, int level, int cost, BufferedImage image) {
		super(name, jpnName, number, rarity, trigger, color, flavor, text, pack, image);
		this.level = level;
		this.cost = cost;
	}
	
	public LevelCard() {
		super();
	}
	
	/**
	 * Getter method for level field.
	 * @return Level
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Getter method for cost field.
	 * @return Cost
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Setter method for level field.
	 * @param level Level
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * Setter method for cost field.
	 * @param cost Cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * produces a description of the card.
	 * @return description of the card.
	 */
	public String getDescription() {
		String str = super.getDescription() 
				     + "\nLevel: " + level
				     + "\nCost: " + cost;
		return str;
	}
}
