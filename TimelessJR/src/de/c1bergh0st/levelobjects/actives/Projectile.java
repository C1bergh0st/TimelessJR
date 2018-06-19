package de.c1bergh0st.levelobjects.actives;

import java.awt.image.BufferedImage;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.Level;

public  abstract class Projectile extends ActiveObject{
	protected BufferedImage img;

	public Projectile(double x, double y, double width, double height, double dx, double dy, Level level, BufferedImage img) {
		super(x, y, width, height, dx, dy, 1, level, "No Projectile should be spawned by a mapload");
		this.img = level.imgload.getActiveTextureByName("proj");
		Debug.send("new Projectile at"+x+"and "+y);
		// TODO Auto-generated constructor stub
	}

}
