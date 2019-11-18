package sistem.master;
import sistem.DistributedNotification;
import sistem.DistributedService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Notification  implements DistributedNotification{

    @Override
    public void activate(String WORKERNAME) throws RemoteException, NotBoundException {
        //ADICIONANDO A LISTA DE WORKERS
        Master.workers.add((DistributedService) Master.registro.lookup(WORKERNAME));
    }

    @Override
    public void workFinished(String WORKERNAME) {
        System.out.println("\n" + WORKERNAME + " finalizou a quebra de senha");
    }
}