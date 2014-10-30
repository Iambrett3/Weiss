package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DropMode;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Card.Card;
import Card.DeckSorter;
import WeissSchwarz.Deck;

/**
 * For data manipulation of DeckReferenceComplexPanel
 * @author Brett
 *
 */
public class DRCController
{
    private JTable deckTable; 
    private ImageSelection imageSelection;  //references to the table, imageSelection, and deckStats
    private DeckStats deckStats;
    private ArrayList<Integer> cardBuffer;
    private Deck deck;
    private DeckTableModel tableModel;
    private JTextPane cardStats;
    private TableRowSorter sorter;
    private JScrollPane tablePane;
    private SorterBox sorterBox;
    
    public DRCController(SorterBox sorterBox, JScrollPane tablePane, JTable deckTable, 
    		ImageSelection imageSelection, DeckStats deckStats, JTextPane cardStats) {
    	this.sorterBox = sorterBox;
        this.deckTable = deckTable;
        this.imageSelection = imageSelection;
        this.deckStats = deckStats;
        this.cardStats = cardStats;
        this.tablePane = tablePane;
        tableModel = (DeckTableModel) deckTable.getModel();
        cardBuffer = new ArrayList<Integer>();
        deck = new Deck();
        init();
    }
    
    /**
     * initializes members
     */
    public void init() {
        initTable();
        initImageSelection();
        initDeckStats();
        initCardStats();
        sorterBox.addActionListener(new SorterHandler());
    }
    
    public JTable getDeckTable() {
    	return deckTable;
    }
    
    public void initTable() {
        tableModel.addTableModelListener(deckTable);
        deckTable.setRowSelectionAllowed(true);
        deckTable.getTableHeader().setReorderingAllowed(false);
        deckTable.setRowSorter(sorter = new TableRowSorter(tableModel));
        sorter.setSortsOnUpdates(true); //this doesn't work
        
        deckTable.setDragEnabled(true);
        deckTable.setDropMode(DropMode.INSERT_ROWS);
        deckTable.setTransferHandler(new DeckTableTransferHandler(this));
        deckTable.setFillsViewportHeight(true);
        
        deckTable.setDefaultRenderer(Color.class, new TableColorRenderer(true));
        
        ListSelectionModel cellSelectionModel = deckTable.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                cardBuffer.clear();
                for (int row: deckTable.getSelectedRows()) {
                	if (row != -1) {
                		cardBuffer.add(row);
                	}
                }
                if (deckTable.getSelectedRow() != -1) {
                    imageSelection.setCurrentImage(deck.get(deckTable.getSelectedRow()).getImage());
                    cardStats.setText(deck.get(deckTable.getSelectedRow()).getDescription());
                }
                else {
                    imageSelection.resetCurrentImage();
                    cardStats.setText("");
                }    
            }
        });
    }
    
    public void initImageSelection() {
    }
    
    public void initCardStats() {
        
    }
    
    public void initDeckStats() {
        deckStats.setText(DeckStats.calculateDeckStats(deck));
    }
    
    public void addCard(Card c) {
        deck.add(c);
        tableModel.addCard(c);
        sorter.modelStructureChanged();
        setRowColor(c); //TODO: I am calling this just to change the Color column from CColor object to Color object. this is stupid fix this
        System.out.println(((Card) tableModel.getValueAt(tableModel.getRowCount() -1, 0)).getDescription());
        deckStats.updateStats(deck);
    }
    
    public void insertCard(int pos, Card c) {
    	deck.add(pos, c);
    	tableModel.insertCard(pos, c);
    	sorter.modelStructureChanged();
    	setRowColor(c);
    	deckStats.updateStats(deck);
    }
    
    public void reorder(Integer[] from, int to) {
    	Vector[] fromCopy = new Vector[from.length];
    	Card[] fromCards = new Card[from.length];
    	int j = 0;
    	//this for loop reverses the order of the cards so they can be removed correctly
    	for (int i = from.length - 1; i > -1; i--, j++) { 
    		fromCopy[j] = tableModel.getRow(from[i]);
    		fromCards[j] = Card.vectorToCard(tableModel.getRow(from[i]));
    		if (from[i] < to) //updates the drop location if the row to be removed is less than the destination.
    			to--;
    		tableModel.removeCard(from[i]);
    		deck.remove(from[i].intValue());
    	}
    	tableModel.insertCards(fromCopy, to);
    	deck.insertCards(fromCards, to);
    }
    
    public void reorder(int from, int to) {
    	if (to == deck.size()) {
    		deck.add(deck.get(from));
    		deck.remove(from);
    	}
    	else if (to > from) {
    		Card fromCard = (Card) deck.get(from);
    		deck.add(to, fromCard);
    		deck.remove(from);
    	}
    	else if (from > to) {
    		Card fromCard = (Card) deck.get(from);
    		deck.remove(from);
    		deck.add(to, fromCard);
    	}
    	sorter.modelStructureChanged();
    	tableModel.reorder(from, to);
    }
    
    /**
     * Creates a color object based on the Card's Color and sets it as
     * the value in the Color row in the deckTable.
     * @param c
     */
    public void setRowColor(Card c) {
        Color cardColor;
        switch (c.getColor().toString()) {
            case "Yellow": cardColor = new Color(255, 255, 0);
            break;
            case "Green": cardColor = new Color(0, 255, 0);
            break;
            case "Blue": cardColor = new Color(0, 0, 255);
            break;
            default: cardColor = new Color(255, 0, 0);
        }
        tableModel.setValueAt(cardColor, tableModel.getRowCount() - 1, 3);
    }
    
    public void removeCard(int row) {
    	deck.remove(row);
    	tableModel.removeCard(deckTable.convertRowIndexToModel(row));
    	sorter.modelStructureChanged();
    }
    
    public void removeCardsInBuffer() { 
        @SuppressWarnings("unchecked")
        ArrayList<Integer> cardBufferCopy = (ArrayList<Integer>) cardBuffer.clone(); //creates clone so cardBuffer.clear() in valueChanged() doesn't affect the list.
        Collections.sort(cardBufferCopy, Collections.reverseOrder()); //reverse order to avoid out of bounds exception
        Iterator<Integer> toBeRemoved = cardBufferCopy.iterator(); //iterator
        while (toBeRemoved.hasNext()) { //while there is another row to be removed
            int remove = toBeRemoved.next(); 
            tableModel.removeCard(deckTable.convertRowIndexToModel(remove));
        	sorter.modelStructureChanged();
            deck.remove(remove); //remove from deck
        }
        deckStats.updateStats(deck);
    }        
    
    private class SorterHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		String selected = (String) sorterBox.getSelectedItem();
    		tableModel.sortBy(selected);
    		deck.sortBy(selected);
    	}
    }
}
