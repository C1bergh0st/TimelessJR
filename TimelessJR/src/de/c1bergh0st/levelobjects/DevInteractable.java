package de.c1bergh0st.levelobjects;

import de.c1bergh0st.debug.Debug;

public class DevInteractable extends Interactable{

	public DevInteractable(double x, double y, double w, double h) {
		super(x, y, w, h);
	}

	@Override
	public void use() {
		Debug.send("USED!!!!");
	}

	@Override
	public String getType() {
		return "DevInteractable";
	}

	@Override
	public String getArgs() {
		return "Na";
	}

}
