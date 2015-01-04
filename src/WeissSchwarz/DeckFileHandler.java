package WeissSchwarz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeckFileHandler {
    
    public static String getDatabaseFilePath() {
        return "Database/";
    }
	
	public static void saveDeck(Deck deck, File file) {
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(file);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(deck);
	         out.close();
	         fileOut.close();
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public static Deck loadDeck(File file) {
		try
	      {
	         FileInputStream fileIn = new FileInputStream(file);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         Deck deck = (Deck) in.readObject();
	         in.close();
	         fileIn.close();
	         return deck;
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Deck class not found");
	         c.printStackTrace();
	         return null;
	      }
	}

}
