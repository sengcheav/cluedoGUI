package Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Piece.Characters;
import Piece.IllegalParameterException;
import Piece.Room;
import Piece.Weapon;
import Square.Position;
import Square.Square;
import Board.*;
import Card.Card;
import Item.Door;

public class Player implements KeyListener {
private int index ; 
private ArrayList<Card> containsCard = new ArrayList<Card>(); 
private Square onSquare ;
private Position pos ; 
private ArrayList<Card> announcementList = new ArrayList<Card>()  ; 
private boolean accusationCheck; 
private boolean terminated = false ; 
private boolean winner = false ; 
private Square [][] square ; 
private String command ; 
private boolean enableKey = false ; 
private Board board ;
private String name ;
private boolean active = false ; 	
private String character ;
	public Player(int i , String n ,String character){
		
		this.index = i ; 
		this.name = n; 
		this.character = character ; 
	}
	
	/*
	 * Set the Position of the player
	 */
	public void setPos( Position p ){
		this.pos = p ; 
	}
	
	/*
	 * Get the Position of the player 
	 */
	public Position getPos( ){
		return this.pos; 
	}
	
	/*
	 * Add the card to the arrayList (containCard)
	 */
	public void addContainsCard( Card c ){
		if(c == null ) throw new NullPointerException("Card Can not be null") ;
		this.containsCard.add(c) ; 
	}
	
	/*
	 * Get the ArrayList of containCard
	 */
	public ArrayList<Card> getContainsCard() {
		return this.containsCard ; 
	}
	
	public void setOnSquare(Square sq ){
		this.onSquare =sq ; 
	}
	
	
	public Square getOnSquare(){
		return this.onSquare ; 
	}
	
	public int getIndex(){
		return this.index ; 
	}
	
	
	/*
	 * First this method will check if the x y position to make sure either
	 * x or y position one by one square, return false if the pieces try
	 * to move over the wall , or piece try to move to the blank space or 
	 * the position x or < 0 or >25 , also return false if the piece try to 
	 * move to the clue room . Other wise return true 
	 */
	public boolean canMove( Position from , Position to , Square[][] s ){
		int xFrom = from.getX() ;
		int yFrom = from.getY() ; 
		int xTo = to.getX() ;
		int yTo = to.getY() ;
		
		if( (from.diffXIsOne(to) && from.diffYIsZero(to)) || (from.diffYIsOne(to)  && from.diffXIsZero(to)) ){
			// allow to move either x or y by one square at a time
			if (xTo < 0 || xTo >24 || yTo <0 || yTo>24 ){ // can not to square -1 or 25
					return false ; 
			}else if(s[yFrom][xFrom].isContainHall() && s[yTo][xTo].isContainRoom()){ // can not move over the wall 
				System.err.println("Can not move over the wall");
				return false ;
			}else if (s[yFrom][xFrom].isContainRoom() && s[yTo][xTo].isContainHall()){ // can not move over the wall
				System.err.println("Can not move over the wall");
				return false ;
			}else if (s[yFrom][xFrom].isContainHall() && s[yTo][xTo].isContainStairWell()){
				System.err.println("Can not move from Hall to StairWell over the wall");
				return false ;
			}else if (s[yFrom][xFrom].isContainStairWell() && s[yTo][xTo].isContainHall()){
				System.err.println("Can not move from StairWell to Hall over the wall");
				return false ;
			}else if (s[yTo][xTo].isContainBlankSpace()){ // can not move to the blank space
				System.err.println("Can not move to blank Space");
				return false ; 
			}else if (s[yTo][xTo].isContainClue()){
				System.err.println("Can not move to clue");
				return false ; // can not move to clue room 
			}else if (s[yTo][xTo].getPlayerON() != null){
				System.err.println("Can not move , there a player on there");
				return false ; 
			}
			return true ; 
		}
		System.err.println("Can only move one square y or x");
		return false ; 
				
	}
	
