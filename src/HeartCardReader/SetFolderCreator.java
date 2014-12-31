package HeartCardReader;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SetFolderCreator {
	public static void main(String[] args) throws IOException {
	File file = new File("C:\\Brett\\workspace\\Weiss\\Database\\SetList.txt");
	Scanner scan = new Scanner(file);
	String input;
	File dir;
	while (scan.hasNext()) {
		input = scan.nextLine();
		dir = new File("C:/Brett/workspace/Weiss/Database/" + input);
		dir.mkdir();
	}
	}
}
