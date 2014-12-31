package Search;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.tree.DefaultTreeCellRenderer;

import WeissSchwarz.ToolTipHelper;
import Card.Card;
import GUI.CardTreeNode;
import GUI.DeckReferenceComplex.DeckTableTransferHandler;
import net.miginfocom.swing.MigLayout;

public class SearchResultsDialog extends JDialog{
	JList resultList;
	ResultListModel resultModel;
	ArrayList<Card> results;

	public SearchResultsDialog(ArrayList<Card> results, Frame parent) {
		super(parent, false);
		setLayout(new MigLayout());
		resultModel = new ResultListModel();
		resultList = new JList(resultModel);
		this.results = results;
		initList();
		JScrollPane resultScroll = new JScrollPane(resultList);
		JPanel resultPanel = new JPanel(new MigLayout());
		resultPanel.add(resultScroll);
		resultScroll.setPreferredSize(new Dimension(400, 500));
		add(new JLabel("Search Results:"), "wrap");
		add(resultPanel);
		
		resultList.setDragEnabled(true);
        resultList.setTransferHandler(new SearchResultsTransferHandler(this));
        resultList.setCellRenderer(new ListCellRenderer());
		setBounds(500, 150, 400, 500);
	}
	
	public void initList() {
		for (Card c: results) {
			resultModel.addCard(c);
		}
	}

	public JList getResultsList() {
		return resultList;
	}
	
	private class ListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Card card = (Card) value;
			        setToolTipText(ToolTipHelper.makeToolTip(card));
            return this;
		}
	}	
}
