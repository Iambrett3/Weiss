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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.DropMode;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.TreePath;
import javax.swing.SwingUtilities;

import Card.Card;
import Card.DeckSorter;
import GUI.BuilderGUI;
import GUI.CardInfoPanel;
import GUI.DeckReferenceComplex.ImageLayoutView.ImageList;
import GUI.DeckReferenceComplex.ImageLayoutView.ImageListTransferHandler;
import WeissSchwarz.Deck;

/**
 * For data manipulation of DeckReferenceComplexPanel
 * @author Brett
 *
 */
public class DRCController
{
    private DeckTable deckTable; 
    private ImageSelection imageSelection;  //references to the table, imageSelection, and deckStats
    private DeckStats deckStats;
    private ArrayList<Integer> cardBuffer; //this is only the card buffer for the deck table. the image view has its own
    private Deck deck;
    private DeckTableModel tableModel;
    private JTextPane cardStats;
    private TableRowSorter sorter;
    private JScrollPane tablePane;
    private JPopupMenu deckTableRightClickPopup;
    private JPopupMenu imageListRightClickPopup;
    private int lastSortedColumn;
    private ImageList imageList;
    private BuilderGUI builder;
    
    public DRCController(BuilderGUI builder, JScrollPane tablePane, DeckTable deckTable, 
    		ImageSelection imageSelection, DeckStats deckStats, JTextPane cardStats, Deck deck,
    		ImageList imageList) {
        this.deckTable = deckTable;
        this.imageSelection = imageSelection;
        this.deckStats = deckStats;
        this.cardStats = cardStats;
        this.tablePane = tablePane;
        this.imageList = imageList;
        this.builder = builder;
        this.deck = deck;
        tableModel = (DeckTableModel)deckTable.getDeckTableModel();
        cardBuffer = new ArrayList<Integer>();
        lastSortedColumn = -1;
        init();
    }
    
    public DeckTableModel getDeckTableModel() {
    	return tableModel;
    }
    
    /**
     * initializes members
     */
    public void init() {
        initTable();
        initImageSelection();
        initDeckStats();
        initCardStats();
		initDeckTableRightClickPopup();
		initImageViewRightClickPopup();
		initKeyListeners();
	}
    
