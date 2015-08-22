package Item;

import java.awt.Color;
import java.awt.Graphics;

import Piece.Room;
import Square.Position;

public class stairWell extends Item {
private static String type = "stairWell"; 
private Room inRoom ; 
private Room connectRoom ; 

	public stairWell(String i, String t) {
		super(i, type);
		// TODO Auto-generated constructor stub
	}


	
	public void setInRoom(Room r ){
		this.inRoom =r ; 
	}
	
	public Room getInRoom(){
		return this.inRoom ; 
	}
	
	public String getInRoomName(){
		return this.inRoom.getName() ; 
	}
	
	public void connectRoom(Room r ){
		this.connectRoom =r ; 
	}
	
	public Room getConnectRoom(){
		return this.connectRoom;
	}



	public void draw(Graphics g, Position xy, int constant) {
		int x = xy.getX(), y = xy.getY(), c = constant ; 
		g.setColor(Color.WHITE);
		g.fillRect(x*c, y*c, c, c);
		g.setColor(Color.YELLOW);
		g.fillRect(x*c+1, y*c+1, c-2, c-2);
		
	}

}
