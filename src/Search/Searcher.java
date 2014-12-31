package Search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.swing.JDialog;

import net.miginfocom.swing.MigLayout;
import Card.Ability;
import Card.Ability.Abilities;
import Card.CColor;
import Card.Card;
import Card.Chara;
import Card.Event;
import Card.LevelCard;
import GUI.DeckReferenceComplex.DeckReferenceComplexPanel;
import GUI.DeckReferenceComplex.SwingHelp;
import WeissSchwarz.CardReader;

public class Searcher {
	
	
	public Searcher() {
	}
	
	public static ArrayList<Card> search(SearchObject search) {
		//get titles to search
		String title = search.getTitle();
		ArrayList<String> titlesToSearch;
		ArrayList<Card> cardsFound = new ArrayList<Card>();
		
		if (title.equals("All Titles")) {
			titlesToSearch = getAllTitles();
		}
		else {
			titlesToSearch = new ArrayList<String>(1);
			titlesToSearch.add(title);
		}
		
		for (String tit: titlesToSearch) { //for each title
			String titleDirPath = "C:/Brett/workspace/Weiss/Database/titles/" + tit;
			try {
				DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath(titleDirPath));
				for (Path p: ds) { //for each set
					if (p.toString().endsWith(".zip")) {
						ZipFile zip = new ZipFile(p.toFile());
						ZipInputStream stream = new ZipInputStream(new FileInputStream(zip.getName()));
						ArrayList<ZipEntry> entrySet = new ArrayList<>();
						try {
							ZipEntry entry;
							while((entry = stream.getNextEntry()) != null) {
								entrySet.add(entry);
							}
							
							for (ZipEntry e: entrySet) { //for each card in set
								Card card = CardReader.loadCard(zip.getInputStream(e));

								String searchInput = search.getSearchInput().toLowerCase();
								
								String engName = card.getName().toLowerCase();
								String jpnName = card.getJpnName().toLowerCase();
								String number = card.getNumber().toLowerCase();
								String cardText = card.getText().toLowerCase();
								String flavor = card.getFlavor();
								
								boolean found = false;
								if (search.isTextEntered()) {
									if (search.getEngName() || !search.isTextInputFilter()) {
										if (engName.contains(searchInput)) {
											found = true;
										}
									}
									if (search.getJpnName() || !search.isTextInputFilter()) {
										if (jpnName.contains(searchInput)) {
											found = true;
										}
									}
									if (search.getNumber() || !search.isTextInputFilter()) {
										if (number.contains(searchInput)) {
											found = true;
										}
									}
									if (search.getCardText() || !search.isTextInputFilter()) {
										if (cardText.contains(searchInput)) {
											found = true;
										}
									}
									if (search.getFlavor() || !search.isTextInputFilter()) {
										if (flavor.contains(searchInput)) {
											found = true;
										}
									}
								}
								if (found == true || !search.isTextEntered()) { //if match was found or no input was entered
									if (filterCheck(card, search)) {
										cardsFound.add(card);
									}
								}
								
							}
						} 
						finally {
							zip.close();
							stream.close();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return cardsFound;
	}
	
	public static boolean filterCheck(Card card, SearchObject search) {
		if (search.getType() != null) {
			if (card.getClass() != search.getType()) {
				return false;
			}
		}
		if (!cardIsRelevantToFilters(card, search)) {
			return false;
		}
		if (card instanceof Chara) { //things that are exclusive to Characters
			if (!search.getTrait1().equals("All")) {
				if (!((Chara)card).getTrait().hasTrait(search.getTrait1())) {
					return false;
				}
			}
			if (!search.getTrait2().equals("All")) {
				if (!((Chara)card).getTrait().hasTrait(search.getTrait2())) {
					return false;
				}
			}
			if (search.getSoul() != -1) {
				if (search.getSoul() != ((Chara)card).getSoul()) {
					return false;
				}
			}
			
			//power
			String powerState = search.getPower().getState();
			Integer powerLevel = search.getPower().getPower();
			
			switch (powerState) {
				case "At Least:":
					if (((Chara)card).getPower() < powerLevel) {
						return false;
					}
					break;
				case "Exactly:":
					if (((Chara)card).getPower() != powerLevel) {
						return false;
					}
					break;
				case "Less Than:":
					if (((Chara)card).getPower() >= powerLevel) {
						return false;
					}
					break;
			}		
		}
		if (card instanceof LevelCard) { //LevelCards (events and characters)
			if (search.getCost() != -1) {
				if (search.getCost() != ((LevelCard)card).getCost()) {
					return false;
				}
			}
			if (search.getLevel() != -1) {
				if (search.getLevel() != ((LevelCard)card).getLevel()) {
					return false;
				}
			}
			//abilities
			Ability abilities = search.getAbilities();
			for (Abilities ab: abilities.getAbilities()) {
				if (!((LevelCard)card).getAbility().hasAbility(ab)) {
					return false;
				}
			}
		}
		//all cards including climaxes
		if (search.getRarity() != "All") {
			if (search.getRarity() != card.getRarity()) {
				return false;
			}
		}
		if (!search.getTrigger1().equals("All")) {
			if (!(card.getTrigger().hasTrigger(search.getTrigger1()))) {
				return false;
			}
		}
		if (!search.getTrait2().equals("All")) {
			if (!(card.getTrigger().hasTrigger(search.getTrigger2()))) {
				return false;
			}
		}
		
		//colors
		if (search.getColors().length > 0) {
			boolean colorFound = false;
			for (CColor color: search.getColors()) {
				if (color.equals(card.getColor())) {
					colorFound = true;
				}
			}
			if (!colorFound) {
				return false;
			}
		}
	return true;
}
	
	public static boolean cardIsRelevantToFilters(Card card, SearchObject search) {
		//if anything specific to Chara objects was entered as a filter
		if (!search.getTrait1().equals("All") || !search.getTrait2().equals("All") 
				|| search.getSoul().intValue() != -1 
				|| !(search.getPower().getPower().intValue() == 0) && search.getPower().getState().equals("At least:")) {
			//and if the card is not a Chara object
			if (!(card instanceof Chara)) {
				return false;
			}
		}
		//if anything specific to LevelCard objects was entered as a filter
		if (search.getCost().intValue() != -1 || search.getLevel().intValue() != -1
				|| !search.getAbilities().hasNoAbilities()) {
			//and if the card is not a LevelCard
			if (!(card instanceof LevelCard)) {
				return false;
			}
		}
		return true;
			
	}

	public static ArrayList<String> getAllTitles() {
		ArrayList<String> titles = new ArrayList<String>();
		try {
			Scanner scan = new Scanner(new File("C:/Brett/workspace/Weiss/Database/TitleList.txt"));
			while (scan.hasNext()) {
				titles.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return titles;	
	}
}
