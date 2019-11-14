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

    private static String SERVER = "127.0.0.1";
    private static int PORT = 12345;
    static String WORKERNAME;
    private static final String MASTERNAME = "Master";

    public static void main(String[] args) {
        try {
            // recebendo nome do servidor por argumento de linha de comando
            if (args[0] != null){
                SERVER = args[0];
            }

            // recebendo PORT do rmiregistry por argumento de linha de comando
            if (args[1] != null){
                PORT = Integer.parseInt(args[1]);
            }

            /*
             *   CRIAR E REGISTRAR O OBJETO DISTRIBUIDO SERVICE
             * */

            // BUSCANDO REFERENCIA DO SERVIÇO DE REGISTRO
            Registry registro = LocateRegistry.getRegistry(SERVER, PORT);

            //SOLICITANDO NOME DO SERVIÇO
            Scanner teclado = new Scanner(System.in);
            System.out.println("Informe nome do worker: ");
            WORKERNAME = teclado.nextLine();
            //System.out.println(SERVER);
            // System.out.println(PORT);
            // CRIANDO OBJETO DISTRIBUIDO
            Service s = new Service(WORKERNAME);

            // DEFININDO O HOSTNAME DO SERVIDOR
            System.setProperty("java.rmi.worker.hostname", SERVER);
            DistributedService stub_s = null;
            while(stub_s == null){
                stub_s = (DistributedService)
                        UnicastRemoteObject.exportObject(s, 0);
            }

            // REGISTRANDO OBJETO DISTRIBUIDO
            registro.bind(WORKERNAME, stub_s);

            System.out.println("Servidor de QUEBRA DE SENHA pronto!\n");

            /*
             *   BUSCAR O OBJETO DISTRIBUIDO NOTIFICATION DO MASTER
             * */

            // PROCURANDO OBJETO DISTRIBUIDO
            DistributedNotification stub = null;
            while(stub == null){
                stub = (DistributedNotification) registro.lookup(MASTERNAME);
            }


            /*
             *   INFORMAR AO MASTER O NOME DO SERVICE
             * */

            //as vezes da erro nessa linha
            System.out.println(stub.activate(WORKERNAME));
            //erro:
//            nov 13, 2019 2:41:24 PM sistem.worker.Worker main
//            GRAVE: null
//            java.rmi.NoSuchObjectException: no such object in table
//            at sun.rmi.transport.StreamRemoteCall.exceptionReceivedFromServer(StreamRemoteCall.java:283)
//            at sun.rmi.transport.StreamRemoteCall.executeCall(StreamRemoteCall.java:260)
//            at sun.rmi.server.UnicastRef.invoke(UnicastRef.java:161)
//            at java.rmi.server.RemoteObjectInvocationHandler.invokeRemoteMethod(RemoteObjectInvocationHandler.java:227)
//            at java.rmi.server.RemoteObjectInvocationHandler.invoke(RemoteObjectInvocationHandler.java:179)
//            at com.sun.proxy.$Proxy1.activate(Unknown Source)
//            at sistem.worker.Worker.main(Worker.java:81)

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

}