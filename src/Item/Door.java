package Item;

import java.awt.Color;
import java.awt.Graphics;

import Piece.Room;
import Square.Position;

public class Door extends Item {
private static String type = "Door"; 
private Room inRoom; 
	
	public Door(String d){
		super(d, type) ;	
	}
	
	public void setInRoom(Room r){
		this.inRoom =r ;
	}
	
	public Room getInRoom(){
		return this.inRoom; 
	}
	
	public void draw(Graphics g, Position xy, int constant) {
		int x = xy.getX(), y = xy.getY(), c = constant ; 
		g.setColor(Color.WHITE);
		g.fillRect(x*c, y*c, c, c);
		g.setColor(Color.BLACK);
		g.fillRect(x*c+1, y*c+1, c-2, c-2);
		
	}
}
