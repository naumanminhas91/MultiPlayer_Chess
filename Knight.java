import javax.swing.ImageIcon;
import java.lang.Math;

public class Knight extends ChessPiece 
{
	public Knight(boolean team) {
		super( Type.KNIGHT, team );	
		
		ImageIcon figure=null;
		try
		{
			if (team== Team.WHITE)
				figure= new ImageIcon("KnightWhite.png");
			else
				figure= new ImageIcon("KnightBlack.png");
		}
		catch(Exception e)
		{
			System.out.println("Error Reading Pawn Figure");
			System.exit(1);
		}
		this.setImage(figure);
		
	}
	
	public boolean validateMove(Location destination)
	{
		ChessBoard chess= ChessBoard.getInstance();
	
		int h= this.getLocation().getX();
		int w= this.getLocation().getY();
		
		int hD=destination.getX();
		int wD=destination.getY();
		
		if( (Math.abs(h-hD)==2 && Math.abs(w-wD)==1) || (Math.abs(h-hD)==1 && Math.abs(w-wD)==2)  )
		{
			if(chess.squares[hD][wD].getState()==State.CAPTURED)
				if(chess.squares[hD][wD].getPiece().getSide()== this.side)
					return false;
		}
		else return false;
		return true;
	}
	
}