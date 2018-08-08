package de.c1bergh0st.levelobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.actives.PlayerProjectile;

public class Player {
	private Level level;
	public double x, y, dx, dy;
	private int health;
	private long lasthurt;
	public BufferedImage[] idlestack;
	public BufferedImage[] runstack;
	private Timer animationtimer;
	private boolean secondTick;
	private int curIdle, curRun;
	public BufferedImage jumping, landing;
	private Rectangle2D.Double downBox, topBox, leftBox, rightBox, jumpBox;
	public boolean onGround;
	private boolean hasjumped = false;
	public boolean down, left, right, shift, use, up;
	private double pw = 0.9d;
	private double ph = 1.8d;
	private String onDeathLevel;
	
	public Player(double x, double y, String imgstr, Level level, String fallbacklevel){
		this.onDeathLevel = fallbacklevel;
		this.level = level;
		this.x = x;
		this.y = y;
		this.health = 100;
		try {
			this.jumping = ImageIO.read(Player.class.getResourceAsStream("/res/"+imgstr+"/jump.png"));
			this.landing = ImageIO.read(Player.class.getResourceAsStream("/res/"+imgstr+"/landing.png"));

			runstack = new BufferedImage[8];
			for(int i = 0; i < 8; i++){
				runstack[i] = ImageIO.read(Player.class.getResourceAsStream("/res/"+imgstr+"/run/"+(i+1)+".png"));
			}
			
			idlestack = new BufferedImage[12];
			for(int i = 0; i < 12; i++){
				idlestack[i] = ImageIO.read(Player.class.getResourceAsStream("/res/"+imgstr+"/idle/"+(i+1)+".png"));
			}
		} catch (IOException e) {
			Debug.sendErr("Devtex failed to load");
		}
		updateBox();
		animationtimer = new Timer();
		animationtimer.schedule(new TimerTask(){
			
			public void run() {
				if(secondTick){
					if(curIdle < 11){
						curIdle++;
					}
					else{
						curIdle = 0;
					}
					if(curRun < 7){
						curRun++;
					}
					else{
						curRun = 0;
					}
				}
				if(!secondTick && shift){
					if(curRun < 7){
						curRun++;
					}
					else{
						curRun = 0;
					}
				}
				secondTick = !secondTick;
				
			}
			
		}, 50, 50);
	}
	
	public void draw(Graphics g){
		if(Math.abs(dx)< 0.03 && onGround){
			//Idle if not Moving and on ground
			drawIdle(g);
		}
		else if(onGround && Math.abs(dx) > 0.03){
			//moving if moving and on ground
			drawWalk(g);
		}
		else if(!onGround && dy < 0){
			//jumping up
			if(dx < 0){
				g.drawImage(jumping, Util.toPix(x+pw), Util.toPix(y), Util.toPix(-pw), Util.toPix(ph), null);
			}else{
				g.drawImage(jumping, Util.toPix(x), Util.toPix(y), Util.toPix(pw), Util.toPix(ph), null);
			}
		}
		else if(!onGround && dy > 0){
			//falling down TODO: Clean this Mess up
			if(dx < 0){
				g.drawImage(landing, Util.toPix(x+pw), Util.toPix(y), Util.toPix(-pw*1.1), Util.toPix(ph), null);
			}else{
				g.drawImage(landing, Util.toPix(x), Util.toPix(y), Util.toPix(pw*1.1), Util.toPix(ph), null);
			}
		}
		else{
			drawIdle(g);
		}
		drawphysbox(g);
		if(isInvulnerable()){
			Util.drawRect(getBounds(), g, new Color(255,255,0,150));
		}
	}

	public Rectangle2D.Double getBounds(){
		return new Rectangle2D.Double(x, y, pw, ph);
	}
	
	public void hit(int dmg){
		if(lasthurt < System.currentTimeMillis()-1000){
			health -= dmg;
			lasthurt = System.currentTimeMillis();
//			level.audio.stopBackground();
			level.audio.playSound("hurt.wav");
		}
		if(health <= 0){
			kill();
		}
	}
	
