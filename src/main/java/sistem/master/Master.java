package sistem.master;
import sistem.DistributedNotification;
import sistem.DistributedService;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Master {

    private static final String NAMEMASTER = "Master";
    private static String SERVER = "127.0.0.1";
    private static int PORT = 12346;
    static ArrayList<DistributedService> workers = new ArrayList<>();
    static Registry registro = null;


    public static void main(String[] args) throws IOException, AlreadyBoundException {
        if (args[0] != null) SERVER = args[0];
        if (args[1] != null) PORT = Integer.parseInt(args[1]);
        createRegistryService();
        offerDistributedObject();
        System.out.println("Servidor de NOTIFICAÇÃO pronto!\n");
        System.out.println("Pressione CTRL + C para encerrar...");
        userInterface();
    }

    private static void createRegistryService() throws RemoteException {
        while (registro == null){
            registro = LocateRegistry.createRegistry(PORT);
        }
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

    private static void userInterface() throws IOException {
        Scanner teclado = new Scanner(System.in);
        while (true) {
            System.out.println("(1) Para saber quantos processos estão online e seus respectivos status");
            System.out.println("(2) Para enviar tarefas ou arquivos");
            System.out.println("(3) Para solicitar o encerramento de tarefas");
            switch (teclado.nextInt()) {
                case 1:
                    //percorrer workers e informar seus status
                    System.out.println("Workers online : " + workers.size());
                    System.out.println(workersOnline());
                    break;
                case 2:
                    System.out.println("Processos em espera : ");
                    System.out.println(isWorking(false));
                    System.out.println("Digite o nome do processo que deseja enviar a tarefa");
                    System.out.println("Digite 'all' para enviar tarefa para todos os processos em espera");
                    sendWorks(teclado.next());
                    break;
                case 3:
                    System.out.println("Processos trabalhando: ");
                    System.out.println(isWorking(true));
                    System.out.println("Digite o nome do processo que deseja finalizar a tarefa");
                    System.out.println("Digite 'all' para finalizar a tarefa de todos os processos");
                    stopWorks(teclado.next());
                    break;

                default:
                    System.out.println("Operação inválida");
            }
            System.out.println("\n------------------------------------------------------------------------\n");
        }
    }

    private static String workersOnline() throws RemoteException {
        StringBuilder s = new StringBuilder();
        for(DistributedService d : workers){
            s.append(d.getName() + " : ");
            if(d.isWorkingStatus()) s.append("trabalhando\n");
            else s.append("em espera\n");
        }
        return s.toString();
    }

    private static String isWorking(boolean op) throws RemoteException {
        StringBuilder s = new StringBuilder();
        for(DistributedService d : workers){
            if(d.isWorkingStatus() == op){
                s.append(d.getName()+"\n");
            }
        }
        return s.toString();
    }

    private static void sendWorks(String op) throws IOException {
        if(op.equals("all")){
            for(DistributedService d : workers){
                if(!d.isWorkingStatus()) {
                    //StringBuilder s = new StringBuilder("teste all");
                    //d.sendFile(s);
                    d.sendWork();
                }
            }

        }else{
            for(DistributedService d : workers){
                if(!d.isWorkingStatus() && d.getName().equals(op)){
                    //StringBuilder s = new StringBuilder("teste w");
                    //d.sendFile(s);
                    d.sendWork();
                }
            }
        }
    }

    private static void stopWorks(String op) throws RemoteException {
        if(op.equals("all")){
            for(DistributedService d : workers){
                if(d.isWorkingStatus()){
                    d.stopWork();
                }
            }
        }else{
            for(DistributedService d : workers){
                if(d.isWorkingStatus() && d.getName().equals(op)){
                    d.stopWork();
                }
            }
        }
    }

}