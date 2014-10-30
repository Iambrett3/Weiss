package GUI.DeckReferenceComplex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;


public class SorterBox extends JComboBox {
	
	private static String[] args = {"sort by...", "Name", "Number", "Level", "Trigger", "Color"};
	
	public SorterBox() {
		super(args);
	}	
	
}
