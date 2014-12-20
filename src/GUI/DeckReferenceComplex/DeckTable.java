package GUI.DeckReferenceComplex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolTip;
import javax.swing.ListSelectionModel;
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
import GUI.CardInfoPanel;

public class DeckTable extends JTable implements TableModelListener
{
    private DeckTableModel tableModel;
    private ArrayList<Integer> cardBuffer;
    private Deck deck;
    
    public DeckTable(Object[][] rows, String[] columns) {
        super(rows, columns);
        deck = new Deck();
       
        cardBuffer = new ArrayList<Integer>();
        //cost, traits, soul, power and type need to be added to the list
        //setModel(tableModel = new DeckTableModel(columns));
        setModel(tableModel = new DeckTableModel(columns));
        tableModel.addTableModelListener(this);
        setCellSelectionEnabled(true);
        
        ListSelectionModel cellSelectionModel = getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                    cardBuffer.clear();
                    for (int row: getSelectedRows()) {
                        cardBuffer.add(row);
                }
            }
            
        });    
    }
    
    
    public DeckTable(String[] columns) {
       // super(columns);
    }
    
    public void tableChanged(TableModelEvent e) {
        
    }
    
    public void addCard(Card c) {
        tableModel.addCard(c);
    }


    public Deck getDeck()
    {
        return deck;
    }
    
}

