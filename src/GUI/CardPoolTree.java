package GUI;

import java.awt.Dimension;
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

public class CardPoolTree extends JPanel {
	private JTree cardTree;
	private ArrayList<Card> cardBuffer;
    DefaultMutableTreeNode top;
	
	public CardPoolTree() throws IOException {
		cardBuffer = new ArrayList<Card>();
		top = new DefaultMutableTreeNode("Cards"); //initializes and names top of list.
		initTree();
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
        cardTree = new JTree(top);
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
                DefaultMutableTreeNode set;
                zip = new ZipFile(p.toFile());
                set = new DefaultMutableTreeNode(zip.getName());
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
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode) cardTree.getSelectionPath().getLastPathComponent(); //gets selected node
	        ZipFile zip = new ZipFile((String) node.getUserObject()); //initializes ZipFile object from selected node
	        createNodes(zip, node);
	    }
	}
	
	public void createNodes(ZipFile cardSet, DefaultMutableTreeNode set) throws IOException {
		DefaultMutableTreeNode card;
		
		ArrayList<Card> theSet = CardReader.loadSet(cardSet);
		for(Card c : theSet){
				card = new DefaultMutableTreeNode(c);
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
					if (t.getPath().length == 3 && cardTree.isPathSelected(t)) {
						DefaultMutableTreeNode node = (DefaultMutableTreeNode)
								t.getLastPathComponent();
						Card nodeCard = (Card) node.getUserObject();
						cardBuffer.add(nodeCard);
					}
				}
			}
		}
	}
}

