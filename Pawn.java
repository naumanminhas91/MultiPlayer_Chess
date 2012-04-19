import javax.swing.ImageIcon;

public class Pawn extends ChessPiece
{
	public Pawn(boolean team) {
		super( Type.PAWN, team );
		
		ImageIcon figure=null;
		try
		{
			if (team== Team.WHITE)
				figure= new ImageIcon("PawnWhite.png");
			else
				figure= new ImageIcon("PawnBlack.png");
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
		
		int h=this.location.getX();
		int w=this.location.getY();
		
		int hD=destination.getX();
		int wD=destination.getY();
		
		int i,j;
		if (this.side==Team.WHITE)
		{
			i=PawnsLoc.incrementWhite;
			j=PawnsLoc.row1White;
		}
		else
		{
			i=PawnsLoc.incrementBlack;
			j=PawnsLoc.row1Black;
		}	
		
		if ( hD == h+i)
		{
			if(wD==w && (chess.squares[hD][wD].getState()==State.FREE) )
				return true;
			
			else if( (wD==w-1 || wD==w+1) && (chess.squares[hD][wD].getState()==State.CAPTURED) )
			{
				if(chess.squares[hD][wD].getPiece().getSide()== this.side)
					return false;
				else 
					return true;
			}
			else
				return false;
		}
		else if( h==j && hD==h+2*i)
		{
			if (wD==w && (chess.squares[hD][wD].getState()==State.FREE))
			{
				if (chess.squares[h+i][w].getState() == State.FREE )
					return true;
				else
					return false;
			}
			else
				return false;
		}
		else 
			return false;
	}
	
	public boolean makeMove(Location dest)
	{
		ChessBoard chess= ChessBoard.getInstance();
		boolean promote=false;
		
		int h=this.location.getX();
		int w=this.location.getY();
		
		if ((h==PawnsLoc.row1Black && this.getSide()==Team.WHITE) || (h==PawnsLoc.row1White && this.getSide()==Team.BLACK) )
			promote=true;
		chess.squares[h][w].removePiece();
		h=dest.getX();
		w=dest.getY();
		ChessPiece a=null;
		if (chess.squares[h][w].state==State.CAPTURED)
		{
			a=chess.squares[h][w].getPiece().kill(this);
		}
		if(promote)
			a=this.kill(this);
		if (a==null)
		{
			chess.squares[h][w].addPiece(this);
		}
		else 
		{
			chess.squares[h][w].addPiece(a);
		}
		
		return true;
	}
	

}