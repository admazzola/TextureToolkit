package com;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SpritesheetTools {
	
	
	
	private static File[] getFilesFromFolder(String path)
	{
		
		
		File folder = new File(path);
		
		if( folder.isDirectory() )
		{			
			return folder.listFiles();	 						
		}
		
		return null;
		
	}

	public static void createSpritesheetFromFolder(String path, int spriteSize) throws Exception {
		 File[] iconFiles = getFilesFromFolder(path);
		 
		 int numIcons = iconFiles.length;
		 
		int numTilesPerRow =  getNextLargestPowerOfTwo ( (int) Math.ceil( Math.sqrt(  numIcons  ) ) ) ; 
		int numRows = numIcons / numTilesPerRow;
	 	
		 int height = getNextLargestPowerOfTwo(numRows) * spriteSize ;
		 int width = numTilesPerRow * spriteSize;
		 
		 //init XML doc
		 DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("TextureAtlas");
			doc.appendChild(rootElement);
			
			Attr imagePath = doc.createAttribute("imagePath");
			imagePath.setValue(path.substring(0, path.lastIndexOf("/")) + "/spritesheet.png");
			rootElement.setAttributeNode(imagePath);
		 
				 
		 BufferedImage sheet = new BufferedImage(width, height,  BufferedImage.TYPE_INT_ARGB);
		 
		 Graphics g = sheet.getGraphics();
		 
		 Image icon;
		 
		 for( int iconIndex = 0; iconIndex< iconFiles.length; iconIndex++ )
		 {
			 
			 
				icon = ImageIO.read( iconFiles[iconIndex] );
				
				
				int x = iconIndex%numTilesPerRow;
				int y = iconIndex/numTilesPerRow;
				
				 g.drawImage( icon.getScaledInstance(spriteSize, spriteSize, 0) , x*spriteSize, y*spriteSize, null); //x first then y
				 
				 
				 String iconname = iconFiles[iconIndex].getName();
				 //iconname = iconname.substring(0,path.lastIndexOf("."));
				 
				 System.out.println("drawing image " + iconname);
				 
				 
				// staff elements
					Element staff = doc.createElement("SubTexture");
					rootElement.appendChild(staff);
			 
					// set attribute to staff element
					Attr nameattr = doc.createAttribute("name");
					nameattr.setValue(iconname);
					staff.setAttributeNode(nameattr);
					
					Attr xattr = doc.createAttribute("x");
					xattr.setValue(""+x*spriteSize);
					staff.setAttributeNode(xattr);
					
					Attr yattr = doc.createAttribute("y");
					yattr.setValue(""+y*spriteSize);
					staff.setAttributeNode(yattr);
					
					Attr wattr = doc.createAttribute("width");
					wattr.setValue(""+spriteSize);
					staff.setAttributeNode(wattr);
					
					Attr hattr = doc.createAttribute("height");
					hattr.setValue(""+spriteSize);
					staff.setAttributeNode(hattr);
			 
			  
		 }
		 
		 	//output to xml file
		 	TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path.substring(0, path.lastIndexOf("/")) + "/spritesheet.xml"));
			transformer.transform(source, result);
		 
		 File output = new File(path.substring(0, path.lastIndexOf("/")) + "/spritesheet.png");		 
		 
		 try {
			ImageIO.write(sheet, "PNG", output);
			System.out.println("exported sheet to " + output);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		 
		 
	}
	
	
	public static int getNextLargestPowerOfTwo (int n)
	   {
	        int res = 2;        
	        while (res < n) {
	        		res *= 2;
	        }
	        return res ;
	   }

	
}
