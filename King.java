import javax.swing.ImageIcon;
import java.lang.Math;

public class King extends ChessPiece 
{
	
	public King(boolean team) {
		super( Type.KING, team );

		ImageIcon figure=null;
		try
		{
			if (team== Team.WHITE)
				figure= new ImageIcon("KingWhite.png");
			else
				figure= new ImageIcon("KingBlack.png");
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
		
		int h=this.getLocation().getX();
		int w=this.getLocation().getY();
		int hD=destination.getX();
		int wD=destination.getY();
		
		if ( Math.abs(h-hD)<=1 && Math.abs(w-wD)<=1 )
		{
			if(chess.squares[hD][wD].getState()==State.CAPTURED)
				if(chess.squares[hD][wD].getPiece().getSide()== this.side)
					return false;
		}
		else 
			return false;
		
		return true;
	}
	
}