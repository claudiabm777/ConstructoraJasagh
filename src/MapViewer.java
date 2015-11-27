

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MapViewer {
	
	
	
	private static String htmlFile;
	
	public void mapa(Coordinate center, Coordinate c1, Coordinate c2, ArrayList<Coordinate> coordinates) {
		int zoom = 6;
		System.out.println("ccccenter"+c1);
    	
    	
    	FileHandler fh = new FileHandler(zoom, center, c1, c2, coordinates);   
    	
    	htmlFile = fh.getBody();
    	
    	
				new MapFrame(htmlFile);
		
	}
	
}