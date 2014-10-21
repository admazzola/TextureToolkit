package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {

	
	BufferedImage data = null;
	
	public Texture(String path)
	{
		
		try {
			data = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("Could not load file at "+ path);
		}
		
		
	}
	
	
	public void save(String path)
	{
		
		String extension = path.substring(path.length() - 3 );
		
		try {
		    // retrieve image		   
		    File outputfile = new File(path);
		    ImageIO.write(data, extension, outputfile);
		} catch (IOException e) {
			System.err.println("Could not save " +extension+ " file to "+ path);
		}
		
	}
	
	
	public void save(BufferedImage image, String path)
	{
		
		String extension = path.substring(path.length() - 3 );
		
		try {
		    // retrieve image		   
		    File outputfile = new File(path);
		    ImageIO.write(image, extension, outputfile);
		} catch (IOException e) {
			System.err.println("Could not save " +extension+ " file to "+ path);
		}
		
	}
	
	public void split(int tilesize,String path)
	{
		String name = path.substring(0, path.length() - 4 );
		String extension = path.substring(path.length() - 3 );
		
		for(int x=0;x<data.getWidth();x+=tilesize){
			for(int y=0;y<data.getWidth();y+=tilesize){
					save( data.getSubimage(x, y, tilesize, tilesize) ,name +"_" +x +"_"+ y + "." +  extension);
			}
		}
	}
	
	
	
	
	
}
