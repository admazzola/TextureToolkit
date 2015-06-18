package com;

import java.awt.image.BufferedImage;
import java.io.File;

public class TextureToolkit {

	public static void main(String[] args) {

		///Texture test = new Texture("assets/terrain.png");
		
		//test.split(32,"assets/terrain2.png");
	
		try {
			SpritesheetTools.createSpritesheetFromFolder( "C:/icons/lorc/originals/png/000000/transparent",64);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
