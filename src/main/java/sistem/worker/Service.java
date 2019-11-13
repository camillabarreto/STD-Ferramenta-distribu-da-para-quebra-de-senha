package sistem.worker;
import sistem.DistributedService;
import java.rmi.RemoteException;

public class Service implements DistributedService{
    private boolean workingStatus = false;
    private String serviceName;

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void sendWork() throws RemoteException {
        //somente para workers que estejam em estado de espera
        workingStatus = true;
    }

    @Override
    public void stopWork() throws RemoteException {
        workingStatus = false;
    }

    @Override
    public void sendFile() throws RemoteException {
    }

    @Override
    public String getName() throws RemoteException {
        return serviceName;
    }

    @Override
    public boolean getStatus() throws RemoteException {
        return workingStatus;
    }
}