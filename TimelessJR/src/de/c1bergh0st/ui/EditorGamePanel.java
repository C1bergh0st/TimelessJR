package de.c1bergh0st.ui;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.MainGame;

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
	
	public EditorGamePanel(Window parent) {
		Debug.send("EDITORPANEL");
		this.parent = parent;
		setLayout(null);
		
		Canvas canvas = new MainGame(parent);
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
		
		InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = getActionMap();

        im.put(pressed(KeyEvent.VK_SHIFT), "pressed");
        System.out.println(KeyEvent.VK_SHIFT);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), "released");
        
		am.put("pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed");
            }
        });

        am.put("released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("released");
            }
        });
		
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
		maingame = (MainGame) canvas;
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

	public MainGame getMainGame() {
		return maingame;
	}

	
}
