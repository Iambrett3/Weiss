package GUI.DeckReferenceComplex;

import javax.swing.JTextPane;

import Card.Card;
import Card.CColor;
import Card.CColor.TypeC;
import Card.LevelCard;
import Card.Trigger;
import Card.Trigger.Type;
import WeissSchwarz.Deck;

public class DeckStats extends JTextPane{
    /*
     * No-Arg constructor. Calls super constructor.
     */
    public DeckStats() {
        super();
    }
    
	public static String calculateTriggerRatio(Deck deck) {
		int soul = 0;
		int doubleSoul = 0;
		int bounce = 0;
		int stock = 0;
		int salvage = 0;
		int draw = 0;
		int shot = 0;
		int treasure = 0;
		for (Card c: deck) {
			for (Trigger.Type type: c.getTrigger().getTriggers()) {
					if (type == Type.SOUL)
						soul++;
					if (type == Type.DOUBLE_SOUL)
						doubleSoul++;
					if (type == Type.RETURN)
						bounce++;
					if (type == Type.POOL)
						stock++;
					if (type == Type.COME_BACK)
						salvage++;
					if (type == Type.DRAW)
						draw++;
					if (type == Type.SHOT)
						shot++;
					if (type == Type.TREASURE)
						treasure++;
			}
		}
		String str = "Trigger Stats:"
				 + "\nSoul: " + soul
			     + "\n2 Soul: " + doubleSoul
			     + "\nBounce: " + bounce
			     + "\nStock: " + stock
			     + "\nSalvage: " + salvage
			     + "\nDraw: " + draw
			     + "\nShot: " + shot
			     + "\nTreasure: " + treasure;
		return str;
	}
	
	public static String calculateColorRatio(Deck deck) {
		int yellow = 0;
		int red = 0;
		int blue = 0;
		int green = 0;
		for (Card c: deck) {
			if (c.getColor().getType() == TypeC.YELLOW) 
				yellow++;
			if (c.getColor().getType() == TypeC.RED)
				red++;
			if (c.getColor().getType() == TypeC.BLUE)
				blue++;
			if (c.getColor().getType() == TypeC.GREEN)
				green++;
		}
		String str = "Color Stats:"
				+ "\nYellow: " + yellow
				+ "\nRed: " + red
				+ "\nBlue: " + blue
				+ "\nGreen: " + green;
		return str;
	}
	
	public static String calculateLevelRatio(Deck deck) {
		int zero = 0;
		int one = 0;
		int two = 0;
		int three = 0;
		for (Card c: deck) {
			if (c.getLevel()  == 0) 
				zero++;
			if (c.getLevel() == 1)
				one++;
			if (c.getLevel() == 2)
				two++;
			if (c.getLevel() == 3)
				three++;
		}
		String str = "Level Stats:"
				+ "\nLevel 0: " + zero
				+  "\nLevel 1: " + one
				+ "\nLevel 2: " + two
				+ "\nLevel 3: " + three;
		
		return str;
	}
	
	public static String calculateDeckStats(Deck deck) {
		return calculateTriggerRatio(deck) + "\n" + calculateColorRatio(deck) + "\n" + calculateLevelRatio(deck);
	}
	
	public void updateStats(Deck deck) {
	    setText(calculateDeckStats(deck));
	}
}
