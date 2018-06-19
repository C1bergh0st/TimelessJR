package de.c1bergh0st.gamecode;

public class RenderThread implements Runnable{
	private MainGame game;
	
	public RenderThread(MainGame in_game){
		game = in_game;
	}
	
	@Override
	public void run() {
		while(game.shouldRun){
			game.render();
		}
	}

}
