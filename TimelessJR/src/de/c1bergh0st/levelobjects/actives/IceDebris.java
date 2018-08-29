package de.c1bergh0st.levelobjects.actives;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public class IceDebris extends ActiveObject{
	private BufferedImage img;

	public IceDebris(double x, double y ,Level level) {
		super(x, y, 0.5d, 0.5d, 0, 0, 9999, level, "");
		try {
			this.img = ImageIO.read(IceDebris.class.getResourceAsStream("/res/enemys/crystal/shards/debris.png"));
			Debug.send("Image loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getType() {
		return "IceDebris";
	}

	@Override
	public void tick() {}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, Util.toPix(x), Util.toPix(y), Util.toPix(width), Util.toPix(height), null);
	}

	@Override
	public void kill() {}

	@Override
	public void playerhit(Player player) {}

	@Override
	public void statichit(StaticObject stat) {}

	@Override
	public void activehit(ActiveObject act) {}

}
