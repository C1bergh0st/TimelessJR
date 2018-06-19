package de.c1bergh0st.levelobjects;

import java.awt.image.BufferedImage;

import de.c1bergh0st.gamecode.ImageLoader;

public class StillObject extends StaticObject{
	private String type;

	public StillObject(double x, double y, String imgStr, ImageLoader load) {
		super(x, y, load.getTileTextureByName(imgStr));
		type = imgStr;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getType() {
		return type;
	}

}
