package de.c1bergh0st.levelobjects.actives;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public class PlayerProjectile extends Projectile{
	//	PlayerProjectile will fly vertically until it hits something
	//	If the Object being hit is an ActiveObject, the Projectile will do dmg-Damage and destroy itself
	private int dmg;

	public PlayerProjectile(double x, double y, double dx, double dy, int dmg, Level level) {
		super(x, y, Util.toUnits(32), Util.toUnits(8), dx, dy, level, level.imgload.getActiveTextureByName("proj"));
		this.dmg = dmg;
	}

	public void draw(Graphics g) {
		if(dx > 0){
			g.drawImage(img,Util.toPix(x), Util.toPix(y), Util.toPix(width), Util.toPix(height),null);
		}
		else{
			g.drawImage(img,Util.toPix(x+width), Util.toPix(y), Util.toPix(-width), Util.toPix(height),null);
		}
	}

	public void tick() {
		x += dx;
		y += dy;
	}

	public String getType() {
		return "PlayerProjectile";
	}

	public void kill() {
		level.queueRemoval(this);
	}

	public void playerhit(Player player) {
		// Players cant be damaged by their own shots
		// At least they SHOULDNT be
	}

	public void statichit(StaticObject stat) {
		kill();
	}

	public void activehit(ActiveObject act) {
		act.damage(dmg);
		kill();
	}
	

}
