package de.c1bergh0st.levelobjects.actives;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public class IceSpike extends NPC{
	private BufferedImage idle;
	
	private BufferedImage[] explosion;
	private int animationIndex;
	private long nextAnimationIndex;
	private boolean exploding;
	private boolean shardsSpawned;
	private int[] ANIMATIONSPEED = {120,120,120,120,150,150,300,200,300,400,500,600,0};

	public IceSpike(double x, double y, Level level) {
		super(x, y, 1, 2.5, 40, level, "");
		explosion = new BufferedImage[13];
		exploding = false;
		shardsSpawned = false;
		animationIndex = 0;
		try {
			this.idle = ImageIO.read(IceSpike.class.getResourceAsStream("/res/enemys/crystal/idle.png"));
			for(int i = 0; i < explosion.length; i++){
				explosion[i] = ImageIO.read(IceSpike.class.getResourceAsStream("/res/enemys/crystal/explosion/"+i+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setCollision(false);
	}

	@Override
	public String getType() {
		return "IceSpike";
	}

	@Override
	public void tick() {
		//If Exploding has begun we increment the animationIndex every ANIMATIONSPEED ms by 1 until it reaches 11
		if(exploding){
			if(nextAnimationIndex < System.currentTimeMillis() && animationIndex < 12){
				animationIndex++;
				nextAnimationIndex += ANIMATIONSPEED[animationIndex];
			}
			if(animationIndex == 10 && !shardsSpawned){
				shardsSpawned = true;
				double shardspeed = 4d/120d;
				level.queueActive(new IceSplinter(x+(width/2),y+(height/5),0,-(10d*rand())/120d,level));
				level.queueActive(new IceSplinter(x+(width/4),y+(height/3),shardspeed*rand(),-(10d*rand())/120d,level));
				level.queueActive(new IceSplinter(x+(width/4)*3,y+(height/3),-shardspeed*rand(),-(10d*rand())/120d,level));
				level.queueActive(new IceSplinter(x+(width/4),y+(height/3)*2,shardspeed*rand(),-(6d*rand())/120d,level));
				level.queueActive(new IceSplinter(x+(width/4)*3,y+(height/3)*2,-shardspeed*rand(),-(6d*rand())/120d,level));
			}
		}
		this.physic();
		this.applyforce();
		this.collisonLoop();
	}
	
	private double rand(){
		return (0.9d+(Util.randInt(0, 20)*0.01d));
	}
	
	public void trigger(){
		if(!exploding){
			exploding = true;
			nextAnimationIndex = System.currentTimeMillis()+ANIMATIONSPEED[0];
		}
	}

	@Override
	public void draw(Graphics g) {
		if(!exploding){
			g.drawImage(idle, Util.toPix(x), Util.toPix(y), Util.toPix(width),Util.toPix(height),null);
		}
		else{
			g.drawImage(explosion[animationIndex], Util.toPix(x), Util.toPix(y), Util.toPix(width),Util.toPix(height),null);
		}
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerhit(Player player) {
		trigger();
		if(animationIndex >= 8 && animationIndex <= 9){
			player.hit(50);
		}
	}

	@Override
	public void statichit(StaticObject stat) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activehit(ActiveObject act) {
		// TODO Auto-generated method stub
		
	}

}
