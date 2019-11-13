package sistem.worker;
import sistem.DistributedService;
import java.rmi.RemoteException;

public class Service implements DistributedService{
    boolean status = false;

//    public Service(boolean status) {
//        this.status = status;
//    }

    @Override
    public void submitJob(int i) throws RemoteException {
        System.out.println("i : " + i);
    }

    @Override
    public String sendFile() throws RemoteException {
        return "retornando...";
    }

    @Override
    public boolean stopNotification() throws RemoteException {
        return true;
    }
}
