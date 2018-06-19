package de.c1bergh0st.levelobjects;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public abstract class StaticObject {
	private double x;
	private double y;
	private BufferedImage img;
	private Rectangle2D.Double hitbox;
	private Point2D.Double point;
	
	public StaticObject(double x, double y, BufferedImage img){
		this.x = x;
		this.y = y;
		this.img = img;
		this.hitbox = new Rectangle2D.Double(x, y, 1, 1);
		this.point = new Point2D.Double(x, y);
	}
	
	public abstract void tick();
	
	public abstract String getType();
	
	public void draw(Graphics g, Point2D.Double cam){
		if(point.distance(cam) < 20){
			g.drawImage(img, (int)(x*96), (int)(y*96), 96, 96, null);
		}
	}
	
	public Rectangle2D.Double getBounds(){
		return hitbox;
		
	}
	
	public Point2D.Double getP(){
		return point;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY() {
		return y;
	}

	protected void setY(double y) {
		this.y = y;
	}

	protected void setX(double x) {
		this.x = x;
	}

	
}
