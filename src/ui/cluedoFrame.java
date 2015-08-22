package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

import Board.Board;
import Piece.IllegalParameterException;
import Player.Player;

public class cluedoFrame extends JFrame {
private cluedoCanvas canvas ; 	
private Board board ; 
	
/**
 * 
 * @param title
 * @param board
 * @param keys
 */
	public cluedoFrame(String title, Board board, KeyListener... keys){
		super(title); 
		
		
		
		this.board = board ; 
		this.canvas = new cluedoCanvas(board);
		ArrayList<Player> plist = this.board.getPlayerList() ;
		
		for(Player kl : plist) {System.out.println("in here");
			kl.setBoard(this.board);
			this.canvas.addKeyListener(kl);
		}	
		setLayout(new BorderLayout());
		
		
	      
		add(canvas, BorderLayout.CENTER);	
		JPanel rightPanel = new JPanel();
		add(rightPanel, BorderLayout.EAST);
		rightPanel(rightPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		// Center window in screen
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		setBounds((screenSize.width - getWidth()) / 3,
				 (screenSize.height - getHeight()) / 4, getWidth(), getHeight());
		pack() ; 
		
		setResizable(false);						
		
		// Display window
		setVisible(true);		
		canvas.requestFocus();
	}
	
	public void rightPanel(JPanel right){
		right.setBackground(Color.BLACK);
		JButton b = new JButton("DDD");
		JLabel label1 = new JLabel();
		label1.setSize(300,300);
		right.add(label1);
		right.add(b);
	}
	
	public void repaint() {
		canvas.repaint();
		
	}
	
	public static void main(String[] args){
		
	
		
		//Panel asking for number of player
		Integer [] numberArray = {3,4,5,6};
		int number = (int) JOptionPane.showInputDialog(null, "How many Players ?",
					"Number of Player", JOptionPane.QUESTION_MESSAGE, null, numberArray, numberArray [0]) ; 
		  
		/*
		 * Panel for user to enter the name , Minimum of 3 players and Maximum of 6
		 * The Player name will default set to p1 , p2 ,p3, p4, p5, p6 if player 
		 * does not change. 
		 * 
		 */
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS)); 
		JTextField p1 = new JTextField(10);
		JTextField p2 = new JTextField(10);
		JTextField p3 = new JTextField(10);
		JTextField p4 = new JTextField(10);
		JTextField p5 = new JTextField(10);
		JTextField p6 = new JTextField(10);
		p1.setText("p1");
		p2.setText("p2");
		p3.setText("p3");
		p4.setText("p4");
		p5.setText("p5");
		p6.setText("p6");
	
	    myPanel.add(new JLabel("--player 1--"));
	    myPanel.add(p1);
	    myPanel.add(new JLabel("--player 2--"));
	    myPanel.add(p2);
	    myPanel.add(new JLabel("--player 3--"));
	    myPanel.add(p3);
	    if(number >=4){
	    	myPanel.add(new JLabel("--player 4--"));
	    	myPanel.add(p4);
	    }
	    if(number >=5){
	    	myPanel.add(new JLabel("--player 5--"));
	    	myPanel.add(p5);
	    }
	    if(number ==6){
	    	myPanel.add(new JLabel("--player 6--"));
	    	myPanel.add(p6);
	    }
	   
	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Enter Player's name", JOptionPane.OK_CANCEL_OPTION);
		ArrayList<String> playerNamesList = new ArrayList<String>(); 
	    if (result == JOptionPane.OK_OPTION) {
	       playerNamesList.add(p1.getText());
	       playerNamesList.add(p2.getText());
	       playerNamesList.add(p3.getText());
	       if(number >=4 ){playerNamesList.add(p4.getText());}
	       if(number >=5 ){playerNamesList.add(p5.getText());}
	       if(number >=6 ){playerNamesList.add(p6.getText());}
	    }
	   
	    /*
	     * 
	     */
	    String[] characters = {"Miss_Scarlett","Colonel_Mustard","Mrs_White","The_Reverend_Green","Mrs_Peacock","Professor_Plum" };
	    ArrayList<String> playerChosenChar = new ArrayList<String>(); 
	    ArrayList<Chosen> chosen = new ArrayList<Chosen>();
	    for ( int i = 0; i < 6 ; i++){
	    	try {
				chosen.add(new Chosen(characters[i]));
			} catch (IllegalParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
	    
 	
	    /*
	     * Loop for number(according to how many player) of time, asking player one by one 
	     * to chhose the character they wanna play as , the chosen character by previous 
	     * player will be gray out. Finally the name of each character will add to playerChosenChar
	     * list.
	     */
	    for(int i = 0 ; i<number; i++){
	    	JPanel charChoosingPanel = new JPanel(); 
	    	charChoosingPanel.setLayout(new BoxLayout(charChoosingPanel, BoxLayout.PAGE_AXIS));
	    	ButtonGroup bGroup = new ButtonGroup();
 	   
	    	for(int ii =0  ; ii < chosen.size() ;ii++){
	    		String istring = ""+ii;
	    		JRadioButton b1 = new JRadioButton(chosen.get(ii).getName()); 
	    		b1.setActionCommand(istring);
	    		bGroup.add(b1);
	    		if(chosen.get(ii).getChosen()){
	    			b1.setEnabled(false);
	    		}
	    		charChoosingPanel.add(b1);
 		  
	    	}
 	        String iPlayer =""+ (i+1) ; 
	    	int result1 = JOptionPane.showConfirmDialog(null, charChoosingPanel, 
	    			"Player "+iPlayer+" Choose Your Character", JOptionPane.OK_CANCEL_OPTION);
	    	int chosenNumber = Integer.parseInt(bGroup.getSelection().getActionCommand());
	    	playerChosenChar.add(chosen.get(chosenNumber).getName()); // add the correspon chosen character to playerChosenChar list
	    	chosen.get(chosenNumber).setChosen(true);
	    	System.out.println("Player "+iPlayer +" : "+playerNamesList.get(i)+" "+bGroup.getSelection().getActionCommand()+" char: "+ chosen.get(chosenNumber).getName());
	    }
	  
	    /*
	     * create the board with number of player , player name list , and their chosen character
	     * then create the frame , then player will take turn to roll the dice 
	     */
		System.out.println(number+ "number of player***");
		Board board = new Board (number, playerNamesList , playerChosenChar) ; 
		cluedoFrame f = new cluedoFrame ( "title", board);
		while(!board.getGameFinished()){
			int index = board.whoNext()  ;
			Player p = board.getPlayerList().get(index) ;
			int n = index +1 ; 
			System.out.println("\n*************************************************");
			System.out.println("Player " + n + " turn");
			p.turn(board.getSquare(), board.getDice(), board.getSolutionList() , board.getPlayerList());
			System.out.println("\n*************************************************");
			if(p.isWinner()){
				System.out.println("\nGame Over!!! \n Player "+ n +" win the game!!!");
			    board.setGameFinished(true); //  = true ; 
			    break; 
			}else if (board.allTerminated()){
				System.out.println("Game Over!!! \n No one WON the game \n All got terminated!!! ");
				board.setGameFinished(true);
				break; 
			}	
		}
	}
}
