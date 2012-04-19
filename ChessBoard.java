import javax.swing.JPanel;

import java.awt.GridLayout;
import java.util.Vector;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;


public class ChessBoard implements RemoteBoard{

	public static RemoteBoard clientStub=null;

	static ChessBoard chess=null;
	
	static boolean team=Team.WHITE;
	boolean singlePC;

	JPanel chessBoard;
	public Square squares[][]= new Square[8][8];

	Square selected;
	boolean currMove;
	
	Vector<ChessPiece> whitePiece;
	Vector<ChessPiece> blackPiece;
	
	private ChessBoard(boolean side)
	{
		ChessBoard.team=side;
		selected=null;
		currMove= Team.WHITE;
		
		chessBoard= new JPanel();
		chessBoard.setLayout(new GridLayout(8,8));
		
		for (int i=0; i<8; i++)
		{
			for (int j=0; j<8; j++)
			{
				squares[i][j]= new Square(new Location(i,j));
				chessBoard.add(squares[i][j].getSquare());

			}
		}
		
		createPieces();
		deployPieces();
		chessBoard.repaint();

	}
	
	private void createPieces()
	{
		whitePiece=new Vector<ChessPiece>();
		blackPiece=new Vector<ChessPiece>();
		for (int i=0; i<8; i++)
		{
			whitePiece.add(new Pawn(Team.WHITE));
			blackPiece.add(new Pawn(Team.BLACK));
		}
		for (int i=0; i<2;i++)
		{
			whitePiece.add(new Rook(Team.WHITE));
			blackPiece.add(new Rook(Team.BLACK));
			whitePiece.add(new Knight(Team.WHITE));
			blackPiece.add(new Knight(Team.BLACK));
			whitePiece.add(new Bishop(Team.WHITE));
			blackPiece.add(new Bishop(Team.BLACK));
		}
		whitePiece.add(new Queen(Team.WHITE));
		blackPiece.add(new Queen(Team.BLACK));
		whitePiece.add(new King(Team.WHITE));
		blackPiece.add(new King(Team.BLACK));
	}
	
	private void deployPieces()
	{
		int row0black,row0white,row1black,row1white;
		
		row0black=0;
		row0white=7;
		row1black=1;
		row1white=6;		
		
		
		for (int i=0; i<8; i++)
		{
			squares[row1white][i].addPiece(whitePiece.get(i));
			squares[row1black][i].addPiece(blackPiece.get(i));
		}
		for (int i=8; i<11; i++)
		{
			squares[row0black][i-8].addPiece(blackPiece.get(i));
			squares[row0white][i-8].addPiece(whitePiece.get(i));
		}
		int j=7;
		for (int i=11; i<14; i++)
		{
			squares[row0black][j].addPiece(blackPiece.get(i));
			squares[row0white][j].addPiece(whitePiece.get(i));
			j--;
		}
		for (int i=14; i<16; i++)
		{
			squares[row0black][i-11].addPiece(blackPiece.get(i));
			squares[row0white][i-11].addPiece(whitePiece.get(i));
		}
	}
	
	public static ChessBoard getInstance(boolean side)
	{
		if (chess== null)
		{
			chess= new ChessBoard(side);
			chess.singlePC=false;
		}
		return chess;
	}

	public static ChessBoard getInstance()
	{
		if (chess==null)
		{
			chess= new ChessBoard(Team.WHITE);
			chess.singlePC=true;
		}
		return chess;
	}
	
	
	public JPanel getBoard()
	{
		return this.chessBoard;
	}
	
	public Square getSelected()
	{
		return this.selected;
	}
	
	public boolean currentMove()
	{
		return currMove;
	}

	public boolean isTeam()
	{
		if (currMove==team || singlePC)
			return true;
		else 
			return false;
	}
	
	public boolean isSinglePC()
	{
		return singlePC;
	}
	
	public void toggleMove()
	{
		currMove= !currMove;
	}
	
	public void deselect()
	{
		if (selected!=null)
			selected.deselect();
		this.selected=null;
	}
	public void select(Square tobeSelected)
	{
		deselect();
		this.selected= tobeSelected;
		tobeSelected.select();
	}
	

// this function returns 0 if move is invalid
// this function returns 1 if move is successfull
// this function returns -1 if move is invalid because we have clicked on our own Piece
	public int move(Square destination)
	{
		if(selected.getPiece().validateMove(destination.getLocation()))
		{
// We will make a dummy move, then check if KingIsOnCheck and then rollback the move
			Location loc= selected.getLocation();
			ChessPiece moveBack= selected.getPiece();
			ChessPiece rollBack= selected.getPiece().dummyMove(destination.getLocation());
			if ( ChessPiece.isOnCheck())
			{
				selected.rollBackMove(loc, rollBack, moveBack);
				return 0;
			}
			selected.rollBackMove(loc, rollBack, moveBack);
			if (selected.getPiece().makeMove(destination.getLocation()))
				return 1;
			else 
				return 0;
		}
		else 
		{
			if (destination.getPiece() !=null )
				if(destination.getPiece().getSide()== selected.getPiece().getSide())
					return -1;
				else
					return 0;
			else
				return 0;
		}
	}
	

	public ChessPiece promote(Pawn q)
	{
		int i;
		if (q.getSide()==Team.WHITE)
		{
			i=whitePiece.indexOf(q);
			whitePiece.set(i, new Queen(Team.WHITE));
			return whitePiece.get(i);
		}
		else
		{
			i=blackPiece.indexOf(q);
			blackPiece.set(i, new Queen(Team.BLACK));
			return blackPiece.get(i);
		}
	}
	

	public void makeMove( Location initial, Location dest)
	{
		chess.squares[initial.getX()][initial.getY()].getPiece().makeMove(dest);
		chess.toggleMove();
		chess.squares[initial.getX()][initial.getY()].getSquare().repaint();
		chess.squares[dest.getX()][dest.getY()].getSquare().repaint();
	}

	public void connect(boolean status)
	{
		if (status == true)
		{
			try
			{
				Registry registry= LocateRegistry.getRegistry("localhost",1990);
				ChessBoard.clientStub=(RemoteBoard)registry.lookup("CLIENTBOARD");
			}
			catch (Exception e) {
					System.out.println("Error connecting to Client ");
					System.out.println();
					System.err.println("Server exception: " + e.toString());
            		e.printStackTrace();
        		}
		}
	}
	

}
