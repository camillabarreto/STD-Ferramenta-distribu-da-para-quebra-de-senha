package sistem.master;
import sistem.DistributedNotification;
import java.rmi.RemoteException;


public class Notification implements DistributedNotification{
    private static int i = 0;

    @Override
    public String activated(String NAMESERVICE) throws RemoteException {
        //recebo o nome do serviço que foi subido por um worker
        //faço conexão com esse worker
        return "    ativando....";
    }

    @Override
    public int workFinished() throws RemoteException {
        return i++;
    }
}
