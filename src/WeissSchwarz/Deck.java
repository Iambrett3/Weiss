package WeissSchwarz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import Card.Card;


public class Deck extends ArrayList<Card> {

/**
 * no-arg constructor for Deck class.
 */
	public Deck () {	
	}
	
	public void insertCards(Card[] cards, int pos) {
		for (int i = 0; i < cards.length; i++) {
			add(pos, cards[i]);
		}
	}
	
	public void sortBy(String key) {
		switch (key) {
		case "Name" : sortByName();
		break;
		case "Number" : sortByNumber();
		break;
		case "Color" : sortByColor();
		break;
		case "Trigger" : sortByTrigger();
		break;
		case "Level" : sortByLevel();
		break;
		default:
			break;
		}
	}
	
	public void sortByNumber() {
		Collections.sort(
				   this,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getNumber().compareTo(b.getNumber());
				     }
				   });
	}
	
	public void sortByColor() {
		Collections.sort(
				   this,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getColor().toString().compareTo(b.getColor().toString());
				     }
				   });
	}
	
	public void sortByTrigger() {
		Collections.sort(
				   this,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getTrigger().toString().compareTo(b.getTrigger().toString());
				     }
				   });
	}
	
	public void sortByLevel() {
		Collections.sort(
				   this,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return Integer.compare(a.getLevel(), b.getLevel());
				     }
				   });
	}
	
	public void sortByName() {
		Collections.sort(
				   this,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getName().compareTo(b.getName());
				     }
				   });
	}
}
