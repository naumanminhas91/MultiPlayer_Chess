import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.BorderFactory;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Square implements MouseListener{
	Location location;
	boolean state;
	ChessPiece piece;
	JPanel box;
	JLabel figure;
	Color color;
	
	public Square(Location loc )
	{
		location= loc;
		if ((loc.getX()+loc.getY())%2 == 1 )
			color=Color.gray;
		else
			color=Color.white;
		state= State.FREE;
		piece= null;
		figure=null;
		box = new JPanel();
		box.addMouseListener(this);
		box.setSize(60, 60);
		box.setBackground(color);
	}
	
	
	public ChessPiece getPiece()
	{
		return piece;
	}
	
	public JPanel getSquare()
	{
		return box;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public boolean getState()
	{
		return state;
	}
	
	public void setState(boolean st )
	{
		state= st;
	}

	public void addPiece(ChessPiece pie)
	{
		state= State.CAPTURED;
		piece=pie;
		piece.setLocation(location);
		if (figure!= null)
			figure.setIcon(piece.getImage());
		else
			figure= new JLabel(piece.getImage());
		box.add(figure);
	}
	public void dummyAdd(ChessPiece pie)
	{
		state= State.CAPTURED;
		piece=pie;
		piece.setLocation(location);
		if (figure!= null)
			figure.setIcon(piece.getImage());
		else
			figure= new JLabel(piece.getImage());
	}
	
	public void removePiece()
	{
		state= State.FREE;
		piece=null;
		figure=null;
		box.removeAll();
	}
	
	public void dummyRemove()
	{
		state= State.FREE;
		piece=null;
		figure=null;
	}
	

	public void rollBackMove(Location dest, ChessPiece rollBack, ChessPiece moveBack)
	{
		ChessBoard chess= ChessBoard.getInstance();
		
		Location loc= moveBack.getLocation();
		int h=loc.getX();
		int w=loc.getY();
		
		chess.squares[h][w].removePiece();
		int hD=dest.getX();
		int wD=dest.getY();
		chess.squares[hD][wD].addPiece(moveBack);
		
		if(rollBack != null)
		{
			chess.squares[h][w].addPiece(rollBack);
			rollBack.setLocation(loc);
			rollBack.freed();
		}	
	}
	

	public void select()
	{
		box.setBorder(BorderFactory.createLineBorder(Color.red, 4));
		box.repaint();
	}
	
	public void deselect()
	{
		box.setBorder(null);
		box.repaint();
	}
	

	public void mouseEntered(MouseEvent e){
	}
	public void mouseExited(MouseEvent e){
	}
	public void mousePressed(MouseEvent e){
	}
	public void mouseReleased(MouseEvent e){
	}
	
	public void mouseClicked(MouseEvent e){
		ChessBoard chess=ChessBoard.getInstance();
		if(chess.isTeam())
		{
			if (chess.getSelected()==null)
			{
				if (this.piece!=null)
				{
					if(this.piece.getSide() == chess.currentMove() )
						chess.select(this);
				}
			}
			else
			{
				int i=chess.move(this);
				if(i==1)
				{
					if(chess.isSinglePC()==false)
					{
						try
						{
							ChessBoard.clientStub.makeMove(chess.selected.getLocation(), this.getLocation());
						}
						catch(Exception exp)
						{
							exp.printStackTrace();	
						}
					}				
					chess.deselect();
					chess.toggleMove();
				}
				else if(i==-1)
				{
					chess.select(this);
				}
			
			}
		}
		
	}
}
