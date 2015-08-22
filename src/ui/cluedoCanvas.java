package ui;

import java.awt.*;
import java.util.HashSet;

import Piece.*;
import Square.*;

import Board.Board;
import Item.*;

public class cluedoCanvas extends Canvas {
	
	private Font font ; 
	private int fontSize = 12 ; 
	private static final String[] preferredFonts = {"Courier New","Arial","Times New Roman"};
	private Board board ; 
	private final int canvasWidth = 250 *2;
	private final int canvasHeight = 250 *2; 
	
	public cluedoCanvas( Board board ){
		//this.board = new Board(); 
		this.board = board; 
		HashSet<String> availableName = new HashSet<String>() ; 
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		for (String s : env.getAvailableFontFamilyNames()){
			availableName.add(s);
		}
		for( int i = 0 ; i< preferredFonts.length ; i++ ){
			if(availableName.contains(preferredFonts[i])){
				font= new Font(preferredFonts[i],Font.BOLD , fontSize); 
				break ; 
			}
		}
		setSize ( new Dimension (canvasWidth, canvasHeight));
		this.board.setCanvas(this); 
	}
	public Board getBoard(){
		return this.board ; 
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g); 
		g.setColor(Color.BLACK);
		g.fillRect(0,0,getWidth(),getHeight());
		//draw everything 
		//Color[] colorArray = {Color.BLACK,Color.BLACK, Color.BLACK, Color.BLACK, Color.YELLOW, Color.YELLOW, Color.YELLOW } ; 
		
		Square[][] s = this.board.getSquare();
		for (int y = 0 ; y< 25 ; y++){
			for(int x = 0 ; x < 25; x++ ){
				s[y][x].draw(g,s); 
				
				
			}
		}
	}
	
	
	
	
	
	
	
}
