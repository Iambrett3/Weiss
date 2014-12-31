package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import WeissSchwarz.CardReader;
import WeissSchwarz.ImageLoader;
import net.miginfocom.swing.MigLayout;
import Card.Card;
import GUI.DeckReferenceComplex.ImageSelection;
/**
 * Class is not current used!
 * @author User
 *
 */

public class CardInfoPanel extends JDialog{
	ImageSelection image;
	JTextPane cardStats;
	int h;
	int w;
	Card c;
	
	public CardInfoPanel(Card c, Frame parent) {
		super(parent, false);
		this.c = c;
		init();
		setBounds(90, 90, 0, 0);
		pack();
		setVisible(true);
		}
	
	public void init() {
		getContentPane().setLayout(new MigLayout());
		getContentPane().add(image = new ImageSelection(ImageLoader.loadImage(c)));
		setTitle("Card: " + c.toString());
		cardStats = new JTextPane();
        cardStats.setEditable(false);
        cardStats.setText(c.getDescription());
        JScrollPane cardStatsPane = new JScrollPane(cardStats);
        cardStatsPane.setPreferredSize(new Dimension(200, 365));
        //w = cardStatsPane.getWidth() + image.getWidth() + 50;
        //h = image.getHeight();
        getContentPane().add(cardStatsPane);
	}
}