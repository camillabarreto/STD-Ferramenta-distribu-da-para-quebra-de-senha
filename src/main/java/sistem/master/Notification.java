package sistem.master;
import sistem.DistributedNotification;
import sistem.DistributedService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Notification  implements DistributedNotification{
    private static int i = 0;

    @Override
    public String activate(String WORKERNAME) throws RemoteException, NotBoundException {
        //ADICIONANDO A LISTA DE WORKERS
//        System.out.println(WORKERNAME);
        Master.workers.add((DistributedService) Master.registro.lookup(WORKERNAME));

//        Registry registro = LocateRegistry.getRegistry("127.0.0.1", 12345);
//        DistributedNotification stub = (DistributedNotification) registro.lookup(WORKERNAME);

//        Master.recorder(WORKERNAME);
        return "    worker add....";
    }

    @Override
    public int workFinished() throws RemoteException {
        return i++;
    }
}
