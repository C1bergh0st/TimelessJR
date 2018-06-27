package de.c1bergh0st.levelobjects.actives;

import java.awt.Color;
import java.awt.Graphics;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public class Target extends ActiveObject{

	public Target(double x, double y, Level level) {
		super(x, y, 1, 1, 0, 0, 100, level, "Na");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "Target";
	}

	@Override
	public void tick() {
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Util.REDTRANSPARENT);
		g.fillRect(Util.toPix(x), Util.toPix(y), Util.toPix(width), Util.toPix(height));
		g.setColor(Color.BLACK);
		g.drawString(""+getHealth(), Util.toPix(x+0.1), Util.toPix(y+0.2));
	}

	@Override
	public void kill() {
		level.removeAct.add(this);
		Debug.send("Target "+toString()+" was destroyed");
	}

	@Override
	public void playerhit(Player player) {
	}

	@Override
	public void statichit(StaticObject stat) {
	}

	@Override
	public void activehit(ActiveObject act) {
	}

}
