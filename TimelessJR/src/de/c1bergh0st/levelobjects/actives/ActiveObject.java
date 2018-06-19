package de.c1bergh0st.levelobjects.actives;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public abstract class ActiveObject {
	protected double x,y,width,height,dx,dy;
	private int health;
	protected Level level;
	protected String data;
	
	public ActiveObject(double x,double y, double width, double height, double dx, double dy, int health, Level level, String data){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.dx = dx;
		this.dy = dy;
		this.health = health;
		this.level = level;
		this.data = data;
	}
	
	public abstract String getType();
	
	public abstract void tick();
	
	public abstract void draw(Graphics g);
	
	public abstract void kill();
	
	public void drawBounds(Graphics g){
		g.setColor(Color.BLACK);
		g.drawRect(Util.toPix(x), Util.toPix(y), Util.toPix(width), Util.toPix(height));
	}
	
	public Rectangle2D.Double getBounds(){
		return new Rectangle2D.Double(x, y, width, height);
	}
	
	public abstract void playerhit(Player player);
	
	public abstract void statichit(StaticObject stat);
	
	public abstract void activehit(ActiveObject act);
	
	public void damage(int dmg){
		health -= dmg;
		if(health <= 0){
			kill();
		}
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getWidth(){
		return width;
	}

	public double getHeight(){
		return height;
	}

	public double getDx(){
		return dx;
	}

	public double getDy(){
		return dy;
	}

	public int getHealth(){
		return health;
	}
	
	public String getData(){
		return data;
	}
	
}
