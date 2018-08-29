package de.c1bergh0st.levelobjects.actives;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public class IceSplinter extends ActiveObject{
	private BufferedImage img;
	private boolean debrisSpawned;

	public IceSplinter(double x, double y, double dx, double dy, Level level) {
		super(x, y, 0.5, 0.5, dx, dy, 9999, level, "");
		int type = Util.randInt(0, 4);
		try {
			this.img = ImageIO.read(IceSplinter.class.getResourceAsStream("/res/enemys/crystal/shards/"+type+".png"));
			Debug.send("Image loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
		debrisSpawned = false;
	}

	@Override
	public String getType() {
		Debug.send("IceSplinter should not be saved");
		return "Unloadable";
	}

	@Override
	public void tick() {
		x += dx;
		y += dy;
		dy += 0.4d/(120d*2);
		if(dy > 20d/120d){
			dy = 20d/120d;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, Util.toPix(x), Util.toPix(y), Util.toPix(width), Util.toPix(height), null);
//		Debug.send("drawing");
	}

	@Override
	public void kill() {level.queueRemoval(this);}

	@Override
	public void playerhit(Player player) {
		player.hit(35);
	}

	@Override
	public void statichit(StaticObject stat) {
		level.queueRemoval(this);
		if(!debrisSpawned && stat.getBounds().intersects(new Rectangle2D.Double(x+0.2, y, 0.1, 0.7))){
			level.queueActive(new IceDebris(x,stat.getY()-height,level));
			debrisSpawned = true;
		}
	}

	@Override
	public void activehit(ActiveObject act) {}

}
