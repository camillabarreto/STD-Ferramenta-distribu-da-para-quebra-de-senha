package sistem;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedService extends Remote{
    void sendWork(StringBuilder stringBuilder) throws IOException;
    void stopWork() throws RemoteException;
    void sendFile(StringBuilder stringBuilder) throws IOException;
    String getName() throws RemoteException;
    boolean isWorkingStatus() throws RemoteException;
}