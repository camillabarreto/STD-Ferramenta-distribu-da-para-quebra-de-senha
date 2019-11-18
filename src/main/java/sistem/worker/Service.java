package sistem.worker;
import sistem.DistributedService;
import java.io.*;

public class Service implements DistributedService{
    private boolean workingStatus = false;
    private String serviceName;
    private String operationMode;  //   (1)dicionário, (2)incremental

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void sendWork(StringBuilder stringBuilder, String op) throws IOException {
        this.operationMode = op;

        System.out.println("......recebendo arquivo de senhas");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Worker.WORKERNAME+"_senhas.txt"));
        bufferedWriter.append(stringBuilder.toString());
        bufferedWriter.close();
        System.out.println("......OK");

        //DISPARANDO UMA THREAD PARA QUEBRA DE SENHA
        Worker.passwordBreaker = new PasswordBreaker();
        Worker.passwordBreaker.start();

        workingStatus = true;
    }

    @Override
    public void stopWork() {
        Worker.passwordBreaker.interrupt();
        Worker.passwordBreaker.stop();
        workingStatus = false;
    }

    @Override
    public void sendDictionary(StringBuilder stringBuilder) throws IOException {
        System.out.println("......recebendo dicionario");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Worker.WORKERNAME+"_dic.txt"));
        bufferedWriter.append(stringBuilder.toString());
        bufferedWriter.close();
        System.out.println("......OK");
    }

    @Override
    public String getName() {
        return serviceName;
    }

    @Override
    public boolean isWorkingStatus() {
        return workingStatus;
    }

    public String getOperationMode() {
        return operationMode;
    }
}