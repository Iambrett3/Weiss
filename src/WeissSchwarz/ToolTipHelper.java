package WeissSchwarz;

import Card.Card;

public class ToolTipHelper {

	
	public static String makeToolTip(Card c) {
		//laptop
	    String imageAddress = "file:C:/Brett/workspace/Weiss/Database/images/" + c.getImagePath();
		//desktop
		//String imageAddress = "file:G:/Code/workspace/Weiss/Database/images/" + c.getImagePath();
	    String str = "<html><body><textarea cols=\"30\" rows=\"21\" wrap=\"hard\">" + c.getDescription() +
	    		"</textarea><img src=\"" + imageAddress + "\" width=\"250\" height=\"365\"></body></html>"; 		 
	    return str;
	}
	
	public static String makeToolTipWithoutImage(Card c) {
		String imageAddress = "file:C:/Brett/workspace/Weiss/Database/images/" + c.getImagePath();
		//desktop
		//String imageAddress = "file:G:/Code/workspace/Weiss/Database/images/" + c.getImagePath();
	    String str = "<html><body><textarea cols=\"30\" rows=\"21\" wrap=\"hard\">" + c.getDescription() +
	    		"</textarea></body></html>"; 		 
	    return str;
	}
}
