package sistem.worker;
import com.sun.corba.se.spi.orbutil.threadpool.NoSuchWorkQueueException;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import sistem.DistributedService;
import java.rmi.RemoteException;

public class Service implements DistributedService{
    boolean workingStatus = false;

    @Override
    public void sendWork(int i) throws RemoteException {
        //somente para workers que estejam em estado de espera
        System.out.println("i : " + i);
    }

    @Override
    public void stopWork() throws RemoteException {
    }

    @Override
    public String sendFile() throws RemoteException {
        return Worker.WORKERNAME + " retornando...";
    }
}