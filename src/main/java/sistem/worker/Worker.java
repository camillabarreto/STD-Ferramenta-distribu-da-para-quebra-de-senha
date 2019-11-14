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

//thread para conexao
//thread para processamento
//atributo status: espera ou trabalhando
//recebe arquivos: dicionario ou senhas
//recebe comandos do master para encerrar tarefa
//notificar master quando a tarefa for concluida

public class Worker {

    private static final String MASTERNAME = "Master";
    private static String SERVER = "127.0.0.1";
    private static int PORT = 12346;
     static String WORKERNAME;
    private static Registry registro = null;
    private static DistributedNotification masterStub = null;
    private static DistributedService workerStub = null;
    static Thread passwordBreaker = null;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {
        if (args[0] != null) SERVER = args[0];
        if (args[1] != null)PORT = Integer.parseInt(args[1]);
        searchRegistryService();
        offerDistributedObject();
        System.out.println("Servidor de QUEBRA DE SENHA pronto!\n");
        findMaster();
        System.out.println(masterStub.activate(WORKERNAME));
        //erro:
//            nov 13, 2019 2:41:24 PM sistem.worker.Worker main
//            GRAVE: null
//            java.rmi.NoSuchObjectException: no such object in table
//            at sun.rmi.transport.StreamRemoteCall.exceptionReceivedFromServer(StreamRemoteCall.java:283)
//            at sun.rmi.transport.StreamRemoteCall.executeCall(StreamRemoteCall.java:260)
//            at sun.rmi.server.UnicastRef.invoke(UnicastRef.java:161)
//            at java.rmi.server.RemoteObjectInvocationHandler.invokeRemoteMethod(RemoteObjectInvocationHandler.java:227)
//            at java.rmi.server.RemoteObjectInvocationHandler.invoke(RemoteObjectInvocationHandler.java:179)
//            at com.sun.proxy.$Proxy1.activate(Unknown Source)
//            at sistem.worker.Worker.main(Worker.java:81)
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

        // DEFININDO O HOSTNAME DO SERVIDOR
        System.setProperty("java.rmi.worker.hostname", SERVER);

        while(workerStub == null){
            workerStub = (DistributedService)
                    UnicastRemoteObject.exportObject(s, 0);
        }

        // REGISTRANDO OBJETO DISTRIBUIDO
        registro.bind(WORKERNAME, workerStub);
    }

    private static void findMaster() throws RemoteException, NotBoundException {
        // PROCURANDO OBJETO DISTRIBUIDO
        while(masterStub == null){
            masterStub = (DistributedNotification) registro.lookup(MASTERNAME);
        }
    }

}