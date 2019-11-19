package sistem.master;
import sistem.DistributedNotification;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Notification  implements DistributedNotification {

    private Master master;

    public Notification(Master m) {
        this.master = m;
    }

    @Override
    public void activate(String WORKERNAME) throws RemoteException, NotBoundException {
        //ADICIONANDO A LISTA DE WORKERS
        this.master.add(WORKERNAME);
    }

    @Override
    public void workFinished(String WORKERNAME) {
        System.out.println("\n" + WORKERNAME + " finalizou a quebra de senha");
    }
}