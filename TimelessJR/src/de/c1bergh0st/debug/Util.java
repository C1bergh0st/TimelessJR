package de.c1bergh0st.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Util {
	public static Color REDTRANSPARENT = new Color(255,0,0,120);
	public static Color GREENTRANSPARENT = new Color(0,255,0,120);
	public static Color BLUETRANSPARENT = new Color(0,0,255,120);
	
	
	public static int toPix(double units){
		return (int)(units*96);
	}
	
	public static double toUnits(int pixels){
		return (double)(pixels)/96d;
	}
	
	public static void drawRect(Rectangle2D.Double rect, Graphics g, Color c){
		g.setColor(c);
		if(rect!= null){
			g.fillRect(toPix(rect.x), toPix(rect.y), toPix(rect.width), toPix(rect.height));
		}
	}
	
	public static String posconv(double x, double y){
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return "("+df.format(x)+";"+df.format(y)+")";
	}
}
