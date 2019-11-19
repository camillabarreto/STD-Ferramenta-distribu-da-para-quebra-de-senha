package sistem.master;

import sistem.DistributedNotification;
import sistem.DistributedService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

public class Master {

    private final String NAMEMASTER = "Master";
    private String SERVER = "127.0.0.1";
    private int PORT = 12346;
    private ArrayList<DistributedService> workers = new ArrayList<>();
    private Registry registro = null;


    public static void main(String[] args) throws IOException, AlreadyBoundException, InterruptedException {
        Master master = new Master();
        if (args[0] != null) master.SERVER = args[0];
        if (args[1] != null) master.PORT = Integer.parseInt(args[1]);
        master.createRegistryService();
        master.offerDistributedObject(master);
        System.out.println("Servidor de NOTIFICAÇÃO pronto!\n");
        System.out.println("Pressione CTRL + C para encerrar...");
        master.userInterface();
    }

    public void add(String service) throws RemoteException, NotBoundException {
        workers.add((DistributedService) registro.lookup(service));
    }

    private void createRegistryService() throws RemoteException {
        registro = LocateRegistry.createRegistry(PORT);
    }

    private void offerDistributedObject(Master master) throws RemoteException, AlreadyBoundException {
        // CRIANDO OBJETO DISTRIBUIDO
        Notification n = new Notification(master);

        // DEFININDO O HOSTNAME DO SERVIDOR
        System.setProperty("java.rmi.worker.hostname", SERVER);
        DistributedNotification stub_n = (DistributedNotification) UnicastRemoteObject.exportObject(n, 0);

        // REGISTRO DO OBJETO DISTRIBUIDO
        registro.bind(NAMEMASTER, stub_n);
    }

    private void userInterface() throws IOException, InterruptedException {
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
                    System.out.println("(2) Incremental: Min = 0 caracteres, Max = 5 caracteres");
                    System.out.println("(3) Incremental: Min = 6 caracteres, Max = 6 caracteres");
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
                            System.out.println("(2) Incremental: Min = 0 caracteres, Max = 5 caracteres");
                            System.out.println("(3) Incremental: Min = 6 caracteres, Max = 6 caracteres");
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

    private String workersOnline() throws RemoteException {
        StringBuilder s = new StringBuilder();
        for(DistributedService d : workers){
            s.append(d.getName() + " : ");
            if(d.isWorkingStatus()) s.append("trabalhando\n");
            else s.append("em espera\n");
        }
        return s.toString();
    }

    private String isWorking(boolean op) throws RemoteException {
        StringBuilder s = new StringBuilder();
        for(DistributedService d : workers){
            if(d.isWorkingStatus() == op){
                s.append(d.getName()+"\n");
            }
        }
        return s.toString();
    }

    private void sendWork(String process, String passwordPath, String op) throws IOException {
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

    private void stopWork(String op) throws RemoteException, InterruptedException {
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

    private void sendDictionaryFile(String process, String dictionaryPath) throws IOException {
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