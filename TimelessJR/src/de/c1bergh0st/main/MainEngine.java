package de.c1bergh0st.main;

import de.c1bergh0st.ui.Window;

public class MainEngine {
public static final String ABOUTURL = "https://www.youtube.com/user/gamerphil2000";
	
	@SuppressWarnings("unused")
	private Window window;
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		MainEngine main = new MainEngine();
		main.start();
	}
	
	public void start(){
		this.window = new Window("Proto3D",1920,1080);
	}

}
