import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ServerGame {
	JFrame GUI;
	JPanel mainBoard,noticeBoard;
	ChessBoard chess;
	
	public ServerGame()
	{
		chess=ChessBoard.getInstance(Team.WHITE);
			
		JOptionPane.showMessageDialog(null, "Waiting for Client to Connect");
		makeServer();
		
		GUI= new JFrame("CHESS !");
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setLayout(new BorderLayout());
		GUI.setSize(700,650);
		
		mainBoard= chess.getBoard();
		mainBoard.setSize(600,600);
		GUI.add(mainBoard,BorderLayout.CENTER );
		
		noticeBoard= Notice.getInstance().getNoticeBoard();
		noticeBoard.setSize(50,600);
		GUI.add(noticeBoard, BorderLayout.SOUTH);
		
		GUI.setVisible(true);
		
	}
	
	private void makeServer()
	{
		try{
			RemoteBoard serverStub=(RemoteBoard)UnicastRemoteObject.exportObject(chess, 0);
			Registry registry= LocateRegistry.createRegistry(1990);
			registry.rebind("SERVERBOARD",serverStub);
			System.err.println("Server ready");			
		}
		catch (RemoteException e) {
            		System.err.println("Server exception: " + e.toString());
            		e.printStackTrace();
        	}
	}


}
