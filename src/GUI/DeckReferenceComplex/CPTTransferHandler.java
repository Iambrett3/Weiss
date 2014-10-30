package GUI.DeckReferenceComplex;

import java.awt.List;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import Card.Card;
import Card.Chara;


public class CPTTransferHandler extends TransferHandler {
	JTree cardTree;
	
	public CPTTransferHandler(JTree cardTree) {
		this.cardTree = cardTree;
	}
	
	public int getSourceActions(JComponent comp) {
		return COPY_OR_MOVE;
	}
	
	
	public Transferable createTransferable(JComponent comp) {
		TreePath[] paths = cardTree.getSelectionPaths();
		if (paths != null) {
			ArrayList<DefaultMutableTreeNode> copies = new ArrayList<DefaultMutableTreeNode>();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) paths[0].getLastPathComponent();
			DefaultMutableTreeNode copy = (DefaultMutableTreeNode) node.clone();
			copies.add(copy);
			for (int i = 1; i < paths.length; i++) {
				DefaultMutableTreeNode next = 
						(DefaultMutableTreeNode)paths[i].getLastPathComponent();
				if (next.getLevel() < node.getLevel()) {
					break;
				} else if(next.getLevel() > node.getLevel()) {
					copy.add((DefaultMutableTreeNode)next.clone());
				}
				else {
					copies.add((DefaultMutableTreeNode)next.clone());
				}
			}
				DefaultMutableTreeNode[] nodes =
						copies.toArray(new DefaultMutableTreeNode[copies.size()]);
				return new NodesTransferable(nodes);
		}
		return null;
	}
	
	public void exportDone(JComponent comp, Transferable trans, int action) {
		return;
	}
	
	public class NodesTransferable implements Transferable {  
        DefaultMutableTreeNode[] nodes;  
   
        public NodesTransferable(DefaultMutableTreeNode[] nodes) {  
            this.nodes = nodes;  
         }  
   
        public Object getTransferData(DataFlavor flavor)  
                                 throws UnsupportedFlavorException {  
            if(!isDataFlavorSupported(flavor))  
                throw new UnsupportedFlavorException(flavor);  
            Card[] cards = new Card[nodes.length];
            for (int i = 0; i < nodes.length; i++) {
            	if (nodes[i].isLeaf()) {
            		cards[i] = (Card) nodes[i].getUserObject();
            	}
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
