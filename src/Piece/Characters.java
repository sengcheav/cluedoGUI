package Piece;

import java.util.ArrayList;

import Card.Card;

public class Characters extends Piece{
private String name ; 
//private boolean isMurder = false ; 
//private Room inRoom = null ; 
private static String type = "Characters"; 	
private int indexInList ; 
//sprivate ArrayList<Card> cardInHand = new ArrayList<Card>() ; 
/*
public enum String{
	 Miss_Scarlett,
	 Colonel_Mustard,
	 Mrs_White,
	 The_Reverend_Green,
	 Mrs_Peacock,
	 Professor_Plum;
	
}
*/
public Characters(String n ) throws IllegalParameterException{
 
	super(n,type) ; 
	this.name = n ; 
	switch(n){
	case("Miss_Scarlett"): 
		indexInList = 0 ;
	case("Colonel_Mustard"): 
		indexInList = 1; 
	case("Mrs_White"):
		indexInList =  2;
	case("The_Reverend_Green"):
		indexInList = 3;
	case("Mrs_Peacock"):
		indexInList = 4 ;
	case("Professor_Plum"):
		indexInList = 5 ; 
	
	}
		
}

//public String getCharacter(){
//	return this.name; 
//}

//public boolean isMurder(){
//	return this.isMurder; 
//}
//
//public void setMurder(boolean t){
//	this.isMurder = t ; 
//}

//public Room inRoom(){
//	return this.inRoom; 
//}
//
//public void setInRoom(Room r){
//	this.inRoom = r ; 
//}

public int getIndexInList(){
	return this.indexInList; 
}


}
