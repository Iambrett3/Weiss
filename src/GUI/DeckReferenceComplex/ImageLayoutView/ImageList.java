package GUI.DeckReferenceComplex.ImageLayoutView;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.control.Tooltip;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;
import WeissSchwarz.Deck;
import WeissSchwarz.ImageLoader;
import WeissSchwarz.ToolTipHelper;
import Card.*;
import GUI.DeckReferenceComplex.DeckStats;
import GUI.DeckReferenceComplex.ImageSelection;

public class ImageList extends JPanel {
	
	ImageListModel imageListModel;
	JList imageView;
	DefaultTableModel model;
	ArrayList<Integer> cardBuffer;
	
	public ImageList() {
		setLayout(new BorderLayout());
		cardBuffer = new ArrayList<Integer>();
		
		imageListModel = new ImageListModel();
		imageView = new JList<Card>(imageListModel);
		imageView.setLayout(new MigLayout());
		imageView.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		imageView.setVisibleRowCount(-1);
		imageView.setBackground(Color.lightGray);
		imageView.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		imageView.setCellRenderer(new ImageListCellRenderer());

		ListSelectionModel listSelectionModel = imageView.getSelectionModel();
		listSelectionModel.addListSelectionListener(new SelectionHandler());
		
		add(imageView);
		}
	
	
	public void addCard(Card card) {
		imageListModel.addCard(card);
	}
	
	public void insertCard(int pos, Card card) {
		imageListModel.insertCard(pos, card);
	}
	
	public void insertCards(Card[] cards, int to) {
		imageListModel.insertCards(cards, to);
	}
	
	public void removeCard(Card card) {
		imageListModel.removeCard(card);
	}
	
	public void removeCard(int i) {
		imageListModel.removeCard(i);
	}
	
	public void removeCards(ArrayList<Integer> cards) {
		imageListModel.removeCards(cards);
	}
	
	public ArrayList<Integer> getCardBuffer() {
		return cardBuffer;
	}
	
	public void setCardBuffer(ArrayList<Integer> cardBuffer) {
		this.cardBuffer = cardBuffer;
	}
		
	public void resetCardBuffer() {
		cardBuffer.clear();
	}
	
	public ImageListModel imageListModel() {
		return imageListModel;
	}
	
	public Card getSelectedCard() {
		return imageListModel.getImages().get(imageView.getSelectedIndex());
	}
	

	private class SelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			resetCardBuffer();
			if (!e.getValueIsAdjusting()) {
			ListSelectionModel lsm = (ListSelectionModel)e.getSource();
			int minIndex = lsm.getMinSelectionIndex();
			int maxIndex = lsm.getMaxSelectionIndex();
				for (int i = minIndex; i <= maxIndex; i++) {
					if (lsm.isSelectedIndex(i)) {
						cardBuffer.add(i);	
					}
				}
			}
		}
	}
	
	private class ImageListCellRenderer extends DefaultListCellRenderer {
		public Component getListCellRendererComponent(JList<?> list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			Card card = (Card) value;
			BufferedImage cardImage = ImageSelection.resize(ImageLoader.loadImage(card), 125, 182);
			setToolTipText(ToolTipHelper.makeToolTipWithoutImage(card));
			ImageIcon icon = new ImageIcon(cardImage);
			label.setIcon(icon);
			if (isSelected) {
				label.setBorder(BorderFactory.createLineBorder(Color.white));
			}
			label.setText("");
            return this;
		}
	}

	public JList getList() {
		return imageView;
	}


	public void sortBy(String sortFilter) {
		imageListModel.sortBy(sortFilter);
		
	}
}

