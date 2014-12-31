package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.zip.ZipFile;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.html.HTML.Tag;
import javax.swing.tree.*;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang3.text.WordUtils;

import WeissSchwarz.CardReader;
import WeissSchwarz.ToolTipHelper;
import Card.*;
import GUI.DeckReferenceComplex.CPTTransferHandler;
import GUI.DeckReferenceComplex.SwingHelp;

public class CardTreePanel extends JPanel implements PropertyChangeListener {
	private JTree cardTree;
	private ArrayList<Card> cardBuffer;
    CardTreeNode top;
    private final int LEAF_DEPTH = 4;
    private final int SET_DEPTH = 3;
    TreeSelectionModel selectionModel;
    DefaultTreeModel treeModel;
    private CardHashRetrieval cardHash;
    private JProgressBar progBar;
    private JDialog progDialog;
    private LoadSetTask loadSet;
    
    private ArrayList<String> titles;
	
	public CardTreePanel() throws IOException {
		setLayout(new MigLayout());
		cardBuffer = new ArrayList<Card>();
		top = new CardTreeNode("All Cards"); //initializes and names top of list.
		initTitles();
		initTree();
		selectionModel = cardTree.getSelectionModel();
		treeModel = new DefaultTreeModel(top);
		cardTree.setModel(treeModel);
		Toolkit tk = Toolkit.getDefaultToolkit();
        cardTree.setCellRenderer(new CardCellRenderer());
        cardHash = new CardHashRetrieval(); 
	}
	
	public void initTitles() {
		titles = new ArrayList<String>();
		try {
			File titlesText = new File("C:/Brett/workspace/Weiss/Database/TitleList.txt");
			Scanner scan = new Scanner(titlesText);
			while (scan.hasNext()) {
				titles.add(scan.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public int getLeafDepth() {
	    return LEAF_DEPTH;
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
        	public Point getToolTipLocation(MouseEvent e)
    		{
        			TreePath row = getPathForLocation(e.getX(), e.getY());
        			Rectangle rowRect = getPathBounds(row);
        			if (rowRect == null) {
        				return null;
        			}
        			double x = rowRect.getX() + rowRect.getWidth()/2;
        			double y = rowRect.getY();
        			return new Point((int) x, (int) y);
    	    }
        };
        cardTree.addTreeSelectionListener(new SelectionHandler());
        JScrollPane tree = new JScrollPane(cardTree);
        tree.setPreferredSize(new Dimension(400, 500));
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
	    for (String str: titles) {
	        initFolder(str);
	    }
	   // cardTree.getModel().
	    //desktop
	    //make it so it checks every folder, not just this base folder and that will solve the problems
        //laptop
        //DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath("C:\\Brett\\workspace\\Weiss\\Database"));
        
	}
	
	public void showProgressBar() {
	    progDialog = new JDialog();
	    progDialog.setBounds(100, 100, 100, 70);
	    progBar = new JProgressBar(0, 100);
        progBar.setValue(0);
        progBar.setStringPainted(true);
        progDialog.add(progBar);
        progDialog.setVisible(true);
	}
	
	public void initFolder(String packName) throws IOException {
	    CardTreeNode currentNode;
	    top.add(currentNode = new CardTreeNode(packName));
	    DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath("C:\\Brett\\workspace\\Weiss\\Database\\titles\\"
	                                    + currentNode.getUserObject()));
	    ZipFile zip;
        for (Path p: ds) {
            if (p.toString().endsWith(".zip")) {
                CardTreeNode set;
                zip = new ZipFile(p.toFile());
                String setName = prepareSetName(zip.getName(), packName);
                set = new CardTreeSetNode(setName, zip.getName());
                currentNode.add(set);
            }
        }
	}
	
	public String prepareSetName(String zipName, String packName) {
	    String str= zipName.split(packName)[1].substring(1);
	    str = str.replaceAll("_", " ");
	    str = str.replaceAll(".zip", "");
	    str = WordUtils.capitalize(str);
	    return str;
	}
	
	/**
	 * Loads selected set. Is called by action listener for load set button.
	 * @throws IOException 
	 */
	public void loadSelectedSet()
	{
	    try {

	        if (cardTree.getSelectionPath().getPath().length == SET_DEPTH) { //check the level of the path
	            CardTreeSetNode node = (CardTreeSetNode) cardTree.getSelectionPath().getLastPathComponent(); //gets selected node
	            ZipFile zip = new ZipFile((String) node.getSetFilePath()); //initializes ZipFile object from selected node
	            node.setIsLoaded(true);
	            createNodes(zip, node);
	        }
	    }
	    catch (IOException io){
	        System.out.println("failed to loadSelectedSet in CardTreePanel");
	    }
	}
	
	public void createNodes(ZipFile cardSet, CardTreeNode set) throws IOException {
	       //setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //showProgressBar();
	       // loadSet = new LoadSetTask(cardSet, set);
	        //loadSet.addPropertyChangeListener(this);
	        //loadSet.execute();
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
	
	
	public void propertyChange(PropertyChangeEvent evt) {
	    if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progBar.setValue(progress);
            progBar.repaint();
        } else if (evt.getPropertyName().equals("state")) {
            SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
            switch (state) {
                case DONE:
                    break;
            }
        }

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
			if (node.isLeaf()) {
			    if(node.getUserObject() instanceof Card) {
			        Card card = (Card) node.getUserObject();
			        setToolTipText(ToolTipHelper.makeToolTip(card));
			        setText(card.toString());
			    }
			    else {
			        setText(node.toString());
			    }
			}
			else {
			    setToolTipText((String) node.getUserObject());
			}
            return this;
		}
		
	}

    public int getSetDepth()
    {
        return SET_DEPTH;
    }
	
    class LoadSetTask extends SwingWorker<Void, Void> {
        ZipFile cardSet; 
        CardTreeNode set;
        
        public LoadSetTask(ZipFile cardSet, CardTreeNode set) {
            super();
            this.set = set;
            this.cardSet = cardSet;
        }
        
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() throws IOException {
            double progress = 0;
            //Initialize progress property.
            setProgress(0);
                //load sets
                CardTreeNode card;
                ArrayList<Card> theSet = CardReader.loadSet(cardSet);
                progress = 10;
                setProgress(10);
                    double incr = 90 / (double)theSet.size();
                    for(Card c : theSet){
                            card = new CardTreeNode(c);
                            set.add(card);
                            progress += incr;
                            setProgress(Math.min((int)Math.ceil(progress), 100));
                    }
                setProgress(100);
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null);
            progDialog.dispose();
        }
    }

}