    public void initImageViewRightClickPopup() {
    	imageListRightClickPopup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Get Card Info");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (int i: imageList.getCardBuffer()) {
        			new CardInfoPanel(deck.get(i), SwingHelp.getOwningFrame(imageList));
        		}
        	}
        });
        imageListRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Remove From Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		trueRemoveCards(imageList.getList().getSelectedIndices());
        	}
        });
        imageListRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Remove 1 From Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		removeCards(imageList.getList().getSelectedIndices());
        	}
        });
        imageListRightClickPopup.add(menuItem);
        
        MouseListener popUpListener = new imageListRightClickListener(); 
        imageList.getList().addMouseListener(popUpListener);
    }
	
	public void initDeckTableRightClickPopup() {
        deckTableRightClickPopup = new JPopupMenu();
        
        JMenuItem menuItem = new JMenuItem("Get Card Info");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (int i: cardBuffer) {
        			new CardInfoPanel(deck.get(i), SwingHelp.getOwningFrame(deckTable));
        		}
        	}
        });
        deckTableRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Remove From Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		trueRemoveCards(deckTable.getSelectedRows());
        	}
        });
        deckTableRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Remove 1 From Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		removeCards(deckTable.getSelectedRows());
        	}
        });
        deckTableRightClickPopup.add(menuItem);
        
        MouseListener popUpListener = new DeckTableRightClickListener(); 
        deckTable.addMouseListener(popUpListener);
	}
	
	public void clearDeck() {
		if (deck.isEmpty()) {
			return;
		}
		//for as many cards as there are in the deck, remove the first index from the table.
		int deckSize = deck.getDeckSize();
		for (int i = 0; i < deckSize; i++) {
			trueRemoveCard(0);
		}
		deckStats.updateStats(deck);
	}
	
	public void importDeck(Deck deck) {
		clearDeck();
		for (Card card: deck) {
			addCard(card);
		}
		deckStats.updateStats(deck);
	}
	
	public void initKeyListeners() {
		KeyListener deleteListener;
		deckTable.addKeyListener(deleteListener = new KeyListener() {
			public void keyPressed(KeyEvent k) {

				if (k.getComponent() instanceof JList) {
					switch (k.getKeyCode()) {
						case KeyEvent.VK_DELETE: removeCardsInImageListBuffer();
						break;
					}
				}
				else {
					switch (k.getKeyCode()) {
						case KeyEvent.VK_DELETE: removeCardsInBuffer();
						break;
					}
				}
			}
			
			public void keyTyped(KeyEvent k) {
				
			}
			
			public void keyReleased(KeyEvent k) {
			}
		});
		imageList.getList().addKeyListener(deleteListener);
	}
    
    public JTable getDeckTable() {
    	return deckTable;
    }
    
    public void initTable() {
        tableModel.addTableModelListener(deckTable);
        deckTable.setRowSelectionAllowed(true);
        deckTable.getTableHeader().setReorderingAllowed(false);
        //deckTable.setRowSorter(sorter = new TableRowSorter<DeckTableModel>(tableModel));
        //sorter.setSortsOnUpdates(true);
        deckTable.setRowHeight(25);
        

        
        deckTable.setDeck(deck);
        
        deckTable.setDragEnabled(true);
        deckTable.setDropMode(DropMode.INSERT_ROWS);
        deckTable.setTransferHandler(new DeckTableTransferHandler(this));
        deckTable.setFillsViewportHeight(true);
        

        
        deckTable.setDefaultRenderer(Object.class, new DeckTableRowCellRenderer(true));
        
        initTableMouseListener();
        initTableHeaderListener();
        
        initImageListMouseListener();
        
        imageList.getList().setDragEnabled(true);
        imageList.getList().setDropMode(DropMode.INSERT);
        imageList.getList().setTransferHandler(new ImageListTransferHandler(imageList, this));
        
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
                		new CardInfoPanel((Card) table.getValueAt(row, DeckTable.getNameColumnNumber()), SwingHelp.getOwningFrame(table));
                	}
                }
            }
        });
    }
    
    public void initImageListMouseListener() {
    	imageList.getList().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JList list = (JList) me.getSource();
                Point p = me.getPoint();
                int row = list.locationToIndex(p);
                if (row != -1) {
                	if (me.getClickCount() == 2) {
                		new CardInfoPanel((Card) imageList.getList().getModel().getElementAt(row), SwingHelp.getOwningFrame(imageList.getList()));
                	}
                }
            }
        });
    }
    
    public void initTableHeaderListener() {
        deckTable.getTableHeader().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                lastSortedColumn = deckTable.columnAtPoint(e.getPoint());
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
        deckTable.updateColumnWidths();
    	if (deck.isInDeck(c)) {
    		int location = deck.deckLocation(c);
    		int numCard = deck.get(location).getNumOfCard();
    		deck.incrementNumOfCard(location, 1);
    		deck.setDeckSize(deck.getDeckSize() + 1);
    		tableModel.setValueAt(numCard+1, location, 0);
    	}
    	else {
    		deck.add(c);
    		imageList.addCard(c);
            tableModel.addCard(c);
            //sorter.modelStructureChanged();
            }
    	deckTable.setColumnWidths();
    	builder.addStarToTitle();
    	deck.setIsSaved(false);
        //System.out.println(((Card) tableModel.getValueAt(tableModel.getRowCount() -1, DeckTable.getNameColumnNumber())).getDescription());
        deckStats.updateStats(deck);
    }
    
    public void insertCard(int pos, Card c) {
        deckTable.updateColumnWidths();
    	if (deck.isInDeck(c)) {
    		int location = deck.deckLocation(c);
    		int numCard = deck.get(location).getNumOfCard();
    		deck.incrementNumOfCard(location, 1);
    		deck.setDeckSize(deck.getDeckSize() + 1);
    		tableModel.setValueAt(numCard+1, location, 0);
    	}
    	else {
    		deck.add(pos, c);
    		imageList.insertCard(pos, c);
    		tableModel.insertCard(pos, c);
    		//sorter.modelStructureChanged();
    	}
        deckTable.setColumnWidths();
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
    		imageList.removeCard(from[i]);
    		deck.remove(from[i].intValue());
    	}
    	tableModel.insertCards(fromCopy, to);
    	imageList.insertCards(fromCards, to);
    	deck.insertCards(fromCards, to);
    	deckStats.updateStats(deck);
    }
    
