package de.c1bergh0st.levelobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.gamecode.LevelEditor;

public class LevelLoadInteractable extends Interactable{
	private Level level;
	private String levelname;
	private boolean valid;

	public LevelLoadInteractable(double x, double y, double w, double h, String levelname, Level level) {
		super(x, y, w, h);
		valid = false;
		this.levelname = levelname;
		this.level = level;
		File f = new File("src/res/levels/"+levelname+".csv");
		if(f.exists() && !f.isDirectory()) { 
		    valid = true;
		}
		else{
			Debug.sendErr("Invalid level in LevelLoadInteractable");
		}
	}
	
	public void devDraw(Graphics g){
		g.setColor(Util.REDTRANSPARENT);
		g.fillRect(Util.toPix(x), Util.toPix(y), Util.toPix(w), Util.toPix(h));
		g.setColor(Color.BLACK);
		g.drawString(levelname+".csv", Util.toPix(x+0.05), Util.toPix(y+0.2));
	}

	@Override
	public void use() {
		if(valid){
			level.loadLevel(levelname);
		}else{
			Debug.sendErr("LevelLoadInteractable("+x+";"+y+""+w+""+h+")invalid. Level at src/res/levels/"+levelname+".csv does not exist");
		}
	}

	@Override
	public String getType() {
		return "LevelLoadInteractable";
	}

	@Override
	public String getArgs() {
		return levelname;
	}
	
}
