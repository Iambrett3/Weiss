package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

import WeissSchwarz.WeissFileChooser;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.miginfocom.swing.MigLayout;
import Card.*;
import GUI.DeckReferenceComplex.DeckReferenceComplexPanel;
import GUI.DeckReferenceComplex.DeckTable;
import GUI.DeckReferenceComplex.SwingHelp;
import GUI.DeckReferenceComplex.ImageLayoutView.ImageList;
import Search.SearchDialog;
import WeissSchwarz.Deck;
import WeissSchwarz.DeckExporter;
import WeissSchwarz.DeckFileHandler;

public class BuilderGUI extends JFrame {
	ImageList deckList;
	DeckReferenceComplexPanel refComplex;
	CardTreePanel cardTreePanel;
	JButton addCard;
	JButton removeCard;
	JButton loadSetButton;
	JPanel buttonPanel;
	BuilderController controller;
	JMenuBar menuBar;
	File workingFile;
	String currentView;
	JSplitPane splitPane;
	
	public BuilderGUI() throws IOException {
		super("Deck Builder" + " - *New Deck");
		setLayout(new BorderLayout());
		setSize(1100, 700);
		setLocation(new Point(20, 20));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createButtons(); 
		
		cardTreePanel = new CardTreePanel();
		JScrollPane cardTreeScrollPane = new JScrollPane(cardTreePanel);
		
		refComplex = new DeckReferenceComplexPanel(this);
		//JScrollPane refComplexScrollPane = new JScrollPane(refComplex);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                cardTreeScrollPane, refComplex);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(300);
		
		add(splitPane, BorderLayout.CENTER);
		
		//add(cardTreePanel = new CardTreePanel());
		//add(refComplex = new DeckReferenceComplexPanel(this), "wrap"); //do i need to add to content pane here?
		//add(buttonPanel);
		
		controller = new BuilderController(refComplex, cardTreePanel);
		menuBar = new JMenuBar();
		initMenuBar();
		setJMenuBar(menuBar);
		currentView = "Deck Table View";
		
        initToolTipManager();
    }
	
	public void changeView() {
		if (currentView.equals("Deck Table View")) {
			refComplex.switchToImageLayoutView();
    		refComplex.repaint();
    		currentView = "Image Layout View";
		}
		else {
			refComplex.switchToDeckTableView();
    		refComplex.repaint();
    		currentView = "Deck Table View";
		}
	}
	
	public void initMenuBar() {
	    initFileMenu();
	    initViewMenu();  
	    initSearchMenu();
	    initSortMenu();
	}
	
	public boolean isSaved() {
		return refComplex.getDeck().isSaved();
	}
	
