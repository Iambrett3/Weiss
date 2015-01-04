package HeartCardReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import WeissSchwarz.DeckFileHandler;
import net.lingala.zip4j.io.ZipOutputStream;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipMaker {
	public static void main(String[] args) {

		
		File homeDir = new File(DeckFileHandler.getDatabaseFilePath() + "dirs");
		for (final File setDir : homeDir.listFiles()) {
			if (setDir.isDirectory()) {
				createZip(setDir);
			}
		}
	}
	
	private static void createZip(File set) {
		ZipOutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			ArrayList cards = new ArrayList();
			for (final File card : set.listFiles()) {
				cards.add(card);
			}
			
			File zipDir = new File(DeckFileHandler.getDatabaseFilePath() + "zips");
			outputStream = new ZipOutputStream(new FileOutputStream(zipDir + "\\" + set.getName() + ".zip"));
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); //deflate compression
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
			parameters.setEncryptFiles(false); //no encryption
			
			
			//loop
			for (int i = 0; i < cards.size(); i++) {
				File card = (File)cards.get(i);
				outputStream.putNextEntry(card, parameters);
				
				inputStream = new FileInputStream(card);
				byte[] readBuff = new byte[4096];
				int readLen = -1;
				
				while ((readLen = inputStream.read(readBuff)) != -1) {
					outputStream.write(readBuff, 0, readLen);
				}
				
				outputStream.closeEntry();
				
				inputStream.close();
			}
			
			outputStream.finish();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
