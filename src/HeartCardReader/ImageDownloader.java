package HeartCardReader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import WeissSchwarz.DeckFileHandler;

import com.lowagie.text.Image;

public class ImageDownloader
{
    
    public static void main (String[] args) throws FileNotFoundException {
        Scanner key = new Scanner(System.in);
//        System.out.println("enter title name");
//        String input = key.nextLine();
        
        File htmlDir = new File("G:/Code/htmls/");
        File[] htmls = htmlDir.listFiles();
        
        ArrayList<String> inputs = new ArrayList<String>();
        
        for (File htmlFile: htmls) {
            if (htmlFile.toString().endsWith(".html")) {
                inputs.add(htmlFile.getName().replaceAll(".html", ""));
            }
        }
        

        for (String input: inputs) {
        
            File inputFile = new File("G:/Code/htmls/" + input + ".html");
            String imagedir = "G:/Code/Database/" + input + "+/";
            File imageDirFile = new File(imagedir);
            imageDirFile.mkdir();
            
            Scanner reader = new Scanner(new FileReader(inputFile));
            
            ArrayList<String> urls = new ArrayList<String>();
            
            while (reader.hasNext()) {
                input = reader.nextLine();
                   if (input.contains("card.php?card_id")) {
                    urls.add(input);
                   }
            }
            
            for (int i = 0; i < urls.size(); i++) {
                String thisUrl = urls.get(i).replaceAll("\t", "").replaceAll("<li>", "").replaceAll("</ul><ul>", "").replaceAll(" ", "");
                thisUrl = thisUrl.substring(66, 71);
                urls.set(i, thisUrl);
            }
            int fileIndex = 0;
            try {            
                for (String cardnum: urls) {
    
                    URL url = new URL("http://images.littleakiba.com/tcg/weiss-schwarz/card-large.php?card_id=" + cardnum);
                    URLConnection conn = url.openConnection();
                    conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
                    InputStream in = conn.getInputStream();
                    BufferedImage image = ImageIO.read(in);
                    
                    ImageIO.write(image, "jpg", new File(imagedir + fileIndex + ".jpg"));
                    fileIndex++;
                }
            }
            catch (IOException e)
            {
                    e.printStackTrace();
            }
            reader.close();
        }
        
//        DirectoryStream<Path> ds;
//        try
//        {
//            ds = Files.newDirectoryStream(FileSystems.getDefault().getPath(imagedir));
//            
//            ArrayList<Path> dirPaths = new ArrayList<Path>();
//            for (Path p: ds) {
//                if (new File(p.toString()).isDirectory()) {
//                    dirPaths.add(p);
//                }
//            }
//            ds.close();
//            for (Path p: dirPaths) {
//                File dirFile = new File(p.toString());
//                File[] directoryListing = dirFile.listFiles();
//                if (directoryListing != null) {
//                    int i = 0;
//                    for (String cardString: urls) {
//                        for (File imageFile: directoryListing) {
//                            if (imageFile.toString().contains(cardString)) {
//                                imageFile.renameTo(new File(imageFile.toString().replaceAll(cardString, Integer.toString(i))));
//                                break;
//                            }
//                        }
//                        i++;
//                    }
//                    ds.close();
//                }
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        
        

    }
}

