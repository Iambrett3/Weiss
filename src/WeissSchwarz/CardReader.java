package WeissSchwarz;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import Card.*;

public class CardReader {

	/**
	 * Creates a card object from a card file.
	 * @param cardFile card file
	 * @return card from card file
	 * @throws IOException
	 */
	public static Card loadCard(final InputStream cardStream) throws IOException {
		Card card;
		BufferedReader reader = new BufferedReader(new InputStreamReader(cardStream));
		ArrayList<String> lines = readAllLines(reader);
		switch (findCardType(lines)) {
			case "Character": card = new Chara();
			break;
			case "Climax": card = new Climax();
			break;
			case "Event": card = new Event();
			break;	
			default: card = new Chara();
		}
		
		card.setName(lines.get(0).substring(5));
		card.setJpnName(lines.get(1).substring(9));
		card.setNumber(lines.get(2).substring(7));
		card.setRarity(lines.get(3).substring(7));
		card.setColor(new CColor(lines.get(4).substring(6)));
		card.setTrigger(new Trigger(lines.get(13).substring(9).split("\\s+")));
		card.setFlavor(lines.get(14).substring(7));
		card.setText(lines.get(15).substring(5).split("\\*")); //because i put a little star in between abilities
		card.setImagePath(lines.get(16).substring(6));
		card.setPack(lines.get(17).substring(5));
		
		if (card instanceof LevelCard) {
		((LevelCard) card).setLevel(Integer.parseInt(lines.get(7).substring(6)));
		((LevelCard) card).setCost(Integer.parseInt(lines.get(8).substring(5)));
		((LevelCard) card).setAttributes();
		}
		
		if (card instanceof Event)
			return (Event) card;
		
		if (card instanceof Chara) {
		((Chara) card).setPower(Integer.parseInt(lines.get(9).substring(6)));
		((Chara) card).setSoul(Integer.parseInt(lines.get(10).substring(5)));
		((Chara) card).getTrait().setTrait(0, lines.get(11).substring(7));
		((Chara) card).getTrait().setTrait(1, lines.get(12).substring(7));
		return (Chara) card;
		}
		
		return (Climax) card;
	}
	
	public static ArrayList<Card> loadSet(final ZipFile setFile) throws IOException {
		ZipInputStream stream = new ZipInputStream(new FileInputStream(setFile.getName()));
		ArrayList<ZipEntry> entrySet = new ArrayList<>();
		ArrayList<Card> cardSet = new ArrayList<>();
		
		try {
			ZipEntry entry;
			while((entry = stream.getNextEntry()) != null) {
				entrySet.add(entry);
			}
			
			for (ZipEntry e: sortCardSet(entrySet)) {
				cardSet.add(loadCard(setFile.getInputStream(e)));
			}
		}
		finally {
			setFile.close();
			stream.close();
		}
		return cardSet;
	}
	
	/**
	 * Reads all lines of card file and adds them to ArrayList object.
	 * @param reader Scanner for card file
	 * @return ArrayList<String> of file's lines
	 */
	public static ArrayList<String> readAllLines(BufferedReader reader) throws IOException {
		String line;
		ArrayList<String> lines = new ArrayList<>();
		while ((line = reader.readLine()) != null) {
			lines.add(line);
			}
		return lines;	
	}
	
	/**
	 * Finds card type (Climax, Character, or Event)
	 * @param lines card file's lines
	 * @return card type as a string
	 */
	public static String findCardType(ArrayList<String> lines) {
		final String type = lines.get(6).substring(5);
		return type;
	}
	
	public static BufferedImage fetchImage(String str) throws IOException
	{
	    BufferedImage image = null;
	    try {
	    image = ImageIO.read(new File(DeckFileHandler.getDatabaseFilePath() + "images\\" + str)); 

	    }
	    catch (Exception ex) {
	        System.out.println("fetchImage failed");
	    }
	    return image;
	}
	
	/* Old fetchImage method that retrieves from online database.
	 * Could be re-implemented as fetchAllImages to download an entire set or something like in forge.
	public static BufferedImage fetchImage(String str) throws IOException {
		URL url = new URL(str);
		URLConnection conn = url.openConnection();
		InputStream in = conn.getInputStream();
		BufferedImage image = ImageIO.read(in);
		return image;
		}
    */
	
	/*
	 * Sorts ZipEntry set of card set.
	 * 
	 * @param cards ZipEntry ArrayList of card set
	 * @return sorted ZipEntry ArrayList of card set
	 */
	public static ArrayList<ZipEntry> sortCardSet(ArrayList<ZipEntry> cards) {
		Collections.sort(
				   cards,
				   new Comparator<ZipEntry>() {
					 /*
					  * Removes all non-numerical characters from strings and then parses to ints to compare them.
					  */
				     public int compare(ZipEntry a, ZipEntry b) {
				    	 String aString = a.toString().replaceAll("[^0-9]", "");
				    	 String bString = b.toString().replaceAll("[^0-9]", "");
				    	 int aInt = Integer.parseInt(aString);
				    	 int bInt = Integer.parseInt(bString);
				       return Integer.compare(aInt, bInt);
				     }
				   });
		return cards;
	}
	}
	


