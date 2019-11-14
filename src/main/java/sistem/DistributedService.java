package sistem;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedService extends Remote{
    public void sendWork() throws RemoteException;
    public void stopWork() throws RemoteException;
    public void sendFile() throws RemoteException;
    public String getName() throws RemoteException;
    public boolean isWorkingStatus() throws RemoteException;
}