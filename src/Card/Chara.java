/**
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
package Card;

import java.awt.image.BufferedImage;

/**
 * Character class for character cards.
 */
public class Chara extends LevelCard {
private int power;
private int soul;
private Trait trait;
	/**
	 * Constructor for Character class.
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
	 * @param power Power
	 * @param soul Soul
	 * @param trait Trait
	 * @param image Image
	 */
	public Chara(String name, String jpnName, String number, String rarity, Trigger trigger, CColor color,
			String flavor, String[] text, String pack, Integer level, int cost,
			int power, int soul, Trait trait, BufferedImage image) {
		super(name, jpnName, number, rarity, trigger, color, flavor, text, pack, level, cost, image);
		this.power = power;
		this.soul = soul;
		this.trait = trait;
	}
	
	/**
	 * no-arg Constructor for Character class.
	 */
	public Chara() {
		super();
		trait = new Trait();
	}
	
	/**
	 * Getter method for power field.
	 * @return Power
	 */
	public int getPower() {
		return power;
	}
	
	/**
	 * Getter method for soul field.
	 * @return Soul
	 */
	public int getSoul() {
		return soul;
	}
	
	/**
	 * Getter method for characteristic field.
	 * @return Characteristic
	 */
	public Trait getTrait() {
		return trait;
	}
	
	/**
	 * Setter method for power field.
	 * @param power Power
	 */
	public void setPower(int power) {
		this.power = power;
	}
	
	/**
	 * Setter method for soul field.
	 * @param soul Soul
	 */
	public void setSoul(int soul) {
		this.soul = soul;
	}
	
	/**
	 * Setter method for characteristic field.
	 * @param characteristic Characteristic
	 */
	public void setTrait(Trait trait) {
		this.trait = trait;
	}
	
	/**
	 * produces a description of the card.
	 * @return description of the card.
	 */
	public String getDescription() {
		String str = "Card Type: Character\n"
				     + super.getDescription()
				     + "\nPower: " + power
				     + "\nSoul: " + soul
				     + "\nTrait(s): " + trait;
		return str;
	}

}
