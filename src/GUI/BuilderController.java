package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import Card.Card;
import GUI.DeckReferenceComplex.DeckReferenceComplexPanel;
import GUI.DeckReferenceComplex.SwingHelp;

public class BuilderController {
	private CardTreePanel cardTreePanel;
	private DeckReferenceComplexPanel refComplex;
    private JPopupMenu rightClickPopup;
	
	public BuilderController(DeckReferenceComplexPanel refComplex, CardTreePanel cardTreePanel) {
		this.refComplex = refComplex;
		this.cardTreePanel = cardTreePanel;
		initCardTreePanel();
	}
	
	public void initCardTreePanel() {
		cardTreePanel.getCardTree().addMouseListener(new MouseHandler());
		initRightClickPopup();
	}
	
	
	public void initRightClickPopup() {
        rightClickPopup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Get Card Info");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (Card c: cardTreePanel.getCardBuffer()) {
        			new CardInfoPanel(c, SwingHelp.getOwningFrame(cardTreePanel.getCardTree()));
        		}
        	}
        });
        rightClickPopup.add(menuItem);
        
        menuItem = new JMenuItem("Add to Deck");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		for (Card c: cardTreePanel.getCardBuffer()) { //Cycles through card buffer and adds cards to deckTable. 
  				  refComplex.addCard(c);
  			  }
        	}
        });
        rightClickPopup.add(menuItem);
        
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
        rightClickPopup.add(menuItem);
        
        MouseListener popUpListener = new RightClickListener(); 
        cardTreePanel.getCardTree().addMouseListener(popUpListener);
	}
	
	private class RightClickListener extends MouseAdapter {
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
				rightClickPopup.show(cardTreePanel.getCardTree(), me.getX(), me.getY());
			}
		}
	}
	
	private class MouseHandler extends MouseAdapter {
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
				else if (selPath.getPath().length == cardTreePanel.getSetDepth()) {
				    if (SwingUtilities.isLeftMouseButton(me) && me.getClickCount() == 2) {
				        if (!((CardTreeSetNode) cardTreePanel.getCardTree().getSelectionPath().getLastPathComponent()).isLoaded()) { //if it's already loaded, do nothing
					        cardTreePanel.loadSelectedSet();
					    }
					}
				}
			}
		}
	}
}