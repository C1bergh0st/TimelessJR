package de.c1bergh0st.levelobjects.actives;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public class EnemyBlob extends NPC{
	private Rectangle2D.Double sensor_left;
	private Rectangle2D.Double sensor_right;
	private Rectangle2D.Double sensor_bottom_left;
	private Rectangle2D.Double sensor_bottom_right;
	private Rectangle2D.Double sensor_kill;
	private BufferedImage img;
	private BufferedImage[] death;
	private double dir;
	private boolean alive;
	private long deathtime;

	public EnemyBlob(double x, double y, Level level) {
		super(x, y, 1.5, 1, 50, level, "");
		alive = true;
		death = new BufferedImage[7];
		try {
			this.img = ImageIO.read(EnemyBlob.class.getResourceAsStream("/res/enemys/blob/blob.png"));
			for(int i = 0; i < 7; i++){
				death[i] = ImageIO.read(EnemyBlob.class.getResourceAsStream("/res/enemys/blob/death/"+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		dir = 1;
	}

	@Override
	public String getType() {
		return "EnemyBlob";
	}

	@Override
	public void tick() {
		updateSensors();
		checkForDeath();
		walkLogic();
		physic();
		applyforce();
		collisonLoop();
	}

	private void checkForDeath() {
		if (level.player.getBounds().intersects(sensor_kill) && !level.player.isInvulnerable() && alive){
			kill();
			level.player.renewJump();
		}
	}

	private void walkLogic() {
		double speed = 2.5d/120;
		boolean shouldinvert = false;
		boolean left = false;
		boolean right = false;
		dx = speed* dir;
		Rectangle2D.Double[] bounds = level.getStaticBounds();
		for(Rectangle2D.Double temp : bounds){
			if(temp.intersects(sensor_left) && dx < 0){
				shouldinvert = true;
			}
			if(temp.intersects(sensor_bottom_left) && dx < 0){
				left = true;
			}
			if(temp.intersects(sensor_bottom_right) && dx > 0){
				right = true;
			}
			if(temp.intersects(sensor_right) && dx > 0){
				shouldinvert = true;
			}
		}
		if(!left && dx< 0){
			shouldinvert = true;
		}

		if(!right && dx >  0){
			shouldinvert = true;
		}
		if(shouldinvert){
			if(dir < 0){
				dir = 1;
			}
			else{
				dir = -1;
			}
		}
		if(!alive){
			dx = 0;
		}
	}
	
	public void updateSensors(){
		sensor_left = new Rectangle2D.Double(x-0.1,y,0.1,0.9);
		sensor_right = new Rectangle2D.Double(x+width,y,0.1,0.9);
		sensor_bottom_left = new Rectangle2D.Double(x-0.1,y+1,0.1,0.5);
		sensor_bottom_right = new Rectangle2D.Double(x+width,y+1,0.1,0.5);
		sensor_kill = new Rectangle2D.Double(x+0.1,y-0.1,1.3,0.1);
	}

	@Override
	public void draw(Graphics g) {
		long timeSinceDeath = System.currentTimeMillis() - deathtime;
		long speed = 200;
		if(alive){
			if(dx > 0){
				g.drawImage(img, Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
			else{
				g.drawImage(img, Util.toPix(x+1.5), Util.toPix(y), Util.toPix(-1.5), Util.toPix(1), null);
			}
			
		}
		else{
			if(timeSinceDeath < speed){
				g.drawImage(death[0], Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
			if(timeSinceDeath > speed && timeSinceDeath < 2*speed){
				g.drawImage(death[1], Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
			if(timeSinceDeath > 2*speed && timeSinceDeath < 3*speed){
				g.drawImage(death[2], Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
			if(timeSinceDeath > 3*speed && timeSinceDeath < 4*speed){
				g.drawImage(death[3], Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
			if(timeSinceDeath > 4*speed && timeSinceDeath < 5*speed){
				g.drawImage(death[4], Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
			if(timeSinceDeath > 5*speed && timeSinceDeath < 6*speed){
				g.drawImage(death[5], Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
			if(timeSinceDeath > 6*speed){
				g.drawImage(death[6], Util.toPix(x), Util.toPix(y), Util.toPix(1.5), Util.toPix(1), null);
			}
		}
//		drawphysbox(g);
//		Util.drawRect(sensor_left, g, Util.REDTRANSPARENT);
//		Util.drawRect(sensor_right, g, Util.REDTRANSPARENT);
//		Util.drawRect(sensor_bottom_left, g, Util.GREENTRANSPARENT);
//		Util.drawRect(sensor_bottom_right, g, Util.GREENTRANSPARENT);
//		Util.drawRect(sensor_kill, g, Util.REDTRANSPARENT);
	}

	@Override
	public void kill() {
		if(alive){
			alive = false;
			deathtime = System.currentTimeMillis();
			setCollision(false);
		}
	}

	@Override
	public void playerhit(Player player) {
		if(alive){
			player.hit(10);
		}
	}

	@Override
	public void statichit(StaticObject stat){}

	@Override
	public void activehit(ActiveObject act){}

}
