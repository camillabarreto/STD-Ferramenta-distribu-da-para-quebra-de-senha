package sistem.worker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PasswordBreaker extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("quebrando senha...");
        ProcessBuilder processBuilder = new ProcessBuilder();
        try {
            String operationMode = Worker.workerStub.getOperationMode();
            if(operationMode.equals("1")){
                processBuilder.command("john",Worker.WORKERNAME+"_senhas.txt", "--session="+Worker.WORKERNAME, "--wordlist:"+Worker.WORKERNAME+"_dic.txt");
            }else if (operationMode.equals("2")){
                processBuilder.command("john","-i=All5",Worker.WORKERNAME+"_senhas.txt", "--session="+Worker.WORKERNAME);
            }else if (operationMode.equals("3")){
                processBuilder.command("john","-i=All6",Worker.WORKERNAME+"_senhas.txt", "--session="+Worker.WORKERNAME);
            }else{
                System.out.println("parando...");
                Worker.masterStub.workFinished(Worker.WORKERNAME);
                Worker.workerStub.stopWork();
            }

            Worker.process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(Worker.process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = Worker.process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

            System.out.println("parando...");
            Worker.masterStub.workFinished(Worker.WORKERNAME);
            Worker.workerStub.stopWork();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        Worker.process.destroy();
    }
}
