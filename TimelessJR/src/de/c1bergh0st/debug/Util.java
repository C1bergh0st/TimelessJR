package de.c1bergh0st.debug;

import java.awt.Color;

public class Util {
	public static Color REDTRANSPARENT = new Color(255,0,0,120);
	
	
	public static int toPix(double units){
		return (int)(units*96);
	}
	
	public static double toUnits(int pixels){
		return (double)(pixels)/96d;
	}
}
