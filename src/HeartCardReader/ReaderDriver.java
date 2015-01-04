package HeartCardReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import WeissSchwarz.DeckFileHandler;

public class ReaderDriver {
	public static void main(String[] args) throws IOException {
		File txtdir = new File(DeckFileHandler.getDatabaseFilePath() + "txts");
		File dirdir = new File(DeckFileHandler.getDatabaseFilePath() + "dirs");
		DirectoryStream<Path> ds = Files.newDirectoryStream(txtdir.toPath());
		for (Path p: ds) {
			String fileName = p.toString().substring(txtdir.toString().length() + 1).replaceAll(".txt", "");
			File file = new File(DeckFileHandler.getDatabaseFilePath() + "txts/" + fileName + ".txt");
			File dir = new File(DeckFileHandler.getDatabaseFilePath() + "dirs/" + fileName);
			dir.mkdir();
			Reader.readSet(file, dir, fileName);
		}
	}
}
