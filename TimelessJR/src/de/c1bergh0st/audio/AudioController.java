package de.c1bergh0st.audio;

import java.util.LinkedList;

import de.c1bergh0st.debug.Debug;

public class AudioController {
	private static final int MAX_ACTIVE_SOUNDS = 15;
	private int activeSounds;
	private int lastId;
	private LinkedList<Sound> sounds = new LinkedList<Sound>();
	private LinkedList<Sound> removeables = new LinkedList<Sound>();
	private Sound loopingSound;
	
	
	public AudioController(){
		
	}
	
	public void playBackground(String path){
		loopingSound = new Sound("/res/sounds/"+path,-1,true);
		activeSounds++;
	}
	
	public void stopBackground(){
		loopingSound.terminate();
		loopingSound = null;
		activeSounds--;
	}
	
	public int playing(){
		return activeSounds;
	}
	
	public void update(){
		//check for finished clips and Destroy them
		removeables = new LinkedList<Sound>();
		for(int i = 0; i < sounds.size();i++){
			if(sounds.get(i).isFinished()){
				removeables.add(sounds.get(i));
			}
		}
		for(Sound temp : removeables){
			sounds.remove(temp);
			activeSounds--;
		}
	}
	
	
	
	public int playSound(String path){
		if(activeSounds < MAX_ACTIVE_SOUNDS+1){
			activeSounds++;
			sounds.add(new Sound("/res/sounds/"+path,lastId+1,false));
			lastId++;
		}
		else{
			Debug.sendErr("Only up to "+MAX_ACTIVE_SOUNDS+" Sounds supported at once");
		}
		return -1;
	}
	
	
	
	public void stopSound(int id){
		activeSounds--;
	}
}
