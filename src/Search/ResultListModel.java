package Search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.AbstractListModel;

import Card.Card;
import WeissSchwarz.Deck;

public class ResultListModel extends AbstractListModel {

	private ArrayList<Card> results;
	
	public ResultListModel() {
		results = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		results.add(card);
		fireIntervalAdded(this, results.size()-1, results.size()-1);
	}
	
	public ArrayList<Card> getResults() {
		return results;
	}
	
	public Iterator<Card> getIterator() {
		return results.iterator();
	}
	
	public void removeCard(Card card) {
		int index = results.indexOf(card);
		results.remove(card);
		fireIntervalRemoved(this, index, index);
	}
	
	public void removeCard(int index) {
		results.remove(index);
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
		return results.get(index);
	}

	@Override
	public int getSize() {
		return results.size();
	}

}
