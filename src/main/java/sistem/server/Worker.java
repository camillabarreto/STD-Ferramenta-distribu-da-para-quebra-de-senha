package sistem.server;
import sistem.DistributedService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
            if (args[0] != null){
                nomeServidor = args[0];
            }
            if (args[1] != null){
                porta = Integer.parseInt(args[1]);
            }
            System.out.println("Conectando no servidor "+ nomeServidor);

            // Obtendo referencia do servi¸co de registro
            Registry registro = LocateRegistry.getRegistry(nomeServidor, porta);

            // Procurando pelo objeto distribu´ıdo registrado previamente com o NOMEOBJDIST
            DistributedService stub = (DistributedService) registro.lookup(NOMEOBJDIST);

            // Invocando m´etodos do objeto distribu´ıdo
            System.out.println("Chamando sendFile() : " + stub.sendFile());

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
