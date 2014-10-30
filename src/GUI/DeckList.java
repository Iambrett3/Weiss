package GUI;



import java.awt.Dimension;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import WeissSchwarz.Deck;
import Card.*;
import GUI.DeckReferenceComplex.DeckStats;
import GUI.DeckReferenceComplex.ImageSelection;

public class DeckList extends JPanel {
	
	DeckListModel deck;
	JList deckView;
	DefaultTableModel model;
	ArrayList<Integer> cardBuffer;
	boolean buttonClicked;
	ImageSelection imageSelection;
	JTextPane deckStats;
	JTextPane cardStats;
	
	public DeckList() {
		cardBuffer = new ArrayList<Integer>();
		buttonClicked = false;
		
		deck = new DeckListModel();
		deckView = new JList(deck);
		
		imageSelection = new ImageSelection();
		add(imageSelection);
		
		deckStats = new DeckStats();
		
		//TODO: make this dynamic
		deckStats.setText(DeckStats.calculateDeckStats(deck.getDeck()));
		deckStats.setEditable(false);
		JScrollPane deckStatsPane = new JScrollPane(deckStats);
		add(deckStatsPane);
		
		cardStats = new JTextPane();
		cardStats.setPreferredSize(new Dimension(200, 400));
		cardStats.setEditable(false);
		JScrollPane cardStatsPane = new JScrollPane(cardStats);
		add(cardStatsPane);

		ListSelectionModel listSelectionModel = deckView.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SelectionHandler());
		JScrollPane tablePane = new JScrollPane(deckView);
		add(tablePane);
		}
		
	public void addCard(Card card) {
		deck.addCard(card);
	}
	
	public void removeCard(Card card) {
		deck.removeCard(card);
	}
	
	public void removeCard(int i) {
		deck.removeCard(i);
	}
	
	public void removeCards(ArrayList<Integer> cards) {
		deck.removeCards(cards);
	}
	
	public ArrayList<Integer> getCardBuffer() {
		return cardBuffer;
	}
	
	public void setCardBuffer(ArrayList<Integer> cardBuffer) {
		this.cardBuffer = cardBuffer;
	}
		
	public void resetCardBuffer() {
		cardBuffer.clear();
	}
	
	public DeckListModel getDeck() {
		return deck;
	}
	
	public Card getSelectedCard() {
		return deck.getDeck().get(deckView.getSelectedIndex());
	}
	
	public JTextPane getDeckStats() {
		return deckStats;
	}
	
	/**
	 * fires a bunch of exceptions - can only remove one at a time.
	 * @author Brett
	 *
	 */
	private class SelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			resetCardBuffer();
			if (!e.getValueIsAdjusting()) {
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
				for (int i = minIndex; i <= maxIndex; i++) {
					if (lsm.isSelectedIndex(i)) {
						cardBuffer.add(i);	
					}
				}
			if (deckView.getSelectedIndex() != -1) {
				imageSelection.setCurrentImage(deck.getDeck().get(deckView.getSelectedIndex()).getImage());
				cardStats.setText(deck.getDeck().get(deckView.getSelectedIndex()).getDescription());
			}
			else {
				imageSelection.resetCurrentImage();
				cardStats.setText("");
			}
			}
		}
	}
}

