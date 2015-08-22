  package Square;

import java.awt.Graphics;

import Item.Door;
import Item.Hallway;
import Item.stairWell;
import Piece.Characters;
import Piece.Piece;
import Piece.Room;
import Player.Player;

public class Square<T> {
private T contain ; 
private Position position ;
private String label ; // label when it print on board
private final String cOriginal ; 
private Player on ; 


public Square(T i, String c ){
	this.contain = i ;
	this.label =c ; 
	cOriginal =  c ; 
}

public Square(T i, String c ,Position p ){
	this.contain = i ;
	this.label =c ; 
	cOriginal =  c ; 
	this.position = p ; 
}

public void setContain(T c){
	this.contain = c ; 
}
	
public T getContain(){
	return this.contain ; 
}

public void setPosition ( int x , int y ){
	this.position = new Position(y,x); 
}

public boolean setPlayerOn( Player player){
	if(on == null){
		this.on = player ; 
		int index = player.getIndex() +1 ; 
		this.label = ""+index ; 
		return true ; 
	}
	return false ;
}

public Player getPlayerON(){
	return this.on ; 
}

public void removeCharactersOn(){
	this.on = null ;
	this.label = this.cOriginal;
}

/*
 * Return the symbol of the square eg: study represent by s
 */
public String getLabel(){
	return this.label ; 
}

/*
 * Return true if the square contain hall
 */
public boolean isContainHall(){
	if (this.cOriginal.equals(" ")){
		return true; 
	}
	return false ;
}

/*
 * Return true if the square contain door
 */
public boolean isContainDoor(){
	if (this.cOriginal.equals("d")){
		return true; 
	}
	return false ;
}

/*
 * Return true if the square contain blank space
 */
public boolean isContainBlankSpace(){
 
	if (this.cOriginal.equals("/")){
		return true; 
	}
	return false ;
}

/*
 * Return true if the square contain room
 */
public boolean isContainRoom(){
	if (this.cOriginal.equals("k")||this.cOriginal.equals("B")||this.cOriginal.equals("c")||this.cOriginal.equals("D")||this.cOriginal.equals("s")
		||this.cOriginal.equals("b")||this.cOriginal.equals("l") || this.cOriginal.equals("L") || this.cOriginal.equals("h")){
		return true; 
	}
	return false ;
}

/*
 * Return true if the square contain the spawn
 */
public boolean isContainSpawn(){
	if (this.cOriginal.equals("1") || this.cOriginal.equals("2") || this.cOriginal.equals("3") || this.cOriginal.equals("4")
			|| this.cOriginal.equals("5") ||this.cOriginal.equals("6")){
		return true; 
	}
	return false ;
}

/*
 * Return true if the square contain the clue room
 */
public boolean isContainClue(){
	if(this.cOriginal.equals("*")){
		return true; 
	}		
	return false ;
}


/*
 * Return true if the square contain the stairWell 
 */
public boolean isContainStairWell(){
	if(this.cOriginal.equals("#")){
		return true; 
	}		
	return false ;
}

/*
 * Check if the adjacent square is the room ( use for door and stairwell only )
 */
public Room adjacentRoom(Square [][]s){
	
	int x = this.position.getX() ;
	int y = this.position.getY();
	System.out.println(y +"----"+x);
	if(s[y-1][x].isContainRoom()){System.out.println("up");
		Room r =(Room) s[y-1][x].getContain() ; 
		return r ; 
	}else if(s[y+1][x].isContainRoom()){System.out.println("down");
		Room r =(Room) s[y+1][x].getContain() ; 
		return r ; 
	}else if(s[y][x+1].isContainRoom()){System.out.println("right");
		Room r =(Room) s[y][x+1].getContain() ; 
		return r ; 
	}else if(s[y][x-1].isContainRoom()){System.out.println("left");
		Room r =(Room) s[y][x-1].getContain() ; 
		return r ; 
	}
	return null ; 
}

public void draw(Graphics g ,Square[][] s){
	int x = this.position.getX(); 
	int y = this.position.getY(); 
	String type = this.getLabel() ; 
	
	switch (type){
	case " " : 
		Hallway hw = (Hallway)this.getContain(); 
		hw.draw(g, new Position(y,x), 20);
		break;
	case "/" :
		blankSpace bsp = (blankSpace)this; 
		bsp.draw(g, new Position(y,x), 20);
		break;
	case "1" :
		Player p1 = this.on;  
		p1.draw(g, new Position(y,x), 20);
		break;	
	case "2" :
		Player p2 = this.on;  
		p2.draw(g, new Position(y,x), 20);
		break;	
	case "3" :
		Player p3 = this.on;  
		p3.draw(g, new Position(y,x), 20);
		break;	
	case "4" :
		Player p4 = this.on;  
		p4.draw(g, new Position(y,x), 20);
		break;	
	case "5" :
		Player p5 = this.on;  
		p5.draw(g, new Position(y,x), 20);
		break;	
	case "6" :
		Player p6 = this.on;  
		p6.draw(g, new Position(y,x), 20);
		break;	
	case "@" :
		Spawn sp = (Spawn)this; 
		sp.draw(g, new Position(y,x), 20);
		break;	
	case "#" :
		stairWell st= (stairWell)this.getContain(); 
		st.draw(g, new Position(y,x), 20);
		break;
	case "d" :
		Room adjacentRoom = this.adjacentRoom(s);
		adjacentRoom.draw(g, new Position(y,x), 20);
		
		
		break;
	default :
		Room r = (Room)this.getContain(); 
		r.draw(g, new Position(y,x), 20);
		break;
		
	}
}


}
