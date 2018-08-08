package de.c1bergh0st.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import de.c1bergh0st.debug.Debug;

public class Sound {
	private Clip clip;
	private AudioInputStream ais;
	private int id;
	private String path;
	
	public Sound(String path, int id, boolean looping){
		this.path = path;
		this.id = id;
		try {
			clip = AudioSystem.getClip();
			ais = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(path));
			clip.open(ais);
			if(looping){
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isFinished(){
		return !clip.isRunning();
	}
	
	
	public void terminate(){
		clip.stop();
		try {
			ais.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	
//	Clip clip = AudioSystem.getClip();
//	AudioInputStream ais = AudioSystem.getAudioInputStream(
//			SoundTest.class.getResourceAsStream("res/short.wav"));
//	clip.open(ais);
//	clip.start();
//	clips.add(clip);
}
