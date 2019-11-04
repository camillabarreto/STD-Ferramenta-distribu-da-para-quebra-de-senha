package sistem.client;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Master {

    //conhecimento de quantos workers estão online e seus respectivos status
    //enviar tarefas e arquivos para os workers
    //notificar encerramento de tarefa para um ou mais workers que estão em status: trabalhando

}
