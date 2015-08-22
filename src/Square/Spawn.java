package Square;

import java.awt.Color;
import java.awt.Graphics;

import Piece.Piece;

public class Spawn extends Square {
private Piece piece; 
	
public Spawn(Piece p , String c ){
		
		super(p ,c ); 
		this.piece = p ; 
		
	}

public Spawn(Piece p , String c , Position po){
	
	super(p ,c, po ); 
	this.piece = p ; 
	
}

public Piece getPiece(){
	return this.piece ; 
}

public void draw(Graphics g, Position xy, int constant) {
	int x = xy.getX(), y = xy.getY(), c = constant ; 
	g.setColor(Color.RED);
	g.fillRect(x*c, y*c, c, c);
	g.setColor(Color.GRAY);
	g.fillRect(x*c+2, y*c+2, c-4, c-4);
	
}
}
