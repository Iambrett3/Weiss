package WeissSchwarz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;




import Card.Card;
import Card.Chara;
import Card.Climax;
import Card.Event;
import Card.LevelCard;
import GUI.BuilderGUI;

public class DeckExporter {

	
	public static void exportAsTextFile(Deck deck, File file) {
		if (!FilenameUtils.getExtension(file.toString()).equals("txt")) {
			file = new File(file.toString() + ".txt");
		}
		deck = (Deck)deck.clone();
		deck.sortBy("Level");
		
		try {
			PrintWriter writer = new PrintWriter(file);
			ArrayList<Climax> climaxCards = new ArrayList<Climax>();
			ArrayList<Event> eventCards = new ArrayList<Event>();
			writer.println("Character:");
			writer.println("Level 0");
			boolean level1Hit = false;
			boolean level2Hit = false;
			boolean level3Hit = false;
			
			for (Card c : deck) {
				if (c instanceof Climax) {
					climaxCards.add((Climax) c);
				}
				else if (c instanceof Event) {
					eventCards.add((Event) c);
				}
				else {
					if (c.getLevel() == 1 && !level1Hit) {
						writer.println();
						writer.println("Level 1");
						level1Hit = true;
					}
					if (c.getLevel() == 2 && !level2Hit) {
						writer.println();
						writer.println("Level 2");
						level2Hit = true;
					}
					if (c.getLevel() == 3 && !level3Hit) {
						writer.println();
						writer.println("Level 3");
						level3Hit = true;
					}
					writer.println();
					writer.println(getTxtFileRepresentation(c));
				}
			}
			writer.println();
			writer.println();
			writer.println("Event:");
			writer.println("Level 0");
			level1Hit = false;
			level2Hit = false;
			level3Hit = false;
			
			for (Card c: eventCards) {
				if (c.getLevel() == 1 && !level1Hit) {
					writer.println();
					writer.println("Level 1");
					level1Hit = true;
				}
				if (c.getLevel() == 2 && !level2Hit) {
					writer.println();
					writer.println("Level 2");
					level2Hit = true;
				}
				if (c.getLevel() == 3 && !level3Hit) {
					writer.println();
					writer.println("Level 3");
					level3Hit = true;
				}
				writer.println();
				writer.println(getTxtFileRepresentation(c));
			}
			
			writer.println();
			writer.println();
			writer.println("Climax:");
			for (Card c: climaxCards) {
				writer.println();
				writer.println(getTxtFileRepresentation(c));
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String getTxtFileRepresentation(Card c) {
		String str = c.getNumber() + " x" + c.getNumOfCard() 
				+ " - " + c.getName() + " - Triggers: " + c.getTrigger();
		if (c instanceof LevelCard) {
			str+= " - Lvl/Cost: " + c.getLevel() + "/" + c.getCost();
		}
		if (c instanceof Chara) {
			str += " - Pwr: " + c.getPower() + " - Soul: " + c.getSoul();
		}
		str += " - " + c.getText();
		return str;
	}

	public static void exportAsTranslationSheet(Deck deck, File file) {
		if (!FilenameUtils.getExtension(file.getPath()).equals("pdf")) {
			file = new File(file.getPath() + ".pdf");
		}
		createXHTMLFile(deck);
		String inputFile = "C:/Brett/workspace/Weiss/temp.html";
		String url;
		try {
			url = new File(inputFile).toURI().toURL().toString();
		String outputFile = file.toString();
		OutputStream os = new FileOutputStream(outputFile);
		
		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(url);
		renderer.layout();
		renderer.createPDF(os);
		os.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "The file you are attempting to write to is "
					+ "either open or being used by another program. Close it and "
					+ "try again.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void createXHTMLFile(Deck deck) {
		File file = new File("C:/Brett/workspace/Weiss/temp.html");
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.println("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\" /><title></title></head><body>");
			for (Card c: deck) {
				writer.println(createHTMLForCard(c));
				writer.println("<br />");
				writer.println("<br />");
				writer.println("<br />");
				writer.println("<br />");
			}
			writer.println("</body></html>");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static String createHTMLForCard(Card c) {
		if (c instanceof Chara) {
			return createHTMLForCharaCard((Chara) c).replaceAll("& ", "&#38;");
		}
		if (c instanceof Event) {
			return createHTMLForEventCard((Event) c).replaceAll("& ", "&#38;");
		}
		return createHTMLForClimaxCard((Climax) c).replaceAll("& ", "&#38;");
	}
	
	public static String createHTMLForEventCard(Event c) {
		String imageAddress = "file:C:/Brett/workspace/Weiss/Database/images/" + c.getImagePath();
		return "<table width=\"400\" style=\"border:1px solid black; page-break-inside: avoid;\">" 
				+ "<tr>"
					+ "<td colspan=\"2\">"
						+ "<b>"
						+ c.getNumber() + " &#160;"
						+ c.getJpnName() + "<br />"
						+ c.getName()
						+ "</b>"
					+ "</td>"
				+ "</tr>\n"
				+ "<tr>"
					+ "<td style=\"font-size:12px\">"
						+ "<img src=\"" + imageAddress + "\" width=\"130\" height=\"182\" /><br />"
					+ "</td>"
					+ "<td style=\"font-size:12px\">"
					+ "Keyword 1: None" + " &#160; Keyword 2: None"
					+ "<br />"
					+ c.getText()
					+ "</td>"
				+ "</tr>"
		+ "</table>";

	}
	
	public static String createHTMLForClimaxCard(Climax c) {
		String imageAddress = "file:C:/Brett/workspace/Weiss/Database/images/" + c.getImagePath();
		return "<table width=\"400\" style=\"border:1px solid black; page-break-inside: avoid;\">"
				+ "<tr>"
					+ "<td colspan=\"2\">"
						+ "<b>"
						+ c.getNumber() + " &#160;"
						+ c.getJpnName() + "<br />"
						+ c.getName()
						+ "</b>"
					+ "</td>"
				+ "</tr>\n"
				+ "<tr>"
					+ "<td style=\"font-size:12px\">"
						+ "<img src=\"" + imageAddress + "\" style=\"image-orientation: 90deg;\" width=\"130\" height=\"182\" /><br />"
					+ "</td>"
					+ "<td style=\"font-size:12px\">"
					+ "Keyword 1: None" + " &#160; Keyword 2: None"
					+ "<br />"
					+ c.getText()
					+ "</td>"
				+ "</tr>"
		+ "</table>";

	}
	
	public static String createHTMLForCharaCard(Chara c) {
		String imageAddress = "file:C:/Brett/workspace/Weiss/Database/images/" + c.getImagePath();
		return "<table width=\"400\" style=\"border:1px solid black; page-break-inside: avoid;\">"
				+ "<tr>"
					+ "<td colspan=\"2\">"
						+ "<b>"
						+ c.getNumber() + " &#160;"
						+ c.getJpnName() + "<br />"
						+ c.getName()
						+ "</b>"
					+ "</td>"
				+ "</tr>\n"
				+ "<tr>"
					+ "<td style=\"font-size:12px\">"
						+ "<img src=\"" + imageAddress + "\" width=\"130\" height=\"182\" /><br />"
					+ "</td>"
					+ "<td style=\"font-size:12px\">"
					+ "Keyword 1: " + c.getTrait().getTrait1() + " &#160; Keyword 2: " + c.getTrait().getTrait2()
					+ "<br />"
					+ c.getText()
					+ "</td>"
				+ "</tr>"
		+ "</table>";

	}

}
