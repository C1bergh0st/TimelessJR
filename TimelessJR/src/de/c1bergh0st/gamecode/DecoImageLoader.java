package de.c1bergh0st.gamecode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.ui.Menu;

public class DecoImageLoader {
	private Map<String, Integer> map = new HashMap<String, Integer>();
	private LinkedList<String> initList = new LinkedList<String>();
	public String[] texnames;
	private BufferedImage[] textures;
	
	public DecoImageLoader(){
		
		File folder = new File("src/res/deco");
		File[] listOfFiles = folder.listFiles();
		String temp;

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        temp = listOfFiles[i].getName();
	        temp = temp.replaceFirst(".png", "");
	    	initList.add(temp);
	      }
	    }
		texnames = new String[initList.size()];
		textures = new BufferedImage[initList.size()];
		for(int i = 0; i < initList.size(); i++){
			texnames[i] = initList.get(i);
			map.put(texnames[i], i);
			try {
				textures[i] = ImageIO.read(Menu.class.getResourceAsStream("/res/deco/"+texnames[i]+".png"));
			} catch (IOException e) {
				Debug.sendErr("Internal Error in ImageLoader");
			}
		}
	}
	
	public BufferedImage getTexByName(String name){
		if(!map.containsKey(name)){
			Debug.sendErr("INVALID TEXTURE:"+name);
			return new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		}
		else{
			return textures[map.get(name)];
		}
	}
	
	
}
