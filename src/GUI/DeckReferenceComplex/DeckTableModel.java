package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Card.Card;
import WeissSchwarz.Deck;

public class DeckTableModel extends DefaultTableModel implements TableModel
{
    String[] columns;
    
    public DeckTableModel(String[] columns) {
        super(columns, 0);
        this.columns = columns;
    }
    
    public DeckTableModel() {
        
    }
    public int getColumnCount() {
        return columns.length;
    }
    
    public int getRowCount() {
        return super.getRowCount();
    }
    
    public Vector getRow(int row) {
    	return (Vector) dataVector.get(row);
    }
    
    public Object getValueAt(int row, int col) {
        return ((Vector) dataVector.get(row)).get(col);
    }
    
    /**
     * This may be questionable?
     */
    public Class getColumnClass(int c) {
    	if (getRowCount() < 1) return Object.class;
        if (getValueAt(0, c) instanceof Color) {
            return Color.class;
        }
        else {
            return Object.class;
        }
    }
    
    //this method inserts cards in reverse order because it is used by the reorder method
    //which reverses the order of cards while removing them
    public void insertCards(Vector[] cards, int pos) {
    	for (int i = 0; i < cards.length; i++) {
    		dataVector.add(pos, cards[i]);
    	}
    	fireTableStructureChanged();
    }
    
    public synchronized void reorder(Integer from, int to) {
    	if (to == dataVector.size()) {
    		dataVector.add(dataVector.get(from.intValue()));
    		dataVector.remove(from.intValue());
    		fireTableStructureChanged();
    	}
    	else if (to > from) {
    		Vector fromVector = (Vector) dataVector.get(from);
    		dataVector.insertElementAt(fromVector, to);
    		dataVector.remove(from.intValue());
    		fireTableStructureChanged();
    	}
    	else if (from > to) {
    		Vector fromVector = (Vector) dataVector.get(from);
    		dataVector.remove(from.intValue());
    		dataVector.insertElementAt(fromVector, to);
    		fireTableStructureChanged();
    	}
    	else {
    		return;
    	}


    }
    
    public void insertCard(int pos, Card c) {
    	dataVector.insertElementAt(Card.vectorizeCard(c), pos);
    	fireTableStructureChanged();
    	fireTableRowsUpdated(0, dataVector.size() -1);
    	fireTableRowsInserted(pos, pos);
    }
    
    public void addCard(Card c) {
    	dataVector.add(Card.vectorizeCard(c));
    	fireTableStructureChanged();
    	fireTableRowsUpdated(0, dataVector.size() - 1);
    	fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
    }
    
    public synchronized void removeCard(final int c) {
    	System.out.println(c);
    	dataVector.remove(c);
    	fireTableRowsDeleted(c - 1, c - 1);
    	
    }
    
    /**
     * cells are never editable.
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }
    
    public void setValueAt(Object value, int row, int col) {
        super.setValueAt(value, row, col);
        fireTableCellUpdated(row, col);
    }

	@SuppressWarnings("unchecked")
	public void sortBy(String selected) {
		switch (selected) {
		case "Name":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return ((Card) a.get(0)).getName().compareTo(((Card) b.get(0)).getName());
				}
			});
			break;
		case "Level":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return Integer.compare(((Card)a.get(0)).getLevel(), ((Card)b.get(0)).getLevel());
				}
			});
			break;
		case "Color":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return ((Card) a.get(0)).getColor().toString().compareTo(((Card) b.get(0)).getColor().toString());
				}
			});
			break;
		case "Trigger":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return ((Card) a.get(0)).getTrigger().toString().compareTo(((Card) b.get(0)).getTrigger().toString());
				}
			});
			break;
		case "Number":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return ((Card) a.get(0)).getNumber().compareTo(((Card) b.get(0)).getNumber());
				}
			});
			break;
		default:
			break;
		}
		fireTableStructureChanged();
		fireTableRowsUpdated(0, dataVector.size()-1);
	}

}