	public void initSearchMenu() {
		JMenu searchMenu = new JMenu("Search");
		menuBar.add(searchMenu);
		
		JMenuItem menuItem = new JMenuItem("Search");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SearchDialog(SwingHelp.getOwningFrame(cardTreePanel.getCardTree()), controller);
			}
		});
		searchMenu.add(menuItem);
	}
	
	public void initSortMenu() {
        JMenu sortMenu = new JMenu("Sort By");
        JMenuItem menuItem;
        
        
        menuItem = new JMenuItem("Number");
        menuItem.addActionListener(new SorterHandler());
        sortMenu.add(menuItem);
        
        menuItem = new JMenuItem("Name");
        menuItem.addActionListener(new SorterHandler());
        sortMenu.add(menuItem);
        
        menuItem = new JMenuItem("Color");
        menuItem.addActionListener(new SorterHandler());
        sortMenu.add(menuItem);
        
        menuItem = new JMenuItem("Trigger");
        menuItem.addActionListener(new SorterHandler());
        sortMenu.add(menuItem);
        
        menuItem = new JMenuItem("Level");
        menuItem.addActionListener(new SorterHandler());
        sortMenu.add(menuItem);
        
        menuItem = new JMenuItem("Soul");
        menuItem.addActionListener(new SorterHandler());
        sortMenu.add(menuItem);
        
        menuItem = new JMenuItem("Cost");
        menuItem.addActionListener(new SorterHandler());
        sortMenu.add(menuItem);
        
        menuBar.add(sortMenu);
	}
	
	public void initFileMenu() {
	    JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!isSaved() && workingFile != null) {
    				int n = JOptionPane.showConfirmDialog(
    					    BuilderGUI.this,
    					    "Your current deck hasn't been saved. Would you like to save it before continuing?",
    					    "Your deck is unsaved!",
    					    JOptionPane.YES_NO_CANCEL_OPTION);
    				if (n == JOptionPane.YES_OPTION) {
    					int saveReturnValue = saveFile();
    						if (saveReturnValue == WeissFileChooser.CANCEL_OPTION){
    							return;
    						}
    				}
    				else if (n == JOptionPane.CANCEL_OPTION
    						|| n == JOptionPane.CLOSED_OPTION) {
    					return;
    				}
        		}
        		refComplex.getController().clearDeck();
        		refComplex.getDeck().setIsSaved(false);
        		workingFile = null;
        		setTitle("Deck Builder" + " - New Deck*");
        	}
        });
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        fileMenu.add(menuItem);
        
        menuItem = new JMenuItem("Open File...");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (!isSaved() && workingFile != null) {
    				int n = JOptionPane.showConfirmDialog(
    					    BuilderGUI.this,
    					    "Your current deck hasn't been saved. Would you like to save it before continuing?",
    					    "Your deck is unsaved!",
    					    JOptionPane.YES_NO_CANCEL_OPTION);
    				if (n == JOptionPane.YES_OPTION) {
    					int saveReturnValue = saveFile();
    						if (saveReturnValue == WeissFileChooser.CANCEL_OPTION){
    							return;
    						}
    				}
    				else if (n == JOptionPane.CANCEL_OPTION
    						|| n == JOptionPane.CLOSED_OPTION) {
    					return;
    				}
        		}
        		
        		//open dialog
        		WeissFileChooser fc = new WeissFileChooser();
        		
        		FileNameExtensionFilter filter = new FileNameExtensionFilter(".dck", "dck");
                fc.setFileFilter(filter);
                
        		fc.setCurrentDirectory(new File("My Decks/"));
        		
        		int returnVal = fc.showOpenDialog(BuilderGUI.this);
        		
        		if (returnVal == WeissFileChooser.APPROVE_OPTION) {
        			File file = fc.getSelectedFile();
        			Deck importedDeck = DeckFileHandler.loadDeck(file);
        			controller.importDeck(importedDeck);
        			workingFile = file;
        			setTitle("Deck Builder - " + file.getName());
        			refComplex.getDeck().setIsSaved(true);
        		}
        	}
        });
        
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        fileMenu.add(menuItem);
        
        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        			saveFile();	
        	}
        });
        
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(menuItem);
        
        menuItem = new JMenuItem("Save As...");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		saveFileAs();
        	}
        });
        fileMenu.add(menuItem);
