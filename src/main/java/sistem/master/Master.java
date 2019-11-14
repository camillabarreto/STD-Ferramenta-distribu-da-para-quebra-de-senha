package sistem.master;

import sistem.DistributedNotification;
import sistem.DistributedService;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;


//conhecimento de quantos workers estão online
//conhecimento de seus respectivos status
//enviar tarefas e arquivos para os workers
//notificar encerramento de tarefa para um ou mais workers que estão em status: trabalhando

public class Master {

    private static final String NAMEMASTER = "Master";
    private static String SERVER = "127.0.0.1";
    private static int PORT = 12345;
    static ArrayList<DistributedService> workers = new ArrayList<>();
    //static HashMap<String, DistributedService> workers = new HashMap<>();
    static Registry registro;


    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        if (args[0] != null) SERVER = args[0];
        if (args[1] != null) PORT = Integer.parseInt(args[1]);
        createRegistryService();
        offerDistributedObject();
        System.out.println("Servidor de NOTIFICAÇÃO pronto!\n");
        System.out.println("Pressione CTRL + C para encerrar...");
        userInterface();
    }

    private static void createRegistryService() throws RemoteException {
        registro = LocateRegistry.createRegistry(PORT);
    }

    private static void offerDistributedObject() throws RemoteException, AlreadyBoundException {
        // CRIANDO OBJETO DISTRIBUIDO
        Notification n = new Notification();

        // DEFININDO O HOSTNAME DO SERVIDOR
        System.setProperty("java.rmi.worker.hostname", SERVER);
        DistributedNotification stub_n = null;
        while(stub_n == null){
            stub_n = (DistributedNotification)
                    UnicastRemoteObject.exportObject(n, 0);
        }

        // REGISTRO DO OBJETO DISTRIBUIDO
        registro.bind(NAMEMASTER, stub_n);
    }

    private static void userInterface() throws RemoteException {
        Scanner teclado = new Scanner(System.in);
        while (true) {
            System.out.println("(1) Para saber quantos processos estão online e seus respectivos status");
            System.out.println("(2) Para enviar tarefas ou arquivos");
            System.out.println("(3) Para solicitar o encerramento de tarefas");
            switch (teclado.nextInt()) {
                case 1:
                    //percorrer workers e informar seus status
                    System.out.println("Workers online : " + workers.size());
                    for(DistributedService d : workers){
                        System.out.print(d.getName() + " : ");
                        if(d.getStatus()) System.out.println("trabalhando");
                        else System.out.println("em espera");
                    }
                    break;
                case 2:
                    System.out.println("Enviando tarefa para: ");
                    for(DistributedService d : workers){
                        if(!d.getStatus()){
                            System.out.println(d.getName());
                            d.sendWork();
                        }
                    }
                    break;
                case 3:
                    System.out.println("Solicitando encerramento de tarefa para: ");
                    for(DistributedService d : workers){
                        if(d.getStatus()){
                            System.out.println(d.getName());
                            d.stopWork();
                        }
                    }
                    break;

                default:
                    System.out.println("Default");
            }
            System.out.println("\n------------------------------------------------------------------------\n");
        }
    }
}