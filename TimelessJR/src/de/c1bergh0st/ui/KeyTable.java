package de.c1bergh0st.ui;

import java.util.HashMap;
import java.util.Map;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.MainGame;

public class KeyTable {
	private MainGame maingame;
	private String[] codes= {"up","down","left","right","use","shoot"};
	private boolean[] values;
	private Map<String, Integer> keymap = new HashMap<String, Integer>();
	
	public KeyTable(MainGame maingame){
		this.maingame = maingame;
		for(int i = 0; i < codes.length; i++){
			keymap.put(codes[i], i);
		}
		values = new boolean[codes.length];
	}
	
	public void setKey(String code, boolean pressed){
		if(keymap.containsKey(code)){
			values[keymap.get(code)] = pressed;
		}
		else{
			Debug.sendErr("Unrecognized KeyCode in KeyTable : "+code);
		}
	}
	
	public boolean isKeyDown(String code){
		if(keymap.containsKey(code)){
			return values[keymap.get(code)];
		}
		else{
			Debug.sendErr("Unrecognized KeyCode in KeyTable : "+code);
			return false;
		}
		
	}

}
