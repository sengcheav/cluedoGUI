package Item;

import Piece.Room;

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
}
