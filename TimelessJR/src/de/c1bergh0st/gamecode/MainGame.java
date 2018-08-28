package de.c1bergh0st.gamecode;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.input.InputHandler;
import de.c1bergh0st.ui.Window;

public class MainGame extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private static final int TICKSPEED = 120; //Ticks per Second
	private static final double NSPERTICK = 1000000000D / TICKSPEED;
	private static final Color BACKGROUNDCOLOR = new Color(79,194,232);
	
	public Window parent;
	public Thread tickThread;
	public Thread renderThread;
	
	public boolean shouldRun;
	private long frames;
	private double fps;
	private double ticks;
	private double tps;
	private double allticks;
	private long lastSecondTick;
	
	public LevelEditor world;
	public InputHandler input;
	public Font custFont;

	public MainGame(Window p){
		input = new InputHandler();
		shouldRun = true;
		parent = p;
	}
	
	public void start(){
		tickThread = new Thread(this,"TickThread");
		tickThread.start();
//		renderThread = new Thread(new RenderThread(this),"RenderThread");
//		renderThread.start();
	}

	public void run() {
		init();
		//Nanoseconds per Tick
		frames = 0;
		ticks = 0;
		long fpsTimer = System.currentTimeMillis();
		double delta = 0;
		double lastTime = System.nanoTime();
		Debug.send("Mainloop startet at:"+System.nanoTime());
		while(shouldRun){
			long now = System.nanoTime();
 			delta += (now - lastTime) / NSPERTICK;
 			lastTime = now;
 			while(delta >= 1 ){
 				ticks++;
 				allticks++;
	 			tick();
 				delta -= 1;
 			}
 			render();
 			if (fpsTimer < System.currentTimeMillis() - 1000){
 				fpsTimer = System.currentTimeMillis();
 				secondTick();
 			}
		}
	}
	
	private void init() {
		drawLoadingScreen();
		world = new LevelEditor(this);
		try {
		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     custFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/res/medium.ttf")).deriveFont(20f);
		     ge.registerFont(custFont);
		     System.out.println("FONT DONE");
		} catch (Exception e) {
		     e.printStackTrace();
		}
	}

	private void drawLoadingScreen() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}
		Graphics g = bs.getDrawGraphics();
		applySettings(g);
		g.dispose();
		bs.show();
		Debug.send("Loadingscreen shown at"+System.nanoTime());
	}

	private void secondTick() {
		tps = ticks;
		ticks = 0;
		fps = frames;
		frames = 0;
	}

	private void tick() {
		world.internaltick();
	}

	public void render(){
		frames++;
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();

	    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		applySettings(g);
		//▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
		
		world.render(g);

		
		//▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
		drawFPS(g);
		g.dispose();
		bs.show();
	}

	private void applySettings(Graphics g) {
		g.setFont(custFont); 
		g.setColor(BACKGROUNDCOLOR);
		g.fillRect(0, 0, getWidth(), getHeight()); 
	}

	private void drawFPS(Graphics g){
		g.setColor(Color.BLACK);
		g.drawString("FPS: "+ (int)fps, 5, 20);
		g.drawString("TPS: "+ (int)tps, 5, 45);
		g.drawString("TileXOffset: "+ world.tileoffsetx, 5, 70);
		g.drawString("TileYOffset: "+ world.tileoffsety, 5, 95);
	}
	
	public LevelEditor getWorld(){
		return world;
	}
	
	public InputHandler getInputHandler(){
		return input;
	}
}
