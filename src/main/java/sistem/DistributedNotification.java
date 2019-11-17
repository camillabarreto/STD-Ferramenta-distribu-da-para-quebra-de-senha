package sistem;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedNotification extends Remote {
    void activate(String WORKERNAME) throws RemoteException, NotBoundException;
    void workFinished(String WORKERNAME);
}