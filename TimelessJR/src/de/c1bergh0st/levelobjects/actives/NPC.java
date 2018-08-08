package de.c1bergh0st.levelobjects.actives;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.Level;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticObject;

public abstract class NPC extends ActiveObject{
	protected Rectangle2D.Double downBox;
	protected Rectangle2D.Double topBox;
	protected Rectangle2D.Double leftBox;
	protected Rectangle2D.Double rightBox;
	protected Rectangle2D.Double jumpBox;
	protected boolean onGround;

	public NPC(double x, double y, double width, double height, int health, Level level,
			String data) {
		super(x, y, width, height, 0, 0, health, level, data);
	}


	

	protected void applyDrag() {
		dx = dx * (0.89);
		if(dx < 0.01/120 && dx > 0.01/120){
			dx = 0;
		}
	}


	protected void updateBox() {
		double dh = 0.3f;
		double dw = width-Math.abs(dx)*8-0.1;
		if(dw >0.9){
			dw = 0.9;
		}
		double vh = (height*0.9)-Math.abs(dy)*8;
		downBox = new Rectangle2D.Double(x+(width-dw)/2, y+height-dh, dw, dh);
		topBox = new Rectangle2D.Double(x+(width-dw)/2, y, dw, dh);
		jumpBox = new Rectangle2D.Double(x+(width-dw/1.01)/2, y+height+0.05-dh, dw/1.01, dh);
		rightBox = new Rectangle2D.Double(x+0.8*width,y+((height-vh)/2),0.2*width,vh);
		leftBox = new Rectangle2D.Double(x,y+((height-vh)/2),0.2,vh);
	}
	

	protected void collisonLoop() {
		onGround = false;
		Point2D.Double pos = new Point2D.Double(x, y);
		LinkedList<StaticObject> statics = level.getStatics();
		for(StaticObject temp : statics){
			if(temp.getP().distance(pos) < 10){
				collision(temp.getBounds());
			}
		}
		
	}

	protected void physic() {
		if(dy < (30f/120)){
			dy+= 9.8f/(120*120);
		}
	}
		
	protected void applyforce() {
		x += dx;
		y += dy;
		updateBox();
	}
	
	public void collision(Rectangle2D.Double box) {
		if(downBox.intersects(box) && dy > 0){
			y = box.y -height;
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
			x = box.x-width;
			updateBox();
		}
		if(jumpBox.intersects(box)){
			onGround = true;
		}
	}
	

	protected void drawphysbox(Graphics g) {
		g.setColor(Color.RED);
		if(downBox != null){
			g.drawRect((int)(downBox.x*Util.toPix(1)),(int)(downBox.y*Util.toPix(1)),(int)(downBox.width*Util.toPix(1)),(int)(downBox.height*Util.toPix(1)));
		}
		if(jumpBox != null){
			g.drawRect((int)(jumpBox.x*Util.toPix(1)),(int)(jumpBox.y*Util.toPix(1)),(int)(jumpBox.width*Util.toPix(1)),(int)(jumpBox.height*Util.toPix(1)));
		}
		if(leftBox != null){
			g.drawRect((int)(leftBox.x*Util.toPix(1)),(int)(leftBox.y*Util.toPix(1)),(int)(leftBox.width*Util.toPix(1)),(int)(leftBox.height*Util.toPix(1)));
		}
		if(rightBox != null){
			g.drawRect((int)(rightBox.x*Util.toPix(1)),(int)(rightBox.y*Util.toPix(1)),(int)(rightBox.width*Util.toPix(1)),(int)(rightBox.height*Util.toPix(1)));
		}
		if(topBox != null){
			g.drawRect((int)(topBox.x*Util.toPix(1)),(int)(topBox.y*Util.toPix(1)),(int)(topBox.width*Util.toPix(1)),(int)(topBox.height*Util.toPix(1)));
		}
		g.setColor(Color.BLACK);
	}
}
