import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteBoard extends Remote{
	void makeMove( Location initial, Location destination ) throws RemoteException;
	void connect(boolean state) throws RemoteException;
	
}