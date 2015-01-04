package WeissSchwarz;

import java.io.File;

import Card.Card;

public class ToolTipHelper {

	
	public static String makeToolTip(Card c) {
	      String imageAddress;
	        if (!(new File(DeckFileHandler.getDatabaseFilePath() + "images/" + c.getImagePath()).exists())) {
	            imageAddress = "file:" + DeckFileHandler.getDatabaseFilePath() + "images/notfound.jpg";
	        }
	        else {
	            imageAddress = "file:" + DeckFileHandler.getDatabaseFilePath() + "images/" + c.getImagePath();
	        }
	    String str = "<html><body><textarea cols=\"35\" rows=\"21\" wrap=\"hard\">" + c.getToolTipDescription() +
	    		"</textarea><img src=\"" + imageAddress + "\" width=\"250\" height=\"365\"></body></html>"; 		 
	    return str;
	}
	
	public static String makeToolTipWithoutImage(Card c) {
	    String str = "<html><body><textarea cols=\"35\" rows=\"21\" wrap=\"hard\">" + c.getToolTipDescription() +
	    		"</textarea></body></html>"; 		 
	    return str;
	}
}
