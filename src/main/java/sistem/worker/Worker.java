package sistem.worker;
import sistem.DistributedNotification;
import sistem.DistributedService;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
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
    private static String WORKERNAME;
    private static final String MASTERNAME = "Master";

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

            /*
            *   CRIAR E REGISTRAR O OBJETO DISTRIBUIDO SERVICE
            * */

            // BUSCANDO REFERENCIA DO SERVIÇO DE REGISTRO
            Registry registro = LocateRegistry.getRegistry(nomeServidor, porta);

            //SOLICITANDO NOME DO SERVIÇO
            Scanner teclado = new Scanner(System.in);
            System.out.println("Informe nome do worker: ");
            WORKERNAME = teclado.nextLine();
            //System.out.println(nomeServidor);
           // System.out.println(porta);
            // CRIANDO OBJETO DISTRIBUIDO
            Service s = new Service();

            // DEFININDO O HOSTNAME DO SERVIDOR
            System.setProperty("java.rmi.worker.hostname", nomeServidor);
            DistributedService stub_s = (DistributedService)
                    UnicastRemoteObject.exportObject(s, 0);

            // REGISTRANDO OBJETO DISTRIBUIDO
            registro.bind(WORKERNAME, stub_s);

            System.out.println("Servidor de QUEBRA DE SENHA pronto!\n");

            /*
             *   BUSCAR O OBJETO DISTRIBUIDO NOTIFICATION DO MASTER
             * */

            // PROCURANDO OBJETO DISTRIBUIDO

            DistributedNotification stub = (DistributedNotification) registro.lookup(MASTERNAME);

            /*
            *   INFORMAR AO MASTER O NOME DO SERVICE
            * */

            System.out.println(stub.activate(WORKERNAME));


        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }


}
