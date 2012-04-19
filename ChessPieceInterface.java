
public interface ChessPieceInterface {
	boolean validateMove( Location destination );
	boolean makeMove( Location destination );
	ChessPiece kill(ChessPiece a);

}
