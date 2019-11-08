package sistem.master;

import sistem.DistributedNotification;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


//conhecimento de quantos workers estão online e seus respectivos status
//enviar tarefas e arquivos para os workers
//notificar encerramento de tarefa para um ou mais workers que estão em status: trabalhando

public class Master {

    private static String nomeServidor = "127.0.0.1";
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuServiço";

    public static void main(String[] args) {
        try {
            if (args[0] != null){
                nomeServidor = args[0];
            }
            if (args[1] != null){
                porta = Integer.parseInt(args[1]);
            }

            // CRIANDO OBJETO DISTRIBUIDO PARA NOTIFICAÇÃO
            Notification n = new Notification();

            // DEFININDO O HOSTNAME DO SERVIDOR
            System.setProperty("java.rmi.worker.hostname", nomeServidor);
            DistributedNotification stub_n = (DistributedNotification)
                    UnicastRemoteObject.exportObject(n, 0);

            // CRIANDO SERVIÇO DE REGISTRO
            Registry registro = LocateRegistry.createRegistry(porta);

            // REGISTRO DO OBJETO DISTRIBUIDO
            registro.bind(NOMEOBJDIST, stub_n);

            System.out.println("Servidor de NOTIFICAÇÃO pronto!\n");
            System.out.println("Pressione CTRL + C para encerrar...");

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}