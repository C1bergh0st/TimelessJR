package de.c1bergh0st.gamecode;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.levelobjects.*;
import de.c1bergh0st.levelobjects.actives.ActiveObject;
import de.c1bergh0st.levelobjects.actives.Target;

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
	public Level(MainGame in_game){
		game = in_game;
		imgload = new ImageLoader();
		decoimgload = new DecoImageLoader();
	}
	
	
	
	public abstract void  tick();
	public abstract void  render(Graphics g);
	public abstract void mousemoved(int x, int y);
	
	public void loadLevel(String level) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
		statics = new LinkedList<StaticObject>();
		interactables = new LinkedList<Interactable>();
		decos = new LinkedList<Decoration>();
		actives = new LinkedList<ActiveObject>();
		removeAct = new LinkedList<ActiveObject>();
		actives.add(new Target(8, 4, this));
		try {
			background = new Background("plx",5);
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
		player = new Player(2,1.5,"jungle",this);
		Debug.send("Level: '"+level+"' loaded successfully");
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
    		Debug.sendErr("Object "+obj+" will already be removed!");
    	}
    }
}
