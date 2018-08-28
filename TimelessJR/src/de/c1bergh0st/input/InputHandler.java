package de.c1bergh0st.input;

import java.util.HashMap;
import java.util.Map;

import de.c1bergh0st.debug.Debug;

public class InputHandler {
	private boolean showPresses = false;
	
	
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private String[] inputKeys = {"up","down","left","right","shift","use"};
	private boolean[] keyStatus;
	
	public InputHandler(){
		keyStatus = new boolean[inputKeys.length];
		for(int i = 0; i < inputKeys.length; i++){
			map.put(inputKeys[i], i);
		}
		Debug.send("INPUTHANDLER initialized");
	}
	
	public boolean isDown(String key){
		if(!map.containsKey(key)){
			Debug.sendErr("Key:"+key+" is not supported!");
			return false;
		}
		else{
			return keyStatus[map.get(key)];
		}
	}

	public void pressed(String key){
		if(!map.containsKey(key)){
			Debug.sendErr("Key:"+key+" is not supported!");
		}
		else{
			keyStatus[map.get(key)] = true;
			logStatus(key,true);
		}
	}

	public void released(String key){
		if(!map.containsKey(key)){
			Debug.sendErr("Key:"+key+" is not supported!");
		}
		else{
			keyStatus[map.get(key)] = false;
			logStatus(key,false);
		}
	}
	
	private void logStatus(String key, boolean pressed) {
		if(showPresses){
			if(pressed){
				Debug.send("Key: "+key+" was pressed");
			}
			else{
				Debug.send("Key: "+key+" was released");
			}
		}
	}
	
}
