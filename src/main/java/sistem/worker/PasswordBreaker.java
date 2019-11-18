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
        processBuilder.command("john","senha_"+Worker.WORKERNAME+".txt", "--session="+Worker.WORKERNAME);
        try {
            Worker.process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(Worker.process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = Worker.process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

            processBuilder.command("john","--show","senha_"+Worker.WORKERNAME+".txt");

            Worker.process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(Worker.process.getInputStream()));

            line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            exitCode = Worker.process.waitFor();
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
