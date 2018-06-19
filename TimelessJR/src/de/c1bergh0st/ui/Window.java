package de.c1bergh0st.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.c1bergh0st.debug.Debug;

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;
	public Storage st;

	public Window (String title, int width, int height){
		super(title);
		st = new Storage();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(width+5, height+25);	//Adjusting for Border
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		pack();
		setResizable(false);
		setVisible(true);
		add(new Menu(this));
		pack();
	}
	
	public void showLayout(String type){
		if(type == "Menu"){
			getContentPane().removeAll();
			add(new Menu(this));
			revalidate();
			repaint();
		}
		else if(type == "Settings"){
			getContentPane().removeAll();
			add(new Settings(this));
			revalidate();
			repaint();
		}
		else if(type == "Editor"){
			getContentPane().removeAll();
			EditorPanel epanel = new EditorPanel(this);
			add(epanel);
			revalidate();
			repaint();
			epanel.getMainGame().start();
			
		}
		else if(type == "Game"){
			getContentPane().removeAll();
			Gamepanel gpanel = new Gamepanel(this);
			add(gpanel);
			revalidate();
			repaint();
			gpanel.getMainGame().start();
		}
		else{
			Debug.sendErr("Window.showLayout(str) only accepts {Menu; Settings; Game}");
		}
	}
}
