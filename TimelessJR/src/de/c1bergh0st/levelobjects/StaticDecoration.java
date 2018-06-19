package de.c1bergh0st.levelobjects;

import de.c1bergh0st.gamecode.DecoImageLoader;

public class StaticDecoration extends Decoration{
	String type;

	public StaticDecoration(double x, double y, String path, DecoImageLoader loadimg){
		super(x,y,loadimg.getTexByName(path));
		type = path;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getType() {
		return "STAT"+type;
	}
	
}
