package Piece;



/**
 * Character Room Weapon 
 * This is a class that let other class such as 
 * Character Room and Weapon to inherite from 
 * @author cheavleat
 *
 */
public class Piece {
private String name ; 
private String type ;


/*
 * This is constructor for Piece which is a super class for Characters , Weapon , Room
 * This will check the String name of the given parameters according to the type given 
 * it only allow the name that only exist in Cluedo game (Characters name , Room name 
 * or Weapon name) otherwise it will throw an IllegalParameterException to indicate the 
 * error
 */
public Piece ( String n , String t ) throws IllegalParameterException{
	if( t.equals("Room")){
		if(!n.equals("kitchen") && !n.equals("ballroom") && !n.equals("conservatory") && !n.equals("library") && 
	       !n.equals("billiardroom") && !n.equals("study") && !n.equals("hall") &&!n.equals("lounge") && 
	       !n.equals("diningroom")	&& !n.equals("clue")){
			throw new IllegalParameterException("Check the Legal String to fit type Room : -"+ n) ; 
		}
	}else if (t.equals("Weapon")){
		if(!n.equals("Candlestick") && !n.equals("Dagger") && !n.equals("Lead_Pipe") && !n.equals("Revolver") && 
		   !n.equals("Rope") && !n.equals("Spanner")){
					throw new IllegalParameterException("Check the Legal String to fit type Weapon : -"+ n) ; 
				}
	}else if (t.equals("Characters")){
		if(!n.equals("Miss_Scarlett") && !n.equals("Colonel_Mustard") && !n.equals("Mrs_White") && 
		   !n.equals("The_Reverend_Green") && !n.equals("Mrs_Peacock") && !n.equals("Professor_Plum")){
					throw new IllegalParameterException("Check the Legal String to fit type Characters : -"+n) ; 
				}
	}else {
		throw new IllegalParameterException("Check the name String and type String ") ;
	}
	this.name = n ; 
	this.type = t ; 
}

public String getName() {
	return this.name ; 
}

public void setName(String name){
	this.name = name ; 
}

public String getType(){
	return this.type; 
}

public String toString(){
	return ""+this.name ; 
}

}
