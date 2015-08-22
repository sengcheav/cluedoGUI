package Piece;

public class Weapon extends Piece{
private String name ; 
//private Room inRoom ; 
private int indexInList ; 
private static String type = "Weapon"; 
/*
	 Candlestick,
	 Dagger,
	 Lead_Pipe,
	 Revolver,
	 Rope,
	 Spanner;
*/

public Weapon( String name) throws IllegalParameterException{
	super(name, type );
	this.name = name ; 
	switch (name){
	case "Candlestick" : indexInList = 0;
	case "Dagger" : indexInList = 1;
	case "Lead_Pipe" : indexInList = 2;
	case "Revolver" : indexInList = 3;
	case "Rope" : indexInList = 4;
	case "Spanner" : indexInList = 5;
	
	}
}

public String getWeapon() {
	return this.name;

}

//public Room getInRoom(){
//	return this.inRoom ; 
//}

//public void setInRoom(Room r ){
//	this.inRoom = r ;  
//}





}
