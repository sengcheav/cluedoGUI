package Piece;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Item.Item;
import Square.Position;

public class Room extends Piece{
private String name ;
private Weapon containWepon  = null ;
private Character containCharacter = null ;
private static String type = "Room"; 
private ArrayList<Item> contains = new ArrayList<Item>(); 
private int indexInList ; 

/*
	kitchen,
	ballroom,
	conservatiory,
	billiardroom, 
	library,
	study,
	hall,
	lounge,
	dinngroom,
	
*/ 

public Room(String name) throws IllegalParameterException{
	super(name , type); 
	this.name = name ; 
	
}
	


//public void setContainWeapon(Weapon w){
//	this.containWepon =w ; 
//}
//public Weapon getContainWeapon() {
//	return this.containWepon ; 
//}
//
//public void setContainCharacter( Character c) {
//	this.containCharacter = c ;  
//}
//
//public Character getContainCharcter(){
//	return this.containCharacter ; 
//}

public void addContains(Item i){
	this.contains.add(i);
}

public ArrayList getContains(){
	return this.contains; 
}

public void setIndexInList(int i ){
	this.indexInList = i ; 
}

public int getIndexInList(){
	return this.indexInList ; 
}

/*
s("kitchen") && !n.equals("ballroom") && !n.equals("conservatory") && !n.equals("library") && 
	       !n.equals("billiardroom") && !n.equals("study") && !n.equals("hall") &&!n.equals("lounge") && 
	       !n.equals("diningroom")	&& !n.equals("clue")
*/

public void draw(Graphics g, Position xy, int constant) {
	Color roomColor = Color.BLUE ;
	switch (name){
	case "kitchen" :
		roomColor = Color.orange;
		break ; 
	case "ballroom" :
		roomColor = Color.CYAN;
		break ; 
	case "conservatory" :
		roomColor = Color.GREEN;
		break ; 
	case "library" :
		roomColor = Color.magenta;
		break ; 
	case "billiardroom" :
		roomColor = Color.pink;
		break ; 
	case "study" :
		roomColor = Color.DARK_GRAY;
		break ; 
	case "hall" :
		roomColor = Color.LIGHT_GRAY;
		break ; 
	case "lounge" :
		roomColor = new Color(215,141,40);
		break ; 
	case "diningroom" :
		roomColor = new Color(215,141,0);
		break ; 
	case "clue" :
		roomColor = new Color(0,141,40);
		break ; 
	
	}
	int x = xy.getX(), y = xy.getY(), c = constant ; 
	g.setColor(Color.WHITE);
	g.fillRect(x*c, y*c, c, c);
	g.setColor(roomColor);
	g.fillRect(x*c+1, y*c+1, c-2, c-2);
	
	
}

}
