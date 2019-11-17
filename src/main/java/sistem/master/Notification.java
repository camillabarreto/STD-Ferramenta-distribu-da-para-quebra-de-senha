package sistem.master;
import sistem.DistributedNotification;
import sistem.DistributedService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Notification  implements DistributedNotification{

    @Override
    public void activate(String WORKERNAME) throws RemoteException, NotBoundException {
        //ADICIONANDO A LISTA DE WORKERS
        DistributedService d = null;
        while(d == null){
            d = (DistributedService) Master.registro.lookup(WORKERNAME);
        }
        Master.workers.add(d);
    }

    @Override
    public void workFinished(String WORKERNAME) throws RemoteException {
    }
}