package sistem.worker;
import sistem.DistributedService;
import java.rmi.RemoteException;

public class Service implements DistributedService{
    private String workingStatus = "em espera";
    private String serviceName;

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

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

    @Override
    public String getName() throws RemoteException {
        return serviceName;
    }

    @Override
    public String getStatus() throws RemoteException {
        return workingStatus;
    }
}