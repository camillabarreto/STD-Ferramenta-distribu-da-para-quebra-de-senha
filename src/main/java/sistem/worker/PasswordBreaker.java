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
        processBuilder.command("john","/home/camilla/senha.txt");
        try {
            Worker.process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(Worker.process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = Worker.process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //processBuilder.command("john","--show","/home/camilla/senha.txt");
        processBuilder.command("pwd");
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true){
            System.out.println("... quebrando senha");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        Worker.process.destroy();
    }
}
