package de.c1bergh0st.gamecode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.ui.Menu;

public class ImageLoader {
	public BufferedImage devtex;
	public BufferedImage devtex2;
	private LinkedList<String> initList;
	public String[] tiletexnames;
	private BufferedImage[] tiletextures;
	public String[] activetexnames;
	private BufferedImage[] activetextures;
	public String[] decotexnames;
	private BufferedImage[] decotextures;
	public static final int TILE = 0;
	public static final int ACTIVE = 1;
	public static final int DECO = 2;
	private List<Map<String, Integer>> maplist = new ArrayList<Map<String, Integer>>();
	
	public ImageLoader(){
		maplist.add(new HashMap<String, Integer>());
		maplist.add(new HashMap<String, Integer>());
		maplist.add(new HashMap<String, Integer>());
		loadTileTextures();
		loadActiveTextures();
		loadDecoTextures();
		//TODO: Remove this ↓
		try {
			devtex = ImageIO.read(Menu.class.getResourceAsStream("/res/tiles/DevFloor.png"));
			devtex2 = ImageIO.read(Menu.class.getResourceAsStream("/res/DevShoot.png"));
		} catch (Exception e) {
			Debug.sendErr("Devtex failed to load");
		}
	}
	
	private void loadDecoTextures() {
		initList = new LinkedList<String>();
		File folder = new File("src/res/actives");
		File[] listOfFiles = folder.listFiles();
		String temp;

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        temp = listOfFiles[i].getName();
	        temp = temp.replaceFirst(".png", "");
	    	initList.add(temp);
	      }
	    }
	    decotexnames = new String[initList.size()];
	    decotextures = new BufferedImage[initList.size()];
		for(int i = 0; i < initList.size(); i++){
			decotexnames[i] = initList.get(i);
			maplist.get(DECO).put(decotexnames[i], i);
			try {
				decotextures[i] = ImageIO.read(Menu.class.getResourceAsStream("/res/deco/"+decotexnames[i]+".png"));
			} catch (Exception e) {
				Debug.sendErr("Internal Exception in ImageLoader while trying to load:"+decotexnames[i]+" at "+i);
			}
		}
	}

	private void loadActiveTextures() {
		initList = new LinkedList<String>();
		File folder = new File("src/res/actives");
		File[] listOfFiles = folder.listFiles();
		String temp;

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        temp = listOfFiles[i].getName();
	        temp = temp.replaceFirst(".png", "");
	    	initList.add(temp);
	      }
	    }
	    activetexnames = new String[initList.size()];
		activetextures = new BufferedImage[initList.size()];
		for(int i = 0; i < initList.size(); i++){
			activetexnames[i] = initList.get(i);
			maplist.get(ACTIVE).put(activetexnames[i], i);
			try {
				activetextures[i] = ImageIO.read(Menu.class.getResourceAsStream("/res/actives/"+activetexnames[i]+".png"));
			} catch (Exception e) {
				Debug.sendErr("Internal Exception in ImageLoader");
			}
		}
	}

	private void loadTileTextures() {
		initList = new LinkedList<String>();
		File folder = new File("src/res/tiles");
		File[] listOfFiles = folder.listFiles();
		String temp;

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        temp = listOfFiles[i].getName();
	        temp = temp.replaceFirst(".png", "");
	    	initList.add(temp);
	      }
	    }
		tiletexnames = new String[initList.size()];
		tiletextures = new BufferedImage[initList.size()];
		for(int i = 0; i < initList.size(); i++){
			tiletexnames[i] = initList.get(i);
			maplist.get(TILE).put(tiletexnames[i], i);
			try {
				tiletextures[i] = ImageIO.read(Menu.class.getResourceAsStream("/res/tiles/"+tiletexnames[i]+".png"));
			} catch (Exception e) {
				Debug.sendErr("Internal Error in ImageLoader");
			}
		}
	}


	public BufferedImage getTileTextureByName(String name){
		if(!maplist.get(TILE).containsKey(name)){
			Debug.sendErr("INVALID TEXTURE");
			return new BufferedImage(Util.toPix(1), Util.toPix(1), BufferedImage.TYPE_INT_ARGB);
		}
		else{
			return tiletextures[maplist.get(TILE).get(name)];
		}
	}

	public BufferedImage getDecoTextureByName(String name){
		if(!maplist.get(DECO).containsKey(name)){
			Debug.sendErr("INVALID TEXTURE");
			return new BufferedImage(Util.toPix(1), Util.toPix(1), BufferedImage.TYPE_INT_ARGB);
		}
		else{
			return decotextures[maplist.get(DECO).get(name)];
		}
	}


	public BufferedImage getActiveTextureByName(String name){
		if(!maplist.get(ACTIVE).containsKey(name)){
			Debug.sendErr("INVALID TEXTURE");
			return new BufferedImage(96, 96, BufferedImage.TYPE_INT_ARGB);
		}
		else{
			return activetextures[maplist.get(ACTIVE).get(name)];
		}
	}

	/**
	 * @deprecated Use {@link #getTileTextureByName(String)} instead
	 */
	public BufferedImage getTexByName(String name){
		return getTileTextureByName(name);
	}
	
}
