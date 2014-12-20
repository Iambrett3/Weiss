package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.zip.ZipFile;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.*;

import WeissSchwarz.CardReader;
import Card.*;
import GUI.DeckReferenceComplex.CPTTransferHandler;
import GUI.DeckReferenceComplex.SwingHelp;

public class CardTreePanel extends JPanel {
	private JTree cardTree;
	private ArrayList<Card> cardBuffer;
    CardTreeNode top;
    private final int LEAF_DEPTH = 3;
    TreeSelectionModel model;
	
	public CardTreePanel() throws IOException {
		cardBuffer = new ArrayList<Card>();
		top = new CardTreeNode("Cards"); //initializes and names top of list.
		initTree();
		model = cardTree.getSelectionModel();
		Toolkit tk = Toolkit.getDefaultToolkit();
        cardTree.setCellRenderer(new CardCellRenderer());
	}
	

	
	public String makeToolTip(Card c) {
    	String imageAddress = "file:C:/Brett/workspace/Weiss/Database/images/" + c.getImagePath();
    	String str = "<html><body><textarea cols=\"30\" rows=\"20\" wrap=\"soft\">" + c.getDescription() +
    			"<img src=\"" + imageAddress + "\" width=\"250\" height=\"365\"></body></html>"; 		 
    	return str;
    }
	
	/**
	 * Initializes tree and adds it to panel.
	 */
	public void initTree()
	{
	    try {
	        initList();
	    }
	    catch (Exception e) {
	        System.out.println("initList failed");
	    }
        cardTree = new JTree(top) {
//        	public Point getToolTipLocation(MouseEvent e)
//    		{
//        			TreePath row = getPathForLocation(e.getX(), e.getY());
//        			Rectangle rowRect = getPathBounds(row);
//        			double x = rowRect.getX() + rowRect.getWidth();
//        			double y = rowRect.getY() + rowRect.getHeight();
//        			return new Point((int) x, (int) y);
//    	    }
        };
        cardTree.addTreeSelectionListener(new SelectionHandler());
        JScrollPane tree = new JScrollPane(cardTree);
        tree.setPreferredSize(new Dimension(150, 500));
        cardTree.setDragEnabled(true);
        cardTree.setTransferHandler(new CPTTransferHandler(cardTree));
        add(tree);
	}
	
	/**
	 * Initializes all top level members of the list and adds it to the frame.
	 * @throws IOException
	 */
	public void initList() throws IOException //TODO: handle this exception
	{

        //DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath("G:\\Code\\workspace\\Weiss\\Database"));
        DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath("C:\\Brett\\workspace\\Weiss\\Database"));
        ZipFile zip;
        for (Path p: ds) {
            if (p.toString().endsWith(".zip")) {
                CardTreeNode set;
                zip = new ZipFile(p.toFile());
                set = new CardTreeNode(zip.getName());
                top.add(set);
            }
        }
	}
	
	/**
	 * Loads selected set. Is called by action listener for load set button.
	 * @throws IOException 
	 */
	public void loadSelectedSet() throws IOException
	{
	    if (cardTree.getSelectionPath().getPath().length == 2) { //check the level of the path
	        CardTreeNode node = (CardTreeNode) cardTree.getSelectionPath().getLastPathComponent(); //gets selected node
	        ZipFile zip = new ZipFile((String) node.getUserObject()); //initializes ZipFile object from selected node
	        createNodes(zip, node);
	    }
	}
	
	public void createNodes(ZipFile cardSet, CardTreeNode set) throws IOException {
		CardTreeNode card;
		
		ArrayList<Card> theSet = CardReader.loadSet(cardSet);
		for(Card c : theSet){
				card = new CardTreeNode(c);
				set.add(card);
		}
	}
				
	public ArrayList<Card> getCardBuffer() {
		return cardBuffer;
	}
	
	public void setCardBuffer(ArrayList<Card> cardBuffer) {
		this.cardBuffer = cardBuffer;
	}
	
	public void resetCardBuffer() {
		cardBuffer.clear();
	}
	
	public JTree getCardTree() {
		return cardTree;
	}
	
	public TreeSelectionModel getTreeModel() {
		return cardTree.getSelectionModel();
	}
	

	
	/**
	 * Need to find better way to do this.
	 * TreeSelectionEvent only fires when node is changed <- need to fix
	 * Throwing Exception when card is selected and then expand is clicked.
	 *
	 */
	private class SelectionHandler implements TreeSelectionListener {
		public void valueChanged(TreeSelectionEvent e) {
			resetCardBuffer();
			if (cardTree.getSelectionPaths() != null) {
				TreePath[] selectedNodes =  
					cardTree.getSelectionPaths();
				for (TreePath t: selectedNodes) {
					if (t.getPath().length == LEAF_DEPTH && cardTree.isPathSelected(t)) {
						CardTreeNode node = (CardTreeNode)
								t.getLastPathComponent();
						Card nodeCard = (Card) node.getUserObject();
						cardBuffer.add(nodeCard);
					}
				}
			}
		}
	}
	
	private class CardCellRenderer extends DefaultTreeCellRenderer {
		public Component getTreeCellRendererComponent(JTree tree,
	            Object value, boolean selected, boolean expanded, 
	            boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
			CardTreeNode node = (CardTreeNode) value;
			if(node.getUserObject() instanceof Card) {
				Card card = (Card) node.getUserObject();
				setToolTipText(makeToolTip(card));
				setText(card.getName());
			}
			else {
				setText(node.toString());
			}
			return this;
		}
		
	}
	

}

