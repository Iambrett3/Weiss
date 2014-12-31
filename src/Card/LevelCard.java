package Card;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * LevelCard class.
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
public abstract class LevelCard extends Card {
	private Integer level;
	private Integer cost;
	private Ability ability;
	
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
		this.level = new Integer(level);
		this.cost = new Integer(cost);
		setAttributes();
	}
	
	public LevelCard() {
		super();
	}
	
	public void setAttributes() {
	    ArrayList<String> tmp = new ArrayList<String>();
	    int cursor = 0;
	    for (String ability: getTextArray()) {
	        for (String attribute: Ability.getListOfAbilities()) {
	            //these if statements may seem redundant, but the first one is necessary to eliminate cards with no or little text
	            //that would make the substring() method fail.
	            if (ability.contains(attribute)) {
	         // this is to prevent from catching these strings later in the description
	         // the if statement is necessary to prevent outofbounds exceptions, like if this ability was the last
	         // ability and the text of that ability was shorter than the longest ability's length.
	            	String stringToCheck;
	            	if (ability.length() < 4 + Ability.getLongestAbilityLength()) {
	            		stringToCheck = ability.substring(4);
	            	}
	            	else {
	            		stringToCheck = ability.substring(4, Ability.getLongestAbilityLength());
	            	}
	                if (stringToCheck.contains(attribute)) 
	                    tmp.add(attribute);
	            }
	        }
	    }
	    if (tmp.size() < 1)
	        tmp.add("None");
	    ability = new Ability(tmp);
	}
	
	/**
	 * Getter method for level field.
	 * @return Level
	 */
	public Integer getLevel() {
		return level;
	}
	
	public Ability getAbility() {
		return ability;
	}
	
	/**
	 * Getter method for cost field.
	 * @return Cost
	 */
	public Integer getCost() {
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
				     + "\nCost: " + cost
					    + "\nAbilities: " + ability;
		return str;
	}
}
