package Square;

public class Position {
private int x ;
private int y ;

public Position(int y ,int x){
	this.x = x ;
	this.y = y ;
}

public Position getPos(){
	return this;
}
	
public int getX(){
	return this.x;
}

public int getY(){
	return this.y; 
}

public boolean diffXIsOne(  Position other){
	int xFrom = this.x ;
	int yFrom = this.y ;
	int xTo =  other.x ;
	int yTo = other.y ; 
	
	if(  (xFrom- xTo == -1 || xFrom - xTo == 1) ){  
		return true ; 
	}
	//System.out.println("xOne :f") ; 
	return false ; 
}


public boolean diffYIsOne(  Position other){
	int xFrom = this.x ;
	int yFrom = this.y ;
	int xTo =  other.x ;
	int yTo = other.y ; 
	
	if(  (yFrom- yTo == -1 || yFrom - yTo == 1) ){  
		return true ; 
	}//System.out.println("yOne :f") ; 
	return false ; 
}

public boolean diffXIsZero(Position other){
	if( this.x - other.x == 0){
		return true ; 
	}//System.out.println("x0 :f") ; 
	return false ; 
}

public boolean diffYIsZero(Position other){
	if( this.y - other.y == 0){
		return true ; 
	}//System.out.println("y0 :f") ; 
	return false ; 
}

public String toString(){
	return "Y  : " + this.y + " X: " +this.x;
}

}
