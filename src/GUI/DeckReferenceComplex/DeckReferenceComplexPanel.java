package GUI.DeckReferenceComplex;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import WeissSchwarz.Deck;
import net.miginfocom.swing.MigLayout;
import Card.Card;

public class DeckReferenceComplexPanel extends JPanel
{
    private DeckStats deckStats;
    private JTable deckTable;
    private JTextPane cardStats;
    private ImageSelection imageSelection;
    private DRCController controller;
    private JScrollPane tablePane;
    private SorterBox sorterBox;
    private Deck deck;
    
    public DeckReferenceComplexPanel() {
        deckStats = new DeckStats(deck = new Deck());
        deckTable = (new TablePopulator().createTable());
        imageSelection = new ImageSelection();
        sorterBox = new SorterBox();
        init();
        controller = new DRCController(sorterBox, tablePane, deckTable, imageSelection, deckStats, cardStats, deck);
    }
    
    public void init() {
    	setLayout(new MigLayout());
    	//code from when this was still displayed in window:
        //add(imageSelection); 
        
        //cardStats = new JTextPane();
        //cardStats.setPreferredSize(new Dimension(200, 365));
        //cardStats.setEditable(false);
        //JScrollPane cardStatsPane = new JScrollPane(cardStats);
        //add(cardStatsPane);        
        
        
        
        tablePane = new JScrollPane(deckTable);
        tablePane.setPreferredSize(new Dimension (1000, 600));
        add(tablePane, "wrap");
   

        deckStats.setPreferredSize(new Dimension(1000, 100));
        add(deckStats);
        
        add(sorterBox);  
    }
    
    public void addCard(Card c) {
        controller.addCard(c);
    }
    
   public void removeCards() {
       controller.removeCardsInBuffer();
    }

public ArrayList<Card> getDeck() {
	return controller.getDeck();
}

}
