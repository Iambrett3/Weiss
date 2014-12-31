package GUI.DeckReferenceComplex.ImageLayoutView;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.activation.ActivationDataFlavor;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.tree.DefaultMutableTreeNode;

import Card.Card;
import GUI.DeckReferenceComplex.DRCController;
import GUI.DeckReferenceComplex.DeckTableTransferHandler.RowsTransferable;

public class ImageListTransferHandler extends TransferHandler{
	
	DataFlavor localObjectFlavor = new ActivationDataFlavor(Integer.class, "Integer Row Selection");
	
	JList imageList;
	DRCController controller;
	
	public ImageListTransferHandler(ImageList imageList, DRCController controller) {
		super();
		this.imageList = imageList.getList();
		this.controller = controller;
	}
	
	public boolean canImport(TransferSupport support) {
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
		Integer[] rows = new Integer[imageList.getSelectedIndices().length];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Integer(imageList.getSelectedIndices()[i]);
		}
		return new ListRowsTransferable(rows);
	}
	
	public int getSourceActions(JComponent comp) {
		return COPY_OR_MOVE;
	}
	
	public void exportDone(JComponent comp, Transferable trans, int action) {
		if (action== TransferHandler.MOVE) {
	         imageList.setCursor(null);
	      }
	}
	
	public boolean importData(TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}
		
		JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();	
		int row = dl.getIndex();
		int max = imageList.getModel().getSize();
		if (row < 0 || row > max)
	         row = max;
		
		if (support.getTransferable().isDataFlavorSupported(localObjectFlavor)) { //this means it is d&d within the table
			imageList.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
		            imageList.getSelectionModel().addSelectionInterval(row, row);
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

	
	private class ListRowsTransferable implements Transferable {  
        Integer[] rows;  
        
        public ListRowsTransferable(Integer[] rows) {  
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
