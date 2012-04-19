import javax.swing.ImageIcon;

public class ChessPiece implements ChessPieceInterface{
	int type;
	Location location;
	boolean side;
	boolean state;
	ImageIcon figure;
	
	public ChessPiece(int nam, boolean team )
	{
		figure= null;
		type=nam;
		side=team;
		state=State.FREE;
		location=new Location();
	}
	
	protected void setImage(ImageIcon fig)
	{
		figure=fig;
	}
	
	public int getType()
	{
		return type;
	}
	
	public ImageIcon getImage()
	{
		return figure;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public boolean getSide()
	{
		return side;
	}
	
	public boolean isFree()
	{
		return state;
	}
	
	public void setLocation(Location loc)
	{
		location=loc;
	}
	
	public void captured()
	{
		state= State.CAPTURED;
		location.setLocation(0, 0);
	}
	
	public void freed()
	{
		state= State.FREE;
	}
	
	public boolean validateMove(Location dest)
	{
		switch(type)
		{
		case Type.KING:
			King temp1= (King) this;
			return temp1.validateMove(dest);
		case Type.QUEEN:
			Queen temp2= (Queen) this;
			return temp2.validateMove(dest);
		case Type.KNIGHT:
			Knight temp3= (Knight) this;
			return temp3.validateMove(dest);
		case Type.BISHOP:
			Bishop temp4= (Bishop) this;
			return temp4.validateMove(dest);
		case Type.ROOK:
			Rook temp5= (Rook) this;
			return temp5.validateMove(dest);
		case Type.PAWN:			
			Pawn temp6= (Pawn) this;
			return temp6.validateMove(dest);
		default:
			return false;
		}
	}
	
	public boolean makeMove(Location dest)
	{
		ChessBoard chess= ChessBoard.getInstance();
		
		int h=this.location.getX();
		int w=this.location.getY();
		
		chess.squares[h][w].removePiece();
		h=dest.getX();
		w=dest.getY();
		ChessPiece a=null;
		if (chess.squares[h][w].state==State.CAPTURED)
		{
			a=chess.squares[h][w].getPiece().kill(this);
		}
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
	
	public ChessPiece kill(ChessPiece a)
	{
		ChessBoard chess= ChessBoard.getInstance();
		
		chess.squares[this.getLocation().getX()][this.getLocation().getY()].removePiece();
		this.setLocation(new Location());
		
		if (this==a)
		{
			return chess.promote((Pawn)this);
		}
		this.captured();
		return null;
	}
	
	public static boolean isOnCheck()
	{
		Notice note= Notice.getInstance();
		ChessBoard chess= ChessBoard.getInstance();
		boolean turn= chess.currentMove();
	
		if(turn== Team.WHITE)
		{
			for( int i=0; i<16 ; i++)
			{
				if (chess.blackPiece.get(i).validateMove(chess.whitePiece.get(15).getLocation()))
				{
					note.setText("White King on Check");
					System.out.println("White King on Check");
					return true;
				}
			}
		}
		else
		{
			for( int i=0; i<16 ; i++)
			{
				if (chess.whitePiece.get(i).validateMove(chess.blackPiece.get(15).getLocation()) && chess.whitePiece.get(i).isFree())
				{
					note.setText("Black King on Check");
					System.out.println("Black King on Check");
					return true;
				}
			}
		}
				
		return false;
	}
	
	public ChessPiece dummyMove(Location dest)
	{
		ChessPiece rollBack=null;
		ChessBoard chess= ChessBoard.getInstance();
		
		int h=this.location.getX();
		int w=this.location.getY();
		
		chess.squares[h][w].dummyRemove();
		h=dest.getX();
		w=dest.getY();
		ChessPiece a=null;
		if (chess.squares[h][w].state==State.CAPTURED)
		{
			rollBack=chess.squares[h][w].getPiece();
			a=chess.squares[h][w].getPiece().dummykill(this);
		}
		if (a==null)
		{
			chess.squares[h][w].dummyAdd(this);
		}
		else 
		{
			chess.squares[h][w].dummyAdd(a);
		}
		return rollBack;
	}
		
	public ChessPiece dummykill(ChessPiece a)
	{
		ChessBoard chess= ChessBoard.getInstance();
		
		chess.squares[this.getLocation().getX()][this.getLocation().getY()].dummyRemove();
		this.setLocation(new Location());
		return null;
	}

}
