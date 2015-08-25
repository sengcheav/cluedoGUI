package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.*;



import Board.Board;
import Card.Card;
import Piece.IllegalParameterException;
import Player.Player;

public class cluedoFrame extends JFrame {
private cluedoCanvas canvas ; 	
private Board board ; 
private boolean rollActivate = false ; 
private static int rolled = 0 ; 
private JLabel playerLabel ; 
private JLabel dice1 ; 
private JLabel dice2 ; 
private JMenuBar menuBar;
private JMenu menu, submenu;
private JMenuItem quit ; 
private JMenuItem newGame;
/**
 * Setup the Frame
 * start of with created a canvas, adding mouseListener, setup the popup
 * panel to response to user click showing what the object user click on
 * Adding the rightPanel to the Frame 
 * @param title 
 * @param board
 * @param keys
 */
	public cluedoFrame(String title, final Board board, KeyListener... keys){
		super(title); 
		menuSetup(); 
		this.board = board ; 
		this.canvas = new cluedoCanvas(board);
		this.canvas.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
			   int constant = 20 ; 
		       int mouseX = e.getPoint().x;
		       int mouseY = e.getPoint().y ; 
		       int x = (int)Math.ceil(mouseX/constant); 
		       int y = (int)Math.ceil(mouseY/constant); 
		       String label  = board.getSquare()[y][x].getLabel(); 
		       switch(label){
		       case("k") : label = "Kitchen" ;  break;
		       case(" ") : label = "Hallway" ;  break;
		       case("/") : label = "Blank Space" ;  break;
		       case("#") : label = "StairWell" ;  break;
		       case("B") : label = "BallRoom" ;  break;
		       case("c") : label = "Conservatory" ;  break;
		       case("b") : label = "Billiar-room" ;  break;
		       case("l") : label = "Library" ;  break;
		       case("s") : label = "Study" ;  break;
		       case("h") : label = "Hall" ;  break;
		       case("L") : label = "Lounge" ;  break;
		       case("D") : label = "Dinning Room" ;  break;
		       case("d") : label = "Door" ;  break;
		       case("*") : label = "ClueRoom" ;  break;
		       }
		       System.out.println(label);
		       /*
		        * set up the pop up panel to response the user's click
		        */
		       JPanel p = new JPanel(); 
		       JLabel popup = new JLabel() ; 
		       popup.setText(label) ; 
		       p.add(popup);
		       int result = JOptionPane.showConfirmDialog(null, p,"What is it?", JOptionPane.PLAIN_MESSAGE);
		       
		     }
		});
		
		ArrayList<Player> plist = this.board.getPlayerList() ;
		for(Player kl : plist) {//System.out.println("in here");
			kl.setBoard(this.board); //adding the board to the player
			this.canvas.addKeyListener(kl);
			this.canvas.setFocusable(true);
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
		setBounds((screenSize.width - getWidth()) / 3,(screenSize.height - getHeight()) / 4, getWidth(), getHeight());
		pack() ; 
		setResizable(false);								
		setVisible(true);		
		canvas.requestFocus();
	}
	
	/**
	 * Adding the menuBar to the Frame , then add Menu with menuItem Quit and 
	 * NewGame, setting the shortCut for both menuItem 
	 */
	private void menuSetup(){
		menuBar = new JMenuBar() ;
		menu = new JMenu("Menu"); 
		menuBar.add(menu);
		quit = new JMenuItem("Quit"); 
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		quit.getAccessibleContext().setAccessibleDescription("Quit the game");
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}			
		});
		menu.add(quit);
		newGame = new JMenuItem("New Game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newGame.getAccessibleContext().setAccessibleDescription("Making new game");
		newGame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("New Game");
			}			
		});
		
		menu.add(newGame);
		menu.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e){menu.doClick();	}
			@Override
			public void mouseExited(MouseEvent e) {menu.doClick();	}			
		});
		
		menuBar.add(menu);		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Get the total result of the dices
	 * @return
	 */
	public int getRoll(){
		return this.rolled ; 
	}
	
	/**
	 * activate the roll button
	 * @param b
	 */
	public void setRollActivatet(boolean b){
		this.rollActivate = b ; 
	}
	
	/**
	 * Set the layout , adding the label that will indicate who turn is it,
	 * Adding the roll button , accusation button , endTurn button and 
	 * show card button
	 * 
	 * @param right
	 */
	public void rightPanel(JPanel right){
		right.setPreferredSize(new Dimension (130,100));
		right.setLayout(new FlowLayout());
		right.setBackground(Color.GRAY);
		JLabel turnLabel = new JLabel();
		turnLabel.setText("Player Turn :"); 
		right.add(turnLabel);
		playerLabel = new JLabel() ; 
		
		int index = 1 ;
		if(this.board.getCurrentTurn()!=-1){
			index = this.board.getCurrentTurn() +1; 
		}
		System.out.println(index+"currentturn");
		playerLabel.setText(""+index);
		playerLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		right.add(playerLabel); 
		
		
		rollButton(right); 
		accusationButton(right);
		endTurnButton(right);
		showCardButton(right);
		
		
	}
	
	/**
	 * Setup the roll button , adding the 2 label for dices (dice image)
	 * and adding actionListener: when the roll button clicked get the 
	 * total roll result from both dice, and showing the image of result
	 * of the dice according to each dice result. 
	 * @param right
	 */
	public void rollButton(JPanel right){
		JButton roll = new JButton("ROLL");
		roll.setPreferredSize(new Dimension (120,20));
		roll.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) { 
				// TODO Auto-generated method stub
				System.out.println("roll select "); 
				if(rollActivate){
					System.out.println("Roll");
					 
					int roll1 = board.getDice1().roll() ;
					int roll2 = board.getDice2().roll() ;
					rolled = roll1+roll2;
					Image img1, img2;
					try {
						img1 = ImageIO.read( cluedoFrame.class.getResource(roll1+".png"));
						img1 = img1.getScaledInstance(100,100, 1);
						dice1.setIcon(new ImageIcon(img1));
						
						img2 = ImageIO.read( cluedoFrame.class.getResource(roll2+".png"));
						img2 = img2.getScaledInstance(100,100, 1);
						dice2.setIcon(new ImageIcon(img2));
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
				}
				
			}
			
		});
		
		/*Adding the keyListener: Becasue the key that use to move the player
		 * will lose the focus each time any button is pressed 
		 */
		roll.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				Player p = board.getPlayerList().get(board.getCurrentTurn());
				if(p.getEnableKey()){
					int code  =e.getKeyCode() ; 
					System.out.println(code) ; 
					if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {			
						p.setCommand("r");  
					} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
						p.setCommand("l");
					} else if(code == KeyEvent.VK_UP) {
						p.setCommand("u");
					} else if(code == KeyEvent.VK_DOWN) {
						p.setCommand("d");
					}
				}
			}
			
		});
		
		
		
		right.add(roll);
	    dice1 = new JLabel();
		dice2 = new JLabel();		
		Image img;
		try {
			img = ImageIO.read( cluedoFrame.class.getResource("5.png"));
			img = img.getScaledInstance(100,100, 1);
			dice1.setIcon(new ImageIcon(img));
			dice2.setIcon(new ImageIcon(img));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		right.add(dice1);
		right.add(dice2);
		
	}
	

	/**
	 * Setting up the accusation button, let the current player(class) know 
	 * when the accusation button is clicked
	 * @param right
	 */
	public void accusationButton(JPanel right){

		JButton accusation = new JButton("Make Accusation"); 
		accusation.setPreferredSize(new Dimension(120,20));
		accusation.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("select "); 
				Player p = board.getPlayerList().get(board.getCurrentTurn());
				p.accusationClick(true);
			}
		});
		
		/*Adding the keyListener: Becasue the key that use to move the player
		 * will lose the focus each time any button is pressed 
		 */
		accusation.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				Player p = board.getPlayerList().get(board.getCurrentTurn());
				if(p.getEnableKey()){
					int code  =e.getKeyCode() ; 
					System.out.println(code) ; 
					if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {			
						p.setCommand("r");  
					} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
						p.setCommand("l");
					} else if(code == KeyEvent.VK_UP) {
						p.setCommand("u");
					} else if(code == KeyEvent.VK_DOWN) {
						p.setCommand("d");
					}
				}
			}
			
		});
		right.add(accusation);
	}
	
	
	/**
	 * Set up the endTurn Button , end the turn when this button is clicked
	 * @param right
	 */
	public void endTurnButton(JPanel right){

		JButton endTurn = new JButton("End Turn"); 
		endTurn.setPreferredSize(new Dimension(120,20));
		endTurn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("endTrun select "); 
				Player p = board.getPlayerList().get(board.getCurrentTurn());
				p.endTurn() ; 
			}	
		});
		/*Adding the keyListener: Becasue the key that use to move the player
		 * will lose the focus each time any button is pressed 
		 */
		endTurn.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				Player p = board.getPlayerList().get(board.getCurrentTurn());
				if(p.getEnableKey()){
					int code  =e.getKeyCode() ; 
					System.out.println(code) ; 
					if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {			
						p.setCommand("r");  
					} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
						p.setCommand("l");
					} else if(code == KeyEvent.VK_UP) {
						p.setCommand("u");
					} else if(code == KeyEvent.VK_DOWN) {
						p.setCommand("d");
					}
				}
			}
			
		});
		right.add(endTurn);
	}
	
	/**
	 * Setting up the showCard button , set up the pop up panel that is in 
	 * gridLayout to show all the card player got .
	 * @param right
	 */
	public void showCardButton(JPanel right){

		JButton showCard = new JButton("SHOW CARD"); 
		showCard.setPreferredSize(new Dimension(120,20));
		showCard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("SHOWCARD select "); 
				Player p = board.getPlayerList().get(board.getCurrentTurn());
				ArrayList<Card> cards = p.getContainsCard(); 
				p.setEnableKey(false); // prevent player to move when the panel popup
				JPanel cardPanel = new JPanel();
				cardPanel.setLayout(new GridLayout(5,2));
				for ( int i =0 ; i< cards.size() ; i++){
					JLabel label1 = new JLabel();
					Image img1;
					try {System.out.println(cards.get(i).getName());
						img1 = ImageIO.read( cluedoFrame.class.getResource(cards.get(i).getName()+".png"));
						img1 = img1.getScaledInstance(100,120, 1);
						label1.setIcon(new ImageIcon(img1));
						cardPanel.add(label1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				JOptionPane.showMessageDialog(null, cardPanel,"Cards In Hand", JOptionPane.PLAIN_MESSAGE);
				p.setEnableKey(true); // reactive the player key 
			}	
		});
		/*Adding the keyListener: Becasue the key that use to move the player
		 * will lose the focus each time any button is pressed 
		 */
		showCard.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				Player p = board.getPlayerList().get(board.getCurrentTurn());
				if(p.getEnableKey()){
					int code  =e.getKeyCode() ; 
					System.out.println(code) ; 
					if(code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_KP_RIGHT) {			
						p.setCommand("r");  
					} else if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_KP_LEFT) {
						p.setCommand("l");
					} else if(code == KeyEvent.VK_UP) {
						p.setCommand("u");
					} else if(code == KeyEvent.VK_DOWN) {
						p.setCommand("d");
					}
				}
			}
			
		});
		right.add(showCard);
	}
	
	
	public void repaint() {
		super.repaint();		
	}
	

	
	public static void main(String[] args){
		//Panel asking for number of player
		Integer [] numberArray = {1,3,4,5,6};
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
	    		JRadioButton b1 ;
	    		if ( ii == 0){
	    			 b1 = new JRadioButton(chosen.get(ii).getName(), true);
	    		}
	    		else {
	    			 b1 = new JRadioButton(chosen.get(ii).getName()); 
	    		}
	    		b1.setActionCommand(istring);
	    		bGroup.add(b1);
	    		if(chosen.get(ii).getChosen()){
	    			b1.setEnabled(false);
	    		}
	    		charChoosingPanel.add(b1);
 		  
	    	}
 	        String iPlayer =""+ (i+1) ; 
	    	int result1 = JOptionPane.showConfirmDialog(null, charChoosingPanel, 
	    			"Player "+iPlayer+" Choose Your Character", JOptionPane.PLAIN_MESSAGE);
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
		cluedoFrame f = new cluedoFrame ( "Cluedo", board);
		while(!board.getGameFinished()){
			int index = board.whoNext()  ;
			Player p = board.getPlayerList().get(index) ;
			board.setCurrentTurn(index); 
			
			int n = index +1 ;  /// n is just thing that print out 
			System.out.println("\n*************************************************");
			System.out.println("Player " + n + " turn");
			f.playerLabel.setText(""+n);
		//	int rolled = board.getDice().roll() ; 
			p.setActive(true);
			f.setRollActivatet(true); 
			/*
			 * While the player havent press the roll button keep getting the random output
			 * and set it as the animation of the dice label
			 */
			while (f.rolled == 0){
				try {
					int dice1 = 1 + (int)(Math.random()*6); 
					int dice2 = 1 + (int)(Math.random()*6); 
					
					Image img1, img2;
					try {
						img1 = ImageIO.read( cluedoFrame.class.getResource(dice1+".png"));
						img1 = img1.getScaledInstance(100,100, 1);
						f.dice1.setIcon(new ImageIcon(img1));
						img2 = ImageIO.read( cluedoFrame.class.getResource(dice2+".png"));
						img2 = img2.getScaledInstance(100,100, 1);
						f.dice2.setIcon(new ImageIcon(img2));
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//System.out.println(f.rolled); 
					TimeUnit.MILLISECONDS.sleep(150);
					f.rolled = f.getRoll() ; 
					
				} catch (InterruptedException e ) {
					e.printStackTrace();
				} 
				
			}
			f.setRollActivatet(false); 
			System.out.println(f.rolled); 
			//f.rolled = 5 ; 
			p.turn(board.getSquare(), f.rolled, board.getSolutionList() , board.getPlayerList());
			f.getContentPane().revalidate();
			f.getContentPane().repaint(); 
			f.rolled=0 ; 
			//p.turn(board.getSquare(), board.getDice(), board.getSolutionList() , board.getPlayerList());
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
