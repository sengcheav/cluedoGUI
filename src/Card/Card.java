

package Card;
import Piece.Piece;
import Piece.Room;
public class Card {
private Piece piece; 
private String type  ; 
	
	public Card(Piece p){
		this.piece = p ; 
		
	}
	
	/*This method is compare between 2 cards first off it check if the given other card is null
	 * then it will checking the class and then check if 2 obj is equals
	 */
	
	public boolean equals(Card other){
		
		if (this == other){
			return true ;
		}else if(other == null){ 
			return false;
		//}else if (this.getClass() != other.getClass()){
			//return false ;
		}else if (this.getName() == null || other.getName() == null ){
			return false ;
		}else if (!this.getName().equals(other.getName())){
			return false ;
		}
		
	return true ; 	
	}
	
	public String getName(){
		return this.piece.getName() ; 
	} 
	
	
}