	public boolean isInvulnerable(){
		if(lasthurt < System.currentTimeMillis()-1000){
			return false;
		}
		return true;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void kill(){
		level.queueLevel(onDeathLevel);
	}
	
	private void drawWalk(Graphics g){
		if(dx < 0){
			g.drawImage(runstack[curRun], Util.toPix(x+pw), Util.toPix(y), Util.toPix(-(pw*1.1)), Util.toPix(ph), null);
		}else{
			g.drawImage(runstack[curRun], Util.toPix(x), Util.toPix(y), Util.toPix((pw*1.1)), Util.toPix(ph), null);
		}
	}

	private void drawIdle(Graphics g){
		if(dx < 0){
			g.drawImage(idlestack[curIdle], Util.toPix(x+pw), Util.toPix(y), Util.toPix(-pw), Util.toPix(ph), null);
		}else{
			g.drawImage(idlestack[curIdle], Util.toPix(x), Util.toPix(y), Util.toPix(pw), Util.toPix(ph), null);
		}
	}
	
	protected void drawphysbox(Graphics g) {
		g.setColor(Color.RED);
		if(downBox != null){
			g.drawRect(Util.toPix(downBox.x),Util.toPix(downBox.y),Util.toPix(downBox.width),Util.toPix(downBox.height));
		}
		if(jumpBox != null){
			g.drawRect(Util.toPix(jumpBox.x),Util.toPix(jumpBox.y),Util.toPix(jumpBox.width),Util.toPix(jumpBox.height));
		}
		if(leftBox != null){
			g.drawRect(Util.toPix(leftBox.x),Util.toPix(leftBox.y),Util.toPix(leftBox.width),Util.toPix(leftBox.height));
		}
		if(rightBox != null){
			g.drawRect(Util.toPix(rightBox.x),Util.toPix(rightBox.y),Util.toPix(rightBox.width),Util.toPix(rightBox.height));
		}
		if(topBox != null){
			g.drawRect(Util.toPix(topBox.x),Util.toPix(topBox.y),Util.toPix(topBox.width),Util.toPix(topBox.height));
		}
		g.setColor(Color.BLACK);
	}

	public void tick() {
		applyforce();
		physic();
		input();
		if(y > 1000){
			x = 2;
			y = 0;
			dx = 0;
			dy = 0;
		}
	}

	private void input() {
		if(!up && onGround){
			hasjumped = false;
		}
		double maxspeed = 4d;
		double maxspeedpertick = maxspeed/120d;
		double accel = 0.5d;
		double airspeed = 0.1;
		double maxairspeed = 0.5;
		boolean moved = false;
		if(use){
			tryUse();
		}
		if(up && !hasjumped &&onGround){
			dy =-(9.4f/120);
			hasjumped = true;
		}
		if(left && dx > -maxspeedpertick*maxairspeed && !onGround){
			dx -= (accel/(120))*airspeed;
			if(dx > 0){
				applyDrag();
			}
		}
		if(left && dx > -maxspeedpertick && onGround && !shift){
			dx -= (accel/(120));
			if(dx > 0){
				applyDrag();
			}
		}
		if(left && dx > -(maxspeedpertick*1.5) && onGround && shift){
			dx -= (accel/(120));
			if(dx > 0){
				applyDrag();
			}
		}
		if(right && dx < maxspeedpertick*maxairspeed && !onGround){
			dx += (accel/(120))*airspeed;
			if(dx < 0){
				applyDrag();
			}
		}
		if(right && dx < maxspeedpertick && onGround && !shift){
			dx += (accel/(120));
			if(dx < 0){
				applyDrag();
			}
		}
		if(right && dx < maxspeedpertick*1.5 && onGround && shift){
			dx += (accel/(120));
			if(dx < 0){
				applyDrag();
			}
		}
		if(left || right){
			moved = true;
		}
		if(dx > maxspeedpertick*1.5 && !onGround){
			dx = maxspeedpertick*1.5;
		}
		if(dx < -maxspeedpertick*1.5 && !onGround){
			dx = -maxspeedpertick*1.5;
		}
		if(dx > maxspeedpertick && onGround && !shift){
			dx = maxspeedpertick;
		}
		if(dx < -maxspeedpertick && onGround && !shift){
			dx = -maxspeedpertick;
		}
		
		if(!moved){
			applyDrag();	
		}
	}


	private void tryUse() {
		if(dx > 0){
			level.actives.add(new PlayerProjectile(x+pw/2,y+0.4,0.1,0d,10,level));
		}
		else{
			level.actives.add(new PlayerProjectile(x+pw/2,y+0.4,-0.1,0d,10,level));
		}
		level.audio.playSound("shot.wav");
		Interactable intersect = level.getInteractable(new Rectangle2D.Double(x, y, pw, ph));
		if(intersect != null){
			intersect.use();
		}
		use = false;
	}

	public void collision(Rectangle2D.Double box) {
		if(downBox.intersects(box) && dy > 0){
			y = box.y -ph;
			dy = 0;
			updateBox();
		}
		if(topBox.intersects(box)){
			y = box.y +1;
			dy = dy*0.93;
			updateBox();
		}
		if(leftBox.intersects(box)){
			dx = -0.0000000000000000000000000000000000000000000000000000001;
			x = box.x +1;
			updateBox();
		}
		if(rightBox.intersects(box)){
			dx = 0;
			x = box.x-pw;
			updateBox();
		}
		if(jumpBox.intersects(box)){
			onGround = true;
		}
	}

	private void applyDrag() {
		dx = dx * (0.89);
		if(dx < 0.01/120 && dx > 0.01/120){
			dx = 0;
		}
	}


	private void applyforce() {
		x += dx;
		y += dy;
		updateBox();
	}

	private void updateBox() {
		double dh = 0.3f;
		double dw = pw-Math.abs(dx)*8-0.1;
		if(dw >0.9){
			dw = 0.9;
		}
		double vh = (ph*0.9)-Math.abs(dy)*8;
		downBox = new Rectangle2D.Double(x+(pw-dw)/2, y+ph-dh, dw, dh);
		topBox = new Rectangle2D.Double(x+(pw-dw)/2, y, dw, dh);
		jumpBox = new Rectangle2D.Double(x+(pw-dw/1.01)/2, y+ph+0.05-dh, dw/1.01, dh);
		rightBox = new Rectangle2D.Double(x+0.8*pw,y+((ph-vh)/2),0.2*pw,vh);
		leftBox = new Rectangle2D.Double(x,y+((ph-vh)/2),0.2,vh);
	}

	private void physic() {
		if(dy < (30f/120)){
			dy+= 9.8f/(120*120);
		}
		if(dy > 0 && !onGround){
			dy += (15f/(120*120));
		}
	}
	
	public void terminate(){
		animationtimer.cancel();
	}
}
