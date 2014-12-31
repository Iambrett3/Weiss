package WeissSchwarz;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Card.Card;

public class ImageLoader {

	public static BufferedImage loadImage(Card c) {
		BufferedImage image = null;
	    try {
	    //desktop TODO: do this!!!!
	    //image = ImageIO.read(new File("G:\\Code\\workspace\\Weiss\\Database\\images\\" + str));
	    //laptop
	    image = ImageIO.read(new File("C:\\Brett\\workspace\\Weiss\\Database\\images\\" + c.getImagePath())); 

	    }
	    catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return image;
	}
}
