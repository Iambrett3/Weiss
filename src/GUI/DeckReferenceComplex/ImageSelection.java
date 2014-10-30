package GUI.DeckReferenceComplex;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImageSelection extends JPanel {
	private JFrame frame;
	private ResourceBundle imageResource;
	private ImageIcon currentImage;
	private JLabel picture;
	private JScrollPane pictureScrollPane;
	
	public ImageSelection() {
	    super();
		currentImage = new ImageIcon();
		picture = new JLabel(currentImage);
		//picture.setPreferredSize(new Dimension(500, 730));
		picture.setPreferredSize(new Dimension(200, 400));
		pictureScrollPane = new JScrollPane(picture);
		add(pictureScrollPane);
	}
	
	public void setCurrentImage(BufferedImage image) {
		currentImage = new ImageIcon(image);
		picture.setIcon(currentImage);
		picture.setPreferredSize(new Dimension(currentImage.getIconWidth(),
											   currentImage.getIconHeight()));
		picture.revalidate();
	}
	
	public void resetCurrentImage() {
		currentImage = new ImageIcon();
		picture.setIcon(currentImage);
		picture.setPreferredSize(new Dimension(500, 730));
		picture.revalidate();
	}
}
