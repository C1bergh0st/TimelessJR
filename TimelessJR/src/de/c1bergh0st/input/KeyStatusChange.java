package de.c1bergh0st.input;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.c1bergh0st.debug.Debug;

public class KeyStatusChange extends AbstractAction{
	private InputHandler handler;
	private String key;
	private boolean pressed;
	
	public KeyStatusChange(InputHandler handler, String key, boolean pressed){
		this.handler = handler;
		this.key = key;
		this.pressed = pressed;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(pressed){
			handler.pressed(key);
		}
		else{
			handler.released(key);
		}
	}

}
