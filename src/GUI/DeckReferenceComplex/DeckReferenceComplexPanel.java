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
import javax.swing.JSplitPane;
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
    private Deck deck;
    private ImageList imageList;
    private JPanel deckViewPanel;
    private JSplitPane splitPane;
    
    public DeckReferenceComplexPanel(BuilderGUI builder) {
        deckStats = new DeckStats(deck = new Deck());
        deckTable = (new TablePopulator().createTable());
        imageSelection = new ImageSelection();
        imageList = new ImageList();
        init();
        controller = new DRCController(builder, tablePane, deckTable, imageSelection, deckStats, cardStats, deck, imageList);
    }
    
    public void init() {
    	setLayout(new BorderLayout());
        
        tablePane = new JScrollPane(deckTable);
        
        imagePane = new JScrollPane(imageList);

        JScrollPane deckStatsPane = new JScrollPane(deckStats);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                tablePane, deckStatsPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(510);
        
        add(splitPane);
    }
    
    public void switchToDeckTableView() {
    	splitPane.remove(imagePane);
    	splitPane.add(tablePane);
    	int previousDividerLocation = splitPane.getDividerLocation();
    	splitPane.revalidate();
    	splitPane.setDividerLocation(previousDividerLocation);
    	
    }
    
    public void switchToImageLayoutView() {
    	splitPane.remove(tablePane);
    	splitPane.add(imagePane);
    	int previousDividerLocation = splitPane.getDividerLocation();
    	splitPane.revalidate();
    	splitPane.setDividerLocation(previousDividerLocation);
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
