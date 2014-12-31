package GUI.DeckReferenceComplex.ImageLayoutView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;

import Card.Card;
import Card.Climax;
import Card.Event;
import GUI.DeckReferenceComplex.DeckTable;

import javax.swing.AbstractListModel;

import WeissSchwarz.Deck;

public class ImageListModel extends AbstractListModel {

	private ArrayList<Card> images;
	
	public ImageListModel() {
		images = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		images.add(card);
		fireIntervalAdded(this, images.size()-1, images.size()-1);
	}
	
    public void insertCard(int pos, Card c) {
    	images.add(pos, c);
    	fireIntervalAdded(this, pos, pos);
    }
	
	public ArrayList<Card> getImages() {
		return images;
	}
	
	public Iterator<Card> getIterator() {
		return images.iterator();
	}
	
	public void insertCards(Card[] cards, int to) {
		for (int i = 0; i < cards.length; i++) {
    		images.add(to, cards[i]);
    	}
    	fireIntervalAdded(this, to, to + cards.length);
	}
	
	public void removeCard(Card card) {
		int index = images.indexOf(card);
		images.remove(card);
		fireIntervalRemoved(this, index, index);
	}
	
	public void removeCard(int index) {
		images.remove(index);
		fireIntervalRemoved(this, index, index);
	}
	
	public void removeCards(ArrayList<Integer> cards) {
		Collections.sort(cards, Collections.reverseOrder());
		
		for(int i: cards) {
		removeCard(i);
		}
	}
	
	@Override
	public Object getElementAt(int index) {
		return images.get(index);
	}

	@Override
	public int getSize() {
		return images.size();
	}
	
	public void sortBy(String key) {
		switch (key) {
		case "Name" : sortByName();
		fireContentsChanged(this, 0, images.size()-1);
		break;
		case "Number" : sortByNumber();
		fireContentsChanged(this, 0, images.size()-1);
		break;
		case "Color" : sortByColor();
		fireContentsChanged(this, 0, images.size()-1);
		break;
		case "Trigger" : sortByTrigger();
		fireContentsChanged(this, 0, images.size()-1);
		break;
		case "Level" : sortByLevel();
		fireContentsChanged(this, 0, images.size()-1);
		break;
		case "Soul" : sortBySoul();
		fireContentsChanged(this, 0, images.size()-1);
		break;
		case "Cost" : sortByCost();
		fireContentsChanged(this, 0, images.size()-1);
		break;
		default:
			break;
		}
	}
	
	
	public void sortByNumber() {
		Collections.sort(
				   images,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getNumber().compareTo(b.getNumber());
				     }
				   });
	}
	
	public void sortByColor() {
		Collections.sort(
				   images,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getColor().toString().compareTo(b.getColor().toString());
				     }
				   });
	}
	
	public void sortByTrigger() {
		Collections.sort(
				   images,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getTrigger().toString().compareTo(b.getTrigger().toString());
				     }
				   });
	}
	
	public void sortByLevel() {
		Collections.sort(
				   images,
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
				   images,
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
				   images,
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
				   images,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getName().compareTo(b.getName());
				     }
				   });
	}

}
