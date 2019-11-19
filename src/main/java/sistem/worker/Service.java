package sistem.worker;

import sistem.DistributedService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Service implements DistributedService{
    private boolean workingStatus = false;
    private String serviceName;
    private String operationMode;
    /*
     * operationMode = 1 -> Dicionário
     * operationMode = 2 -> Incremental: Min = 0 caracteres, Max = 5 caracteres,
     * operationMode = 3 -> Incremental: Min = 6 caracteres, Max = 6 caracteres
     * */

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void sendWork(StringBuilder stringBuilder, String op) throws IOException {
        this.operationMode = op;

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Worker.WORKERNAME+"_senhas.txt"));
        bufferedWriter.append(stringBuilder.toString());
        bufferedWriter.close();

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
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Worker.WORKERNAME+"_dic.txt"));
        bufferedWriter.append(stringBuilder.toString());
        bufferedWriter.close();
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