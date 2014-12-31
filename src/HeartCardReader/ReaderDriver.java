package HeartCardReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ReaderDriver {
	public static void main(String[] args) throws IOException {
		File txtdir = new File("C:/Brett/workspace/Weiss/Database/txts");
		File dirdir = new File("C:/Brett/workspace/Weiss/Database/dirs");
		DirectoryStream<Path> ds = Files.newDirectoryStream(txtdir.toPath());
		for (Path p: ds) {
			String fileName = p.toString().substring(39).replaceAll(".txt", "");
			File file = new File("C:/Brett/workspace/Weiss/Database/txts/" + fileName + ".txt");
			File dir = new File("C:/Brett/workspace/Weiss/Database/dirs/" + fileName);
			dir.mkdir();
			Reader.readSet(file, dir, fileName);
		}
	}
}
