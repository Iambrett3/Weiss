package GUI.DeckReferenceComplex;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

import Card.Card;

public class DeckTableTransferHandler extends TransferHandler {
	DRCController controller;
	JTable deckTable;
	DataFlavor localObjectFlavor = new ActivationDataFlavor(Integer.class, "Integer Row Selection");
	
	public DeckTableTransferHandler(DRCController controller) {
		super();
		this.controller = controller;
		deckTable = controller.getDeckTable();
	}
	
	public boolean canImport(TransferSupport support ) {
		if (!support.isDrop()) { //only supports drops, change this
			return false;
		}
		if (!support.isDataFlavorSupported(new DataFlavor(Card.class, "Card")) &&
				!support.isDataFlavorSupported(localObjectFlavor)) {
			return false; //only import Card and Integer objects
		}
		return true;
	}
	
	
	public Transferable createTransferable(JComponent c) {
		Integer[] rows = new Integer[deckTable.getSelectedRows().length];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Integer(deckTable.getSelectedRows()[i]);
		}
		return new RowsTransferable(rows);
	}

	public int getSourceActions(JComponent comp) {
		return COPY_OR_MOVE;
	}
	
	public void exportDone(JComponent comp, Transferable trans, int action) {
		if (action== TransferHandler.MOVE) {
	         deckTable.setCursor(null);
	      }
	}
	
	public boolean importData(TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}
		
		JTable.DropLocation dl = (JTable.DropLocation) support.getDropLocation();	
		int row = dl.getRow();
		int max = deckTable.getModel().getRowCount();
		if (row < 0 || row > max)
	         row = max;
		
		if (support.getTransferable().isDataFlavorSupported(localObjectFlavor)) { //this means it is d&d within the table
			deckTable.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			try {
		         Integer[] rowsFrom = (Integer[]) support.getTransferable().getTransferData(localObjectFlavor);
		         Integer[] validRows = new Integer[rowsFrom.length]; //going to transfer all valid rows to this array for reordering
		         int validCounter = 0;
		         for (int i = 0; i < rowsFrom.length; i++) {
		        	 if (rowsFrom[i] != -1) {
		        		 validRows[validCounter] = rowsFrom[i];
		        		 validCounter++;
		        	 }
		         }
		            controller.reorder(validRows, row);
		            if (row > validRows[validCounter - 1])
		               row--;
		            deckTable.getSelectionModel().addSelectionInterval(row, row);
		            return true;
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		}
		
		Card[] cards;
		try {
			cards = (Card[]) support.getTransferable().getTransferData(
						new DataFlavor(Card.class, "Card"));
		} catch (UnsupportedFlavorException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		
		for (int i = 0; i < cards.length; i++) {
			if (cards[i] instanceof Card) {
				controller.insertCard(row++, cards[i]);
			}
		}
		return true;
	}
	
	public class RowsTransferable implements Transferable {  
        Integer[] rows;  
   
        public RowsTransferable(Integer[] rows) {  
            this.rows = rows;
         }  
   
        public Object getTransferData(DataFlavor flavor)  
                                 throws UnsupportedFlavorException {  
            if(!isDataFlavorSupported(flavor))  
                throw new UnsupportedFlavorException(flavor);  
            return rows;
        }  
   
        public DataFlavor[] getTransferDataFlavors() {  
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = localObjectFlavor;
            return flavors;
        }  
   
        public boolean isDataFlavorSupported(DataFlavor flavor) {  
            return flavor.equals(localObjectFlavor);  
        }  
    }
}
