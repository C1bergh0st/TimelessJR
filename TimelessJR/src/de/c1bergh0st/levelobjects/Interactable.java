package de.c1bergh0st.levelobjects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.Util;

public abstract class Interactable {
	protected double x;
	protected double y;
	protected double w;
	protected double h;
	private Rectangle2D.Double bounds;
	
	public Interactable(double x, double y, double w, double h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		bounds = new Rectangle2D.Double(x, y, w, h);
	}
	
	public void devDraw(Graphics g){
		g.setColor(Util.REDTRANSPARENT);
		g.fillRect(Util.toPix(x), Util.toPix(y), Util.toPix(w), Util.toPix(h));
	}
	
	public Rectangle2D.Double getBounds(){
		return bounds;
	}
	
	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getW(){
		return w;
	}
	
	public double getH(){
		return h;
	}
	public abstract String getArgs();

	
	public abstract String getType();
	
	public abstract void use();
	
}
