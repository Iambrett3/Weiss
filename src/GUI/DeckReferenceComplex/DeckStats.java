package GUI.DeckReferenceComplex;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
import Card.Card;
import Card.CColor;
import Card.CColor.TypeC;
import Card.LevelCard;
import Card.Trigger;
import Card.Trigger.Type;
import WeissSchwarz.Deck;

public class DeckStats extends JPanel{
	private JTextArea deckView;
	private JTextArea mainStatsPane;
	private JTextArea triggerPane;
	private JTextArea colorPane;
	private JTextArea levelPane;
	private final Dimension STATS_PANE_DIMENSIONS = new Dimension(150, 100);
	
    /*
     * No-Arg constructor. Calls super constructor.
     */
    public DeckStats(Deck deck) {
        super();
        init(deck);
        
		//deckView -- should be temporary, just for debug purposes.
		JDialog deckViewer = new JDialog();
		deckViewer.setBounds(90, 90, 150, 400);
		deckViewer.setAlwaysOnTop(true);
		deckViewer.add(deckView = new JTextArea());
		//deckViewer.setVisible(true);
    }
    
    public void init(Deck deck) {
    	setLayout(new MigLayout());
    	initMainPane(deck);
    	initLevelPane(deck);
    	initTrigPane(deck);
    	initColorPane(deck);
    }
    
    public void initMainPane(Deck deck) {
        JScrollPane mainScroll = new JScrollPane();
        mainStatsPane = new JTextArea();
        mainStatsPane.setText(calculateMainStats(deck));
        mainStatsPane.setEditable(false);
        mainScroll.setViewportView(mainStatsPane);
        mainScroll.setPreferredSize(STATS_PANE_DIMENSIONS);
        add(mainScroll);
    }
    
    public void initTrigPane(Deck deck) {
    	JScrollPane trigScroll = new JScrollPane();
        triggerPane = new JTextArea();
        triggerPane.setText(calculateTriggerRatio(deck));
        triggerPane.setEditable(false);
        trigScroll.setViewportView(triggerPane);
        trigScroll.setPreferredSize(STATS_PANE_DIMENSIONS);
        add(trigScroll);
        trigScroll.getVerticalScrollBar().setValue(0);
        triggerPane.setCaretPosition(0); //this makes the scroll bar start at the top automatically
    }
    
    public void initColorPane(Deck deck) {
    	JScrollPane colorScroll = new JScrollPane();
        colorPane = new JTextArea();
        colorPane.setEditable(false);
        colorPane.setText(calculateColorRatio(deck));
        colorScroll.setViewportView(colorPane);
        colorScroll.setPreferredSize(STATS_PANE_DIMENSIONS);
        add(colorScroll);
    }
    
    public void initLevelPane(Deck deck) {
    	JScrollPane levelScroll = new JScrollPane();
        levelPane = new JTextArea();
        levelPane.setEditable(false);
        levelPane.setText(calculateLevelRatio(deck));
        levelScroll.setViewportView(levelPane);
        levelScroll.setPreferredSize(STATS_PANE_DIMENSIONS);
        add(levelScroll);
    }
    
    public static String calculateMainStats(Deck deck) {
        String str = "Deck Stats:\n";
        str += "Number of cards: " + deck.getNumberOfCardsInDeck();
        return str;
    }
    
	public static String calculateTriggerRatio(Deck deck) {
		int soul = 0;
		int doubleSoul = 0;
		int bounce = 0;
		int stock = 0;
		int salvage = 0;
		int draw = 0;
		int shot = 0;
		int treasure = 0;
		for (Card c: deck) {
			for (Trigger.Type type: c.getTrigger().getTriggers()) {
					if (type == Type.SOUL)
						soul++;
					if (type == Type.DOUBLE_SOUL)
						doubleSoul++;
					if (type == Type.RETURN)
						bounce++;
					if (type == Type.POOL)
						stock++;
					if (type == Type.COME_BACK)
						salvage++;
					if (type == Type.DRAW)
						draw++;
					if (type == Type.SHOT)
						shot++;
					if (type == Type.TREASURE)
						treasure++;
			}
		}
		String str = "Trigger Stats:"
				 + "\nSoul: " + soul
			     + "\n2 Soul: " + doubleSoul
			     + "\nBounce: " + bounce
			     + "\nStock: " + stock
			     + "\nSalvage: " + salvage
			     + "\nDraw: " + draw
			     + "\nShot: " + shot
			     + "\nTreasure: " + treasure;
		return str;
	}
	
	public static String calculateColorRatio(Deck deck) {
		int yellow = 0;
		int red = 0;
		int blue = 0;
		int green = 0;
		for (Card c: deck) {
			if (c.getColor().getType() == TypeC.YELLOW) 
				yellow++;
			if (c.getColor().getType() == TypeC.RED)
				red++;
			if (c.getColor().getType() == TypeC.BLUE)
				blue++;
			if (c.getColor().getType() == TypeC.GREEN)
				green++;
		}
		String str = "Color Stats:"
				+ "\nYellow: " + yellow
				+ "\nRed: " + red
				+ "\nBlue: " + blue
				+ "\nGreen: " + green;
		return str;
	}
	
	public static String calculateLevelRatio(Deck deck) {
		int zero = 0;
		int one = 0;
		int two = 0;
		int three = 0;
		for (Card c: deck) {
			if (c.getLevel()  == 0) 
				zero++;
			if (c.getLevel() == 1)
				one++;
			if (c.getLevel() == 2)
				two++;
			if (c.getLevel() == 3)
				three++;
		}
		String str = "Level Stats:"
				+ "\nLevel 0: " + zero
				+  "\nLevel 1: " + one
				+ "\nLevel 2: " + two
				+ "\nLevel 3: " + three;
		
		return str;
	}
	
	public void updateStats(Deck deck) {
		triggerPane.setText(calculateTriggerRatio(deck));
        colorPane.setText(calculateColorRatio(deck));
        levelPane.setText(calculateLevelRatio(deck));
        mainStatsPane.setText(calculateMainStats(deck));
	    deckView.setText(deck.toString());
	}
}
