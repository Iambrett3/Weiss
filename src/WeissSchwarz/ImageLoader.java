package WeissSchwarz;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Card.Card;

public class ImageLoader {

	public static BufferedImage loadImage(Card c) {
		BufferedImage image = null;
	    try {
	    image = ImageIO.read(new File(DeckFileHandler.getDatabaseFilePath() + "images\\" + c.getImagePath())); 
	    }
	    catch (Exception ex) {
	        image = loadDefaultImage();
	    }
	    return image;
	}
	
	public static BufferedImage loadDefaultImage() {
        BufferedImage image = null;
        
        try
        {
            image = ImageIO.read(new File(DeckFileHandler.getDatabaseFilePath() + "images\\" + "notfound.jpg"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return image;
	}
}
