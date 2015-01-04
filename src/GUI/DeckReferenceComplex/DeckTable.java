package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JComponent;

import com.lowagie.text.Rectangle;

import WeissSchwarz.Deck;
import Card.Card;
import GUI.BuilderGUI;
import GUI.CardInfoPanel;

public class DeckTable extends JTable implements TableModelListener
{
    private DeckTableModel tableModel;
    private Deck deck;
    
    
    
    final static private int NAME_COLUMN_NUMBER = 2;
    final static private int QUANTITY_COLUMN_NUMBER = 0;
    final static private int NUMBER_COLUMN_NUMBER = 1;
    final static private int TRIGGER_COLUMN_NUMBER = 3;
    final static private int LEVEL_COLUMN_NUMBER = 4;
    final static private int COST_COLUMN_NUMBER = 6;
    final static private int SOUL_COLUMN_NUMBER = 5;
    
    private static Integer[] columnWidths = TablePopulator.getPreferredColumnWidths();
    
    
	public DeckTable() {
        super();
    }
	
//	public Point getToolTipLocation(MouseEvent event) {
////		int x = event.getX();
////		int y = event.getY();
////		int row = rowAtPoint(new Point(x, y));
////		int hoveringColumn = columnAtPoint(new Point(x, y));
////		int column = Math.min(hoveringColumn + 2, getColumnCount());
////		java.awt.Rectangle rect = getCellRect(row, column, false);
////		return new Point((int)rect.getX(), (int)rect.getY());
//	}
	
	public void updateColumnWidths() {
	    for (int i = 0; i < columnWidths.length; i++) {
	        columnWidths[i] = getColumnModel().getColumn(i).getWidth();
	    }
	}
	
	public void setColumnWidths() {
        for (int i = 0; i < columnWidths.length; i++) {

            TableColumn column = getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
    }
	
	public static Integer[] getColumnWidths() {
	    return columnWidths;
	}
    
    public static int getQuantityColumnNumber() {
		return QUANTITY_COLUMN_NUMBER;
	}

	public static int getNumberColumnNumber() {
		return NUMBER_COLUMN_NUMBER;
	}

	public static int getTriggerColumnNumber() {
		return TRIGGER_COLUMN_NUMBER;
	}

	public static int getLevelColumnNumber() {
		return LEVEL_COLUMN_NUMBER;
	}

	public static int getCostColumnNumber() {
		return COST_COLUMN_NUMBER;
	}

	public static int getSoulColumnNumber() {
		return SOUL_COLUMN_NUMBER;
	}
    
    public static int getNameColumnNumber() {
    	return NAME_COLUMN_NUMBER;
    }
    
    public void setDeck(Deck deck) {
        this.deck = deck;
    }
    
    public DeckTableModel getDeckTableModel() {
        return (DeckTableModel)getModel();
    }
    
    public void sortBy(final String column) {
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                getDeckTableModel().sortBy(column);
            }
        });
        deck.sortBy(column);
    }

    
}

