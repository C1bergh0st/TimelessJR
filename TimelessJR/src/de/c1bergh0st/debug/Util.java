package de.c1bergh0st.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

import de.c1bergh0st.gamecode.Level;

public class Util {
	public static Color REDTRANSPARENT = new Color(255,0,0,60);
	public static Color GREENTRANSPARENT = new Color(0,255,0,60);
	public static Color BLUETRANSPARENT = new Color(0,0,255,60);
	
	
	public static int toPix(double units){
		return (int)(units*Level.TILESIZE);
	}
	
	public static double toUnits(int pixels){
		return (double)(pixels)/(Level.TILESIZE*1d);
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
	
	public static int randInt(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
