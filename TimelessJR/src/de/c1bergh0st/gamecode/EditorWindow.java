package de.c1bergh0st.gamecode;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.ui.Storage;

public class EditorWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	public LevelEditor parent;

	public EditorWindow (String title, LevelEditor levelSelect){
		super(title);
		parent = levelSelect;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(500, 350);	//Adjusting for Border
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		pack();
		setVisible(true);
		add(new EditorPanel(this));
		pack();
	}
}
