package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import Card.*;
import GUI.DeckReferenceComplex.DeckReferenceComplexPanel;
import GUI.DeckReferenceComplex.DeckTable;

public class BuilderGUI extends JFrame {
	DeckList deckList;
	DeckReferenceComplexPanel refComplex;
	CardPoolTree cardTree;
	JButton addCard;
	JButton removeCard;
	JButton loadSetButton;
	JPanel buttonPanel;
	
	public BuilderGUI() throws IOException {
		super("Deck Builder");
		setLayout(new BorderLayout());
		setSize(500, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(refComplex = new DeckReferenceComplexPanel(), BorderLayout.WEST); //do i need to add to content pane here?
		add(cardTree = new CardPoolTree(), BorderLayout.EAST);
		createButtons();
		add(buttonPanel, BorderLayout.NORTH);
		cardTree.getCardTree().addMouseListener(new MouseHandler());
		pack();
	}
	
	/**
	 * Initializes add card button and remove card button.
	 */
	public void createButtons() {
	    buttonPanel = new JPanel();
	    initLoadButton();
		addCard = new JButton("Add to Deck");
		addCard.addActionListener( 
				      new ActionListener() { //ActionListener for Add button
				    	  public void actionPerformed(ActionEvent e) { 
				    			  for (Card c: cardTree.getCardBuffer()) { //Cycles through card buffer and adds cards to deckTable. 
				    				  refComplex.addCard(c);
				    			  }
				    		 //cardTree.resetCardBuffer(); //reset cardTree card buffer
				    	  }
				      }
		);
		
		removeCard = new JButton("Remove from Deck");
		removeCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
						refComplex.removeCards();
			}
		}
	    );
		buttonPanel.add(addCard);
		buttonPanel.add(removeCard);
		}
	
	public void initLoadButton() {
	    loadSetButton = new JButton("Load Selected Set");
	    loadSetButton.addActionListener(
	            new ActionListener() {
	                public void actionPerformed(ActionEvent e) {
	                    try {
	                        cardTree.loadSelectedSet();
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
		if (cardTree.getCardTree().getPathForLocation(me.getX(), me.getY()) != null) {
			TreePath selPath = cardTree.getCardTree().getPathForLocation(me.getX(), me.getY());
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

				     
