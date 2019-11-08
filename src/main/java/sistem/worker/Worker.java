package sistem.worker;
import sistem.DistributedNotification;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;


//thread para conexao
//thread para processamento
//atributo status: espera ou trabalhando
//recebe arquivos: dicionario ou senhas
//recebe comandos do master para encerrar tarefa
//notificar master quando a tarefa for concluida

public class Worker {

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

            // BUSCANDO REFERENCIA DO SERVIÇO DE REGISTRO
            Registry registro = LocateRegistry.getRegistry(nomeServidor, porta);

            // PROCURANDO OBJETO DE NOTIFICAÇÃO DISTRIBUIDO
            DistributedNotification stub = (DistributedNotification) registro.lookup(NOMEOBJDIST);

            System.out.println("chamando metodo para ativar após acordar:");

            Thread.sleep(10000);

            System.out.println("acordei....");

            System.out.println(stub.activated());
            System.out.println(stub.workFinished());



        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