	/*
	 * This method will ask user and will return command as integer
	 */
	public String ask( String message){
		String command = "";
		while (command.equals("")){
		try{
			Scanner s = new Scanner(System.in);
			System.out.println(message);
			command = s.next() ; 
			}catch(InputMismatchException ie){
				System.out.println("Please type in letter"); 
			
			}
		}
		return command ; 
	}
	
	
	/*
	 * This method will ask user and will return command as String
	 */
	public int askInt( String message){
		int command = 0 ;
		while(command == 0){
			try{
				Scanner s = new Scanner(System.in);
				System.out.println(message);
				command = s.nextInt() ; 
			}catch(InputMismatchException ie){
				System.out.println("Please type in number"); 
				
			}
		}
		return command ; 
	}
	
	
	/*
	 * This method will return and calculate the new position according 
	 * to the command given by player.
	 */
	public Position calculatePos( Position p , String m){
	
		
		if( m.equals("l")){
			return new Position ( p.getY()  ,p.getX()-1);
		}else if (m.equals("r")){
			return new Position (p.getY(), p.getX()+1); 
		}else if (m.equals("u")){
			return new Position(p.getY()-1, p.getX());
			
		}else if (m.equals("d")){
			return new Position(p.getY()+1, p.getX()); 
		}else 
		
		return p ;
	}
	
	
	/*
	 * This method will return true if a player is currently in a room
	 */
	public boolean inRoom(Square  s ){
		if( s.getContain() instanceof Room || s.isContainDoor()) {
			return true ; 
		}
		return false ; 
	}
	
	
	/*
	 * This method will return the room that player is currently in if 
	 * player is in a room otherwise return null
	 */
	public Room getRoomPlayerIn( Square s){
		if(inRoom(s)){
			if(s.isContainDoor()){
				Door d = (Door)s.getContain() ; 
				Room r = d.getInRoom() ;
				return r ;
			}
			Room r = (Room) s.getContain() ; 
			return r ; 
		}
		return null ; 
	}
	
