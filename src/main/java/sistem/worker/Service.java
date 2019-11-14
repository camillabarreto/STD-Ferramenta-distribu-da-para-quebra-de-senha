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
        Worker.passwordBreaker = new PasswordBreaker();
        Worker.passwordBreaker.start();
        workingStatus = true;
    }

    @Override
    public void stopWork() throws RemoteException {
        Worker.passwordBreaker.stop();
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