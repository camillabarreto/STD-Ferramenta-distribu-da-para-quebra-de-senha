package sistem;
import java.io.IOException;
import java.rmi.Remote;

/**
 * Interface compartilhada entre servidor e cliente
 */
public interface DistributedService extends Remote{
    void sendWork();
    void stopWork();
    void sendFile(StringBuilder s) throws IOException;
    String getName();
    boolean isWorkingStatus();
}