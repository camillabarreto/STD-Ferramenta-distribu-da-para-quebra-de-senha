package sistem.worker;
import sistem.DistributedNotification;
import sistem.DistributedService;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Worker {

    private static final String MASTERNAME = "Master";
    private static String SERVER = "127.0.0.1";
    private static int PORT = 12346;
    private static Registry registro = null;
    static String WORKERNAME;
    static DistributedNotification masterStub = null;
    static DistributedService workerStub = null;
    static Thread passwordBreaker = null;
    static Process process = null;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {
        if (args[0] != null) SERVER = args[0];
        if (args[1] != null) PORT = Integer.parseInt(args[1]);
        searchRegistryService();
        offerDistributedObject();
        System.out.println("Servidor de QUEBRA DE SENHA pronto!\n");
        findMaster(); //erro ocasional
    }

    private static void searchRegistryService() throws RemoteException {
        registro = LocateRegistry.getRegistry(SERVER, PORT);
    }

    private static void offerDistributedObject() throws RemoteException, AlreadyBoundException {
        //SOLICITANDO NOME DO SERVIÇO
        Scanner teclado = new Scanner(System.in);
        System.out.println("Informe nome do worker: ");
        WORKERNAME = teclado.nextLine();

        // CRIANDO OBJETO DISTRIBUIDO
        Service s = new Service(WORKERNAME);
        System.setProperty("java.rmi.worker.hostname", SERVER);
        workerStub = (DistributedService) UnicastRemoteObject.exportObject(s, 0);

        // REGISTRANDO OBJETO DISTRIBUIDO
        registro.bind(WORKERNAME, workerStub);
    }

    private static void findMaster() throws RemoteException, NotBoundException {
        // PROCURANDO OBJETO DISTRIBUIDO
        masterStub = (DistributedNotification) registro.lookup(MASTERNAME);
        masterStub.activate(WORKERNAME);
    }

}