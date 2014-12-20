package GUI.DeckReferenceComplex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.print.DocFlavor.URL;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import Card.Card;
import GUI.CardInfoPanel;

public class RowCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer
{
    private boolean isBordered;

    /**
     * 
     * @param isBordered
     * @param c the Card from the row in which this cell resides. Used for tooltip.
     */
    public RowCellRenderer(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object cell,
                        boolean isSelected, boolean hasFocus, int row, int column) {
    	super.getTableCellRendererComponent(table, cell, isSelected, hasFocus, row, column);
    	setToolTipText(makeToolTip((Card)table.getValueAt(row, 1)));
    	if (cell instanceof Color) {
            setBackground((Color) cell);
            setText(null);
    	}
    	else {
    		setText(cell.toString());
    		if (!isSelected)
    			setBackground(Color.pink);
    	}

        return this;
    }
    
    public String makeToolTip(Card c) {
    	String imageAddress = "file:C:/Brett/workspace/Weiss/Database/images/" + c.getImagePath();
    	String str = "<html><body><textarea cols=\"30\" rows=\"20\" wrap=\"soft\">" + c.getDescription() +
    			"<img src=\"" + imageAddress + "\" width=\"250\" height=\"365\"></body></html>"; 		 
    	return str;
    }
}
