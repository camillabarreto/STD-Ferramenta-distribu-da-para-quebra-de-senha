package sistem.worker;
import java.io.IOException;
import org.omg.SendingContext.RunTime;

public class PasswordBreaker extends Thread {
    @Override
    public void run() {
        super.run();

        Runtime run = Runtime.getRuntime();
        try {
            run.exec("mkdir /home/camilla/"+Worker.WORKERNAME);
        } catch (IOException e) {
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
}
