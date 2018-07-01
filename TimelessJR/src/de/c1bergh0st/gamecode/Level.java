package de.c1bergh0st.gamecode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import de.c1bergh0st.audio.AudioController;
import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.levelobjects.*;
import de.c1bergh0st.levelobjects.actives.ActiveObject;
import de.c1bergh0st.levelobjects.actives.Target;
import de.c1bergh0st.levelobjects.actives.TestNPC;

public abstract class Level {
	protected MainGame game;
	public static final int TILESIZE = 96;
	public double tileoffsetx;
	public double tileoffsety;
	public LinkedList<StaticObject> statics;
	public LinkedList<Interactable> interactables;
	public LinkedList<Decoration> decos;
	public LinkedList<ActiveObject> actives;
	public LinkedList<ActiveObject> removeAct;
	public ImageLoader imgload;
	public DecoImageLoader decoimgload;
	public Player player;
	public Background background;
	private String qLevel;
	public AudioController audio;
	
	public Level(MainGame in_game){
		game = in_game;
		imgload = new ImageLoader();
		decoimgload = new DecoImageLoader();
		audio = new AudioController();
//		audio.playSound("/res/sounds/short.wav");
		audio.playBackground("track.wav");
	}
	
	public void internaltick(){
		tick();
		audio.update();
		if(qLevel != null){
			loadLevel(qLevel);
		}
	}
	
	protected abstract void  tick();
	public abstract void  render(Graphics g);
	public abstract void mousemoved(int x, int y);
	
	public void queueLevel(String level){
		qLevel = level;
	}
	
	public void loadLevel(String level) {
		qLevel = null;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		statics = new LinkedList<StaticObject>();
		interactables = new LinkedList<Interactable>();
		decos = new LinkedList<Decoration>();
		actives = new LinkedList<ActiveObject>();
		removeAct = new LinkedList<ActiveObject>();
		actives.add(new Target(8, 4, this));
		actives.add(new TestNPC(15,2,this));
		actives.add(new TestNPC(17,2,this));
		try {
			background = new Background("ice",5);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        try {

            br = new BufferedReader(new FileReader("src/res/levels/"+level+".csv"));
            while ((line = br.readLine()) != null) {
            	if(!line.startsWith("id")){
                    // use comma as separator
                    String[] value = line.split(cvsSplitBy);
                    loadBlock(value);
            	}
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
//        interactables.add(new LevelLoadInteractable(3,3,3,3,"Mario",this));
//		statics.add(new DevFloor(2, 4, imgload));
//		statics.add(new DevFloor(3, 4, imgload));
        if(player != null){
        	player.terminate();
        }
		player = new Player(2,1.5,"jungle",this,level);
		Debug.send("Level: '"+level+"' loaded successfully");
	}
	
	public Rectangle2D.Double[] getStaticBounds(){
		Rectangle2D.Double[] temp = new Rectangle2D.Double[statics.size()];
		for(int i = 0; i < statics.size(); i++){
			temp[i] = statics.get(i).getBounds();
		}
		return temp;
	}
	
	
	public Interactable getInteractable(Rectangle2D.Double pbounds) {
		for(int i = 0; i < interactables.size();i++){
			if (interactables.get(i).getBounds().contains(pbounds)){
				return interactables.get(i);
			}
		}
		return null;
	}

    private void loadBlock(String[] arg){
    	// arg[0] = internalID
    	// arg[1] = String defining the Block type
    	// arg[2] = Double:x
    	// arg[3] = Double:y
    	// arg[4] = String:args
        if(arg[1].contains("STATIC")){
        	statics.add(new StillObject(Double.parseDouble(arg[2]),Double.parseDouble(arg[3]),arg[1].replaceAll("STATIC", ""),imgload));
//            System.out.println(arg[0] + "_Object:" + arg[1] + " At (" + arg[2] + ";" + arg[3]+")");
        }
        else if(arg[1].contains("INTERACT")){
        	if(arg[1].contains("LevelLoadInteractable")){
        		interactables.add(new LevelLoadInteractable(Double.parseDouble(arg[2]),
						Double.parseDouble(arg[3]),
						Double.parseDouble(arg[4]),
						Double.parseDouble(arg[5]),arg[6],this));
//        		System.out.println(arg[0] + "_Interactavble:" + arg[1] + " At (" + arg[2] + ";" + arg[3]+")");
        	}
        }
        else if(arg[1].contains("DECO")){
        	if(arg[1].contains("DECOSTAT")){
            	decos.add(new StaticDecoration(Double.parseDouble(arg[2]),Double.parseDouble(arg[3]),arg[1].replaceAll("DECOSTAT", ""),decoimgload));
//            	Debug.send(arg[0]+"_Deco+"+arg[1]+" At (" + arg[2] + ";" + arg[3]+")");
        	}
        }
        else{
        }
    }
	
    public void queueRemoval(ActiveObject obj){
    	if(!removeAct.contains(obj)){
    		removeAct.add(obj);
    	}
    	else{
    		Debug.send("Object "+obj+" will already be removed!");
    	}
    }

    protected void drawInfo(Graphics g){
    	g.setColor(Color.LIGHT_GRAY);
    	g.fillRect(1700, 0, 200, 150);
    	g.setColor(Color.BLACK);
    	g.drawString("Statics:"+statics.size(), 1700, 20);
    	g.drawString("Actives:"+actives.size(), 1700, 45);
    	g.drawString("Decos:"+decos.size(), 1700, 70);
    	g.drawString("Interactables:"+interactables.size(), 1700, 95);
    	g.drawString("Player Health:"+player.getHealth(), 1700, 120);
    	g.drawString("Active Audio:"+audio.playing(), 1700, 145);
    }

    public LinkedList<StaticObject> getStatics(){
    	return statics;
    }
    
}
