package Board;

import java.io.Console;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import ui.cluedoCanvas;

import Card.Card;
import Card.RoomCard;
import Item.Door;
import Item.Hallway;
import Item.Item;
import Item.stairWell;
import Piece.Characters;
import Piece.IllegalParameterException;
import Piece.Piece;
import Piece.Piece.*;
import Piece.Room;
import Piece.Weapon;
import Player.Player;
import Square.Position;
import Square.Spawn;
import Square.Square;
import Square.blankSpace;

public class Board {
	
	private int numOfPlayer ; 
	private Dice dice = new Dice() ; 
	private Square square[][] =  new Square[25][25];
	
	private ArrayList<Characters> characterList = new ArrayList<Characters>(); 
	private ArrayList<Room> roomList = new ArrayList<Room>(); 
	private ArrayList<Weapon> weaponList = new ArrayList<Weapon>(); 
	private static ArrayList<Player> playerList = new ArrayList<Player>() ;
	
	private ArrayList<Card> weaponCard = new ArrayList<Card>(); 
	private ArrayList<Card> charactersCard = new ArrayList<Card>(); 
	private static ArrayList<Card> roomCard = new ArrayList<Card>(); 
	private ArrayList<Card> solution = new ArrayList<Card>() ; 
	private static ArrayList<Card> cardLeft = new ArrayList<Card>(); 
	private static int currentTurn = -1 ;
	private boolean gameFinished = false ; 
	
	private cluedoCanvas canvas ; 
	private ArrayList<String> playerNamesList = new ArrayList<String>();
	private ArrayList<String> playerChosenChar = new ArrayList<String>(); 
	/**
	 * The method will do all the setup 
	 */
	
	public Board(int number, ArrayList<String> nameList , ArrayList<String> charList) {
		
		
		
		
		this.playerNamesList = nameList ; 
		this.playerChosenChar = charList; 
//		this.numOfPlayer = ask("How many Players?") ; 
//		while (this.numOfPlayer <3 || this.numOfPlayer > 6){
//			System.err.println("Must be between 3 - 6") ;
//			this.numOfPlayer = ask("How many Players? ") ; 
//		}
		
		this.numOfPlayer = number; 
		CharacterSetup();
		PlayerSetup();
		RoomSetup() ;
		weaponSetup() ;
		cardSetup() ;
		solutionSetup() ;
		dealCard( cardLeft , playerList);
		squareSetup();
		setupPlayerPosition() ;
		System.out.println("Number of Players"+ this.numOfPlayer );
		
	
	}
	
	public int ask( String message){
		Scanner s = new Scanner(System.in);
		System.out.println(message);
		int number = s.nextInt() ; 
		return number ; 
	}
	
	public void PlayerSetup() {
		for ( int i = 0 ; i < this.numOfPlayer ; i++ ){
			playerList.add(new Player(i, this.playerNamesList.get(i), this.playerChosenChar.get(i))) ; 
		}
	}
	

	/*
	 * This method run through the whole player list and check if the next
	 * player is terminated or not , if not it will return index of the player
	 * that go next otherwise it keep checking until it find the next player
	 * by keep increment the currentTurn index by 1 ; 
	 */
	public int whoNext(){
		
			if(currentTurn ==-1){
				currentTurn = 0 ;
			}else if(this.currentTurn == numOfPlayer -1 ){
				//System.out.println("IS in here");
				currentTurn = 0 ;
			}
			else {
				currentTurn++ ; 
				//System.out.println("IS in here2");
			}
			
			if(playerList.get(currentTurn).getTerminated()){
				//System.out.println("IS in here3");
				return whoNext() ; 
			}
				
		return currentTurn; 
		
	}

