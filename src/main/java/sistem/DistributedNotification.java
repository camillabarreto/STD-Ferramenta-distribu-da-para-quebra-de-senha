package sistem;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedNotification extends Remote {
    public String activated() throws RemoteException;
    public int workFinished() throws RemoteException;
}