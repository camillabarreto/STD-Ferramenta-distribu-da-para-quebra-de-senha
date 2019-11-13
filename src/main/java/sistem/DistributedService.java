package sistem;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedService extends Remote{
    //public boolean isWorkingStatus();
    public void sendWork(int i) throws RemoteException;
    public void stopWork() throws RemoteException;
    public String sendFile() throws RemoteException;
}
