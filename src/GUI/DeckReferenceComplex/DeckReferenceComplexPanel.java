package GUI.DeckReferenceComplex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import WeissSchwarz.Deck;
import net.miginfocom.swing.MigLayout;
import Card.Card;
import GUI.BuilderGUI;
import GUI.DeckReferenceComplex.ImageLayoutView.ImageList;

public class DeckReferenceComplexPanel extends JPanel
{
    private DeckStats deckStats;
    private DeckTable deckTable;
    private JTextPane cardStats;
    private ImageSelection imageSelection;
    private DRCController controller;
    private JScrollPane tablePane;
    private JScrollPane imagePane;
    //private SorterBox sorterBox;
    private Deck deck;
    private ImageList imageList;
    private JPanel deckViewPanel;
    
    public DeckReferenceComplexPanel(BuilderGUI builder) {
        deckStats = new DeckStats(deck = new Deck());
        deckTable = (new TablePopulator().createTable());
        imageSelection = new ImageSelection();
        //sorterBox = new SorterBox();
        imageList = new ImageList();
        init();
        controller = new DRCController(builder, tablePane, deckTable, imageSelection, deckStats, cardStats, deck, imageList);
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
        
    	deckViewPanel = new JPanel(new BorderLayout());
        
        tablePane = new JScrollPane(deckTable);
        tablePane.setPreferredSize(new Dimension (500, 600));
        
        imagePane = new JScrollPane(imageList);
        tablePane.setPreferredSize(new Dimension (500, 600));
        
        deckViewPanel.add(tablePane);
        add(deckViewPanel, "wrap");
        


        deckStats.setPreferredSize(new Dimension(500, 100));
        add(deckStats);
    }
    
    public void switchToDeckTableView() {
    	deckViewPanel.remove(imagePane);
    	deckViewPanel.add(tablePane);
    	deckViewPanel.revalidate();
    	deckViewPanel.setPreferredSize(new Dimension(500, 600));
    }
    
    public void switchToImageLayoutView() {
    	deckViewPanel.remove(tablePane);
    	deckViewPanel.add(imagePane);
    	deckViewPanel.revalidate();
    	deckViewPanel.setPreferredSize(new Dimension(500, 600));
    }
    
    public void importDeck(Deck deck) {
    	controller.importDeck(deck);
    }
    
    public DRCController getController() {
    	return controller;
    }
    
    public void addCard(Card c) {
        controller.addCard(c);
    }
    
   public void removeCards() {
       controller.removeCardsInBuffer();
    }

public Deck getDeck() {
	return controller.getDeck();
}

public void sortBy(String sortFilter) {
	controller.getDeckTableModel().sortBy(sortFilter);
	imageList.sortBy(sortFilter);
	deck.sortBy(sortFilter);
	deckStats.updateStats(deck);
	
}

}
