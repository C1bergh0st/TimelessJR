package de.c1bergh0st.gamecode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.levelobjects.Decoration;
import de.c1bergh0st.levelobjects.Interactable;
import de.c1bergh0st.levelobjects.LevelLoadInteractable;
import de.c1bergh0st.levelobjects.Player;
import de.c1bergh0st.levelobjects.StaticDecoration;
import de.c1bergh0st.levelobjects.StaticObject;
import de.c1bergh0st.levelobjects.StillObject;
import de.c1bergh0st.levelobjects.actives.ActiveObject;

public class LevelEditor extends Level {
	public boolean up = false;
	public boolean down = false;
	public boolean left = false;
	public boolean right = false;
	public boolean shift = false;
	public EditorWindow editwindow;
	public String currBlock;
	public String currInteractableArgs;
	public String currInteractable;
	
	public LevelEditor(MainGame in_game){
		super(in_game);
		loadLevel("base");
		game.requestFocus();
		editwindow = new EditorWindow("Editor",this);
		currBlock = "DevFloor";
		currInteractable = "";
		currInteractableArgs = "";
		
	}
	

	public void render(Graphics g){
		Point2D.Double campos = new Point2D.Double(player.x,player.y);
		//No offset
		g.setColor(Color.WHITE);
		g.fillRect(0,0,2000,2000);
		background.setOffset(tileoffsetx);
		background.draw(g);
		//From now on we draw with offset
		g.translate((int)(tileoffsetx*TILESIZE), (int)(tileoffsety*TILESIZE));
		drawDevBackdrop(g);
		
//		drawDevBackdrop(g);
		for (int i = 0; i < decos.size(); i++) {
			if(decos.get(i).getPoint().distance(campos) < 15){
				decos.get(i).draw(g);
			}
		}
		
		for(int i = 0; i < statics.size();i++){
			statics.get(i).draw(g,campos);
		}
		
		player.draw(g);
		

		for(int i = 0; i< actives.size(); i++){
			actives.get(i).draw(g);
		}
		
		for(int i = 0; i < interactables.size();i++){
			interactables.get(i).devDraw(g);
		}
		
		g.translate(-(int)(tileoffsetx*TILESIZE), -(int)(tileoffsety*TILESIZE));
		//No offset
		g.setColor(Color.BLACK);
		g.drawString("PlayerX: "+ player.x, 5, 120);
		g.drawString("PlayerDXS: "+ player.dx, 5, 145);
		g.drawString("PlayerY: "+ player.y, 5, 170);
		g.drawString("PlayerDYS: "+ player.dy, 5, 195);
		
	}
	
	public void r(){
		statics = new LinkedList<StaticObject>();
		player = new Player(2,2,"Player.png",this);
	}
	
	
	public void tick(){
//		contrOffset();
		contrPlayer();
		player.tick();
		player.onGround =false;
		for(ActiveObject ob: actives){
			ob.tick();
		}
		collision();
		for(ActiveObject ob: removeAct){
			actives.remove(ob);
		}
	}
	
	private void collision() {
		Point2D.Double pp = new Point2D.Double(player.x, player.y);
		Point2D.Double pc;
		for(StaticObject temp: statics){
			pc = temp.getP();
			if(pp.distance(pc) < 4){
				player.collision(temp.getBounds());
			}
			for(ActiveObject obj : actives){
				if(obj.getBounds().intersects(temp.getBounds())){
					obj.statichit(temp);
				}
			}
		}
		for(ActiveObject obj : actives){
			for(ActiveObject obj2 : actives){
				if(obj.getBounds().intersects(obj2.getBounds()) && obj != obj2){
					obj.activehit(obj2);
				}
			}
			if(obj.getBounds().intersects(player.getBounds())){
				obj.playerhit(player);
			}
		}
		
	}
	
