package sistem.worker;
import sistem.DistributedService;
import java.rmi.RemoteException;

public class Service implements DistributedService{
    boolean workingStatus = false;

//    @Override
//    public boolean isWorkingStatus() {
//        return workingStatus;
//    }

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
        return "retornando...";
    }
}