package de.c1bergh0st.ui;

import javax.swing.JPanel;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.MainGame;

import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Point2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Gamepanel extends JPanel {
	
	Window parent;
	MainGame maingame;
	private int startx;
	private int starty;
	
	public Gamepanel(Window parent) {
		Debug.send("GAMEPANEL");
		this.parent = parent;
		setLayout(null);
		
		Canvas canvas = new MainGame(parent);
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Debug.send("Mouse Pressed:"+e.getButton()+" At px = "+e.getX()+" ; py = "+e.getY());
				if(e.getButton() == 1){
					maingame.getWorld().addBlock(e.getX(),e.getY());
				}
				if(e.getButton() == 3){
					startx = e.getX();
					starty = e.getY();
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Debug.send("Mouse Released:"+e.getButton()+" At px = "+e.getX()+" ; py = "+e.getY());
				if(e.getButton() == 3){
					if(startx < e.getX() && starty < e.getY()){
						maingame.getWorld().addInteractable(startx, starty, e.getX(), e.getY());
					}
				}
			}
		});
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent me) {
				try{
					maingame.getWorld().mousemoved(me.getX(),me.getY());
				}catch(Exception e){}
			}
		});
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e){
				char key = e.getKeyChar();
				if(key == 'f'){
					
				}
				if(key == 'h'){
					
				}
				if(key == 't'){
				}
				if(key == 'g'){
				}
				if(key == 'r'){
					maingame.getWorld().tileoffsetx = 0;
					maingame.getWorld().tileoffsety = 0;
					maingame.getWorld().r();
				}
			}
			
			public void keyPressed(KeyEvent e) {
				char key = e.getKeyChar();
				if(key == 'a' || key == 'A'){
					maingame.getWorld().player.left = true;
				}
				if(key == 'd' || key == 'D'){
					maingame.getWorld().player.right = true;
				}
				if(key == 'w' || key == 'W'){
					maingame.getWorld().player.up = true;
				}
				if(key == 's' || key == 'S'){
					maingame.getWorld().player.down = true;
				}
				if(key == 'e' || key == 'E'){
					maingame.getWorld().player.use = true;
				}
				if(e.isShiftDown()){
					//Shift Key
					maingame.world.player.shift = true;
				}
			}
			public void keyReleased(KeyEvent e) {
				char key = e.getKeyChar();
				if(key == 'a' || key == 'A'){
					maingame.getWorld().player.left = false;
				}
				if(key == 'd' || key == 'D'){
					maingame.getWorld().player.right = false;
				}
				if(key == 'w' || key == 'W'){
					maingame.getWorld().player.up = false;
				}
				if(key == 's' || key == 'S'){
					maingame.getWorld().player.down = false;
				}
				if(key == 'e' || key == 'E'){
					maingame.getWorld().player.use = false;
				}
				if(!e.isShiftDown()){
					//Shift Key
					maingame.world.player.shift = false;
				}
			}
		});
		canvas.setBounds(0, 0, 1920, 1080);
		add(canvas);
		maingame = (MainGame) canvas;
	}

	public MainGame getMainGame() {
		return maingame;
	}

	
}
