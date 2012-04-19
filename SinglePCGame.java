import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class SinglePCGame {
	JFrame GUI;
	JPanel mainBoard,noticeBoard;
	
	public SinglePCGame()
	{
		GUI= new JFrame("CHESS !");
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setLayout(new BorderLayout());
		GUI.setSize(700,650);
		
		ChessBoard chess= ChessBoard.getInstance();
		mainBoard=chess.getBoard();
		GUI.add(mainBoard,BorderLayout.CENTER );
		
		noticeBoard= Notice.getInstance().getNoticeBoard();
		GUI.add(noticeBoard, BorderLayout.SOUTH);
		
		GUI.setSize(600, 650);
		GUI.setVisible(true);	
	}

}