//        
//        menuItem = new JMenuItem("Import...");
//        menuItem.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		System.out.println(DeckFileHandler.importDeck());
//        	}
//        });
//        fileMenu.add(menuItem);
//        
        
        JMenu exportMenu = new JMenu("Export as...");
        fileMenu.add(exportMenu);
        
        menuItem = new JMenuItem("Text File");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		WeissFileChooser fc = new WeissFileChooser();
        		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
        		fc.setFileFilter(filter);
        		fc.setCurrentDirectory(new File("Exports/"));
        		fc.setSelectedFile(new File("MyDeckTxt.txt")); 
        		int returnVal = fc.showSaveDialog(BuilderGUI.this);
        		
        		if (returnVal == WeissFileChooser.APPROVE_OPTION) {
        			File txtFile = fc.getSelectedFile();
            		DeckExporter.exportAsTextFile(refComplex.getDeck(), txtFile);
        		}
        	}
        });
        exportMenu.add(menuItem);
        
        menuItem = new JMenuItem("Translation Sheet (PDF)");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		WeissFileChooser fc = new WeissFileChooser();
        		FileNameExtensionFilter filter = new FileNameExtensionFilter(".pdf", "pdf");
        		fc.setFileFilter(filter);
        		fc.setCurrentDirectory(new File("Exports/"));
        		fc.setSelectedFile(new File("MyTranslationSheet.pdf"));
        		
        		int returnVal = fc.showSaveDialog(BuilderGUI.this);
        		
        		if (returnVal == WeissFileChooser.APPROVE_OPTION) {
        			File pdfFile = fc.getSelectedFile();
            		DeckExporter.exportAsTranslationSheet(refComplex.getDeck(), pdfFile);
        		}
        	}
        });
        exportMenu.add(menuItem);
	}
	
	public void addStarToTitle() {
		if (getTitle().contains("*")) {
			return;
		}
		setTitle("Deck Builder - *" + workingFile.getName());
	}
	
	public void removeStarFromTitle() {
		if (!getTitle().contains("*")) {
			return;
		}
		setTitle("Deck Builder - " + workingFile.getName());
	}
	
	//return -1 if dialog was not shown.
	public int saveFile() {
		if (workingFile == null) {
			WeissFileChooser fc = new WeissFileChooser();
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter(".dck", "dck");
            fc.setFileFilter(filter);
            
            
			fc.setSelectedFile(new File("My Deck.dck"));
			fc.setCurrentDirectory(new File("My Decks/"));
        	
    		int returnVal = fc.showSaveDialog(BuilderGUI.this);

    		if (returnVal == WeissFileChooser.APPROVE_OPTION) {
    			File file = fc.getSelectedFile();
    			workingFile = file;
    			DeckFileHandler.saveDeck(refComplex.getDeck(), workingFile);
    			setTitle("Deck Builder - " + file.getName());
    			refComplex.getDeck().setIsSaved(true);
    			removeStarFromTitle();
    		}
			return returnVal;
		}
		else {
			DeckFileHandler.saveDeck(refComplex.getDeck(), workingFile);
			refComplex.getDeck().setIsSaved(true);
			removeStarFromTitle();
			return -1;
		}

	}
	
	public void saveFileAs() {
		WeissFileChooser fc = new WeissFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".dck", "dck");
        fc.setFileFilter(filter);
		
		fc.setSelectedFile(new File("My Deck.dck")); 
		fc.setCurrentDirectory(new File("Exports/"));
    	
		int returnVal = fc.showSaveDialog(BuilderGUI.this);

		if (returnVal == WeissFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			DeckFileHandler.saveDeck(refComplex.getDeck(), file);
			workingFile = file;
			setTitle("Deck Builder - " + file.getName());
			refComplex.getDeck().setIsSaved(true);
			removeStarFromTitle();
		} 
	}
	
	public void initViewMenu() {
        JMenu viewMenu = new JMenu("View");
        menuBar.add(viewMenu);
        
        JMenuItem menuItem = new JMenuItem("Deck Table View");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (currentView.equals("Deck Table View")) {
        			return;
        		}
        		refComplex.switchToDeckTableView();
        		refComplex.repaint();
        		currentView = "Deck Table View";
        	}
        });
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        viewMenu.add(menuItem);
        
        menuItem = new JMenuItem("Image Layout View");
        menuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (currentView.equals("Image Layout View")) {
        			return;
        		}
        		refComplex.switchToImageLayoutView();
        		refComplex.repaint();
        		currentView = "Image Layout View";
        	}
        });
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        viewMenu.add(menuItem);
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
	    //initLoadButton();
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
	

    private class SorterHandler implements ActionListener {
    	public void actionPerformed(ActionEvent e) {
    		refComplex.sortBy(e.getActionCommand());
    	}
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

				     
