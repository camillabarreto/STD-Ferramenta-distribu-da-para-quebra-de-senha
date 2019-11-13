package sistem.master;

import sistem.DistributedNotification;
import sistem.DistributedService;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


//conhecimento de quantos workers estão online e seus respectivos status
//enviar tarefas e arquivos para os workers
//notificar encerramento de tarefa para um ou mais workers que estão em status: trabalhando

public class Master {

    private static final String NAMEMASTER = "Master";
    private static String nomeServidor = "127.0.0.1";
    private static int PORT = 12345;
    static ArrayList<DistributedService> workers = new ArrayList<>();
    static Registry registro;


    public static void main(String[] args) {
        try {
            if (args[0] != null){
                nomeServidor = args[0];
            }
            if (args[1] != null){
                PORT = Integer.parseInt(args[1]);
            }

            // CRIANDO SERVIÇO DE REGISTRO
            registro = LocateRegistry.createRegistry(PORT);

            /*
             *   CRIAR E REGISTRAR O OBJETO DISTRIBUIDO NOTIFICATION
             * */

            // CRIANDO OBJETO DISTRIBUIDO
            Notification n = new Notification();

            // DEFININDO O HOSTNAME DO SERVIDOR
            System.setProperty("java.rmi.worker.hostname", nomeServidor);
            DistributedNotification stub_n = (DistributedNotification)
                    UnicastRemoteObject.exportObject(n, 0);

            // REGISTRO DO OBJETO DISTRIBUIDO
            registro.bind(NAMEMASTER, stub_n);

            System.out.println("Servidor de NOTIFICAÇÃO pronto!\n");
            System.out.println("Pressione CTRL + C para encerrar...");

            //==============================================================================
            // Vou esperar algum trabalhador informar status: espera
            // quando informar devo buscar o objeto que ele disponibilizou
            // será solicitado para esse objeto a quebra de senha

            System.out.println("Digite '1'");
            Scanner teclado = new Scanner(System.in);
            if(teclado.nextInt() == 1){
                System.out.println(workers.get(0).sendFile());
            }

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
