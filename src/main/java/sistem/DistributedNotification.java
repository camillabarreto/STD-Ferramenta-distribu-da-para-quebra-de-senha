package sistem;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedNotification extends Remote {
    public String activate(String NAMESERVICE) throws RemoteException, NotBoundException;
    public int workFinished() throws RemoteException;
}
