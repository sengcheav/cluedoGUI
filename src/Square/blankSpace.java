package Square;

import java.awt.Color;
import java.awt.Graphics;

public class blankSpace extends Square {
private String type = "blankSpace";
public blankSpace (String s ,String c ){
	super(s , c );
}
public blankSpace (String s ,String c , Position p){
	super(s , c,p );
}

public void draw(Graphics g, Position xy, int constan) {
	int x = xy.getX(), y = xy.getY(), c = constan ; 
	g.setColor(Color.WHITE);
	g.fillRect(x*c, y*c, c, c);
	g.setColor(new Color(141,215,240)); // light blue
	g.fillRect(x*c+1, y*c+1, c-2, c-2);
	
}
}
