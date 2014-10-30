package GUI.DeckReferenceComplex;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

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
    
    public DeckReferenceComplexPanel() {
        deckStats = new DeckStats();
        deckTable = (new TablePopulator().createTable());
        imageSelection = new ImageSelection();
        sorterBox = new SorterBox();
        init();
        controller = new DRCController(sorterBox, tablePane, deckTable, imageSelection, deckStats, cardStats);
    }
    
    public void init() {
    	add(sorterBox);
    	
        tablePane = new JScrollPane(deckTable);
        tablePane.setPreferredSize(new Dimension (400, 150));
        add(tablePane);
        
        cardStats = new JTextPane();
        cardStats.setPreferredSize(new Dimension(200, 400));
        cardStats.setEditable(false);
        JScrollPane cardStatsPane = new JScrollPane(cardStats);
        add(cardStatsPane);
        
        deckStats.setEditable(false);
        JScrollPane deckStatsPane = new JScrollPane(deckStats);
        add(deckStatsPane);
        
        
        add(imageSelection);
    }
    
    public void addCard(Card c) {
        controller.addCard(c);
    }
    
   public void removeCards() {
       controller.removeCardsInBuffer();
    }

}
