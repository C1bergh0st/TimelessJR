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
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.actives.PlayerProjectile;

public class Player {
	private Level level;
	public double x;
	public double y;
	public double dx;
	public double dy;
	public BufferedImage[] idlestack;
	public BufferedImage[] runstack;
	private Timer animationtimer;
	private boolean secondTick;
	private int curIdle;
	private int curRun;
	public BufferedImage jumping;
	public BufferedImage landing;
	private Rectangle2D.Double downBox;
	private Rectangle2D.Double topBox;
	private Rectangle2D.Double leftBox;
	private Rectangle2D.Double rightBox;
	private Rectangle2D.Double jumpBox;
	public boolean onGround;
	public boolean up = false;
	private boolean hasjumped = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;
	public boolean shift = false;
	public boolean use = false;
	private double pw = 0.9d;
	private double ph = 1.8d;
	
	public Player(double x, double y, String imgstr, Level level){
		this.level = level;
		this.x = x;
		this.y = y;try {
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
				g.drawImage(jumping, (int)((x+pw)*96), (int)(y*96), (int)(-pw*96), (int)(96*ph), null);
			}else{
				g.drawImage(jumping, (int)(x*96), (int)(y*96), (int)(pw*96), (int)(96*ph), null);
			}
		}
		else if(!onGround && dy > 0){
			//falling down
			if(dx < 0){
				g.drawImage(landing, (int)((x+pw)*96), (int)(y*96), (int)(-(pw*1.1)*96), (int)(96*ph), null);
			}else{
				g.drawImage(landing, (int)(x*96), (int)(y*96), (int)((pw*1.1)*96), (int)(96*ph), null);
			}
		}
		else{
			drawIdle(g);
		}
//		drawphysbox(g);
	}

	public Rectangle2D.Double getBounds(){
		return new Rectangle2D.Double(x, y, pw, ph);
	}
	
	public void hit(int dmg){
		
	}
	
	private void drawWalk(Graphics g){
		if(dx < 0){
			g.drawImage(runstack[curRun], (int)((x+pw)*96), (int)(y*96), (int)(-(pw*1.1)*96), (int)(96*ph), null);
		}else{
			g.drawImage(runstack[curRun], (int)(x*96), (int)(y*96), (int)((pw*1.1)*96), (int)(96*ph), null);
		}
	}

	private void drawIdle(Graphics g){
		if(dx < 0){
			g.drawImage(idlestack[curIdle], (int)((x+pw)*96), (int)(y*96), (int)(-pw*96), (int)(96*ph), null);
		}else{
			g.drawImage(idlestack[curIdle], (int)(x*96), (int)(y*96), (int)(pw*96), (int)(96*ph), null);
		}
	}
	
	protected void drawphysbox(Graphics g) {
		g.setColor(Color.RED);
		if(downBox != null){
			g.drawRect((int)(downBox.x*96),(int)(downBox.y*96),(int)(downBox.width*96),(int)(downBox.height*96));
		}
		if(jumpBox != null){
			g.drawRect((int)(jumpBox.x*96),(int)(jumpBox.y*96),(int)(jumpBox.width*96),(int)(jumpBox.height*96));
		}
		if(leftBox != null){
			g.drawRect((int)(leftBox.x*96),(int)(leftBox.y*96),(int)(leftBox.width*96),(int)(leftBox.height*96));
		}
		if(rightBox != null){
			g.drawRect((int)(rightBox.x*96),(int)(rightBox.y*96),(int)(rightBox.width*96),(int)(rightBox.height*96));
		}
		if(topBox != null){
			g.drawRect((int)(topBox.x*96),(int)(topBox.y*96),(int)(topBox.width*96),(int)(topBox.height*96));
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
	
	
}
