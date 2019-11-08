package sistem.master;
import sistem.DistributedNotification;
import java.rmi.RemoteException;


public class Notification implements DistributedNotification{
    private static int i = 0;

    @Override
    public String activated() throws RemoteException {
        return "    ativando....";
    }

    @Override
    public int workFinished() throws RemoteException {
        return i++;
    }
}
