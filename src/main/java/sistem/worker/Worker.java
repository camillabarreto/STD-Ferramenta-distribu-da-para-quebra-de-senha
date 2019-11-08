package sistem.worker;
import sistem.DistributedNotification;
import sistem.DistributedService;
import sistem.master.Notification;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Timestamp;
import java.sql.SQLOutput;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Scanner;
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
    private static int porta_n = 12345;
    private static int porta_s;
    private static final String NOMEOBJDIST = "MeuServiço";

    public static void main(String[] args) {
        try {
            // recebendo nome do servidor por argumento de linha de comando
            if (args[0] != null){
                nomeServidor = args[0];
            }

            // recebendo porta do rmiregistry por argumento de linha de comando
            if (args[1] != null){
                porta_n = Integer.parseInt(args[1]);
            }

            // BUSCANDO REFERENCIA DO SERVIÇO DE REGISTRO
            Registry registro_n = LocateRegistry.getRegistry(nomeServidor, porta_n);

            // PROCURANDO OBJETO DE NOTIFICAÇÃO DISTRIBUIDO
            DistributedNotification stub = (DistributedNotification) registro_n.lookup(NOMEOBJDIST);

            System.out.println("chamando metodo para ativar após acordar:");

            Thread.sleep(10000);

            System.out.println("acordei....");

            System.out.println(stub.activated());
            System.out.println(stub.workFinished());

            //==============================================================================
            // Aqui eu vou criar o objeto distribuido para realizar a quebra de senha
            // e aguardar solicitações de trabalho

            Scanner teclado = new Scanner(System.in);
            System.out.println("Informe a porta: ");
            porta_s = teclado.nextInt();

            // CRIANDO OBJETO DISTRIBUIDO PARA NOTIFICAÇÃO
            Service s = new Service();

            // DEFININDO O HOSTNAME DO SERVIDOR
            System.setProperty("java.rmi.worker.hostname", nomeServidor);
            DistributedService stub_s = (DistributedService)
                    UnicastRemoteObject.exportObject(s, 0);

            // CRIANDO SERVIÇO DE REGISTRO
            Registry registro_s = LocateRegistry.createRegistry(porta_s);

            // REGISTRO DO OBJETO DISTRIBUIDO
            registro_s.bind(NOMEOBJDIST, stub_s);

            System.out.println("Servidor de QUEBRA DE SENHA pronto!\n");
            System.out.println("Pressione CTRL + C para encerrar...");


        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }


}
