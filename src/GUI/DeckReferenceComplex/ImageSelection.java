package GUI.DeckReferenceComplex;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	private final int prefWidth = 250;
	private final int prefHeight = 365;
	
	public ImageSelection() {
	    super();
		currentImage = new ImageIcon();
		picture = new JLabel(currentImage);
		picture.setPreferredSize(new Dimension(prefWidth, prefHeight));
		pictureScrollPane = new JScrollPane(picture);
		add(pictureScrollPane);
	}
	
	public ImageSelection(BufferedImage image) {
		this();
		setCurrentImage(image);
	}
	
	public int getHeight() {
		return prefHeight;
	}
	
	public int getWidth() {
		return prefWidth;
	}
	
	public void setCurrentImage(BufferedImage image) {
		BufferedImage resized = resize(image, prefWidth, prefHeight);
		currentImage = new ImageIcon(resized);
		picture.setIcon(currentImage);
		picture.setPreferredSize(new Dimension(currentImage.getIconWidth(),
											   currentImage.getIconHeight()));
		picture.revalidate();
	}
	
	public void resetCurrentImage() {
		currentImage = new ImageIcon();
		picture.setIcon(currentImage);
		picture.setPreferredSize(new Dimension(prefWidth, prefHeight));
		picture.revalidate();
	}
	
	public static BufferedImage resize(BufferedImage image, int width, int height) {
	    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(image, 0, 0, width, height, null);
	    g2d.dispose();
	    return bi;
	}
}
