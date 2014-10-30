package Card;
import WeissSchwarz.Deck;

import java.net.URL;
import java.awt.image.BufferedImage;

import WeissSchwarz.CardReader;

import java.io.*;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.net.*;
import java.util.ArrayList;
import java.util.zip.ZipFile;

import javax.swing.*;

public class test {

	public test() {
		
	}

	public static void main(String[] args) throws IOException {
	ZipFile file = new ZipFile("G:\\Code\\workspace\\Weiss\\Database\\accel_world_booster_pack.zip");
	ArrayList<Card> cardList = CardReader.loadSet(file);
	System.out.println(cardList.get(cardList.size()-1).getDescription());
	}
	
}

