package sistem.server;
import sistem.DistributedService;

import java.rmi.RemoteException;

public class Service implements DistributedService{
    boolean status = false;

    @Override
    public void submitJob() throws RemoteException {

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
