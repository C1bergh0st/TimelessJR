package de.c1bergh0st.levelobjects.actives;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public class TestNPC extends NPC{
	private Rectangle2D.Double sensor_left;
	private Rectangle2D.Double sensor_right;
	private Rectangle2D.Double sensor_jump_left;
	private Rectangle2D.Double sensor_jump_right;
	private boolean shouldjump;

	public TestNPC(double x, double y, Level level) {
		super(x, y, 1.2, 2, 50, level, "");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "TestNPC";
	}

	@Override
	public void tick() {
		physic();
		applyforce();
		collisonLoop();
		refreshSensors();
		doSensorLogic();
	}


	private void doSensorLogic() {
		Rectangle2D.Double player = level.player.getBounds();
		boolean moving = false;
		if(player.intersects(sensor_left)){
			dx = -0.01;
			moving = true;
		}
		if(player.intersects(sensor_right)){
			dx = 0.01;
			moving = true;
		}
		shouldjump = false;
		for(StaticObject temp : level.statics){
			if(temp.getBounds().intersects(sensor_jump_left) && dx < 0){
				shouldjump = true;
			}
			else if(temp.getBounds().intersects(sensor_jump_right) && dx > 0){
				shouldjump = true;
			}
		}
		if(moving && shouldjump && onGround){
			dy =-(5.2f/120);
			onGround = false;
		}
		if(!moving){
			applyDrag();
			Double d = Math.random();
			if(d < 0.3){
				dx += -0.001;
			}
			else if(d > 0.7){
				dx += 0.001;
			}
		}
	}

	private void refreshSensors() {
		sensor_left = new Rectangle2D.Double(x-4,y-1,4,4);
		sensor_right = new Rectangle2D.Double(x+width,y-1,4,4);
		sensor_jump_left = new Rectangle2D.Double(x-0.5,y+1,0.5,0.9);
		sensor_jump_right = new Rectangle2D.Double(x+width,y+1,0.5,0.9);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(0,255,0,200));
		g.fillRect(Util.toPix(x), Util.toPix(y), Util.toPix(width), Util.toPix(height));
		drawphysbox(g);
		Util.drawRect(sensor_left, g, Util.BLUETRANSPARENT);
		Util.drawRect(sensor_right, g, Util.BLUETRANSPARENT);
		Util.drawRect(sensor_jump_left, g, Util.REDTRANSPARENT);
		Util.drawRect(sensor_jump_right, g, Util.REDTRANSPARENT);
	}

	@Override
	public void kill() {
		level.queueRemoval(this);
	}

	@Override
	public void playerhit(Player player) {
		player.hit(10);
	}

	@Override
	public void statichit(StaticObject stat){}

	@Override
	public void activehit(ActiveObject act){}

}
