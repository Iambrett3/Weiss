package GUI.DeckReferenceComplex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;

import javax.print.DocFlavor.URL;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.sun.glass.events.MouseEvent;

import WeissSchwarz.ToolTipHelper;
import Card.CColor;
import Card.Card;
import GUI.CardInfoPanel;

public class DeckTableRowCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer
{
    private boolean isBordered;

    /**
     * 
     * @param isBordered
     * @param c the Card from the row in which this cell resides. Used for tooltip.
     */
    public DeckTableRowCellRenderer(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object cell,
                        boolean isSelected, boolean hasFocus, int row, int column) {
    	super.getTableCellRendererComponent(table, cell, isSelected, hasFocus, row, column);
    	TableColumn tableColumn = table.getColumnModel().getColumn(column);
    	tableColumn.setPreferredWidth(TablePopulator.getPreferredColumnWidth(column));
    	
    	setToolTipText(ToolTipHelper.makeToolTip((Card)table.getValueAt(row, DeckTable.getNameColumnNumber())));
            if (!isSelected) {
            	Color color = (Color)((DeckTableModel)table.getModel()).getRowColor(row);
            	setBackground(color);
            	if (color.equals(new Color(204, 0, 0)) || color.equals(new Color(0, 76, 153))) {
            		setForeground(Color.WHITE);
            	}
            	else {
            		setForeground(Color.BLACK);
            	}
            }
            if(cell instanceof Card) {
            	setText(((Card)cell).getName());
            }
            else if(cell instanceof Integer) {
            	if ((Integer) cell == -1) {
            		setText("--");
            	}
            	else {
            		setText(cell.toString());
            	}
            }
            else {
            	setText(cell.toString());
            }

        return this;
    }
}
