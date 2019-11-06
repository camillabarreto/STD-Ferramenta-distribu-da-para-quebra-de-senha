package sistem;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedService extends Remote{
    public void submitJob() throws RemoteException;
    public String sendFile() throws RemoteException;
    public boolean stopNotification() throws RemoteException;
}
