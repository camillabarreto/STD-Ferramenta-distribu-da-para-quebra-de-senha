package sistem;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedService extends Remote{
    void sendWork(StringBuilder stringBuilder, String op) throws IOException;
    void stopWork() throws RemoteException;
    void sendDictionary(StringBuilder stringBuilder) throws IOException;
    String getName() throws RemoteException;
    String getOperationMode() throws RemoteException;
    boolean isWorkingStatus() throws RemoteException;
}