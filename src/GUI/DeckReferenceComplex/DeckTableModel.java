package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Card.CColor;
import Card.Card;
import Card.Climax;
import Card.Event;
import WeissSchwarz.Deck;

public class DeckTableModel extends DefaultTableModel implements TableModel
{
    String[] columns;
    ArrayList<Color> rowColors;
    
    public DeckTableModel(String[] columns) {
        super(columns, 0);
        rowColors = new ArrayList<Color>();
        this.columns = columns;
    }
    
    public DeckTableModel() {
        
    }
    
    //called when sorting
    public void forceUpdateRowColors() {
    	int i = 0;
    	for (Object v: dataVector) {
    		Card card = (Card)((Vector) v).get(DeckTable.getNameColumnNumber());
    		CColor color = card.getColor();
    		rowColors.set(i, color.getTrueColor());
    		i++;
    	}
    }
    
    public void addRowColor(int row, Color color) { 
    	if (row > dataVector.size()) {
    		rowColors.add(color);
    	}
    	rowColors.add(row, color);
        fireTableRowsUpdated(row, row);
    }
    
    public void removeRowColor(int row) {
    	rowColors.remove(row);
    	fireTableRowsUpdated(0, dataVector.size() - 1);
    }
    
    public Color getRowColor(int row) {
        return rowColors.get(row);
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
    public Class<?> getColumnClass(int c) {
        if (dataVector.isEmpty()) {
            return Object.class;
        }
        if (c == DeckTable.getQuantityColumnNumber() || c == DeckTable.getLevelColumnNumber() || c == DeckTable.getSoulColumnNumber() 
        		|| c == DeckTable.getCostColumnNumber()) {
            return Object.class; //these are Integers but when I return Integer, setBackground doesn't work??? this is probably a bug...
        }
        return getValueAt(0, c).getClass();
    }
    
    //this method inserts cards in reverse order because it is used by the reorder method
    //which reverses the order of cards while removing them
    public void insertCards(Vector[] cards, int pos) {
    	for (int i = 0; i < cards.length; i++) {
    		dataVector.add(pos, cards[i]);
    		addRowColor(pos, ((Card)cards[i].get(DeckTable.getNameColumnNumber())).getColor().getTrueColor());
    	}
    	fireTableStructureChanged();
    }
    
//    public synchronized void reorder(Integer from, int to) {
//    	if (to == dataVector.size()) {
//    		dataVector.add(dataVector.get(from.intValue()));
//    		dataVector.remove(from.intValue());
//    		fireTableStructureChanged();
//    	}
//    	else if (to > from) {
//    		Vector fromVector = (Vector) dataVector.get(from);
//    		dataVector.insertElementAt(fromVector, to);
//    		dataVector.remove(from.intValue());
//    		fireTableStructureChanged();
//    	}
//    	else if (from > to) {
//    		Vector fromVector = (Vector) dataVector.get(from);
//    		dataVector.remove(from.intValue());
//    		dataVector.insertElementAt(fromVector, to);
//    		fireTableStructureChanged();
//    	}
//    	else {
//    		return;
//    	}


//    }
    
    public void insertCard(int pos, Card c) {
    	Vector vectorizedCard = Card.vectorizeCard(c);
    	dataVector.insertElementAt(vectorizedCard, pos);
    	addRowColor(pos, c.getColor().getTrueColor());
    	fireTableStructureChanged();
    	fireTableRowsUpdated(0, dataVector.size() -1);
    	fireTableRowsInserted(pos, pos);
    }
    
    public void addCard(Card c) {
    	Vector vectorizedCard = Card.vectorizeCard(c);
    	dataVector.add(vectorizedCard);
    	addRowColor(dataVector.size() - 1, c.getColor().getTrueColor());
    	fireTableStructureChanged();
    	fireTableRowsUpdated(0, dataVector.size() - 1);
    	fireTableRowsInserted(dataVector.size() - 1, dataVector.size() - 1);
    }
    
    public synchronized void removeCard(final int c) {
    	dataVector.remove(c);
    	removeRowColor(c);
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
					String cardA = ((Card) a.get(DeckTable.getNameColumnNumber())).getName();
					String cardB = ((Card) b.get(DeckTable.getNameColumnNumber())).getName();
					String aString = cardA.replaceAll("[^a-zA-Z0-9]", "");
			    	String bString = cardB.replaceAll("[^a-zA-Z0-9]", "");
					return (aString.compareTo(bString));
				}
			});
			forceUpdateRowColors();
			break;
		case "Level":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector vA, Vector vB) {
					Card a = (Card) vA.get(DeckTable.getNameColumnNumber());
					Card b = (Card) vB.get(DeckTable.getNameColumnNumber());
			    	 if (a instanceof Climax) { //climaxes should sort to be after all level cards
			    		 if (b instanceof Climax) {
			    			 return 0;
			    		 }
			    		 return 1;
			    	 }
			    	 if (b instanceof Climax) {
			    		 return -1;
			    	 }
			       return Integer.compare(a.getLevel(), b.getLevel());
			     }
			   });
			forceUpdateRowColors();
			break;
		case "Soul":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector vA, Vector vB) {
					Card a = (Card) vA.get(DeckTable.getNameColumnNumber());
					Card b = (Card) vB.get(DeckTable.getNameColumnNumber());
			    	 if (a instanceof Climax || a instanceof Event) { //climaxes should sort to be after all level cards
			    		 if (b instanceof Climax || b instanceof Event) {
			    			 return 0;
			    		 }
			    		 return 1;
			    	 }
			    	 if (b instanceof Climax || b instanceof Event) {
			    		 return -1;
			    	 }
			       return Integer.compare(a.getSoul(), b.getSoul());
			     }
			   });
			forceUpdateRowColors();
			break;
		case "Cost":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector vA, Vector vB) {
					Card a = (Card) vA.get(DeckTable.getNameColumnNumber());
					Card b = (Card) vB.get(DeckTable.getNameColumnNumber());
			    	 if (a instanceof Climax) { //climaxes should sort to be after all level cards
			    		 if (b instanceof Climax) {
			    			 return 0;
			    		 }
			    		 return 1;
			    	 }
			    	 if (b instanceof Climax) {
			    		 return -1;
			    	 }
			       return Integer.compare(a.getCost(), b.getCost());
			     }
			   });
			forceUpdateRowColors();
			break;
		case "Color":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return ((Card) a.get(DeckTable.getNameColumnNumber())).getColor().toString().compareTo(((Card) b.get(DeckTable.getNameColumnNumber())).getColor().toString());
				}
			});
			forceUpdateRowColors();
			break;
		case "Trigger":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return ((Card) a.get(DeckTable.getNameColumnNumber())).getTrigger().toString().compareTo(((Card) b.get(DeckTable.getNameColumnNumber())).getTrigger().toString());
				}
			});
			forceUpdateRowColors();
			break;
		case "Number":
			Collections.sort(dataVector, new Comparator<Vector>() {
				public int compare(Vector a, Vector b) {
					return ((Card) a.get(DeckTable.getNameColumnNumber())).getNumber().compareTo(((Card) b.get(DeckTable.getNameColumnNumber())).getNumber());
				}
			});
			forceUpdateRowColors();
			break;
		default:
			break;
		}
		fireTableStructureChanged();
		fireTableRowsUpdated(0, dataVector.size()-1);
	}

}




