package sistem.master;

import sistem.DistributedNotification;
import sistem.DistributedService;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


//conhecimento de quantos workers estão online
//conhecimento de seus respectivos status
//enviar tarefas e arquivos para os workers
//notificar encerramento de tarefa para um ou mais workers que estão em status: trabalhando

public class Master {

    private static final String NAMEMASTER = "Master";
    private static String nomeServidor = "127.0.0.1";
    private static int PORT = 12345;
    static ArrayList<DistributedService> workers = new ArrayList<>();
    //static HashMap<String, DistributedService> workers = new HashMap<>();
    static Registry registro;


    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
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
        System.setProperty("java.rmi.worker.hostname", nomeServidor);
        DistributedNotification stub_n = (DistributedNotification)
                UnicastRemoteObject.exportObject(n, 0);

        // REGISTRO DO OBJETO DISTRIBUIDO
        registro.bind(NAMEMASTER, stub_n);
    }

    private static void userInterface() throws RemoteException {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Scanner teclado = new Scanner(System.in);
                while(true){
                    System.out.println("(1) Para saber quantos processos estão online e seus respectivos status");
                    System.out.println("(2) Para enviar tarefas ou arquivos");
                    System.out.println("(3) Para solicitar o encerramento de tarefas");
                    switch (teclado.nextInt()){
                        case 1:
                            //percorrer workers e informar seus status
                            try {
                                System.out.println("1 : " + workers.get(0).sendFile());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            try {
                                System.out.println("2 : " + workers.get(0).sendFile());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            try {
                                System.out.println("3 : " + workers.get(0).sendFile());
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            break;

                            default:
                                System.out.println("Default");
                    }
                }
            }
        }.start();

    }

}
