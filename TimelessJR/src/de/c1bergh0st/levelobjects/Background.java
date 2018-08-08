package de.c1bergh0st.levelobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.ui.Menu;

public class Background {

	public BufferedImage[] img;
	private double offset;
	private double offsety;
	private Color bg;
	
	public Background(String filename, int level) throws IOException{
		img = new BufferedImage[level];
		for(int i = 1; i <= level; i++){
			img[i-1] = ImageIO.read(Menu.class.getResourceAsStream("/res/background/"+filename+""+i+".png"));
		}
		if(img[level-1] != null){
			bg = new Color(img[level-1].getRGB(0, 0));
		}
	}
	
	public void setOffset(double offset){
		this.offset = offset;
	}
	

	public void setHeight(double offsety){
		this.offsety = offsety;
	}
	
	public void draw(Graphics g){
		if(offsety != 0){
			g.setColor(bg);
			g.fillRect(0, 0, 1920, 1080);
		}
		int[] offs = new int[img.length];
		for(int i = 0; i < img.length; i++){
			offs[i] = (int)(((offset*10)*i)%1920);
		}
		for(int i = 0; i < img.length; i++){
			drawParralax(g,i,offs[i]);
			drawParralax(g,i,offs[i]-1920);
			drawParralax(g,i,offs[i]+1920);
		}
	}
	
	
	private void drawParralax(Graphics g, int i,int divider){
		g.drawImage(img[i], divider, (int)(offsety*Util.toPix(1)),1920,1080, null);
	}
}
