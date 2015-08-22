package ui;

import Piece.IllegalParameterException;

public class Chosen {
private String name ;
private boolean chose = false ;

public Chosen ( String n  ) throws IllegalParameterException{
	if(!n.equals("Miss_Scarlett") && !n.equals("Colonel_Mustard") && !n.equals("Mrs_White") && 
	   !n.equals("The_Reverend_Green") && !n.equals("Mrs_Peacock") && !n.equals("Professor_Plum")){
		throw new IllegalParameterException("Check the Legal String to fit type Characters : -"+n) ; 	
	}
	this.name = n ; 
 
}

public String getName(){
	return this.name; 
}

public void setChosen(boolean b){
	this.chose = true ; 
}
	
public boolean getChosen(){
	return this.chose ; 
}
}
