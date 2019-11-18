package sistem.master;
import sistem.DistributedNotification;
import sistem.DistributedService;

import java.io.*;
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


    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {
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
        DistributedNotification stub_n = (DistributedNotification) UnicastRemoteObject.exportObject(n, 0);

        // REGISTRO DO OBJETO DISTRIBUIDO
        registro.bind(NAMEMASTER, stub_n);
    }

    private static void userInterface() throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);
        while (true) {
            System.out.println("(1) Para saber quantos processos estão online e seus respectivos status");
            System.out.println("(2) Para enviar tarefa para um processo específico");
            System.out.println("(3) Para enviar tarefa para todos os processos");
            System.out.println("(4) Para solicitar o encerramento de tarefas");
            String passwordPath, op, dictionaryPath, process, worker;
            switch (teclado.nextInt()) {
                case 1:
                    System.out.println("\nPROCESSOS ONLINE : " + workers.size());
                    System.out.println(workersOnline());

                    break;

                case 2:
                    worker = isWorking(false);
                    if(worker.length() == 0){
                        System.out.println("\nNÃO HÁ PROCESSOS EM ESPERA");
                        break;
                    }
                    System.out.println("\nPROCESSOS EM ESPERA : ");
                    System.out.println(worker);

                    System.out.println("Digite o NOME DO PROCESSO que deseja ENVIAR a tarefa");
                    process = teclado.next();

                    System.out.println("Digite o CAMINHO COMPLETO para o arquivo de SENHAS");
                    passwordPath = teclado.next();

                    System.out.println("Escolha a OPÇÃO de quebra de senha : ");
                    System.out.println("(1) Dicionário");
                    System.out.println("(2) Incremental");
                    op = teclado.next();
                    if(op.equals("1")){
                        System.out.println("Digite o CAMINHO COMPLETO para o arquivo de DICIONARIO");
                        dictionaryPath = teclado.next();
                        sendDictionaryFile(process, dictionaryPath);
                    }
                    sendWork(process, passwordPath, op);

                    break;

                case 3:
                    worker = isWorking(false);
                    if(worker.length() == 0){
                        System.out.println("\nNÃO HÁ PROCESSOS EM ESPERA");
                        break;
                    }
                    System.out.println("\nPROCESSOS EM ESPERA : ");
                    System.out.println(worker);

                    System.out.println("Digite o CAMINHO COMPLETO para o arquivo de SENHAS");
                    passwordPath = teclado.next();

                    for(DistributedService w : workers){
                        if(!w.isWorkingStatus()){
                            System.out.println("\nPROCESSO : "+w.getName());
                            System.out.println("Escolha a OPÇÃO de quebra de senha : ");
                            System.out.println("(1) Dicionário");
                            System.out.println("(2) Incremental");
                            op = teclado.next();
                            if(op.equals("1")){
                                System.out.println("Digite o CAMINHO COMPLETO para o arquivo de DICIONARIO");
                                dictionaryPath = teclado.next();
                                sendDictionaryFile(w.getName(), dictionaryPath);
                            }
                            sendWork(w.getName(), passwordPath, op);
                        }
                    }

                    break;
                case 4:
                    worker = isWorking(true);
                    if(worker.length() == 0){
                        System.out.println("\nNÃO HÁ PROCESSOS TRABALHANDO");
                        break;
                    }
                    System.out.println("\nPROCESSOS TRABALHANDO: ");
                    System.out.println(worker);

                    System.out.println("Digite o NOME DO PROCESSO que deseja FINALIZAR a tarefa");
                    System.out.println("Digite 'all' para FINALIZAR TODOS os processos");
                    stopWork(teclado.next());
                    break;
                default:
                    System.out.println("\n-- Operação inválida --");
            }
            System.out.println("------------------------------------------------------------------------\n");
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

    private static void sendWork(String process, String passwordPath, String op) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(passwordPath));
        while(bufferedReader.ready()){
            stringBuilder.append(bufferedReader.readLine()+"\n");
        }bufferedReader.close();

        for(DistributedService d : workers){
            if(d.getName().equals(process)){
                d.sendWork(stringBuilder, op);
                break;
            }
        }
    }

    private static void stopWork(String op) throws RemoteException, InterruptedException {
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

    private static void sendDictionaryFile(String process, String dictionaryPath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(dictionaryPath));
        while(bufferedReader.ready()){
            stringBuilder.append(bufferedReader.readLine()+"\n");
        }bufferedReader.close();

        for(DistributedService d : workers){
            if(d.getName().equals(process)){
                d.sendDictionary(stringBuilder);
                break;
            }
        }
    }

}