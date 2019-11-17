package sistem.worker;
import sistem.DistributedService;
import java.io.*;

public class Service implements DistributedService{
    private boolean workingStatus = false;
    private String serviceName;

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public void sendWork() {
        //DISPARANDO UMA THREAD PARA QUEBRA DE SENHA
        Worker.passwordBreaker = new PasswordBreaker();
        Worker.passwordBreaker.start();
        workingStatus = true;
    }

    @Override
    public void stopWork() throws InterruptedException {
        Worker.passwordBreaker.interrupt();
        Worker.passwordBreaker.stop();
        workingStatus = false;
    }

    @Override
    public void sendFile(StringBuilder s) throws IOException {
        BufferedWriter bfw = new BufferedWriter(new FileWriter("/home/camilla/teste_"+Worker.WORKERNAME+".txt"));
        bfw.append(s.toString());
        bfw.close();
    }

    @Override
    public String getName() {
        return serviceName;
    }

    @Override
    public boolean isWorkingStatus() {
        return workingStatus;
    }
}