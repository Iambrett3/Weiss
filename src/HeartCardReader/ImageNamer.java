package HeartCardReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * ImageNamer that names all image files after text files exactly.
 * @author Brett
 *
 */
public class ImageNamer {
	public static void main(String[] args) throws IOException {
		Scanner keyboard = new Scanner(System.in);
		String input;
		File txtDir = new File("G:/Code/workspace/Weiss/Database/tmptxt");
		File imageDir = new File("G:/Code/workspace/Weiss/Database/images/tmpimage");
		
		ArrayList<String> names = new ArrayList<>();
		for (File child: txtDir.listFiles()) {
			names.add(child.getName().replaceAll(".txt", ""));
		}
//		Collections.sort(
//				   names,
//				   new Comparator<String>() {
//					 /*
//					  * Removes all non-numerical characters from strings and then parses to ints to compare them.
//					  */
//				     public int compare(String a, String b) {
//				    	 a = a.replaceAll("[^0-9]", "");
//				    	 b = b.replaceAll("[^0-9]", "");
//				    	 int aInt = Integer.parseInt(a);
//				    	 int bInt = Integer.parseInt(b);
//				       return Integer.compare(aInt, bInt);
//				     }
//				   });
//		System.out.println(names);

		List<File> images = Arrays.asList(imageDir.listFiles());
		Collections.sort(images, new Comparator<File>() { //need to sort because windows file explorer sorts differently than java
		    public int compare(File a, File b) {
		        String aString = a.toString().replaceAll("[^0-9]", "");
		        String bString = b.toString().replaceAll("[^0-9]", "");
		        int aInt = Integer.parseInt(aString);
		        int bInt = Integer.parseInt(bString);
		        return Integer.compare(aInt, bInt);
		    }
		});
	    int i = 0;
		for (File child: images) {
            child.renameTo(new File(imageDir + "/" + names.get(i++) + ".jpg"));
        }
		
//		DirectoryStream<Path> ds = Files.newDirectoryStream(FileSystems.getDefault().getPath(imageDir.getPath()));
//		int index = 0; 
//		for (Path p: ds) {
//			File imageFile = new File(p.toString());
//			String str = names.get(index);
//			imageFile.renameTo(new File(imageDir + "/" + str + ".jpg"));
//			index++;
//		}
	}
}

