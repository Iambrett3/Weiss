package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.List;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DropMode;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;
import javax.swing.SwingUtilities;

import Card.Card;
import Card.DeckSorter;
import GUI.CardInfoPanel;
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
    private final int CARD_COLUMN_NUMBER = 1;
    private final int COLOR_COLUMN_NUMBER = 4;
    private JPopupMenu rightClickPopup;
    
    public DRCController(SorterBox sorterBox, JScrollPane tablePane, JTable deckTable, 
    		ImageSelection imageSelection, DeckStats deckStats, JTextPane cardStats, Deck deck) {
    	this.sorterBox = sorterBox;
        this.deckTable = deckTable;
        this.imageSelection = imageSelection;
        this.deckStats = deckStats;
        this.cardStats = cardStats;
        this.tablePane = tablePane;
        this.deck = deck;
        tableModel = (DeckTableModel) deckTable.getModel();
        cardBuffer = new ArrayList<Integer>();
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
		initRightClickPopup();
		initKeyListener();
	}
	
	public void initRightClickPopup() {
        rightClickPopup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Remove From Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		removeCards(deckTable.getSelectedRows());
        	}
        });
        rightClickPopup.add(menuItem);
        
        MouseListener popUpListener = new RightClickListener(); 
        deckTable.addMouseListener(popUpListener);
	}
	
	public void initKeyListener() {
		deckTable.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent k) {
				switch (k.getKeyCode()) {
				case KeyEvent.VK_DELETE: removeCardsInBuffer();
				break;
				}
			}
			
			public void keyTyped(KeyEvent k) {
				
			}
			
			public void keyReleased(KeyEvent k) {
			}
		});
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
        deckTable.setRowHeight(25);
        
        deckTable.setDragEnabled(true);
        deckTable.setDropMode(DropMode.INSERT_ROWS);
        deckTable.setTransferHandler(new DeckTableTransferHandler(this));
        deckTable.setFillsViewportHeight(true);
        
        deckTable.setDefaultRenderer(Object.class, new RowCellRenderer(true));
        
        initTableMouseListener();
        
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
                	//this was code from when the refComplex was visible on page (maybe bring back as alternate view)
                    //imageSelection.setCurrentImage(deck.get(deckTable.getSelectedRow()).getImage());
                    //cardStats.setText(deck.get(deckTable.getSelectedRow()).getDescription());
                    //new CardInfoPanel(deck.get(deckTable.getSelectedRow()));
                    //new CardInfoPanel();
                }
                else {
                    //imageSelection.resetCurrentImage();
                    //cardStats.setText("");
                }    
            }
        });
    }
    
    public void initTableMouseListener() {
    	deckTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (row != -1) {
                	if (me.getClickCount() == 2) {
                		new CardInfoPanel((Card) table.getValueAt(row, CARD_COLUMN_NUMBER), SwingHelp.getOwningFrame(table));
                	}
                }
            }
        });
    }
    
    public void initImageSelection() {
    }
    
    public void initCardStats() {
        
    }
    
    public void initDeckStats() {
    }
    
    public void addCard(Card c) {
    	if (deck.isInDeck(c)) {
    		int location = deck.deckLocation(c);
    		int numCard = deck.get(location).getNumOfCard();
    		deck.get(location).incrementNumOfCard(1);
    		tableModel.setValueAt(numCard+1, location, 0);
    	}
    	else {
    		deck.add(c);
            tableModel.addCard(c);
            sorter.modelStructureChanged();
            setRowColor(c); //TODO: I am calling this just to change the Color column from CColor object to Color object. this is stupid fix this
    	}
        System.out.println(((Card) tableModel.getValueAt(tableModel.getRowCount() -1, CARD_COLUMN_NUMBER)).getDescription());
        deckStats.updateStats(deck);
    }
    
    public void insertCard(int pos, Card c) {
    	if (deck.isInDeck(c)) {
    		int location = deck.deckLocation(c);
    		int numCard = deck.get(location).getNumOfCard();
    		deck.get(location).incrementNumOfCard(1);
    		tableModel.setValueAt(numCard+1, location, 0);
    	}
    	else {
    		deck.add(pos, c);
    		tableModel.insertCard(pos, c);
    		sorter.modelStructureChanged();
    		setRowColor(c);
    	}
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
    	deckStats.updateStats(deck);
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
    	tableModel.reorder(from, to);
    	sorter.modelStructureChanged();
    	deckStats.updateStats(deck);
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
        tableModel.setValueAt(cardColor, tableModel.getRowCount() - 1, COLOR_COLUMN_NUMBER);
    }
    
    public void removeCard(int row) {
    	Card c = deck.get(row);
    	int numCard = c.getNumOfCard();
    	if (numCard > 1) {
    		deck.get(row).incrementNumOfCard(-1);
    		tableModel.setValueAt(numCard-1, row, 0);
    	}
    	else {
    		deck.remove(row);
    		tableModel.removeCard(deckTable.convertRowIndexToModel(row));
    		sorter.modelStructureChanged();
    	}
    }
    
    public void removeCards(int[] cards) {
    	int[] reverseCards = new int[cards.length];
    	int j = cards.length - 1;
    	for (int i = 0; i < cards.length; i++) {
    		reverseCards[j] = cards[i];
    		j--;
    	}
    	for (int i = 0; i < reverseCards.length; i++) {
    		removeCard(reverseCards[i]);
    	}
    }
    
    public void removeCardsInBuffer() { 
        @SuppressWarnings("unchecked")
        ArrayList<Integer> cardBufferCopy = (ArrayList<Integer>) cardBuffer.clone(); //creates clone so cardBuffer.clear() in valueChanged() doesn't affect the list.
        Collections.sort(cardBufferCopy, Collections.reverseOrder()); //reverse order to avoid out of bounds exception
        Iterator<Integer> toBeRemoved = cardBufferCopy.iterator(); //iterator
        while (toBeRemoved.hasNext()) { //while there is another row to be removed
            int remove = toBeRemoved.next(); 
            //tableModel.removeCard(deckTable.convertRowIndexToModel(remove));
        	//sorter.modelStructureChanged();
            //deck.remove(remove); //remove from deck
            removeCard(remove);
        }
        deckStats.updateStats(deck);
    }        
    
    private class SorterHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		String selected = (String) sorterBox.getSelectedItem();
    		tableModel.sortBy(selected);
    		deck.sortBy(selected);
    		deckStats.updateStats(deck);
    	}
    }
    
    private class RightClickListener extends MouseAdapter {
    	public void mouseReleased(MouseEvent me) {
			Integer row = deckTable.rowAtPoint(me.getPoint());
			if (row != -1) {
				if(SwingUtilities.isRightMouseButton(me)) {
					boolean clickedOnSelection = false;
					//check if clicked on current selection paths
					for (Integer i: deckTable.getSelectedRows()) { 
						if (row.equals(i)) {
							clickedOnSelection = true;
						}
					}
					//this is to correctly select paths and maintain already selected paths, etc.
					if (!clickedOnSelection) {
						deckTable.setRowSelectionInterval(row, row);
					}
				}
					showRightClickMenu(me);
			}
		}
    	private void showRightClickMenu(MouseEvent me) {
			if (me.isPopupTrigger()) {
				rightClickPopup.show(deckTable, me.getX(), me.getY());
			}
		}
    }

	public Deck getDeck() {
		return deck;
	}
}
