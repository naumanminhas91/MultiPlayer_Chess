import javax.swing.ImageIcon;

public class Rook extends ChessPiece 
{
	public Rook(boolean team) {
		super( Type.ROOK, team );
		
		ImageIcon figure=null;
		try
		{
			if (team== Team.WHITE)
				figure= new ImageIcon("RookWhite.png");
			else
				figure= new ImageIcon("RookBlack.png");
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
		
		if (h==hD || w==wD )
		{
			int i,j,m,n;
			if (h==hD)
				m=0;
			else
				m=h>hD? -1 : 1;
			if (w==wD)
				n=0;
			else
				n=w>wD? -1 : 1;
				
			i=h+m;
			j=w+n;
			while(i!=hD || j!=wD)
			{
				if (chess.squares[i][j].getState()==State.CAPTURED )
					return false;
				i=i+m;
				j=j+n;
			}
			if(chess.squares[i][j].getState()==State.CAPTURED)
				if(chess.squares[i][j].getPiece().getSide()== this.side)
					return false;
		}
		else 
			return false;
		
		return true;
	}

}