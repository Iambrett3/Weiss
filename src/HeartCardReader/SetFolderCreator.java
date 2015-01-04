package HeartCardReader;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import WeissSchwarz.DeckFileHandler;

public class SetFolderCreator {
	public static void main(String[] args) throws IOException {
	File file = new File(DeckFileHandler.getDatabaseFilePath() + "TitleList.txt");
	Scanner scan = new Scanner(file);
	String input;
	File dir;
	while (scan.hasNext()) {
		input = scan.nextLine();
		dir = new File(DeckFileHandler.getDatabaseFilePath() + "titles/" + input);
		if (!dir.exists()) {
		    dir.mkdir();
		}
	}
	}
}
