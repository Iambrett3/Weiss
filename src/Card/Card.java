/**
 * Card.java
 * 
 * @author Brett Reuben, Jeremy Kleiman
 * @version 0.1
 */
package Card;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import GUI.DeckReferenceComplex.DeckTable;

/**
 * Card class.
 */
public abstract class Card implements Serializable{

private String name;
private String jpnName;
private String number;
private String rarity;
private Trigger trigger;
private CColor color;
private String flavor;
private String[] text;
private String pack;
private String imagePath;
private Integer numOfCard; //the number this card in existence. (most likely in the context of a deck)
private String ID;

private final int MAX_NUM_ATTRIBUTES = 4;

/**
 * Card constructor
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
    public Card (String name, String jpnName, String number, String rarity, Trigger trigger,
		     CColor color, String flavor, String[] text, String pack, BufferedImage image) {
	this.name = name;
	this.jpnName = jpnName;
	this.number = number;
	this.rarity = rarity;
	this.trigger = trigger;
	this.color = color;
	this.flavor = flavor;
	this.text = text;
	this.pack = pack;
	//this.image = image;
	numOfCard = 1;  
}

/**
 * no-arg constructor for Card class.
 */
public Card() {
	numOfCard = 1;
}



public Integer getNumOfCard() {
	return numOfCard;
}

public void setNumOfCard(int num) {
	numOfCard = num;
}

public void incrementNumOfCard(int num) {
	numOfCard += num;
}

public void setID() {
    ID = number.split("/")[0];
}

public String getID() {
    return ID;
}

/**
 * Getter method for name field.
 * @return name
 */
public String getName() {
	return name;
}

/**
 * Overridden by LevelCard. Returns level of card.
 * @return 
 */
public Integer getLevel() {return new Integer(-1);}

public Integer getCost() {return new Integer(-1);}

public Integer getPower() {return new Integer(-1);}

public Integer getSoul() {return new Integer(-1);}

/**
 * Getter method for jpnName field.
 * @return jpnName
 */
public String getJpnName() {
	return jpnName;
}

/**
 * Getter method for number field.
 * @return number
 */
public String getNumber() {
	return number;
}

/**
 * Getter method for rarity field.
 * @return rarity
 */
public String getRarity() {
	return rarity;
}

/**
 * Getter method for trigger field.
 * @return trigger
 */
public Trigger getTrigger() {
	return trigger;
}

/**
 * Getter method for color field.
 * @return color
 */
public CColor getColor() {
	return color;
}

/**
 * Getter method for flavor field.
 * @return flavor
 */
public String getFlavor() {
	return flavor;
}

/**
 * Getter method for text field.
 * @return text
 */
public String getText() {
    String str = "";
    for (String textLine: text) {
        str += textLine + "\n";
    }
	return str;
}

public String[] getTextArray() {
	return text;
}

/**
 * Getter method for pack field.
 * @return pack
 */
public String getPack() {
	return pack;
}

public String getImagePath() {
	return imagePath;
}

public void setImagePath(String imagePath) {
	this.imagePath = imagePath;
}

/**
 * Setter method for name field.
 * @param name Name
 */
public void setName(String name) {
	this.name = name;
}

/**
 * Setter method for jpnName field.
 * @param jpnName jpnName
 */
public void setJpnName(String jpnName) {
	this.jpnName = jpnName;
}

/**
 * Setter method for number field.
 * @param number Number
 */
public void setNumber(String number) {
	this.number = number;
}

/**
 * Setter method for rarity field.
 * @param rarity Rarity
 */
public void setRarity(String rarity) {
	this.rarity = rarity;
}

/**
 * Setter method for trigger field.
 * @param trigger Trigger
 */
public void setTrigger(Trigger trigger) {
	this.trigger = trigger;
}

/**
 * Setter method for color field.
 * @param color Color
 */
public void setColor(CColor color) {
	this.color = color;
}

/**
 * Setter method for flavor field.
 * @param flavor Flavor
 */
public void setFlavor(String flavor) {
	this.flavor = flavor;
}

/**
 * Setter method for text field.
 * @param text Text
 */
public void setText(String[] text) {
	this.text = text;
}

/**
 * Setter method for level field.
 * @param level level
 */
public void setLevel(int level) {}

/**
 * Setter method for cost field.
 * @param cost cost
 */
public void setCost(int cost) {}

/**
 * Setter method for pack field.
 * @param pack Pack
 */
public void setPack(String pack) {
	this.pack = pack;
}

public boolean equals(Card otherCard) {
	return (number.equals(otherCard.getNumber()));
}

/**
 * produces a description of the card.
 * @return description of the card.
 */
public String getDescription() {
	String str = "Name: " + name
			     + "\nNumber: " + number
			     + "\nTriggers: " + trigger
			     + "\nColor: " + color
			     + "\nFlavor: " + flavor
			     + "\nText: "
			     + getText();
			str += "Pack: " + pack;
	return str;
}
	
//similar to getDescription(), but formats the text so it wraps in the tool tip
public String getToolTipDescription() {
    String str = "Name: " + name
                 + "\nNumber: " + number
                 + "\nTriggers: " + trigger
                 + "\nColor: " + color
                 + "\nFlavor: " + prepareTextForTip(flavor)
                 + "\nText: " + prepareTextForTip(getText())
                 + "Pack: " + pack;
    return str;
}

public String prepareTextForTip(String s) {
    String description = "";
    int i = 1;
    boolean breakToNewLine = false;
    for (char c: s.toCharArray()) {
        if ((i % 50 == 0)) {
            if (breakToNewLine) {
                if (c != '\n') {
                    description += "\n";
                }
                i = 1;
                breakToNewLine = false;

            }
            description += c;
            if (c == ' ') {
                breakToNewLine = true;
            }
        }
        else {
            description += c;
            if (c == '\n') {
                i = 0;
            }
            i++;
        }
    }
    return description;
}


	/*
	 * Static method for representing a Card object as an Array. Used for controlling the DeckTable.
	 * TODO: fill out card properties
	 */
	public static Vector vectorizeCard(Card c) {
		Object[] cardData = {c.numOfCard, c.getNumber(), c, c.getTrigger(),
				c.getLevel(), c.getSoul(), c.getCost()};
		Vector cardVector = new Vector(Arrays.asList(cardData));
		return cardVector;
	}
	
	public static Card vectorToCard(Vector v) {
		if (!(v.get(DeckTable.getNameColumnNumber()) instanceof Card)) {
			return null;
		}
		return (Card) v.get(DeckTable.getNameColumnNumber());
		
	}

    public String toString() {
        return name + " - " + number;
    }
}
