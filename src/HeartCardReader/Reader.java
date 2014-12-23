package HeartCardReader;
import java.util.Scanner;
import java.io.*;

public class Reader {	
	public static void main(String[] args) throws IOException{
		Scanner keyboard = new Scanner(System.in);
		String input;
		System.out.println("enter file name to read from. (without .txt extension)");
		input = keyboard.nextLine();
		File file = new File("G:/Code/workspace/Weiss/Database/" + input + ".txt");
		File dir = new File("G:/Code/workspace/Weiss/Database/" + input);
		dir.mkdir();
		readSet(file, dir, input);
		keyboard.close();
	}
	
    public static void readSet(File file, File dir, String set) throws IOException {
        	
            file = removeBlankLines(file);
        	Scanner reader = new Scanner(file);        
            int index = 0;
            String input = "";
            String in = "";                
            PrintWriter writer = null;
            input = reader.nextLine();
            String pack = input.split(" Translation")[0];
            while(!(in = reader.next()).startsWith("="));
            while (reader.hasNext()) {        
            reader.nextLine();
            input = reader.nextLine();
            String cardName = index + "_" +
                    input.replaceAll("[^a-zA-Z ]", "").replaceAll(" ", "_").toLowerCase();
            File newCardFile = new File(dir + "/" + cardName);

            index++;
            String name = input;
            input = reader.nextLine();
            String jpnName = input;
            
            reader.next();
            reader.next();
            input = reader.next();
            
            String[] split1 = input.split("/"); //split the number input around the slash
            String[] split2 = split1[1].split("-"); //split the second half of the original input around the :. index 0 is now the set number.
            String location = split2[0] + "/" + input.replaceAll("/", "") + ".jpg"; //inits location in this format: PackNumber/CardNumber(Without"/").jpg
            writer = new PrintWriter(dir + "/" + location.split("/")[1].replaceAll(".jpg",  ".txt"));
            writer.println("Name:" + name);
            writer.println("JPN_Name:" + jpnName); 
            writer.println("Number:" + input); 
            reader.next();
            input = reader.next();
            writer.println("Rarity:" + input);
            reader.next();
            input = reader.next();
            writer.println("Color:" + input);
            reader.next();
            input = reader.next();
            writer.println("Side:" + input);
            input = reader.next();
            writer.println("Type:" + input);
            reader.next();
            input = reader.next();
            writer.println("Level:" + input);
            reader.next();
            input = reader.next();
            writer.println("Cost:" + input);
            reader.next();
            input = reader.next();
            writer.println("Power:" + input);
            reader.next();
            input = reader.next();
            writer.println("Soul:" + input);                
            reader.next();
            reader.next();
            input = reader.next();
            if (!input.startsWith("None")) {
                    while (!(in = reader.next()).startsWith("Trait")) {
                    input += " " + in;
                    }
            }
            else {
            	reader.next();
            }
       
            writer.println("Trait1:" + input);        
            reader.next();
            input = reader.next();
            if (!input.startsWith("None")) {
                    while (!(in = reader.next()).startsWith("Triggers:")) {
                    input += " " + in;
            }
            }
            else {
                    reader.next();
            }
            writer.println("Trait2:" + input);
            input = reader.next();
            if (!input.startsWith("None")) {
                    while (!(in = reader.next()).startsWith("Flavor:")) {
                    		if (input.equals("2"))
                    			input += "" + in;
                    		else
                    			input += " " + in;
                    }
            }
            else {
                    reader.next();
            }
            writer.println("Triggers:" + input);
            input = "";
            while (!(in = reader.next()).startsWith("TEXT")) {
                    input += in + " ";
            }
            writer.println("Flavor:" + input);
            input = "";
            int textCount = 0;
            while(!(in = reader.next()).startsWith("==")) {
            		if (in.startsWith("[A]") || in.startsWith("[C]") || in.startsWith("[S]"))
            			textCount++;
            		if (textCount > 1) {
            			break;
            			}
                    input += in + " ";
                    
            }
            writer.print("Text:" + input);
            input = "";
            if (textCount > 1) {
            while(!(in.startsWith("=="))) {
            	if (in.startsWith("[A]") || in.startsWith("[C]") || in.startsWith("[S]"))
        			textCount++;
        		if (textCount > 3) {
        			break;
        			}
                input += in + " ";
                in = reader.next();
        }
            	writer.print("*" + input);
            	input = "";
            }
            if (textCount > 3) {
            while(!(in.startsWith("=="))) {
            	input += in + " ";
            	in = reader.next();
            }
            	writer.print("*" + input);
            	input = "";
            }
            
            writer.printf("%n%s%s", "Image:", location);
            writer.printf("%n%s%s", "Pack:", pack);
            writer.close();
            
        } 
            reader.close();             
    }
	
	public static File removeBlankLines(File inFile) throws IOException {
	Scanner scan = new Scanner(inFile);
	File outFile = new File("temp");
	PrintWriter writer = new PrintWriter(outFile);
	String line;
	while (scan.hasNextLine()) {
		line = scan.nextLine();
		line = line.trim();
		if(line.length() > 0) {
			writer.println(line);
		}
		outFile.renameTo(inFile);
	}
	writer.close();
	scan.close();
	return outFile;
	
		
	}
	
}



		
		
	