	public void export(String levelname){
		PrintWriter pw;
		int id = 0;
		try {
			pw = new PrintWriter(new File("src/res/levels/"+levelname+".csv"));
		    StringBuilder sb = new StringBuilder();
		    sb.append("id");
		    sb.append(',');
		    sb.append("ObjectType");
		    sb.append(',');
		    sb.append("XDouble");
		    sb.append(',');
		    sb.append("YDouble");
		    sb.append(',');
		    sb.append("WDouble");
		    sb.append(',');
		    sb.append("HDouble");
		    sb.append(',');
		    sb.append("Args");
		    sb.append('\n');
		    //save all statics to the file
		    for(int i = 0; i < statics.size(); i++){
		    	sb.append(i+",");
		    	sb.append("STATIC"+statics.get(i).getType()+",");
		    	sb.append(statics.get(i).getX()+",");
		    	sb.append(statics.get(i).getY()+",");
		    	sb.append(1+",");
		    	sb.append(1+",");
		    	sb.append("Na"+"\n");
		    	id++;
		    }
		    //save all interactables to file
		    for(Interactable tempinteract : interactables){
		    	sb.append(id+",");
		    	sb.append("INTERACT"+tempinteract.getType()+",");
		    	sb.append(tempinteract.getX()+",");
		    	sb.append(tempinteract.getY()+",");
		    	sb.append(tempinteract.getW()+",");
		    	sb.append(tempinteract.getH()+",");
		    	sb.append(tempinteract.getArgs()+"\n");
		    	id++;
		    }
		    //save all decoration to file 
		    for(Decoration tempdeco : decos){
		    	sb.append(id+",");
		    	sb.append("DECO"+tempdeco.getType()+",");
		    	sb.append(tempdeco.getX()+",");
		    	sb.append(tempdeco.getY()+",");
		    	sb.append("Na,Na,Na\n");
		    	id++;
		    }
		    //save all decoration to File
		    for(ActiveObject tempobj : actives){
		    	sb.append(id+",");
		    	sb.append("DECO"+tempobj.getType()+",");
		    	sb.append(tempobj.getX()+",");
		    	sb.append(tempobj.getY()+",");
		    	sb.append(tempobj.getWidth()+",");
		    	sb.append(tempobj.getHeight()+",");
		    	sb.append("h"+tempobj.getHealth()+"-data"+tempobj.getData());
		    	sb.append("\n");
		    	id++;
		    }
		
		    pw.write(sb.toString());
		    pw.close();
		    System.out.println("done!");
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	
	public void contrPlayer(){
		centrePlayer();
	}
	
	public void centrePlayer(){
		tileoffsetx = 9.5-player.x;
		tileoffsety = 0;
		if(tileoffsety < player.y-6){
			tileoffsety = 6-player.y;
		}
		if(tileoffsety > player.y-0.5){
			tileoffsety = 0.5-player.y;
		}
		background.setHeight(tileoffsety);
	}
	
	public void contrOffset(){
		if(shift){
			if(up){
				tileoffsety+=(10f/120);
			}
			if(down){
				tileoffsety-=(10f/120);
			}
			if(left){
				tileoffsetx+=(10f/120);
			}
			if(right){
				tileoffsetx-=(10f/120);
			}
		}
		if(up){
			tileoffsety+=(0.5f/120);
		}
		if(down){
			tileoffsety-=(0.5f/120);
		}
		if(left){
			tileoffsetx+=(0.5f/120);
		}
		if(right){
			tileoffsetx-=(0.5f/120);
		}
	}

	public void drawDevBackdrop(Graphics g){
		g.setColor(Color.BLACK);
		int upperCornerx =(int)(-tileoffsetx) -2;
		int upperCornery = (int)(-tileoffsety)-2;
		for(int x = 0; x < 30; x++){
			for(int y = 0; y < 30; y++){
				drawEditorBlock(g,upperCornerx+x,upperCornery+y);
			}	
		}
		drawEditorBlock(g,upperCornerx,upperCornery);
	}
	
	private void drawEditorBlock(Graphics g, int x, int y){
		g.drawRect(x*96, y*96, 96, 96);
	}

	public void addBlock(int px, int py) {
		double dx = ((double)px / 96d)-tileoffsetx;
		double dy = ((double)py / 96d)-tileoffsety;
		int x = (int)dx;
		int y = (int)dy;
		if(dx < 0){
			x--;
		}
		if(dy < 0){
			y--;
		}
		if(!currBlock.contains("DECO")){
			boolean alreadyExists = false;
			for(int i = 0; i<statics.size();i++){
				if(statics.get(i).getX() == x && statics.get(i).getY() == y){
					alreadyExists = true;
					Debug.send("There is already a Tile at : ("+x+";"+y+")");
				}
			}
			if(!alreadyExists && !currBlock.equals("Nothing")){
				statics.add(new StillObject(x,y,currBlock,imgload));
				Debug.send(currBlock);
			}
			if(currBlock.equals("Nothing")){
				for(int i = 0; i < statics.size();i++){
					if(statics.get(i).getX() == x && statics.get(i).getY() == y){
						statics.remove(i);
						Debug.send("Removed a Tile at : ("+x+";"+y+")");
					}
				}
			}
		}
		else if(currBlock.contains("DECO")){
			boolean alreadyExists = false;
			for(int i = 0; i<decos.size();i++){
				if(decos.get(i).getX() == x && decos.get(i).getY() == y){
					alreadyExists = true;
					Debug.send("There is already a Deco at : ("+x+";"+y+")");
				}
			}
			if(!alreadyExists && !currBlock.contains("Nothing")){
				decos.add(new StaticDecoration(x,y,currBlock.replace("DECO", ""),decoimgload));
				Debug.send(currBlock);
			}
			if(currBlock.contains("Nothing")){
				for(int i = 0; i < decos.size();i++){
					if(decos.get(i).getX() == x && decos.get(i).getY() == y){
						decos.remove(i);
						Debug.send("Removed a Deco at : ("+x+";"+y+")");
					}
				}
			}
		}
	}
	
	public void addInteractable(int mx1, int my1, int mx2, int my2){
		Point2D.Double start = new Point2D.Double((mx1/96d)-tileoffsetx, (my1/96d)-tileoffsety);
		Point2D.Double stop = new Point2D.Double((mx2/96d)-tileoffsetx, (my2/96d)-tileoffsety);
		if(currInteractable.equals("LevelLoad")){
			Rectangle2D.Double testrect = new Rectangle2D.Double(start.x, start.y, stop.x, stop.y);
			boolean validPlacement = true;
			for(int i = 0; i < interactables.size(); i++){
				if(interactables.get(i).getBounds().intersects(testrect)){
					validPlacement = false;
					Debug.sendErr("Could Not Place LevelLoadInteractable");
				}
			}
			if(validPlacement){
				interactables.add(new LevelLoadInteractable(start.x, start.y, stop.x-start.x, stop.y-start.y, currInteractableArgs, this));
				
			}
		}
	}
	

	public Interactable getInteractable(Rectangle2D.Double pbounds) {
		for(int i = 0; i < interactables.size();i++){
			if (interactables.get(i).getBounds().contains(pbounds)){
				return interactables.get(i);
			}
		}
		return null;
	}




	public void mousemoved(int x, int y) {
		int rx = (int)(((double)x / 96d)-tileoffsetx);
		int ry = (int)(((double)y / 96d)-tileoffsety);
		//Debug.send("Mouse at("+rx+";"+ry+")");
	}
}
