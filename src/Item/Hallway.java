package Item;

import java.awt.Color;
import java.awt.Graphics;

import Piece.Room;
import Square.Position;

public class Hallway extends Item{
private static String type = "Hallway";

	public Hallway(String h) {
		super(h, type);
		// TODO Auto-generated constructor stub
	}

	public void draw(Graphics g , Position xy , int constant){
		int x = xy.getX(), y = xy.getY(), c = constant ; 
		g.setColor(Color.WHITE);
		g.fillRect(x*c, y*c, c, c);
		g.setColor(Color.GRAY);
		g.fillRect(x*c+1, y*c+1, c-2, c-2);
			 
	}
	
}
