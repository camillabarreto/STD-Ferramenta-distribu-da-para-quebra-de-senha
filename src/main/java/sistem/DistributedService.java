package sistem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedService extends Remote{
    public void sendWork() throws RemoteException;
    public void stopWork() throws RemoteException, InterruptedException;
    public void sendFile(StringBuilder s) throws IOException;
    public String getName() throws RemoteException;
    public boolean isWorkingStatus() throws RemoteException;
}