	/*
	 * Every player begin the turn by rolling the dice, then the method will 
	 * keep asking the player to move the piece one by one square respond to 
	 * the result of the dice, after get the command from player the method 
	 * will first check if the piece can move to the corresponding square, if 
	 * it can the piece will then need to updating it new location (square ) 
	 * otherwise it will print out telling player to chhose another direction. 
	 * Finally it will check if the piece is in the room or not if it is the 
	 * player will then have to make suggestion 
	 * 
	 */
	public void turn ( Square[][] s ,Dice dice , ArrayList<Card> solution ,ArrayList<Player> plist ){
		this.active = true ; this.board.getCanvas().repaint();
		this.square= s ;
		int rolled = dice.roll() ;
		//Position from = this.getPos() ; 
		int i = this.getIndex() + 1 ; 
		while (rolled >0){ // move correspond to result of the dice
			printBoard(s) ;
			//String m = ask("\nPlayer "+i +": You got "+ rolled + " moves Respond with u for up, d for down, l for left, r for right as move ");
			System.err.println("\nPlayer "+i +": You got "+ rolled + " moves Respond with u for up, d for down, l for left, r for right as move ");
			this.enableKey = true ; 
			String m = command ; //System.out.println("Command "+ command); 
//			while (!m.equals("l") && !m.equals("r") && !m.equals("u") && !m.equals("d")){
//				System.err.println("Please respond with l for left , r for right , u for up , d for down ");
//				m = ask("\nYou got "+ rolled + " moves Respond with u for up, d for down, l for left, r for right as move ");
//			}
			int n =0 ;
			while(m != "u" && m!="l" && m!="r" && m!="d"){
				
				try {
					TimeUnit.MILLISECONDS.sleep(200); // listen to user input every 200 ms so the program is not busy waiting
					//n++;
					//System.out.println(n);
					m = this.command ;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}command = null ; 
			//System.out.println("Before is : "+this.getPos().toString()); 
			
			Position to = calculatePos( this.pos , m) ; 
			if(canMove(this.pos, to ,s)){
				s[this.pos.getY()][this.pos.getX()].removeCharactersOn();
				if(s[to.getY()][to.getX()].setPlayerOn(this)) { // if no other character on the square , set the character on that square 
					this.pos = to.getPos() ;  // and change the position of the player to that position 
					//System.out.println("after is : "+this.getPos().toString()); 
					this.board.getCanvas().repaint();
					
				}
				else {
					System.err.println("Can not move to this square because another player already on this square \n" +
						"Please chhose other option") ;	
				}
			}else {
				rolled++ ; // so the rolled wont change ( + and - value stay the same ) 
				System.err.println("You can not move this way, please chhose another way") ;
			}
			rolled--;	
			
		}
		if(inRoom(s[this.getPos().getY()][this.getPos().getX()]) ){ // check if the player is in room 
			int choice = askInt("accusation type 1 or 2 for suggestion ") ;
			while (choice != 1 && choice !=2){
				System.err.println("Type 1 or 2 "); 
				choice = askInt("accusation type 1 or 2 for suggestion ") ;
			}
			if(choice == 1 ){
				accusation(solution); 
			}else {
				suggestion(s[this.getPos().getY()][this.getPos().getX()], plist);
			}	
		}
		this.enableKey = false ;
		this.active=false ; 
	}
	
	/*
	 * This method will ask the player to choose character , weapon and the room they 
	 * currently in they'd like to do the annocement with then it will call on 
	 * announcement method . After that every player in game except the player that make 
	 * accusation will get turn in clockwise fashion to refute the annouement if they 
	 * got the card .
	 */
	
	public void suggestion ( Square s , ArrayList<Player> plist){
		String cString = "", wString = "" ;
		printCardInHand(); 
		
		int character  = askInt("Which Character you think is the murderer ? \n 1.Miss_Scarlett, \n 2.Colonel_Mustard,"+
				"\n 3.Mrs_White, \n 4.The_Reverend_Green,\n  5.Mrs_Peacock, \n 6.Professor_Plum "); 
		int weapon  = askInt("Which weapon you think is the murderer used ? \n 1.Candlestick, \n 2.Dagger, \n 3.Lead_Pipe," +
				"\n 4.Revolver, \n 5.Rope, \n 6.Spanner "); 
		
		try {
			switch (character){
			case 1 : cString = "Miss_Scarlett" ; break ;
			case 2 : cString = "Colonel_Mustard" ; break ;
			case 3 : cString = "Mrs_White" ; break ;
			case 4 : cString = "The_Reverend_Green" ; break ;
			case 5 : cString = "Mrs_Peacock" ; break ;
			case 6 : cString = "Professor_Plum" ; break ;
			}
			switch (weapon){
			case 1 : wString = "Candlestick"; break ;
			case 2 : wString = "Dagger"; break ;
			case 3 : wString = "Lead_Pipe"; break ;
			case 4 : wString = "Revolver"; break ;
			case 5 : wString = "Rope"; break ;
			case 6 : wString = "Spanner"; break ;
			}
			Room r1 = getRoomPlayerIn(s) ;
			Card r = new Card (r1) ;
			Card c = new Card (new Characters(cString));
			Card w = new Card (new Weapon(wString)); 
			System.out.println("Your choice are :" + r.getName() +" - " +c.getName() +" - "+w.getName());
			if(!this.announcementList.isEmpty()){
				this.announcementList.clear() ; // clear the card from last time announcemnent if any
			}
			this.announcementList = announcement(c, w, r);
			int refuteNum = 0 ; 
			for(Player p : plist){
				if(p.getIndex() != this.index){
					int index = p.getIndex() +1 ; 
					if (p.refute(announcementList)){
						refuteNum++ ; 
						System.out.println("Player " + index +" has refute your announcement"); 
					}
				}
			}
			if (refuteNum == 0){
				System.out.println("No one refute your announcement") ; 
			}
		} catch (IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
	/*
	 * This method will return the arrayList that contain all the card that player
	 * would like to do the announcement with
	 */
	public ArrayList<Card> announcement (Card character , Card weapon , Card room){
		ArrayList<Card> announcement = new ArrayList<Card>() ; 
		announcement.add(character);
		announcement.add(weapon);
		announcement.add(room);
		return announcement;
	}
	
	
	/*
	 * The player will refute again the other players announcement (suggestion)
	 * by compare the card in hand list to suggestion list the method will return 
	 * true if player got any card that match any one card in the suggestion list
	 * otherwise false will be return.
	 */
	public boolean refute( ArrayList<Card> suggestion ){
		for( int i = 0 ; i < this.containsCard.size() ; i++){
			Card contain = this.containsCard.get(i);
			for(int ii = 0; ii<suggestion.size() ; ii++ ){
				if(contain.equals(suggestion.get(ii))){
					return true;
				}
			}
		}
		return false ; 
	}
	
	public void printCardInHand(){
		System.out.println("Cards in Hand : ");
		for(Card c: this.getContainsCard()){
			System.out.println(c.getName() );
		}
	}
	
	
	/*
	 * This method will ask the player which room , character and weapon do they
	 * want to make accusation with then it will call accusationCheck that will 
	 * performed the checking the accusation cards with the solution list and 
	 * result will store in this.accusationCheck as boolean , The player will 
	 * get Terminated if the accusationCheck return true 
	 */
	public void accusation(ArrayList<Card> solution){
		printCardInHand();
		String cString = "", wString = "" , rString = "" ;
		int character  = askInt("Which Character you think is the murderer ? \n 1.Miss_Scarlett, \n 2.Colonel_Mustard,"+
				"\n 3.Mrs_White, \n 4.The_Reverend_Green,\n  5.Mrs_Peacock, \n 6.Professor_Plum "); 
		int weapon  = askInt("Which weapon you think is the murderer used ? \n 1.Candlestick, \n 2.Dagger, \n 3.Lead_Pipe," +
				"\n 4.Revolver, \n 5.Rope, \n 6.Spanner "); 
		int room  = askInt("Which Room you think is the murdere in ? \n 1.kitchen, \n 2.ballroom, \n 3.conservatory, " +
				"\n 4.billiardroom,  \n 5.library, \n 6.study, \n 7.hall, \n 8.lounge \n 9.dinngroom, ");
		
		try {
			switch (character){
			case 1 : cString = "Miss_Scarlett" ; break ;
			case 2 : cString = "Colonel_Mustard" ; break ;
			case 3 : cString = "Mrs_White" ; break ;
			case 4 : cString = "The_Reverend_Green" ; break ;
			case 5 : cString = "Mrs_Peacock" ; break ;
			case 6 : cString = "Professor_Plum" ; break ;
			}
			switch (weapon){
			case 1 : wString = "Candlestick"; break ;
			case 2 : wString = "Dagger"; break ;
			case 3 : wString = "Lead_Pipe"; break ;
			case 4 : wString = "Revolver"; break ;
			case 5 : wString = "Rope"; break ;
			case 6 : wString = "Spanner"; break ;
			}
			switch (room){
			case 1 : rString = "kitchen"; break ;
			case 2 : rString = "ballroom"; break ;
			case 3 : rString = "conservatory"; break ;
			case 4 : rString = "billiardroom"; break ;
			case 5 : rString = "library"; break ;
			case 6 : rString = "study"; break ;
			case 7 : rString = "hall"; break ;
			case 8 : rString = "lounge"; break ;
			case 9 : rString = "diningroom"; break ;
			}
			Card c = new Card ( new Characters(cString));
			Card w = new Card ( new Weapon(wString));
			Card r = new Card ( new Room(rString));
			System.out.println("you Choose :" + c.getName() + " - "+ w.getName() +" - "+ r.getName()); 
			this.accusationCheck = accusationCheck(c,w,r,solution);
			if(!this.accusationCheck){
				System.out.println("You been Terminated from the game :)"); 
				this.setTerminated() ; 
				this.hideDeadBody(square);
			}else {
				this.winner = true ; 
			}
			
		} catch (IllegalParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/*
	 * after Player making accusation (character, weapon ,room) card 
	 * These 3 card then will be checking again the solution Card 
	 * Correct Variable will be increment by 1 every time a card is correct
	 * If player can get 3 card correct the method will return true otherwise false.
	 */
	public boolean accusationCheck(Card character, Card weapon , Card room , ArrayList<Card> solution){
		int correct = 0 ; 
		for(int i =0 ; i<solution.size() ; i++){
			if(character.equals(solution.get(i))){ correct++ ;}
			if(weapon.equals(solution.get(i))){ correct++ ;}
			if(room.equals(solution.get(i))){ correct++ ;}
		}
		if(correct ==3 ) {
			return true ;
		}
		return false ; 
	}
	
	
	public void setTerminated(){
		this.terminated = true ; 
	}
	
	public boolean getTerminated(){
		return this.terminated ; 
	}
	
	public void printBoard(Square [][] square){
		
		for (int i =0 ; i<25 ; i++ ){//
			System.out.println();
			
			for (int ia =0 ; ia<25 ; ia++ ){
				System.out.print("|"+ square[i][ia].getLabel());
			}
		}
	
	}
	
	public boolean isWinner(){
		return this.winner ; 
	}
	
	public void hideDeadBody( Square [][]s){
		s[this.pos.getY()][this.pos.getX()].removeCharactersOn();
	}
	
	public void draw(Graphics g,Position xy, int constant){
		int x = xy.getX(), y = xy.getY(), c = constant ; 
		g.setColor(Color.WHITE);
		g.fillRect(x*c, y*c, c, c);
		if(this.active){
			g.setColor(Color.RED);
		}else {
			g.setColor(Color.BLACK);
		}
		g.fillOval(x*c+1, y*c+1, c-2, c-2);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(enableKey){
			int code  =e.getKeyCode() ; 
			System.out.println(code) ; 
			if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {			
				this.command = "r";   
			} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
				this.command = "l"; 
			} else if(code == KeyEvent.VK_UP) {
				this.command = "u"; 
			} else if(code == KeyEvent.VK_DOWN) {
				this.command = "d";
			}
		}
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void setBoard( Board b){
		this.board = b ; 
	}
	public String getName(){
		return this.name;
	}
	public String getCharacter(){
		return this.character ; 
	}
	
	
}
