package de.c1bergh0st.levelobjects;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import de.c1bergh0st.debug.Util;

public abstract class Decoration {
	public double x,y;
	public BufferedImage img;
	
	public Decoration(double x, double y, BufferedImage img){
		this.x = x;
		this.y = y;
		this.img = img;
	}
	
	public abstract void tick();
	
	public abstract String getType();
	
	public void draw(Graphics g){
		g.drawImage(img, Util.toPix(x), Util.toPix(y), null);
	}
	
	public Point2D.Double getPoint(){
		return new Point2D.Double(x, y);
	}
	
	public Double getX(){
		return x;
	}
	
	public Double getY(){
		return y;
	}
	
	
}
