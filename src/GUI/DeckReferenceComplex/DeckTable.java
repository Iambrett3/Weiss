package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import javax.swing.JComponent;

import WeissSchwarz.Deck;
import Card.Card;
import GUI.BuilderGUI;
import GUI.CardInfoPanel;

public class DeckTable extends JTable implements TableModelListener
{
    private DeckTableModel tableModel;
    private Deck deck;
    
    public DeckTable() {
        super();
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

