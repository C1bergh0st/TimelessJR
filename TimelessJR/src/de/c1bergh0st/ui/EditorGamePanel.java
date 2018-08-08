package de.c1bergh0st.ui;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.input.InputHandler;
import de.c1bergh0st.input.KeyStatusChange;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class EditorGamePanel extends JPanel {
	
	Window parent;
	MainGame maingame;
	InputHandler input;
	
	public EditorGamePanel(Window parent) {
		Debug.send("EDITORPANEL");
		this.parent = parent;
		setLayout(null);
		
		Canvas canvas = new MainGame(parent);
		maingame = (MainGame) canvas;
		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Debug.send("Mouse Pressed:"+e.getButton()+" At px = "+e.getX()+" ; py = "+e.getY());
				if(e.getButton() == 1){
					maingame.world.addBlock(e.getX(),e.getY());
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				Debug.send("Mouse Released:"+e.getButton()+" At px = "+e.getX()+" ; py = "+e.getY());
			}
		});
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent me) {
				try{
//					maingame.getWorld().mousepos = me.getPoint();
				}catch(Exception e){}
			}
		});
		
		
		input = maingame.getInputHandler();
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();
		
		//SHIFT
        im.put(pressed(KeyEvent.VK_SHIFT), "shiftpressed");
		am.put("shiftpressed", new KeyStatusChange(input,"shift",true));
        im.put(released(KeyEvent.VK_SHIFT), "shiftreleased");
        am.put("shiftreleased", new KeyStatusChange(input,"shift",false));

		//LEFT
        im.put(pressed(KeyEvent.VK_A), "apressed");
        im.put(pressed2(KeyEvent.VK_A), "apressed");
		am.put("apressed", new KeyStatusChange(input,"left",true));
        im.put(released(KeyEvent.VK_A), "areleased");
        im.put(released2(KeyEvent.VK_A), "areleased");
        am.put("areleased", new KeyStatusChange(input,"left",false));

		//RIGHT
        im.put(pressed(KeyEvent.VK_D), "dpressed");
        im.put(pressed2(KeyEvent.VK_D), "dpressed");
		am.put("dpressed", new KeyStatusChange(input,"right",true));
        im.put(released(KeyEvent.VK_D), "dreleased");
        im.put(released2(KeyEvent.VK_D), "dreleased");
        am.put("dreleased", new KeyStatusChange(input,"right",false));

		//UP
        im.put(pressed(KeyEvent.VK_W), "wpressed");
        im.put(pressed2(KeyEvent.VK_W), "wpressed");
		am.put("wpressed", new KeyStatusChange(input,"up",true));
        im.put(released(KeyEvent.VK_W), "wreleased");
        im.put(released2(KeyEvent.VK_W), "wreleased");
        am.put("wreleased", new KeyStatusChange(input,"up",false));
        
        //USE
        im.put(pressed(KeyEvent.VK_E), "epressed");
        im.put(pressed2(KeyEvent.VK_E), "epressed");
		am.put("epressed", new KeyStatusChange(input,"use",true));
        im.put(released(KeyEvent.VK_E), "ereleased");
        im.put(released2(KeyEvent.VK_E), "ereleased");
        am.put("ereleased", new KeyStatusChange(input,"use",false));
        
		
        
//        am.put("shiftpressed", new AbstractAction(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				Debug.send("PRESS");
//			}
//        	
//        });
        
        
//		canvas.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent e){
//				char key = e.getKeyChar();
//				if(key == 'f'){
//					
//				}
//				if(key == 'h'){
//					
//				}
//				if(key == 't'){
//				}
//				if(key == 'g'){
//				}
//				if(key == 'r'){
//					maingame.world.tileoffsetx = 0;
//					maingame.world.tileoffsety = 0;
//					maingame.world.r();
//				}
//			}
//			
//			public void keyPressed(KeyEvent e) {
//				char key = e.getKeyChar();
//				if(key == 'a' || key == 'A'){
//					maingame.world.player.left = true;
//				}
//				if(key == 'd' || key == 'D'){
//					maingame.world.player.right = true;
//				}
//				if(key == 'w' || key == 'W'){
//					maingame.world.player.up = true;
//				}
//				if(key == 's' || key == 'S'){
//					maingame.world.player.down = true;
//				}
//				if(key == 'e' || key == 'E'){
//					maingame.world.player.use = true;
//				}
//				if(e.getKeyCode() == 16){
//					//Shift Key
//					maingame.world.shift = true;
//				}
//			}
//			public void keyReleased(KeyEvent e) {
//				char key = e.getKeyChar();
//				if(key == 'a' || key == 'A'){
//					maingame.world.player.left = false;
//				}
//				if(key == 'd' || key == 'D'){
//					maingame.world.player.right = false;
//				}
//				if(key == 'w' || key == 'W'){
//					maingame.world.player.up = false;
//				}
//				if(key == 's' || key == 'S'){
//					maingame.world.player.down = false;
//				}
//				if(key == 'e' || key == 'E'){
//					maingame.world.player.use = false;
//				}
//				if(e.getKeyCode() == 16){
//					//Shift Key
//					maingame.world.shift = false;
//				}
//			}
//		});
//		
		canvas.setBounds(0, 0, 1920, 1080);
		add(canvas);
	}
	
	private KeyStroke pressed(int key){
		if(key == KeyEvent.VK_SHIFT){
			return KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK, false);
		}
		return KeyStroke.getKeyStroke(key, 0, false);
	}
	
	private KeyStroke released(int key){
		return KeyStroke.getKeyStroke(key, 0, true);
	}

	
	private KeyStroke pressed2(int key){
		return KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK, false);
	}
	
	private KeyStroke released2(int key){
		return KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK, true);
	}
	public MainGame getMainGame() {
		return maingame;
	}

	
}
