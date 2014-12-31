package Search;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.activation.ActivationDataFlavor;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;

import Card.Card;
import GUI.DeckReferenceComplex.DeckTableTransferHandler.RowsTransferable;

public class SearchResultsTransferHandler extends TransferHandler{
	
	DataFlavor localObjectFlavor = new ActivationDataFlavor(Integer.class, "Integer Row Selection");
	
	JList resultsList;
	
	public SearchResultsTransferHandler(SearchResultsDialog dialog) {
		resultsList = dialog.getResultsList();
	}
	
	public boolean canImport() {
		return false;
	}
	
	public Transferable createTransferable(JComponent c) {
		Integer[] rows = new Integer[resultsList.getSelectedIndices().length];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Integer(resultsList.getSelectedIndices()[i]);
		}
		return new ListRowsTransferable(rows);
	}
	
	public int getSourceActions(JComponent comp) {
		return COPY_OR_MOVE;
	}
	
	public void exportDone(JComponent comp, Transferable trans, int action) {
		if (action== TransferHandler.MOVE) {
	         resultsList.setCursor(null);
	      }
	}

	
	private class ListRowsTransferable implements Transferable {  
        Integer[] rows;  
   
        public ListRowsTransferable(Integer[] rows) {  
            this.rows = rows;  
         }  
   
        public Object getTransferData(DataFlavor flavor)  
                                 throws UnsupportedFlavorException {  
            if(!isDataFlavorSupported(flavor))  
                throw new UnsupportedFlavorException(flavor);  
            Card[] cards = new Card[rows.length];
            for (int i = 0; i < rows.length; i++) {
            		cards[i] = (Card) resultsList.getModel().getElementAt(rows[i]);
            }
            return cards;
        }  
   
        public DataFlavor[] getTransferDataFlavors() {  
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = new DataFlavor(Card.class, "card");
            return flavors;
        }  
   
        public boolean isDataFlavorSupported(DataFlavor flavor) {  
            return flavor.equals(new DataFlavor(Card.class, "card"));  
        }  
    }  
}
