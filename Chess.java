import javax.swing.JOptionPane;

public class Chess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String choices[]= {"Single PC MultiPlayer Game","Network Based Game"};
		int response = JOptionPane.showOptionDialog(null,"Select Mode to Play :","Welcome to CHESS!",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices,null);
	
		if(response== JOptionPane.YES_OPTION)
		{
			SinglePCGame game= new SinglePCGame();
		}
		else if(response== JOptionPane.NO_OPTION)
		{
			String options[]= {"Create New Game","Join a Game"};
			int answer = JOptionPane.showOptionDialog(null,"Select Mode to Play :","Welcome to CHESS!",JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,null);
			
			if(answer==JOptionPane.YES_OPTION)
			{
				ServerGame game= new ServerGame();
			}
			else if (answer==JOptionPane.NO_OPTION)
			{
				ClientGame game= new ClientGame();
			}
		}
	}

}
