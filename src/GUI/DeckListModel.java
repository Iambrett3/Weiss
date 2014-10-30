package GUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import Card.Card;

import javax.swing.AbstractListModel;

import WeissSchwarz.Deck;

public class DeckListModel extends AbstractListModel {

	private final Deck deck = new Deck();
	
	public void addCard(Card card) {
		deck.add(card);
		fireIntervalAdded(this, deck.size()-1, deck.size()-1);
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public Iterator<Card> getIterator() {
		return deck.iterator();
	}
	
	public void removeCard(Card card) {
		int index = deck.indexOf(card);
		deck.remove(card);
		fireIntervalRemoved(this, index, index);
	}
	
	public void removeCard(int index) {
		deck.remove(index);
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
		return deck.get(index);
	}

	@Override
	public int getSize() {
		return deck.size();
	}

}
