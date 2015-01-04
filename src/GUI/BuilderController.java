package GUI;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import Card.Card;
import GUI.DeckReferenceComplex.DeckReferenceComplexPanel;
import GUI.DeckReferenceComplex.SwingHelp;
import Search.SearchResultsDialog;
import WeissSchwarz.Deck;

public class BuilderController {
	private CardTreePanel cardTreePanel;
	private DeckReferenceComplexPanel refComplex;
    private JPopupMenu treeRightClickPopup;
	
	public BuilderController(DeckReferenceComplexPanel refComplex, CardTreePanel cardTreePanel) {
		this.refComplex = refComplex;
		this.cardTreePanel = cardTreePanel;
		initCardTreePanel();
	}
	
	public void initCardTreePanel() {
		cardTreePanel.getCardTree().addMouseListener(new TreeLeftClickMouseHandler());
		initTreeRightClickPopup();
	}
	
	public void importDeck(Deck deck) {
		refComplex.importDeck(deck);
	}
	
	
	public void initTreeRightClickPopup() {
        treeRightClickPopup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Get Card Info");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (Card c: cardTreePanel.getCardBuffer()) {
        			new CardInfoPanel(c, SwingHelp.getOwningFrame(cardTreePanel.getCardTree()));
        		}
        	}
        });
        treeRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Add to Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (Card c: cardTreePanel.getCardBuffer()) { //Cycles through card buffer and adds cards to deckTable. 
  				  refComplex.addCard(c);
  			  }
        	}
        });
        treeRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Add 4 to Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (Card c: cardTreePanel.getCardBuffer()) { //Cycles through card buffer and adds cards to deckTable. 
  				  for (int i = 0; i < 4; i++) {
  					  refComplex.addCard(c);
  				  }
  			  }
        	}
        });
        treeRightClickPopup.add(menuItem);
        
        MouseListener popUpListener = new RightClickTreeListener(); 
        cardTreePanel.getCardTree().addMouseListener(popUpListener);
	}

	
	public void initResultsListRightClickPopup(JPopupMenu resultsListRightClickPopup, JList list) {
		final JList resultsList = list;
        JMenuItem menuItem = new JMenuItem("Get Card Info");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (int i: resultsList.getSelectedIndices()) {
        			Card c = (Card) resultsList.getModel().getElementAt(i);
        			new CardInfoPanel(c, SwingHelp.getOwningFrame(resultsList));
        		}
        	}
        });
        resultsListRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Add to Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (int i: resultsList.getSelectedIndices()) { //Cycles through selected indices and adds cards to deckTable. 
  				  refComplex.addCard((Card)resultsList.getModel().getElementAt(i));
  			  }
        	}
        });
        resultsListRightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Add 4 to Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (int selectedRow: resultsList.getSelectedIndices()) { //Cycles through selected indices and adds cards to deckTable. 
  				  for (int i = 0; i < 4; i++) {
  					  refComplex.addCard((Card)resultsList.getModel().getElementAt(selectedRow));
  				  }
  			  }
        	}
        });
        resultsListRightClickPopup.add(menuItem);
	}
	
	//adds a right click listener to and search results dialogs that appear
	public void registerSearchResultsDialog(SearchResultsDialog dialogToRegister) {
		dialogToRegister.getResultsList().addMouseListener(new RightClickListListener(dialogToRegister.getResultsList()));
		dialogToRegister.getResultsList().addMouseListener(new ListLeftClickMouseHandler(dialogToRegister.getResultsList()));
	}
	
	private class RightClickListListener extends MouseAdapter {
		JList list;
		public RightClickListListener(JList list) {
			this.list = list;
		}
		public void mouseReleased(MouseEvent me) {
			int clickedRow = list.locationToIndex(new Point(me.getX(), me.getY()));
			Card card = null;
			if (clickedRow!= -1) {
			    card = (Card) list.getModel().getElementAt(clickedRow);
			}
			if (card != null) {
				if(SwingUtilities.isRightMouseButton(me)) {
					boolean clickedOnSelection = false;
					//check if clicked on current selection paths
					for (int ind: list.getSelectedIndices()) { 
						if (clickedRow == ind) {
							clickedOnSelection = true;
						}
					}
					//this is to correctly select paths and maintain already selected paths, etc.
						if (!clickedOnSelection) {
							list.setSelectedIndex(clickedRow);
						}
						showRightClickMenu(me);
				}
			}
		}
		
		private void showRightClickMenu(MouseEvent me) { //TODO: this creates a new popup every time you right click, it would be better to create 1 new popup for each list figure this out later.
			if (me.isPopupTrigger()) {
				JPopupMenu resultsListRightClickPopup = new JPopupMenu();
				initResultsListRightClickPopup(resultsListRightClickPopup, list); //creates a right click popup for this list
				resultsListRightClickPopup.show(list, me.getX(), me.getY());
			}
		}
	}
	
	private class RightClickTreeListener extends MouseAdapter {
		public void mouseReleased(MouseEvent me) {
			TreePath path = cardTreePanel.getCardTree().getPathForLocation(me.getX(), me.getY());
			if (path != null) {
				if(SwingUtilities.isRightMouseButton(me)) {
					boolean clickedOnSelection = false;
					//check if clicked on current selection paths
					for (TreePath sp: cardTreePanel.getTreeModel().getSelectionPaths()) { 
						if (path.equals(sp)) {
							clickedOnSelection = true;
						}
					}
					//this is to correctly select paths and maintain already selected paths, etc.
						if (!clickedOnSelection) {
							cardTreePanel.getTreeModel().setSelectionPath(path);
						}
						showRightClickMenu(me);
				}
			}
		}
		
		private void showRightClickMenu(MouseEvent me) {
			if (me.isPopupTrigger()) {
				treeRightClickPopup.show(cardTreePanel.getCardTree(), me.getX(), me.getY());
			}
		}
	}
	
	private class TreeLeftClickMouseHandler extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			if (cardTreePanel.getCardTree().getPathForLocation(me.getX(), me.getY()) != null) {
				TreePath selPath = cardTreePanel.getCardTree().getPathForLocation(me.getX(), me.getY());
				if (selPath.getPath().length == cardTreePanel.getLeafDepth()) {
				    if (SwingUtilities.isLeftMouseButton(me) && me.getClickCount() == 2) {
				        Object selectedObject = ((DefaultMutableTreeNode)cardTreePanel.getCardTree().getLastSelectedPathComponent()).getUserObject();
				            if (selectedObject instanceof Card) {
				                refComplex.addCard((Card)selectedObject);
				            }
				    }
				}
			}
		}
	}
	
	private class ListLeftClickMouseHandler extends MouseAdapter {
		JList list;
		public ListLeftClickMouseHandler(JList list) {
			this.list = list;
		}
		
		public void mouseClicked(MouseEvent me) {
			if (list.locationToIndex(new Point(me.getX(), me.getY())) != -1 && list.getSelectedIndex() != -1) { //TODO: check what -1 means on this method
				    if (SwingUtilities.isLeftMouseButton(me) && me.getClickCount() == 2) {
				        Object selectedObject = list.getModel().getElementAt(list.getSelectedIndex());
				            if (selectedObject instanceof Card) {
				            	new CardInfoPanel((Card) selectedObject, SwingHelp.getOwningFrame(list));
				            }
				    }
			}
		}
	}
}