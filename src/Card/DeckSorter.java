package Card;

import java.util.Collections;
import java.util.Comparator;

import WeissSchwarz.Deck;

public class DeckSorter {
	
	public static void sortByName(Deck deck) {
		Collections.sort(
				   deck,
				   new Comparator<Card>() {
				     public int compare(Card a, Card b) {
				       return a.getName().compareTo(b.getName());
				     }
				   });
	}
}
