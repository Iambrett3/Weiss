package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;
import Card.*;
import GUI.DeckReferenceComplex.DeckReferenceComplexPanel;
import GUI.DeckReferenceComplex.DeckTable;

public class BuilderGUI extends JFrame {
	DeckList deckList;
	DeckReferenceComplexPanel refComplex;
	CardTreePanel cardTreePanel;
	JButton addCard;
	JButton removeCard;
	JButton loadSetButton;
	JPanel buttonPanel;
	BuilderController controller;
	
	public BuilderGUI() throws IOException {
		super("Deck Builder");
		setLayout(new MigLayout());
		setSize(1200, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createButtons(); 
		add(cardTreePanel = new CardTreePanel());
		add(refComplex = new DeckReferenceComplexPanel(), "wrap"); //do i need to add to content pane here?
		add(buttonPanel);
		controller = new BuilderController(refComplex, cardTreePanel);
		
		
		//pack();
        initToolTipManager();
        
    }
    
    public void initToolTipManager() {
    	ToolTipManager.sharedInstance().setInitialDelay(350);
    	ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		ToolTipManager.sharedInstance().registerComponent(cardTreePanel.getCardTree());
    }
	
	/**
	 * Initializes add card button and remove card button.
	 */
	public void createButtons() {
	    buttonPanel = new JPanel();
	    initLoadButton();
//		addCard = new JButton("Add to Deck");
//		addCard.addActionListener( 
//				      new ActionListener() { //ActionListener for Add button
//				    	  public void actionPerformed(ActionEvent e) { 
//				    			  for (Card c: cardTreePanel.getCardBuffer()) { //Cycles through card buffer and adds cards to deckTable. 
//				    				  refComplex.addCard(c);
//				    			  }
//				    		 //cardTree.resetCardBuffer(); //reset cardTree card buffer
//				    	  }
//				      }
//		);
//		
//		removeCard = new JButton("Remove from Deck");
//		removeCard.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) { 
//						refComplex.removeCards();
//			}
//		}
//	    );
//		buttonPanel.add(addCard);
//		buttonPanel.add(removeCard);
		}
	
	public void initLoadButton() {
	    loadSetButton = new JButton("Load Selected Set");
	    loadSetButton.addActionListener(
	            new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    try {
	                        cardTreePanel.loadSelectedSet();
	                    }
	                    catch (Exception exception)
	                    {
	                        System.out.println("load selected set failed");
	                    }

	                }
	            }
	     );
	    buttonPanel.add(loadSetButton);
	}
	
private class MouseHandler extends MouseAdapter {
	public void mouseClicked(MouseEvent me) {
		if (cardTreePanel.getCardTree().getPathForLocation(me.getX(), me.getY()) != null) {
			TreePath selPath = cardTreePanel.getCardTree().getPathForLocation(me.getX(), me.getY());
			if (SwingUtilities.isLeftMouseButton(me) && me.getClickCount() == 2) {
				if (selPath.getPath().length == 3) {
					refComplex.addCard((Card) ((DefaultMutableTreeNode) selPath.getLastPathComponent()).getUserObject());
				}
			}
		}
	}
}
	public static void main(String[] args) throws IOException{
		Runnable runner = new Runnable() {
			public void run() {
		BuilderGUI b;
		try {
			b = new BuilderGUI();
			b.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
			}
		};
		SwingUtilities.invokeLater(runner);
	}
}

				     
