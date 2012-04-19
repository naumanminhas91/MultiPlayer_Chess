import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class ClientGame {
	JFrame GUI;
	JPanel mainBoard,noticeBoard;
	ChessBoard chess;
	JTextField host,port;
	
	public ClientGame()
	{
		host = new JTextField();
		port = new JTextField(5);
		
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Address :"));
		myPanel.add(host);
		myPanel.add(new JLabel("Port :"));
		myPanel.add(port);

	    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Please Enter Address of Host", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) 
	    {
	    	chess=ChessBoard.getInstance(Team.BLACK);
	    	connectServer();

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
	}
	
	private void connectServer()
	{
		try{
			RemoteBoard serverStub=(RemoteBoard)UnicastRemoteObject.exportObject(chess, 0);
			Registry registry= LocateRegistry.getRegistry(host.getText(), Integer.parseInt(port.getText()));
			registry.rebind("CLIENTBOARD",serverStub);
			System.err.println("Client connected");	
			ChessBoard.clientStub=(RemoteBoard) registry.lookup("SERVERBOARD");
			ChessBoard.clientStub.connect(true);
		}
		catch (Exception e) 
		{
			System.out.println("Error Connecting to server, Check your HOST ADDRESS AND RETRY ");
			System.out.println();
			System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}
}