//    public void reorder(int from, int to) {
//    	if (to == deck.size()) {
//    		deck.add(deck.get(from));
//    		deck.remove(from);
//    	}
//    	else if (to > from) {
//    		Card fromCard = (Card) deck.get(from);
//    		deck.add(to, fromCard);
//    		deck.remove(from);
//    	}
//    	else if (from > to) {
//    		Card fromCard = (Card) deck.get(from);
//    		deck.remove(from);
//    		deck.add(to, fromCard);
//    	}
//    	tableModel.reorder(from, to);
//    	sorter.modelStructureChanged();
//    	deckStats.updateStats(deck);
//    }
    
    public void removeCard(int row) {
        deckTable.updateColumnWidths();
    	Card c = deck.get(row);
    	int numCard = c.getNumOfCard();
    	if (numCard > 1) {
    		deck.incrementNumOfCard(row, -1);
    		deck.setDeckSize(deck.getDeckSize() -1);
    		tableModel.setValueAt(numCard-1, row, 0);
    	}
    	else {
    		trueRemoveCard(row);
    	}
        deckTable.setColumnWidths();
    	builder.addStarToTitle();
    	deck.setIsSaved(false);
    	deckStats.updateStats(deck);
    }
    
    public void trueRemoveCard(int row) {
        deckTable.updateColumnWidths();
    	deck.remove(row);
		tableModel.removeCard(deckTable.convertRowIndexToModel(row));
		imageList.removeCard(row);
		
	    deckTable.setColumnWidths();
		builder.addStarToTitle();
    	deck.setIsSaved(false);
    	deckStats.updateStats(deck);
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
    	deckStats.updateStats(deck);
    }
    
    public void trueRemoveCards(int[] cards) {
    	int[] reverseCards = new int[cards.length];
    	int j = cards.length - 1;
    	for (int i = 0; i < cards.length; i++) {
    		reverseCards[j] = cards[i];
    		j--;
    	}
    	for (int i = 0; i < reverseCards.length; i++) {
    		trueRemoveCard(reverseCards[i]);
    	}
    	builder.addStarToTitle();
    	deck.setIsSaved(false);
    	deckStats.updateStats(deck);
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
            trueRemoveCard(remove);
        }
        deckStats.updateStats(deck);
    }        
    
    public void removeCardsInImageListBuffer() {
    	ArrayList<Integer> cardBufferCopy = (ArrayList<Integer>) imageList.getCardBuffer().clone(); //creates clone so cardBuffer.clear() in valueChanged() doesn't affect the list.
        Collections.sort(cardBufferCopy, Collections.reverseOrder()); //reverse order to avoid out of bounds exception
        Iterator<Integer> toBeRemoved = cardBufferCopy.iterator(); //iterator
        while (toBeRemoved.hasNext()) { //while there is another row to be removed
            int remove = toBeRemoved.next(); 
            //tableModel.removeCard(deckTable.convertRowIndexToModel(remove));
        	//sorter.modelStructureChanged();
            //deck.remove(remove); //remove from deck
            trueRemoveCard(remove);
        }
    	deckStats.updateStats(deck);
    }
    
    private class DeckTableRightClickListener extends MouseAdapter {
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
				deckTableRightClickPopup.show(deckTable, me.getX(), me.getY());
			}
		}
    }

    
    private class imageListRightClickListener extends MouseAdapter {
    	public void mouseReleased(MouseEvent me) {
			Integer row = imageList.getList().locationToIndex(me.getPoint());
			if (row != -1) {
				if(SwingUtilities.isRightMouseButton(me)) {
					boolean clickedOnSelection = false;
					//check if clicked on current selection paths
					for (Integer i: imageList.getList().getSelectedIndices()) { 
						if (row.equals(i)) {
							clickedOnSelection = true;
						}
					}
					//this is to correctly select paths and maintain already selected paths, etc.
					if (!clickedOnSelection) {
						imageList.getList().setSelectedIndex(row);
					}
				}
					showRightClickMenu(me);
			}
		}
    	private void showRightClickMenu(MouseEvent me) {
			if (me.isPopupTrigger()) {
				imageListRightClickPopup.show(imageList, me.getX(), me.getY());
			}
		}
    }
    
	public Deck getDeck() {
		return deck;
	}
}
