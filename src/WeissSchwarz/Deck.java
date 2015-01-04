package WeissSchwarz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import Card.Card;
import Card.Climax;
import Card.Event;


public class Deck extends ArrayList<Card> {

/**
 * no-arg constructor for Deck class.
 */
	private int deckSize;
	private boolean isSaved;
	
	public Deck () {
		deckSize = 0;
		isSaved = false;
	}
	
	//returns whether the deck has been altered since last save.
	public boolean isSaved() {
		return isSaved;
	}
	
	public void setIsSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	
	public int getDeckSize() {
		return deckSize;
	}
	
	public void setDeckSize(int size) {
		deckSize += size;
	}
	
	public boolean add(Card c) {
		deckSize++;
		return super.add(c);
	}
	
	public Card remove(int row) {
		deckSize--;
		return super.remove(row);
	}
	
	public String toString() {
		String str = "";
		for (Card c: this) {
			str+= c.toString() +"\n";
		}
		return str;
	}
	
	
    public boolean isInDeck(Card c) {
    	for (Card dc: this) {
    		if (c.equals(dc)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * returns integer value of location of card in deck.
     * @param c
     * @return
     */
    public int deckLocation(Card c) {
    	for (int i = 0; i < size(); i++) {
    		if (get(i).equals(c)) {
    			return i;
    		}
    	}
    	return -1; //not found;
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
		case "Soul" : sortBySoul();
		break;
		case "Cost" : sortByCost();
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
				    	 if (a instanceof Climax) { //climaxes should sort to be after all level cards
				    		 if (b instanceof Climax) {
				    			 return 0;
				    		 }
				    		 return 1;
				    	 }
				    	 if (b instanceof Climax) {
				    		 return -1;
				    	 }
				       return Integer.compare(a.getLevel(), b.getLevel());
				     }
				   });
	}
	
	public void sortBySoul() {
		Collections.sort(
				   this,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				    	 if (a instanceof Climax || a instanceof Event) { //climaxes should sort to be after all level cards
				    		 if (b instanceof Climax || b instanceof Event) {
				    			 return 0;
				    		 }
				    		 return 1;
				    	 }
				    	 if (b instanceof Climax || b instanceof Event) {
				    		 return -1;
				    	 }
				       return Integer.compare(a.getSoul(), b.getSoul());
				     }
				   });
	}
	
	public void sortByCost() {
		Collections.sort(
				   this,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				    	 if (a instanceof Climax) { //climaxes should sort to be after all level cards
				    		 if (b instanceof Climax) {
				    			 return 0;
				    		 }
				    		 return 1;
				    	 }
				    	 if (b instanceof Climax) {
				    		 return -1;
				    	 }
				       return Integer.compare(a.getCost(), b.getCost());
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

	public void incrementNumOfCard(int row, int i) {
		get(row).incrementNumOfCard(i);
		isSaved = false;
	}

    public int getNumberOfCardsInDeck()
    {
        int sum = 0;
        for (Card c: this) {
            sum += c.getNumOfCard();
        }
        return sum;
    }
}