	public void CharacterSetup() {
		try {
			characterList.add(new Characters("Miss_Scarlett") ) ;
			characterList.add(new Characters("Colonel_Mustard")) ; 
			characterList.add(new Characters("Mrs_White")  ) ; 
			characterList.add(new Characters("The_Reverend_Green")) ; 
			characterList.add(new Characters("Mrs_Peacock") ) ; 
			characterList.add(new Characters("Professor_Plum") ) ; 
		} catch (IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*	In this method I create rooms such as kitchen , conservatory , hall etc ...
	 * 	And in each room I added item ( door, stairwell ) and also I added the connecting room 
	 *  between the stairWell 
	 *  All the room are added to the roomList
	 */
	public void RoomSetup(){
		try{
		Room kitchen = new Room("kitchen") ; 
		stairWell kitchenStair = new stairWell("kit_stairWell", "stairWell");
		kitchen.addContains(kitchenStair); 
		kitchen.addContains(new Item("door", "door"));
		kitchen.setIndexInList(0);
		
		Room ballroom = new Room("ballroom") ; 
		ballroom.addContains(new Item("door0" , "door")); 
		ballroom.addContains(new Item("door1" , "door")); 
		ballroom.addContains(new Item("door2" , "door")); 
		ballroom.addContains(new Item("door3" , "door")); 
		ballroom.setIndexInList(1);
		
		Room conservatory = new Room("conservatory") ; 
		stairWell conservatoryStair = new stairWell ("con_stairWell", "stairWell");
		conservatory.addContains( conservatoryStair) ; 
		conservatory.addContains(new Item ("door0" , "door"));
		conservatory.setIndexInList(2); 
		
		Room billiardroom = new Room("billiardroom") ; 
		billiardroom.addContains(new Item("door0", "door"));
		billiardroom.addContains(new Item("door1", "door"));
		billiardroom.setIndexInList(3); 
		
		Room library = new Room("library") ; 
		library.addContains(new Item("door0", "door"));
		library.addContains(new Item("door1", "door"));
		library.setIndexInList(4) ; 
		
		Room study = new Room("study") ; 
		stairWell studyStair = new stairWell("stu_stairWell", "stairWell"); 
		study.addContains(studyStair); 
		study.addContains(new Item("door0" , "door"));
		study.setIndexInList(5);
		
		Room hall = new Room("hall") ; 
		hall.addContains(new Item("door0", "door")); 
		hall.addContains(new Item("door1" , "door"));
		hall.setIndexInList(6);
		
		Room lounge = new Room("lounge") ; 
		stairWell loungeStair = new stairWell("lou_stairWell", "stairWell");
		lounge.addContains(loungeStair);
		lounge.addContains(new Item("door0", "door")); 
		lounge.setIndexInList(7);
		
		Room dinningroom = new Room("diningroom") ; 
		dinningroom.addContains(new Item("door0", "door"));
		dinningroom.addContains(new Item("door1", "door"));
		dinningroom.setIndexInList(8);
		
		Room clue = new Room("clue");
		clue.setIndexInList(9);
		
		kitchenStair.connectRoom(conservatory); 
		conservatoryStair.connectRoom(kitchen);
		studyStair.connectRoom(lounge);
		loungeStair.connectRoom(study); 
		
		/*
		 * the order of adding to list is important since I set the 
		 * IndexInList of each room to get easy access to the list
		 * so do not have to iterate through the list 
		 */
		roomList.add(kitchen);//0
		roomList.add(ballroom);//1
		roomList.add(conservatory);//2
		roomList.add(billiardroom);//3
		roomList.add(library);//4
		roomList.add(study);//5 
		roomList.add(hall);//6
		roomList.add(lounge);//7
		roomList.add(dinningroom);//8
		roomList.add(clue);//9

		} catch (IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	public void weaponSetup(){
		try{
		weaponList.add(new Weapon("Candlestick"));
		weaponList.add(new Weapon("Dagger"));
		weaponList.add(new Weapon("Lead_Pipe"));
		weaponList.add(new Weapon("Revolver"));
		weaponList.add(new Weapon("Rope"));
		weaponList.add(new Weapon("Spanner"));
		} catch (IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void cardSetup(){
		for (Characters c: characterList){
			charactersCard.add(new Card(c)); 	
		}
		for(Weapon w: weaponList ){
			weaponCard.add(new Card(w)); 
		}
		for(Room r : roomList){
			if(r.getName() != "clue"){ // do not add clue to roomCard
			roomCard.add(new Card(r));
			}
		}
	}
	
	public void squareSetup(){
		
		Square k = new Square ( roomList.get(0) , "k");
		ArrayList<Item> iList = roomList.get(0).getContains();
		Item i1 = iList.get(0) ; 
		Square s1 = new Square (i1 , "#");
			
		Square B = new Square ( roomList.get(1) , "B");  //Ballroom
		Square c = new Square ( roomList.get(2) , "c");
		ArrayList<Item> iList2 = roomList.get(2).getContains();
		Item i2 = iList2.get(0) ; 
		Square s2 = new Square (i2 , "#");
		
		Square b = new Square ( roomList.get(3) , "b");
		Square l = new Square ( roomList.get(4) , "l"); // library 
		Square s = new Square ( roomList.get(5) , "s");

		System.out.println(s.isContainRoom());
		ArrayList<Item> iList3 = roomList.get(5).getContains();
		Item i3 = iList3.get(0) ; 
		Square s3 = new Square (i3 , "#");
		
		Square h = new Square ( roomList.get(6) , "h");
		Square L = new Square ( roomList.get(7) , "L");// Lounge 
		ArrayList<Item> iList4 = roomList.get(7).getContains(); 
		Item i4 = iList4.get(0) ; 
		Square s4 = new Square (i4 , "#");
		Square D = new Square ( roomList.get(8) , "D");
		Square cl = new Square (roomList.get(9) , "*") ; 
		Square H = new Square ( new Hallway("Hallway"), " ");  // Hallway 
		Square d = new Square ( new Door("d"), "d"); 
//		Square p1 = new Spawn(characterList.get(0) ,"1" ) ;  // Miss_Scarlett
//		Square p2 = new Spawn(characterList.get(1) ,"2" ) ; //Colonel_Mustard
//		Square p3 = new Spawn(characterList.get(2) ,"3" ) ; //Mrs_White
//		Square p4 = new Spawn(characterList.get(3) ,"4" ) ; //The_Reverend_Green
//		Square p5 = new Spawn(characterList.get(4) ,"5" ) ; //Mrs_Peacock
//		Square p6 = new Spawn(characterList.get(5) ,"6" ) ; //Professor_Plum
		Square p1 = new Spawn(characterList.get(0) ,"@" ) ;  // Miss_Scarlett
		Square p2 = new Spawn(characterList.get(1) ,"@" ) ; //Colonel_Mustard
		Square p3 = new Spawn(characterList.get(2) ,"@" ) ; //Mrs_White
		Square p4 = new Spawn(characterList.get(3) ,"@" ) ; //The_Reverend_Green
		Square p5 = new Spawn(characterList.get(4) ,"@" ) ; //Mrs_Peacock
		Square p6 = new Spawn(characterList.get(5) ,"@" ) ; //Professor_Plum
		Square z =  (new blankSpace("b" , "/") ); 
		 
		Square square1[][]  = {
				{z,z,z,z,z,z,z,z,z,z,p1,z,z,z,z,p2,z,z,z,z,z,z,z,z,z} ,
				{z,k,k,k,k,k,s1,z,H,H,H,B,B,B,B,H,H,H,z,c,c,c,c,c,c } ,
				{z,k,k,k,k,k,k,H,H,B,B,B,B,B,B,B,B,H,H,c,c,c,c,c,c} , 
				{z,k,k,k,k,k,k,H,H,B,B,B,B,B,B,B,B,H,H,c,c,c,c,c,c} , 
				{z,k,k,k,k,k,k,H,H,B,B,B,B,B,B,B,B,H,H,d,c,c,c,c,c} , 
				{z,k,k,k,k,k,k,H,H,d,B,B,B,B,B,B,d,H,H,H,c,c,c,s2,z} ,
				{z,k,k,k,k,d,k,H,H,B,B,B,B,B,B,B,B,H,H,H,H,H,H,H,p3} ,
				{z,H,H,H,H,H,H,H,H,B,B,B,B,B,B,B,B,H,H,H,H,H,H,H,z} ,
				{z,z,H,H,H,H,H,H,H,H,H,H,H,H,H,H,H,H,H,b,b,b,b,b,b} ,
				{z,D,D,D,D,D,H,H,H,H,H,H,H,H,H,H,H,H,H,d,b,b,b,b,b} ,
				{z,D,D,D,D,D,D,D,D,H,H,cl,cl,cl,cl,cl,H,H,H,b,b,b,b,b,b} ,
				{z,D,D,D,D,D,D,D,D,H,H,cl,cl,cl,cl,cl,H,H,H,b,b,b,b,b,b} ,
				{z,D,D,D,D,D,D,D,d,H,H,cl,cl,cl,cl,cl,H,H,H,b,b,b,b,d,b} ,
				{z,D,D,D,D,D,D,D,D,H,H,cl,cl,cl,cl,cl,H,H,H,H,H,H,H,H,z} ,
				{z,D,D,D,D,D,D,D,D,H,H,cl,cl,cl,cl,cl,H,H,H,l,l,d,l,l,z} ,
				{z,D,D,D,D,D,D,d,D,H,H,cl,cl,cl,cl,cl,H,H,l,l,l,l,l,l,l} ,
				{z,H,H,H,H,H,H,H,H,H,H,cl,cl,cl,cl,cl,H,H,d,l,l,l,l,l,l} ,
				{z,p4,H,H,H,H,H,H,H,H,H,H,H,H,H,H,H,H,l,l,l,l,l,l,l} ,
				{z,z,H,H,H,H,H,H,H,H,h,h,d,d,h,h,H,H,H,l,l,l,l,l,z} ,
				{z,s3,L,L,L,L,L,d,H,H,h,h,h,h,h,h,H,H,H,H,H,H,H,p5,z} ,
				{z,L,L,L,L,L,L,L,H,H,h,h,h,h,h,d,H,H,H,H,H,H,H,z,z} ,
				{z,L,L,L,L,L,L,L,H,H,h,h,h,h,h,h,H,H,d,s,s,s,s,s,s4} ,
				{z,L,L,L,L,L,L,L,H,H,h,h,h,h,h,h,H,H,s,s,s,s,s,s,s} ,
				{z,L,L,L,L,L,L,L,H,H,h,h,h,h,h,h,H,H,s,s,s,s,s,s,s} ,
				{z,L,L,L,L,L,L,L,p6,H,h,h,h,h,h,h,H,H,s,s,s,s,s,s,s} 
				
		};
		square = square1 ;  
		setupNewSquare();
	}
	
	public void setupNewSquare(){
		for( int i = 0 ; i< 25 ;i++){
			for( int ii = 0 ; ii< 25 ;ii++){
				String c = square[i][ii].getLabel() ; 
				if (c.equals("k")||c.equals("B")||c.equals("c")||c.equals("D")||c.equals("b")||c.equals("s")
				||c.equals("l") || c.equals("L") || c.equals("h") ||c.equals("*")){
					Room r = (Room) square[i][ii].getContain() ; 
					square[i][ii]= new Square(r, c , new Position(i, ii) );	
				}else if (c.equals(" ") ){
					
					square[i][ii]= new Square(new Hallway("Hallway"), c , new Position(i, ii) );	// chang position from ii, i to i ii
				}else if (c.equals("d")){
					Door door = new Door("Door") ; 
					
					square[i][ii]= new Square(door, c , new Position(i, ii) );	
					Room r = square[i][ii].adjacentRoom(square); 
					door.setInRoom(r); 
				}else if (c.equals("1") || c.equals("2") ||  c.equals("3") ||  c.equals("4") ||  c.equals("5") || c.equals("6")||c.equals("@")  ){
					Characters ch = (Characters) square[i][ii].getContain() ;
					square[i][ii]= new Spawn(ch, c , new Position(i,ii));	
					
				}else if (c.equals("/") ){
					
					square[i][ii]= new blankSpace("b", c , new Position(i, ii) );	
					
				}
				else if (c.equals("#")  ){
					square[i][ii] =new Square( new stairWell("stairWell","stairWell"),c,new Position(i, ii) );
				}
				
			}
		}
	}
	
	public ArrayList<Position> positionList(){
		ArrayList<Position> p = new ArrayList<Position>();
		p.add(new Position(0,10));
		p.add(new Position(0,15));
		p.add(new Position(6,24));
		p.add(new Position(17,1));
		p.add(new Position(19,23));
		p.add(new Position(24,10));
		
		return p; 
			
	}
	
	public void setupPlayerPosition(){
		ArrayList<Position> p = positionList();
		for( int i =0 ; i < playerList.size() ; i++){
			playerList.get(i).setPos(p.get(i));
			this.square[p.get(i).getY()][p.get(i).getX()].setPlayerOn(playerList.get(i));
		}
	}
	
	/*
	 * In this method It will generate random number and by the number it generated 
	 * it will go get the correspond element in Character, room , weapon 
	 * by index and add the card to the solution list and the card that left will 
	 * add to the cardLeft list 
	 */
	public void solutionSetup(){
		cardLeft.clear() ; // clear the cardLeft list before using 
		//randomNum = minimum + (int)(Math.random()*maximum); 
		int randomNum = (int)(Math.random()*charactersCard.size()-1);
		solution.add(charactersCard.get(randomNum)); 
		for (int i=0 ; i<charactersCard.size() ;i++){
			if(i != randomNum ){
				cardLeft.add(charactersCard.get(i)); 
			}
		}
		
		randomNum = (int)(Math.random()*roomCard.size()-1); // dnt need -2 because clue didnt add to the card 
		solution.add(roomCard.get(randomNum)); 
		for (int i=0 ; i<roomCard.size() ;i++){
			if(i != randomNum ){
				cardLeft.add(roomCard.get(i)); 
			}
		}	
		
		randomNum = (int)(Math.random()*weaponCard.size()-1);
		solution.add(weaponCard.get(randomNum)); 
		for (int i=0 ; i<weaponCard.size() ;i++){
			if(i != randomNum ){
				cardLeft.add(weaponCard.get(i)); 
			}
		}	
	}
	
	
	/*
	 * Deal the card left from solution to the player evenly 
	 */
	public void dealCard( ArrayList<Card> cardleft , ArrayList<Player> playerlist){
		for (int i = 0 ; i < playerlist.size() ; i++){
			int cardIndex = i ; 
			while(cardIndex < cardleft.size()){
				 playerlist.get(i).addContainsCard(cardleft.get(cardIndex)); 
				// System.out.println(i + " :"+ cardIndex ); 
				 cardIndex += playerlist.size() ;	 
			}
		}
	}
	
	/*
	 * Get the status if the game has finished 
	 * true if it is otherwise false will be return
	 */
	public boolean getGameFinished(){
		return this.gameFinished ; 
	}
	
	public void printBoard(){
		System.out.println("_____________________________________________________");
		for (int i =0 ; i<25 ; i++ ){//
			System.out.println();
			
			for (int ia =0 ; ia<25 ; ia++ ){
				System.out.print("|"+ square[i][ia].getLabel());
			}
		}
		System.out.println("_____________________________________________________");
	}
	
	public boolean allTerminated(){
		int terminated = 0 ; 
		
		for(Player p : playerList){
			if(p.getTerminated()){
				terminated++ ; 
			}
		}
		if (terminated != this.numOfPlayer){
			return false;
		}
		return true ;
	}
	
	public Square[][] getSquare(){
		return this.square ; 
	}
	
	public ArrayList<Player> getPlayerList(){
		return this.playerList ;
	}
	
	public void setCanvas(cluedoCanvas c ){
		this.canvas = c ; 
	}
	
	public cluedoCanvas getCanvas(){
		return this.canvas; 
	}
	
	public ArrayList<Card> getSolutionList(){
		return this.solution ; 
	}
	public Dice getDice(){
		return this.dice; 
	}
	public void setGameFinished(boolean finished){
		this.gameFinished = finished ; 
	}
	public int getNumOfPlayer(){
		return this.numOfPlayer;
	}
		public static void main (String args[]){
			
			ArrayList<String> e = new ArrayList<String>();
			ArrayList<String> e2 = new ArrayList<String>();
		Board b = new Board(3, e, e2);
//		for(Card c:b.solution){
//			System.out.println("solution: "+c.getName()); 
//		}
		
		System.out.println("\tWelcome to the Cluedo game !!!");
		while(!b.gameFinished){
			int index = b.whoNext()  ;
			Player p = playerList.get(index) ;
			int n = index +1 ; 
			System.out.println("\n*************************************************");
			System.out.println("Player " + n + " turn");
			p.turn(b.square, b.dice, b.solution , b.playerList);
			System.out.println("\n*************************************************");
			if(p.isWinner()){
				System.out.println("\nGame Over!!! \n Player "+ n +" win the game!!!");
			    b.gameFinished = true ; 
			    break; 
			}else if (b.allTerminated()){
				System.out.println("Game Over!!! \n No one WON the game \n All got terminated!!! ");
				b.gameFinished = true ; 
				break; 
			}	
		}
		
		
		
	}
}
