package sistem.client;
import sistem.DistributedService;
import sistem.server.Service;

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
            // recebendo nome do servidor por argumento de linha de comando
            if (args[0] != null){
                nomeServidor = args[0];
            }

            // recebendo porta do rmiregistry por argumento de linha de comando
            if (args[1] != null){
                porta = Integer.parseInt(args[1]);
            }

            // Criando
            Service s = new Service();

            // Definindo o hostname do servidor
            System.setProperty("java.rmi.server.hostname", nomeServidor);
            DistributedService stub = (DistributedService)
                                       UnicastRemoteObject.exportObject(s, 0);

            // Criando serviço de registro
            Registry registro = LocateRegistry.createRegistry(porta);

            // Registrando objeto distribuido
            registro.bind(NOMEOBJDIST, stub);
            System.out.println("Servidor pronto!\n");
            System.out.println("Pressione CTRL + C para encerrar...");

        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
