package de.c1bergh0st.levelobjects;

import de.c1bergh0st.gamecode.ImageLoader;

public class DevFloor extends StaticObject {

	public DevFloor(double x, double y, ImageLoader imgload) {
		super(x, y, imgload.devtex);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public String getType() {
		return "DevFloor";
	}

}
